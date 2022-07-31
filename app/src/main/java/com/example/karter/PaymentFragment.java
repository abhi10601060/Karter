package com.example.karter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static  final  String REVIEW_COLLECTION = "Reviews";
    private static final String ALL_GROCERY_ITEMS_COLLECTION = "AllGroceryItems";
    private static final String ALL_USERS_COLLECTION = "Users";
    private static final String USER_CART_COLLECTION = "Cart";
    private static  final String USERS_HISTORY_COLLECTION = "OrderHistory";


    private TextView name, item_list,address , contact_details,total_amount,description_edt;
    private Button btn_place_order;
    private RadioGroup payment_rg;
    private String payment_method = null;
    private  Address incomingAddress;
    private ArrayList<CartItem> myCart;
    private double total;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment_layout,container,false);

        initViews(view);
        Bundle bundle = getArguments();
        if (bundle!= null){
             incomingAddress = bundle.getParcelable("payment_address");
            if (address!=null){

                name.setText(incomingAddress.getName());
                address.setText(incomingAddress.getAddress()+" - "+incomingAddress.getZipCode());
                contact_details.setText(incomingAddress.getContactNo()+" / "+incomingAddress.getEmail());

              myCart = getAllCartItems();


                handlePlaceOrder();
            }

        }
        return view;
    }

    private void initViews(View view) {
        name=view.findViewById(R.id.payment_fragment_name);
        address=view.findViewById(R.id.payment_fragment_address);
        contact_details=view.findViewById(R.id.payment_fragment_contact_details);
        item_list=view.findViewById(R.id.payment_fragment_item_list);
        total_amount=view.findViewById(R.id.payment_fragment_total_amount);

        btn_place_order=view.findViewById(R.id.payment_fragment_btn_place_order);

        payment_rg= view.findViewById(R.id.payment_radio_grp);
        description_edt = view.findViewById(R.id.payment_fragment_description_edt);
    }

    private void handlePlaceOrder(){
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (payment_rg.getCheckedRadioButtonId()){
                    case R.id.payment_card_btn:
                        payment_method="Card";
                        break;
                    case R.id.payment_upi_btn:
                        payment_method="UPI";
                        break;
                    case R.id.payment_net_banking_btn:
                        payment_method="Net Banking";
                        break;
                    case R.id.payment_cod_btn:
                        payment_method="Cash On Delivery";
                        break;
                    default:
                        break;
                }
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                String description =description_edt.getText().toString();
                Order order = new Order(myCart,incomingAddress,description,total,payment_method,date);

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PostOrder postOrder = retrofit.create(PostOrder.class);
                Call<Order> call = postOrder.postOrder(order);
                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (!response.isSuccessful()){
                            Bundle bundle = new Bundle();
                            bundle.putString("order_key" , "failure");

                            OrderSuccessFragment fragment = new OrderSuccessFragment();
                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.cart_activity_fragment_container,fragment);
                            transaction.commit();

                        }
                        else {

                            db.collection(ALL_USERS_COLLECTION).document(user.getUid())
                                    .collection(USERS_HISTORY_COLLECTION).add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    String id =documentReference.getId();
                                    documentReference.update("orderId",id).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            clearCart();
                                        }
                                    });
                                }
                            });

                            Bundle bundle = new Bundle();
                            bundle.putString("order_key" , "success");
                            OrderSuccessFragment fragment = new OrderSuccessFragment();
                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.cart_activity_fragment_container,fragment);
                            transaction.commit();

                        }

                    }
                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
    }

    private void clearCart() {
        // TODO: 30-07-2022 emptying the cart here....
        db.collection(ALL_USERS_COLLECTION).document(user.getUid())
                .collection(USER_CART_COLLECTION).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc :queryDocumentSnapshots){
                    DocumentReference item =doc.getReference();
                    item.delete();
                }
            }
        });
    }

    private ArrayList<CartItem> getAllCartItems() {
        ArrayList<CartItem> cart = new ArrayList<>();

        db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_CART_COLLECTION)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots!=null){
                    for(QueryDocumentSnapshot doc :queryDocumentSnapshots){
                        CartItem item = doc.toObject(CartItem.class);
                        cart.add(item);
                    }
                    total = 0;
                    int idx =1;
                    for (CartItem i : cart){
                        item_list.setText(item_list.getText()+"\n"+
                                idx+". "+ i.getItemName()+"  (x"+i.getQuantity()+")");
                        total+=i.getTotalPrice();
                        idx++;
                    }
                    double delivery_charge=40;
                    double tax = total*10.0/100.0;
                    total=total+delivery_charge+tax;
                    total_amount.setText("\u20B9"+total);
                }
            }
        });

        return cart;
    }
}
