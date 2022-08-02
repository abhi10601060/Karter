package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static  final  String REVIEW_COLLECTION = "Reviews";
    private static final String ALL_GROCERY_ITEMS_COLLECTION = "AllGroceryItems";
    private static final String ALL_USERS_COLLECTION = "Users";
    private static final String USER_CART_COLLECTION = "Cart";
    private static  final String USERS_HISTORY_COLLECTION = "OrderHistory";

    private RecyclerView orders_RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        orders_RV= findViewById(R.id.orders_RV);

        ArrayList<Order> history = new ArrayList<>();
        OrdersAdapter adapter = new OrdersAdapter(this);
        adapter.setAllOrders(history);
        orders_RV.setAdapter(adapter);
        orders_RV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USERS_HISTORY_COLLECTION)
        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        Order order = doc.toObject(Order.class);
                        history.add(order);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    // TODO: 01-08-2022 add if empty Rl
                }
            }
        });


    }
}