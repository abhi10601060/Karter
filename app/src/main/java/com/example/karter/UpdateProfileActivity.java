package com.example.karter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateProfileActivity extends AppCompatActivity {

    private MaterialToolbar toolbar ;
    private EditText edt_first_name , edt_last_name, edt_phone , edt_mail;
    private Button btn_save;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser Fuser =auth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        initViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        edt_mail.setText(Fuser.getEmail());
        edt_mail.setFocusable(false);

        db.collection("Users").document(Fuser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot dc = task.getResult();
                    User user = dc.toObject(User.class);

                    String name = user.getName();
                    String[] arr = name.split(" ");

                    edt_first_name.setText(arr[0]);
                    edt_last_name.setText(arr[1]);

                    edt_phone.setText(user.getPhone_No());

                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateCredentials()==true){
                    String name = edt_first_name.getText().toString().trim() + " " + edt_last_name.getText().toString().trim();
                    String ph_no = edt_phone.getText().toString().trim();
                    String mail = edt_mail.getText().toString().trim();
                    String id = Fuser.getUid();
                    User new_user = new User(name,mail,ph_no,id);
                    db.collection("Users").document(Fuser.getUid()).set(new_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully...", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(UpdateProfileActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(UpdateProfileActivity.this, "Please enter data correctly...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateCredentials() {
        if(edt_first_name.getText().toString().trim().length()==0 || edt_last_name.getText().toString().trim().length()==0
        || edt_phone.getText().toString().trim().length()!=10)return false;
        else{
            return true;
        }

    }


    private void initViews() {
        edt_first_name= findViewById(R.id.update_edt_first_name);
        edt_last_name=findViewById(R.id.update_edt_last_name);
        edt_phone = findViewById(R.id.update_edt_phone);
        edt_mail = findViewById(R.id.update_edt_mail);
        btn_save=findViewById(R.id.update_btn_save);
        toolbar= findViewById(R.id.update_profile_toolbar);
    }
}