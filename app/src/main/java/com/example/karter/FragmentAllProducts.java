package com.example.karter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentAllProducts extends Fragment {

    private static final String TAG = "FragmentAllProducts";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String ALLGROCERYITEMS_COLLECTION = "AllGroceryItems";
    
    private EditText search_bar;
    private RecyclerView category_RV;
    private  RecyclerView popular_RV;
    private RecyclerView new_items_Rv;
    private BottomNavigationView bottomNavigationView;
    private TextView see_all;
    private ImageSlider ads;


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

        handleSeeAll();

        handleAds();

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
//        initRecViews();
    }

    private  void  initViews(View view){

        category_RV = view.findViewById(R.id.Categories_RV);
        popular_RV=view.findViewById(R.id.Popular_RV);
        new_items_Rv=view.findViewById(R.id.New_RV);

        bottomNavigationView=view.findViewById(R.id.btm_navigation_bar);

        search_bar=view.findViewById(R.id.search_bar);

        see_all=view.findViewById(R.id.all_product_see_all);

        ads = view.findViewById(R.id.advertisements);

    }
    private  void  initRecViews(){
        handleNewItems();
        handlePopular();
    }
    private void handleCategories(){

        ArrayList<String> allCategories = new ArrayList<>();
        allCategories.add("Healthy");
        allCategories.add("Pizza");
        allCategories.add("Beverages");
        allCategories.add("Fruits");
        allCategories.add("Cleansers");

        CategoryAdapter adapter = new CategoryAdapter(getActivity());
        adapter.setAllCategories(allCategories);
        category_RV.setAdapter(adapter);
        category_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void handlePopular(){
        ArrayList<GroceryItem> items = new ArrayList<>();
        GroceryItemAdapter adapter = new GroceryItemAdapter(getActivity());
        adapter.setGroceryItems(items);
        popular_RV.setAdapter(adapter);
        popular_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        db.collection(ALLGROCERYITEMS_COLLECTION).
                orderBy("id").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        GroceryItem item = doc.toObject(GroceryItem.class);
                        items.add(item);
                        Log.d(TAG, "onSuccess: Item added from Firestore\n" + item.toString());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        // TODO: 28-07-2022 Order it by Popularity point
        Comparator<GroceryItem> comparator = new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem t, GroceryItem t1) {
                return t1.getPopularityPoint()-t.getPopularityPoint();
            }
        };
        Collections.sort(items,comparator);

    }

    private void handleNewItems(){
        ArrayList<GroceryItem> list = new ArrayList<>();
        GroceryItemAdapter adapter = new GroceryItemAdapter(getActivity());
        adapter.setGroceryItems(list);
        new_items_Rv.setAdapter(adapter);
        new_items_Rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        db.collection(ALLGROCERYITEMS_COLLECTION).
                orderBy("id" , Query.Direction.DESCENDING)
                .limit(15)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots ){
                        GroceryItem item = doc.toObject(GroceryItem.class);
                        list.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
    private void handleBottomNavigation(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btm_cart:

                        Intent intent1 = new Intent(getActivity(),CartActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.btm_search:

                        Intent intent = new Intent(getActivity(),SearchActivity.class);
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
    private void handleSeeAll(){
        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                intent.putExtra("category","all");
                startActivity(intent);
            }
        });
    }

    private void handleAds() {
        db.collection("Ads").document("images").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> images = (List<String>) documentSnapshot.get("url");
                ArrayList<SlideModel> sliders = new ArrayList<>();

                for(String url : images){
                    sliders.add(new SlideModel(url, ScaleTypes.FIT));
                }
                ads.setImageList(sliders);
            }
        });
    }
}
