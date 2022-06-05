package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity  implements ReviewDialogue.AddReview , ReviewAdapter.RemoveReview {

    private static final String TAG = "DetailsActivity";

    private MaterialToolbar toolbar ;
    public static final String GROCERY_ITEM_KEY = "incoming_item";

    private TextView name , price , quantity , total_price , description , add_review;
    private ImageView image , plus,minus, rating1 , rating2, rating3 , rating4,rating5;
    private RelativeLayout add_to_cart, first_star,second_star , third_star, fourth_star,fifth_star ;
    private RecyclerView review_RV;
    private ReviewAdapter reviewAdapter = new ReviewAdapter(this);

    private int amount = 1;

    GroceryItem incomingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initViews();

        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if(intent!= null){
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (incomingItem!= null){

                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(image);

                name.setText(incomingItem.getName());
                price.setText(""+incomingItem.getPrice());

                quantity.setText(""+ amount);

                double total_amount = incomingItem.getPrice() * this.amount;
                total_price.setText(""+total_amount);

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        amount++;
                        quantity.setText(""+ amount);
                        double total_amount = incomingItem.getPrice() * amount;
                        total_price.setText(""+total_amount);
                    }
                });


                    minus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(amount>1){
                            amount--;
                            quantity.setText(""+ amount);
                            double total_amount = incomingItem.getPrice() * amount;
                            total_price.setText(""+total_amount);
                            }
                        }
                    });


                description.setText(incomingItem.getDesc());
                
                add_to_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.addCartItem(DetailsActivity.this,new CartItem(incomingItem,amount));
                        Toast.makeText(DetailsActivity.this, "Item Added To Cart Successfully...", Toast.LENGTH_SHORT).show();
                    }
                });

                handleRating();
                handleReview();


            }
        }

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
        ArrayList<Review> reviews = Utils.getReviews(this,incomingItem.getId());

        int r = 0;
        if (reviews!=null && reviews.size()!=0){
            for (Review e : reviews){
                r=r+e.getRating();
            }
            r=r/reviews.size();
        }
        Log.d(TAG, "handleRating: rating is " + r);


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


        review_RV.setAdapter(reviewAdapter);
        reviewAdapter.setAllReviews(Utils.getReviews(this,incomingItem.getId()));
        review_RV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewDialogue dialogue = new ReviewDialogue();
                Bundle bundle = new Bundle();
                bundle.putParcelable(GROCERY_ITEM_KEY,incomingItem);
                dialogue.setArguments(bundle);
                dialogue.show(getSupportFragmentManager(),"add_review");
            }
        });
    }

    @Override
    public void onAddReviewResult(Review review) {

        Utils.addReview(this,review,incomingItem.getId());
        ArrayList<Review> newReviews = Utils.getReviews(this, incomingItem.getId());

        if (newReviews!= null){
            reviewAdapter.setAllReviews(newReviews);
            handleRating();
            Toast.makeText(this, "Review added successfully", Toast.LENGTH_SHORT).show();
        }


//        review_RV.setAdapter(reviewAdapter);
//        review_RV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


    }

    @Override
    public void onRemoveResult(Review review) {
        Utils.removeReview(this,incomingItem.getId(),review);
        ArrayList<Review> newReviews = Utils.getReviews(this, incomingItem.getId());
        handleRating();

        if (newReviews!=null){
            reviewAdapter.setAllReviews(newReviews);
            Toast.makeText(this, "Review removed successfully", Toast.LENGTH_SHORT).show();
        }



    }
}