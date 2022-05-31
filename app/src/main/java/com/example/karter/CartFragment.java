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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartFragment extends Fragment   {

    private RelativeLayout if_empty , cart;
    private Button btn_start_shopping;
    private RecyclerView cart_item_RV;
    private  ArrayList<CartItem> myCart;
    private TextView total_price, tax , delivery_charges , total_amount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment_layout,container,false);

        initViews(view);

        Bundle bundle = getArguments();
        myCart=bundle.getParcelableArrayList("cart");

        if (myCart==null){
            if_empty.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);

            handleEmptyCart();

        }
        else{
            if_empty.setVisibility(View.GONE);
            cart.setVisibility(View.VISIBLE);

            handleCart();
            priceCalculation();
        }



        return view;
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

    private void handleCart(){

        CartItemAdapter adapter= new CartItemAdapter(getActivity());
        adapter.setAllCartItems(myCart);
        cart_item_RV.setAdapter(adapter);
        cart_item_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

    }

    private void priceCalculation(){
        double tp=0;
        double charges = 40;
        for (CartItem i : myCart){
            tp+=i.getItem().getPrice()*i.getQuantity();
        }
        double tx = tp*20.0/100.0;

        double ta = tp+charges+tx;

        total_price.setText("\u20B9"+tp);
        delivery_charges.setText("\u20B9"+charges);
        tax.setText("\u20B9"+tx);
        total_amount.setText("\u20B9"+ta);



    }


}
