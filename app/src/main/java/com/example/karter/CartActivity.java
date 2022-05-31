package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        setSupportActionBar(toolbar);
        bottomNavigationView.setSelectedItemId(R.id.btm_cart);

    }
    private void initView(){
        toolbar=findViewById(R.id.cart_activity_toolbar);
        bottomNavigationView=findViewById(R.id.cart_activity_btm_navigation_bar);
    }
}