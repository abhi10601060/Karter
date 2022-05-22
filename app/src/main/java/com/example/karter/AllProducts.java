package com.example.karter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class AllProducts extends AppCompatActivity {

    private DrawerLayout drawer ;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        initViews();

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
                        Toast.makeText(AllProducts.this, "Cart Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_category:
                        Toast.makeText(AllProducts.this, "category Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_terms:
                        Toast.makeText(AllProducts.this, "Terms Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_abot_us:
                        Toast.makeText(AllProducts.this, "About us Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_licenses:
                        Toast.makeText(AllProducts.this, "Licenses Selected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    private  void  initViews(){
        drawer=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigation_view);
    }
}