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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentFragment extends Fragment {

    private TextView name, item_list,address , contact_details,total_amount;
    private Button btn_place_order;
    private RadioGroup payment_rg;
    private String payment_method = null;
    private  Address incomingAddress;



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

                ArrayList<CartItem> myCart = Utils.getAllCartItems(getActivity());

                double total = 0;
                int idx =1;
                for (CartItem i : myCart){
                    item_list.setText(item_list.getText()+"\n"+
                            idx+". "+ i.getItem().getName()+"  (x"+i.getQuantity()+")");
                    total+=i.getItem().getPrice()*i.getQuantity();
                    idx++;
                }

                total_amount.setText("\u20B9"+total);

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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String date = simpleDateFormat.format(calendar.getTime());
                Order order = new Order(Utils.getAllCartItems(getActivity()),incomingAddress,payment_method,date);

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

                            Bundle bundle = new Bundle();
                            bundle.putString("order_key" , "success");
                            ArrayList<CartItem> cartItems = Utils.getAllCartItems(getActivity());
                            for(CartItem i : cartItems){
                                Utils.increasePopularity(getActivity(),i.getItem());
                            }
                            Utils.clearCart(getActivity());

                            // TODO: 04-06-2022 Increase the [popularity rating of each item in cart

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
}
