package com.example.karter;

import static com.example.karter.CategoryDialogue.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AllProducts extends AppCompatActivity {

    private  static  final  String ACTIVITY_NAME= "all_product";


    private DrawerLayout drawer ;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private TextView profile_name;
    private FirebaseAuth auth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        initViews();


        setSupportActionBar(toolbar);

        handleToolbarProfile();

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(AllProducts.this)
                                .setMessage("There are no terms.Enjoy using this App...")
                                .setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create().show();

                        break;
                    case R.id.menu_abot_us:
                        Intent aboutIntent = new Intent(AllProducts.this,AboutActivity.class);
                        startActivity(aboutIntent);

                        break;
                    case R.id.menu_licenses:

                        LiscencesDialogue dialogue = new LiscencesDialogue();
                        dialogue.show(getSupportFragmentManager(),"licences");


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

        handleAuth();



    }

    private void handleToolbarProfile() {

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.profile_menu_item:
                        startActivity(new Intent(AllProducts.this,ProfileActivity.class));
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void handleAuth() {
        FirebaseUser user = auth.getCurrentUser();
        String[]name = user.getDisplayName().split(" ");
        profile_name.setText("Hi..!\n"+name[0]);
    }

    private  void  initViews(){
        drawer=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigation_view);
        profile_name=findViewById(R.id.txt_profile_name);
    }
}