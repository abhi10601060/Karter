package com.example.karter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private TextView first_category , second_category , third_category , all_category;
    private EditText search_bar;
    private RelativeLayout search_icon;
    private RecyclerView search_result;
    private GroceryItemAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        handleBottomNavigation();





        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSearch();
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void  initViews(){
        first_category=findViewById(R.id.sa_txt_first_category);
        second_category=findViewById(R.id.sa_txt_second_category);
        third_category=findViewById(R.id.sa_txt_third_category);
        all_category=findViewById(R.id.sa_txt_see_all_categories);

        search_bar=findViewById(R.id.search_activity_search_bar);
        search_icon= findViewById(R.id.sa_search_icon);
        search_result=findViewById(R.id.sa_search_RV);

        bottomNavigationView=findViewById(R.id.sa_btm_navigation_bar);
    }
    private void initSearch(){
        if (!search_bar.getText().toString().equals("")){
            String name = search_bar.getText().toString();
            ArrayList<GroceryItem> result = Utils.searchByName(this,name);
            if (result!=null){

                adapter=new GroceryItemAdapter(this);
                adapter.setGroceryItems(result);
                search_result.setAdapter(adapter);
                search_result.setLayoutManager(new GridLayoutManager(this,2));
            }

        }
    }
    private void handleBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.btm_search);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btm_cart:
                        // TODO: 29-05-2022 go to cart activity
                        break;
                    case R.id.btm_search:
                        // TODO: 29-05-2022 go to search activity

                        break;
                    case R.id.btm_home:
                        // TODO: 29-05-2022 go to home activity
                        Intent intent = new Intent(SearchActivity.this,AllProducts.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }

                return false;
            }
        });
    }
}