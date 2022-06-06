package com.example.karter;

import static com.example.karter.CategoryDialogue.CALLING_ACTIVITY;

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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity  implements CategoryDialogue.CategorySelected {


    private TextView first_category , second_category , third_category , all_category;
    private EditText search_bar;
    private RelativeLayout search_icon;
    private RecyclerView search_result;
    private GroceryItemAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setSupportActionBar(toolbar);
        handleBottomNavigation();
        handleThreeCategories();


        all_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryDialogue dialogue= new CategoryDialogue();
                Bundle bundle=new Bundle();
                bundle.putString(CALLING_ACTIVITY,"search_activity");
                dialogue.setArguments(bundle);
                dialogue.show(getSupportFragmentManager(),"select_category");
            }
        });
        Intent intent = getIntent();
        if (intent!= null){
            String incoming_category = intent.getStringExtra("category");
            if (incoming_category!=null){
                ArrayList<GroceryItem> items_of_category = Utils.getItemsByCategories(this,incoming_category);
                if (items_of_category!= null){
                    adapter = new GroceryItemAdapter(this);
                    adapter.setGroceryItems(items_of_category);
                    search_result.setAdapter(adapter);
                    search_result.setLayoutManager(new GridLayoutManager(this,2));
                }
            }
        }


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

        toolbar=findViewById(R.id.search_toolbar);
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
                        Intent intent1 = new Intent(SearchActivity.this, CartActivity.class);

                        startActivity(intent1);
                        break;
                    case R.id.btm_search:
                        // TODO: 29-05-2022 go to search activity

                        break;
                    case R.id.btm_home:
                        // TODO: 29-05-2022 go to home activity
                        Intent intent = new Intent(SearchActivity.this,AllProducts.class);
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

    private void handleThreeCategories(){
        ArrayList<String> categories = Utils.getAllCategories();

        first_category.setText(categories.get(0));
        second_category.setText(categories.get(1));
        third_category.setText(categories.get(2));


        first_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter=new GroceryItemAdapter(SearchActivity.this);
                ArrayList<GroceryItem> items = Utils.getItemsByCategories(SearchActivity.this,first_category.getText().toString());
                if (items!=null && items.size()!=0){
                    adapter.setGroceryItems(items);
                    search_result.setAdapter(adapter);
                    search_result.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
                }
            }
        });
        second_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter=new GroceryItemAdapter(SearchActivity.this);
                ArrayList<GroceryItem> items = Utils.getItemsByCategories(SearchActivity.this,second_category.getText().toString());
                if (items!=null && items.size()!=0){
                    adapter.setGroceryItems(items);
                    search_result.setAdapter(adapter);
                    search_result.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
                }

            }
        });
        third_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter=new GroceryItemAdapter(SearchActivity.this);
                ArrayList<GroceryItem> items = Utils.getItemsByCategories(SearchActivity.this,third_category.getText().toString());
                if (items!=null && items.size()!=0){
                    adapter.setGroceryItems(items);
                    search_result.setAdapter(adapter);
                    search_result.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
                }
            }
        });

    }

    @Override
    public void onCategorySelectedResult(String category) {
        ArrayList<GroceryItem> items_of_category = Utils.getItemsByCategories(this,category);

        if (items_of_category!= null){
            adapter = new GroceryItemAdapter(this);
            adapter.setGroceryItems(items_of_category);
            search_result.setAdapter(adapter);
            search_result.setLayoutManager(new GridLayoutManager(this,2));
        }
    }
}