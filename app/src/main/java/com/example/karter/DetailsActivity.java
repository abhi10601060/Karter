package com.example.karter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity  implements ReviewDialogue.AddReview, ReviewAdapter.RemoveReview {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static  final  String REVIEW_COLLECTION = "Reviews";
    private static final String ALL_GROCERY_ITEMS_COLLECTION = "AllGroceryItems";
    private static final String ALL_USERS_COLLECTION = "Users";
    private static final String USER_CART_COLLECTION = "Cart";

    private static final String TAG = "DetailsActivity";

    private MaterialToolbar toolbar ;
    public static final String GROCERY_ITEM_KEY = "incoming_item";

    private TextView name , price , quantity , total_price , description , add_review;
    private ImageView image , plus,minus, rating1 , rating2, rating3 , rating4,rating5;
    private RelativeLayout add_to_cart, first_star,second_star , third_star, fourth_star,fifth_star ;
    private RecyclerView review_RV;
    private ReviewAdapter reviewAdapter = new ReviewAdapter(this);

    private int amount = 1;
    private double total_amount;

    private GroceryItem incomingItem;
    private GroceryItem dummy;
    private String DocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initViews();

        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if(intent!= null){
            dummy = intent.getParcelableExtra(GROCERY_ITEM_KEY);

            getIncomingItem(dummy);
        }

    }

    private void getIncomingItem(GroceryItem dummy) {
        db.collection(ALL_GROCERY_ITEMS_COLLECTION)
                .whereEqualTo("id", dummy.getId())
                .limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots!=null){
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        DocId = doc.getId();
                        incomingItem = doc.toObject(GroceryItem.class);
                        Log.d(TAG, "onSuccess: incoming item added");
                    }
                    if (incomingItem!=null){
                        showItem();
                    }

                }
            }
        });
    }

    private void showItem() {

        Glide.with(this)
                .asBitmap()
                .load(incomingItem.getImageUrl())
                .into(image);

        name.setText(incomingItem.getName());
        price.setText(""+incomingItem.getPrice());

        quantity.setText(""+ amount);

        total_amount = incomingItem.getPrice() * this.amount;
        total_price.setText(""+total_amount);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount++;
                quantity.setText(""+ amount);
                total_amount = incomingItem.getPrice() * amount;
                total_price.setText(""+total_amount);
            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount>1){
                    amount--;
                    quantity.setText(""+ amount);
                    total_amount = incomingItem.getPrice() * amount;
                    total_price.setText(""+total_amount);
                }
            }
        });


        description.setText(incomingItem.getDesc());

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartItem cartItem  = new CartItem(DocId,amount,incomingItem.getPrice(),total_amount,incomingItem.getName(),incomingItem.getImageUrl());
                checkUserCartExist(cartItem);
                Log.d(TAG, "onClick: add to cart clickedd//.....");
            }
        });
        handleRating();
        handleReview();

    }

    private void addCartItemToFirestore(CartItem cartItem) {

        Log.d(TAG, "addCartItemToFirestore: Adding to cart");
        CollectionReference userCartRef = db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_CART_COLLECTION);
        userCartRef.document(cartItem.getItemName()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    db.runTransaction(new Transaction.Function<Void>() {
                        @Nullable
                        @Override
                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                            Log.d(TAG, "apply: running update transaction.....");
                            DocumentReference itemRef = db.collection(ALL_GROCERY_ITEMS_COLLECTION).document(cartItem.getCartItemId());
                            DocumentReference cartItemRef = userCartRef.document(cartItem.getItemName());

                            DocumentSnapshot item = transaction.get(itemRef);
                            long available = item.getLong("available_amount");

                            if(cartItem.getQuantity()>available){
                                Log.d(TAG, "apply: firing firestore exception");
                                throw new FirebaseFirestoreException("Available amount exceeded", FirebaseFirestoreException.Code.ABORTED);
                            }

                            long newItemAmount = available-cartItem.getQuantity();

                            DocumentSnapshot cartItemDoc = transaction.get(cartItemRef);
                            long newQuantity = cartItemDoc.getLong("quantity")+cartItem.getQuantity();
                            double newTotalPrice = newQuantity*cartItem.getSingleItemPrice();

                            Map<String,Object> map = new HashMap<>();
                            map.put("quantity",newQuantity);
                            map.put("totalPrice",newTotalPrice);

                            transaction.update(cartItemRef,map);
                            transaction.update(itemRef,"available_amount",newItemAmount);
                            return null;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(DetailsActivity.this, "Item Added To Cart Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: "+e.getMessage());
                            showWarning();
                        }
                    });
                }
                else {

                    db.runTransaction(new Transaction.Function<Void>() {
                        @Nullable
                        @Override
                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                            DocumentReference itemRef = db.collection(ALL_GROCERY_ITEMS_COLLECTION).document(cartItem.getCartItemId());
                            DocumentSnapshot item = transaction.get(itemRef);
                            if(cartItem.getQuantity()>item.getLong("available_amount")){
                                throw  new FirebaseFirestoreException("Available amount exceeded", FirebaseFirestoreException.Code.ABORTED);
                            }
                            else {

                                long newItemAmount = item.getLong("available_amount") - cartItem.getQuantity();
                                transaction.update(itemRef, "available_amount", newItemAmount);

                            }
                            return null;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            userCartRef.document(cartItem.getItemName()).set(cartItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DetailsActivity.this, "Item Added To Cart Successfully...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: update failed");
                            showWarning();
                        }
                    });

                }
            }
        });
    }

    private void checkUserCartExist(CartItem cartItem) {


        db.collection(ALL_USERS_COLLECTION).document(user.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this)
                            .setTitle("Complete Profile")
                            .setMessage("Please complete your profile before adding items to cart...")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(DetailsActivity.this,UpdateProfileActivity.class));
                                }
                            });
                    builder.create().show();
                }
                else{
                    addCartItemToFirestore(cartItem);
                }
            }
        });

    }

    private void showWarning() {
        Log.d(TAG, "showWarning: running");
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this)
                .setTitle("Item Quantity exceeded!")
                .setMessage("Please reduce item quantity...\n Very few left")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();

    }

    private void initViews(){
        toolbar = findViewById(R.id.details_toolbar);

        name = findViewById(R.id.txt_detail_item_name);
        price = findViewById(R.id.quantity_btn_price);
        total_price = findViewById(R.id.txt_total_price);
        quantity=findViewById(R.id.details_txt_quantity);
        description=findViewById(R.id.details_description);
        add_review=findViewById(R.id.details_add_review_btn);

        image =findViewById(R.id.details_img);
        plus=findViewById(R.id.plus_btn);
        minus=findViewById(R.id.minus_btn);
        rating1=findViewById(R.id.first_star);
        rating2=findViewById(R.id.second_star);
        rating3=findViewById(R.id.third_star);
        rating4=findViewById(R.id.fourth_star);
        rating5=findViewById(R.id.fifth_star);

        first_star=findViewById(R.id.first_star_container);
        second_star=findViewById(R.id.second_star_container);
        third_star=findViewById(R.id.third_star_container);
        fourth_star=findViewById(R.id.fourth_star_container);
        fifth_star=findViewById(R.id.fifth_star_container);

        review_RV= findViewById(R.id.details_Review_RV);
        
        add_to_cart=findViewById(R.id.details_add_to_cart_Rl);

    }

    private void handleRating(){
        int r=0;
        if(incomingItem.getPopularityPoint()!=0){
            r = incomingItem.getRate()/incomingItem.getPopularityPoint();
        }


        switch (r){

            case 0 :
                rating1.setVisibility(View.GONE);
                rating2.setVisibility(View.GONE);
                rating3.setVisibility(View.GONE);
                rating4.setVisibility(View.GONE);
                rating5.setVisibility(View.GONE);
                break;

            case 1 :
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.GONE);
                rating3.setVisibility(View.GONE);
                rating4.setVisibility(View.GONE);
                rating5.setVisibility(View.GONE);
                break;

            case 2 :
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.VISIBLE);
                rating3.setVisibility(View.GONE);
                rating4.setVisibility(View.GONE);
                rating5.setVisibility(View.GONE);
                break;

            case 3 :
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.VISIBLE);
                rating3.setVisibility(View.VISIBLE);
                rating4.setVisibility(View.GONE);
                rating5.setVisibility(View.GONE);
                break;
            case 4 :
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.VISIBLE);
                rating3.setVisibility(View.VISIBLE);
                rating4.setVisibility(View.VISIBLE);
                rating5.setVisibility(View.GONE);
                break;
            case 5 :
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.VISIBLE);
                rating3.setVisibility(View.VISIBLE);
                rating4.setVisibility(View.VISIBLE);
                rating5.setVisibility(View.VISIBLE);
                break;

            default:
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.GONE);
                rating3.setVisibility(View.GONE);
                rating4.setVisibility(View.GONE);
                rating5.setVisibility(View.VISIBLE);
                break;
        }


    }

    private void handleReview(){

        add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewDialogue dialogue = new ReviewDialogue();
                Bundle bundle = new Bundle();
                bundle.putString(GROCERY_ITEM_KEY,DocId);
                dialogue.setArguments(bundle);
                dialogue.show(getSupportFragmentManager(),"add_review");
            }
        });


        ArrayList<Review> allReviews = new ArrayList<>();
        reviewAdapter.setAllReviews(allReviews);
        review_RV.setAdapter(reviewAdapter);
        review_RV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        db.collection(REVIEW_COLLECTION)
                .whereEqualTo("itemId",DocId)
                .orderBy("date" , Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        Review review = doc.toObject(Review.class);

                        allReviews.add(review);
                    }
                    reviewAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void onAddReviewResult(Review review) {
        db.collection(REVIEW_COLLECTION).add(review).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                review.setReviewId(documentReference.getId());

                db.runTransaction(new Transaction.Function<Void>() {
                    @Nullable
                    @Override
                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                        DocumentReference doc = db.collection(ALL_GROCERY_ITEMS_COLLECTION).document(DocId);
                        DocumentSnapshot curDoc = transaction.get(doc);

                        long newRate = curDoc.getLong("rate")+review.getRating();
                        long newPopularity = curDoc.getLong("popularityPoint")+1;

                        Map<String,Object> map = new HashMap<>();
                        map.put("rate",newRate);
                        map.put("popularityPoint",newPopularity);

                        transaction.update(doc,map);

                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        getIncomingItem(dummy);

                        db.collection(REVIEW_COLLECTION).document(review.getReviewId()).set(review).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(DetailsActivity.this, "Review Added Successfully....", Toast.LENGTH_SHORT).show();
                            }
                        });              // this is here to get and change review id to query it while deleting....
                        // TODO: 29-07-2022  do something optimized to remove this updating as well....
                    }
                });

            }
        });

    }

    @Override
    public void onRemoveResult(Review review) {

        DocumentReference doc = db.collection(REVIEW_COLLECTION).document(review.getReviewId());
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentReference item =db.collection(ALL_GROCERY_ITEMS_COLLECTION).document(review.getItemId());
                DocumentSnapshot itemSnapshot = transaction.get(item);

                long newRating = itemSnapshot.getLong("rate")-review.getRating();
                long popularity = itemSnapshot.getLong("popularityPoint")-1;

                Map<String,Object> map = new HashMap<>();
                map.put("rate",newRating);
                map.put("popularityPoint",popularity);

                transaction.update(item,map);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DetailsActivity.this, "Review deleted successfully...", Toast.LENGTH_SHORT).show();
                getIncomingItem(dummy);
            }
        });
        doc.delete();

        handleReview();

    }
}