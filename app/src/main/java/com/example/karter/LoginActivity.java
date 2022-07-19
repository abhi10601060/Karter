package com.example.karter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FloatingActionButton login_btn;
    private EditText edt_mail , edt_pass;
    private TextView signUp;
    private RelativeLayout warning;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = edt_mail.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();
                warning.setVisibility(View.GONE);
                if(mail.length()!=0 && pass.length()!=0){
                    auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                if(user.isEmailVerified()){
                                    Toast.makeText(LoginActivity.this, "login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,AllProducts.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else {
                                    user.sendEmailVerification();
                                    auth.signOut();
                                    showVerificationAlert();
                                }
                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                    showSignupDialogue();
                                }
                                else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                    warning.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });

                }
                else{
                    warning.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    private void showSignupDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Sign up")
                .setMessage("user does not exist. Please sign-up first...")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Sign-Up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                    }
                });
        builder.create().show();
    }

    private void showVerificationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Email Verification")
                .setMessage("Please Verify your registered email.\nCheck your Email for Verification link...")
                .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }

    private void initViews() {
        login_btn=findViewById(R.id.login_floating_btn);
        signUp = findViewById(R.id.login_signUp_btn);
        edt_mail=findViewById(R.id.login_edt_mail);
        edt_pass=findViewById(R.id.login_edt_pass);
        warning=findViewById(R.id.login_warning_RL);
    }
}