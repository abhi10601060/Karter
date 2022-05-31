package com.example.karter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentAllProducts extends Fragment {

    private EditText search_bar;
    private RecyclerView category_RV;
    private  RecyclerView popular_RV;
    private RecyclerView new_items_Rv;
    private BottomNavigationView bottomNavigationView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_products,container,false);

        initViews(view);

        initRecViews();

        handleCategories();

        handleNewItems();

        handleBottomNavigation();

        handleSearchBar();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecViews();
    }

    private  void  initViews(View view){

        category_RV = view.findViewById(R.id.Categories_RV);
        popular_RV=view.findViewById(R.id.Popular_RV);
        new_items_Rv=view.findViewById(R.id.New_RV);

        bottomNavigationView=view.findViewById(R.id.btm_navigation_bar);

        search_bar=view.findViewById(R.id.search_bar);

    }
    private  void  initRecViews(){
        handleNewItems();
        handlePopular();
    }
    private void handleCategories(){

        ArrayList<String> allCategories=Utils.getAllCategories();
        CategoryAdapter adapter = new CategoryAdapter(getActivity());
        adapter.setAllCategories(allCategories);
        category_RV.setAdapter(adapter);
        category_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void handlePopular(){

        GroceryItemAdapter adapter = new GroceryItemAdapter(getActivity());
        adapter.setGroceryItems(Utils.getAllItems(getActivity()));
        popular_RV.setAdapter(adapter);
        popular_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void handleNewItems(){

        Comparator<GroceryItem> comparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem t1, GroceryItem t2) {
                return t2.getId()-t1.getId();
            }
        };

        ArrayList<GroceryItem> list = Utils.getAllItems(getActivity());

        Collections.sort(list,comparator);

        GroceryItemAdapter adapter = new GroceryItemAdapter(getActivity());
        adapter.setGroceryItems(list);
        new_items_Rv.setAdapter(adapter);
        new_items_Rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

    }
    private void handleBottomNavigation(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btm_cart:
                        // TODO: 29-05-2022 go to cart activity
                        Intent intent1 = new Intent(getActivity(),CartActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.btm_search:
                        // TODO: 29-05-2022 go to search activity
                        Intent intent = new Intent(getActivity(),SearchActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    case R.id.btm_home:
                        // TODO: 29-05-2022 go to home activity
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }
    private void handleSearchBar(){
        search_bar.setCursorVisible(false);
        search_bar.setFocusable(false);
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
