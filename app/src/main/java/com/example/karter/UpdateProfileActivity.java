package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText edt_first_name , edt_last_name, edt_phone , edt_mail;
    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        initViews();


        edt_mail.setText(auth.getCurrentUser().getEmail());
        edt_mail.setFocusable(false);



    }

    private void initViews() {
        edt_first_name= findViewById(R.id.update_edt_first_name);
        edt_last_name=findViewById(R.id.update_edt_last_name);
        edt_phone = findViewById(R.id.update_edt_phone);
        edt_mail = findViewById(R.id.update_edt_mail);
    }
}