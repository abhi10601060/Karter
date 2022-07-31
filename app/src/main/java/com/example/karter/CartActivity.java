package com.example.karter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity  implements CartItemAdapter.CartItemDelete, CartItemAdapter.ChangeQuantity, AddressAdapter.RemoveAddress , AddressAdapter.AddressSelected {

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

    @Override
    public void onDeleteCartItemResult(CartItem item) {

        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentReference groceryItemRef =db.collection(ALL_GROCERY_ITEMS_COLLECTION).document(item.getCartItemId());
                DocumentReference cartItemRef = db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_CART_COLLECTION).document(item.getItemName());

                DocumentSnapshot groceryItem = transaction.get(groceryItemRef);
                DocumentSnapshot cartItem = transaction.get(cartItemRef);

                long cartItemQuantity = cartItem.getLong("quantity");
                long newAvailableAmount = groceryItem.getLong("available_amount") + cartItemQuantity;

                transaction.update(groceryItemRef,"available_amount",newAvailableAmount);
                transaction.delete(cartItemRef);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                CartFragment fragment = new CartFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.cart_activity_fragment_container,fragment);
                transaction.commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CartActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onQuantityAdded(CartItem item) {

        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentReference cartItemRef = db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_CART_COLLECTION).document(item.getItemName());
                DocumentReference groceryItemRef = db.collection(ALL_GROCERY_ITEMS_COLLECTION).document(item.getCartItemId());

                DocumentSnapshot cartItem = transaction.get(cartItemRef);
                long newCartItemQuantity = cartItem.getLong("quantity")+1;
                long newTotalPrice = cartItem.getLong("singleItemPrice")*newCartItemQuantity;

                Map<String,Object> map = new HashMap<>();
                map.put("quantity",newCartItemQuantity);
                map.put("totalPrice",newTotalPrice);

                DocumentSnapshot groceryItem = transaction.get(groceryItemRef);
                long currentAvailableAmount = groceryItem.getLong("available_amount");

                if(currentAvailableAmount>0){
                    long newGroceryItemAmount = currentAvailableAmount-1;
                    transaction.update(groceryItemRef,"available_amount",newGroceryItemAmount);
                    transaction.update(cartItemRef,map);
                }
                else{
                    throw  new FirebaseFirestoreException("Available amount exceeded", FirebaseFirestoreException.Code.ABORTED );
                }

                return null;
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showWarning();
            }
        })
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                CartFragment fragment = new CartFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.cart_activity_fragment_container,fragment);
                transaction.commit();
            }
        });

    }

    private void showWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this)
                .setTitle("Item Quantity exceeded!")
                .setMessage("Please reduce item quantity...\n Very few left")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();


    }

    @Override
    public void onQuantityReduced(CartItem item) {

        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentReference cartItemRef = db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_CART_COLLECTION).document(item.getItemName());
                DocumentReference groceryItemRef = db.collection(ALL_GROCERY_ITEMS_COLLECTION).document(item.getCartItemId());

                DocumentSnapshot cartItem = transaction.get(cartItemRef);
                long newCartItemQuantity = cartItem.getLong("quantity")-1;
                long newTotalPrice = cartItem.getLong("singleItemPrice")*newCartItemQuantity;

                Map<String,Object> map = new HashMap<>();
                map.put("quantity",newCartItemQuantity);
                map.put("totalPrice",newTotalPrice);

                DocumentSnapshot groceryItem = transaction.get(groceryItemRef);
                long currentAvailableAmount = groceryItem.getLong("available_amount");
                long newGroceryItemAmount = currentAvailableAmount+1;

                transaction.update(groceryItemRef,"available_amount",newGroceryItemAmount);
                transaction.update(cartItemRef,map);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        CartFragment fragment = new CartFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.cart_activity_fragment_container,fragment);
                        transaction.commit();
                    }
                });

    }

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