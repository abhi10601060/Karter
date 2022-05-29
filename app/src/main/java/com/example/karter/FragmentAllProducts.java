package com.example.karter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentAllProducts extends Fragment {

    private RecyclerView category_RV;
    private  RecyclerView popular_RV;
    private RecyclerView new_items_Rv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_products,container,false);

        initViews(view);

        initRecViews();

        handleCategories();

        handleNewItems();

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


    }
    private  void  initRecViews(){
        handleNewItems();
        handlePopular();
    }
    private void handleCategories(){
        ArrayList<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem("Healthy"));
        categoryItems.add(new CategoryItem("Pizza"));
        categoryItems.add(new CategoryItem("Beverages"));
        categoryItems.add(new CategoryItem("Fruits"));
        categoryItems.add(new CategoryItem("Cleansers"));

        CategoryAdapter adapter = new CategoryAdapter(getActivity());
        adapter.setAllCategories(categoryItems);
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
}
