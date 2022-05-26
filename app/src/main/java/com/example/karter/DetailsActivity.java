package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

public class DetailsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar ;
    public static final String GROCERY_ITEM_KEY = "incoming_item";

    private TextView name , price , quantity , total_price , description;
    private ImageView image , plus,minus, rating1 , rating2, rating3;
    private RelativeLayout add_to_cart, first_star,second_star , third_star ;

    private int amount =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initViews();

        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if(intent!= null){
            GroceryItem item = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (item!= null){

                Glide.with(this)
                        .asBitmap()
                        .load(item.getImageUrl())
                        .into(image);

                name.setText(item.getName());
                price.setText(""+item.getPrice());

                quantity.setText(""+ amount);

                double total_amount = item.getPrice() * this.amount;
                total_price.setText(""+total_amount);

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        amount++;
                        quantity.setText(""+ amount);
                        double total_amount = item.getPrice() * amount;
                        total_price.setText(""+total_amount);
                    }
                });

                if(amount>0){
                    minus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            amount--;
                            quantity.setText(""+ amount);
                            double total_amount = item.getPrice() * amount;
                            total_price.setText(""+total_amount);
                        }
                    });
                }


                description.setText(item.getDesc());

                handleRating();


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

        image =findViewById(R.id.details_img);
        plus=findViewById(R.id.plus_btn);
        minus=findViewById(R.id.minus_btn);
        rating1=findViewById(R.id.first_star);
        rating2=findViewById(R.id.second_star);
        rating3=findViewById(R.id.third_star);

        first_star=findViewById(R.id.first_star_container);
        second_star=findViewById(R.id.second_star_container);
        third_star=findViewById(R.id.third_star_container);

    }
    private void handleRating(){
        first_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.GONE);
                rating3.setVisibility(View.GONE);
            }
        });

        second_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.VISIBLE);
                rating3.setVisibility(View.GONE);
            }
        });

        third_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating1.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.VISIBLE);
                rating3.setVisibility(View.VISIBLE);
            }
        });
    }
}