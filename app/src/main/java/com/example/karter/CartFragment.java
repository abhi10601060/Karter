package com.example.karter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartFragment extends Fragment   {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static  final  String REVIEW_COLLECTION = "Reviews";
    private static final String ALL_GROCERY_ITEMS_COLLECTION = "AllGroceryItems";
    private static final String ALL_USERS_COLLECTION = "Users";
    private static final String USER_CART_COLLECTION = "Cart";

    private RelativeLayout if_empty , cart;
    private Button btn_start_shopping , btn_checkout;
    private RecyclerView cart_item_RV;
    private  ArrayList<CartItem> myCart = new ArrayList<>();
    private TextView total_price, tax , delivery_charges , total_amount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment_layout,container,false);

        initViews(view);

        fillMyCart();

        return view;
    }

    private void fillMyCart() {
        CartItemAdapter adapter= new CartItemAdapter(getActivity());
        adapter.setAllCartItems(myCart);
        cart_item_RV.setAdapter(adapter);
        cart_item_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_CART_COLLECTION)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    myCart.clear();
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        CartItem cartItem = doc.toObject(CartItem.class);
                        myCart.add(cartItem);
                    }
                    if_empty.setVisibility(View.GONE);
                    cart.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    priceCalculation();
                    handleCheckout();

                }
                else{
                    if_empty.setVisibility(View.VISIBLE);
                    cart.setVisibility(View.GONE);

                    handleEmptyCart();
                }
            }
        });
    }

    private void handleCheckout() {

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("address",Utils.getAllAddresses(getActivity()));

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                AllAddressesFragment allAddressesFragment = new AllAddressesFragment();
                allAddressesFragment.setArguments(bundle1);
                transaction.replace(R.id.cart_activity_fragment_container,allAddressesFragment);
                transaction.commit();
            }
        });
    }

    private  void  initViews(View view){
        if_empty = view.findViewById(R.id.if_empty_cart_RL);
        cart = view.findViewById(R.id.cart_RL);
        btn_start_shopping = view.findViewById(R.id.if_empty_cart_btn);
        cart_item_RV=view.findViewById(R.id.cart_item_RV);

        total_amount=view.findViewById(R.id.cart_fragment_total_amount);
        total_price=view.findViewById(R.id.cart_fragment_total_price);
        delivery_charges=view.findViewById(R.id.cart_fragment_delivery_charges);
        tax=view.findViewById(R.id.cart_fragment_tax);

        btn_checkout=view.findViewById(R.id.checkout_btn);

    }
    private void handleEmptyCart(){
        btn_start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AllProducts.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    private void priceCalculation(){
        double tp=0;
        double charges = 40;
        for (CartItem i : myCart){
            tp+=i.getTotalPrice();
        }
        double tx = tp*10.0/100.0;

        double ta = tp+charges+tx;

        total_price.setText("\u20B9"+tp);
        delivery_charges.setText("\u20B9"+charges);
        tax.setText("\u20B9"+tx);
        total_amount.setText("\u20B9"+ta);

    }


}
