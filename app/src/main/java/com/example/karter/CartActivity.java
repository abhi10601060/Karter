package com.example.karter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CartActivity extends AppCompatActivity  implements CartItemAdapter.CartItemDelete , CartItemAdapter.ChangeQuantity, AddressAdapter.RemoveAddress , AddressAdapter.AddressSelected {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        setSupportActionBar(toolbar);
        handleBottomNavigation();


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

    private void  handleBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.btm_cart);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btm_cart:
                        // TODO: 29-05-2022 go to cart activity
                        break;
                    case R.id.btm_search:
                        // TODO: 29-05-2022 go to search activity
                        Intent intent1 = new Intent(CartActivity.this, SearchActivity.class);

                        startActivity(intent1);
                        break;
                    case R.id.btm_home:
                        // TODO: 29-05-2022 go to home activity
                        Intent intent = new Intent(CartActivity.this,AllProducts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
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

    @Override
    public void onAddressRemoved(Address address) {
        Utils.removeAddresses(this,address);

        Bundle bundle =new Bundle();
        bundle.putParcelableArrayList("address", Utils.getAllAddresses(this));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AllAddressesFragment allAddressesFragment = new AllAddressesFragment();
        allAddressesFragment.setArguments(bundle);
        transaction.replace(R.id.cart_activity_fragment_container,allAddressesFragment);
        transaction.commit();
    }

    @Override
    public void onAddressSelected(Address address) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payment_address",address);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentFragment paymentFragment = new PaymentFragment();
        paymentFragment.setArguments(bundle);
        transaction.replace(R.id.cart_activity_fragment_container,paymentFragment);
        transaction.commit();
    }
}