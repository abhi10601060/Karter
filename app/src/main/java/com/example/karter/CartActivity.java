package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity  implements CartItemAdapter.CartItemDelete , CartItemAdapter.ChangeQuantity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        setSupportActionBar(toolbar);
        bottomNavigationView.setSelectedItemId(R.id.btm_cart);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        CartFragment cartFragment = new CartFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cart",Utils.getAllCartItems(this));

        cartFragment.setArguments(bundle);

        transaction.replace(R.id.cart_activity_fragment_container,cartFragment);
        transaction.commit();


    }
    private void initView(){
        toolbar=findViewById(R.id.cart_activity_toolbar);
        bottomNavigationView=findViewById(R.id.cart_activity_btm_navigation_bar);
    }

    @Override
    public void onDeleteCartItemResult(CartItem item) {
        Utils.removeCartItem(this,item);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cart",Utils.getAllCartItems(this));

        CartFragment fragment = new CartFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cart_activity_fragment_container,fragment);
        transaction.commit();


    }

    @Override
    public void onQuantityAdded(CartItem item) {
        Utils.addQuantity(this,item);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cart",Utils.getAllCartItems(this));


        CartFragment fragment = new CartFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cart_activity_fragment_container,fragment);
        transaction.commit();
    }

    @Override
    public void onQuantityReduced(CartItem item) {
        Utils.reduceQuantity(this, item);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("cart", Utils.getAllCartItems(this));

        CartFragment fragment = new CartFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cart_activity_fragment_container,fragment);
        transaction.commit();

    }
}