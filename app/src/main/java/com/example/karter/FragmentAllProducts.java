package com.example.karter;

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

public class FragmentAllProducts extends Fragment {

    private RecyclerView category_RV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_products,container,false);
        category_RV = view.findViewById(R.id.Categories_RV);

        initViews();
        handleCategories();

        return view;
    }
    private  void  initViews(){

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
}
