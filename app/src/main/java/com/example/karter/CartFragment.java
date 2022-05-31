package com.example.karter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private RelativeLayout if_empty , cart;
    private Button btn_start_shopping;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment_layout,container,false);

        initViews(view);

        ArrayList<CartItem> myCart = Utils.getAllCartItems(getActivity());
        if (myCart==null){
            if_empty.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);

            handleEmptyCart();

        }
        else{
            if_empty.setVisibility(View.GONE);
            cart.setVisibility(View.VISIBLE);

            handleCart();
        }



        return view;
    }
    private  void  initViews(View view){
        if_empty = view.findViewById(R.id.if_empty_cart_RL);
        cart = view.findViewById(R.id.cart_RL);
        btn_start_shopping = view.findViewById(R.id.if_empty_cart_btn);
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

    }
}
