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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartActivity extends AppCompatActivity  implements  AddressAdapter.RemoveAddress , AddressAdapter.AddressSelected {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static  final  String REVIEW_COLLECTION = "Reviews";
    private static final String ALL_GROCERY_ITEMS_COLLECTION = "AllGroceryItems";
    private static final String ALL_USERS_COLLECTION = "Users";
    private static final String USER_CART_COLLECTION = "Cart";
    private static final String USER_ADDRESS_COLLECTION = "Addresses";

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

//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("cart",Utils.getAllCartItems(this));
//
//        cartFragment.setArguments(bundle);

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
                    case R.id.btm_search:

                        Intent intent1 = new Intent(CartActivity.this, SearchActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.btm_home:

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

//    @Override
//    public void onDeleteCartItemResult(CartItem item) {
//        Utils.removeCartItem(this,item);
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("cart",Utils.getAllCartItems(this));
//
//        CartFragment fragment = new CartFragment();
//        fragment.setArguments(bundle);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.cart_activity_fragment_container,fragment);
//        transaction.commit();
//
//
//    }
//
//    @Override
//    public void onQuantityAdded(CartItem item) {
//        Utils.addQuantity(this,item);
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("cart",Utils.getAllCartItems(this));
//
//
//        CartFragment fragment = new CartFragment();
//        fragment.setArguments(bundle);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.cart_activity_fragment_container,fragment);
//        transaction.commit();
//    }
//
//    @Override
//    public void onQuantityReduced(CartItem item) {
//        Utils.reduceQuantity(this, item);
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("cart", Utils.getAllCartItems(this));
//
//        CartFragment fragment = new CartFragment();
//        fragment.setArguments(bundle);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.cart_activity_fragment_container,fragment);
//        transaction.commit();
//
//    }

    @Override
    public void onAddressRemoved(Address address) {

        DocumentReference addressRef =db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_ADDRESS_COLLECTION)
                .document(address.getDbId());
        addressRef.delete();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AllAddressesFragment allAddressesFragment = new AllAddressesFragment();
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