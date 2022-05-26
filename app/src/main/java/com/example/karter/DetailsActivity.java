package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

public class DetailsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar ;
    public static final String GROCERY_ITEM_KEY = "incoming_item";

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

            }
        }

    }

    private void initViews(){
        toolbar = findViewById(R.id.details_toolbar);
    }
}