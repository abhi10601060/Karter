package com.example.karter;

import static com.example.karter.CategoryDialogue.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AllProducts extends AppCompatActivity {

    private  static  final  String ACTIVITY_NAME= "all_product";


    private DrawerLayout drawer ;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        initViews();
        Utils.initSharedPreferences(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_close,
                R.string.drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_cart:
                        Intent intent = new Intent(AllProducts.this,CartActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.menu_category:

                         CategoryDialogue categoryDialogue = new CategoryDialogue();
                         Bundle bundle = new Bundle();
                         bundle.putString(CALLING_ACTIVITY,ACTIVITY_NAME);
                         categoryDialogue.setArguments(bundle);
                         categoryDialogue.show(getSupportFragmentManager(),"select_category");


                        break;
                    case R.id.menu_terms:
                        Toast.makeText(AllProducts.this, "Terms Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_abot_us:
                        Intent aboutIntent = new Intent(AllProducts.this,AboutActivity.class);
                        startActivity(aboutIntent);
                        Toast.makeText(AllProducts.this, "About us Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_licenses:

                        LiscencesDialogue dialogue = new LiscencesDialogue();
                        dialogue.show(getSupportFragmentManager(),"licences");

                        Toast.makeText(AllProducts.this, "Licenses Selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentAllProducts fragmentAllProducts = new FragmentAllProducts();
        transaction.replace(R.id.container,fragmentAllProducts);
        transaction.commit();




    }

    private  void  initViews(){
        drawer=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigation_view);
    }
}