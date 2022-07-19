package com.example.karter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    EditText edt_name, edt_mail,edt_pass ,edt_confirm_pass;
    TextView pass_warning, mail_warning;
    private FloatingActionButton signup_btn;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();



        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_name.getText().toString();
                String email = edt_mail.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();
                String confirm_pass = edt_confirm_pass.getText().toString().trim();

                if(name.length()!=0 && email.length()!=0 && pass.length()>=8 && confirm_pass.length()>=8){
                    if(!pass.equals(confirm_pass)){
                        pass_warning.setVisibility(View.VISIBLE);
                        return;
                    }
                    pass_warning.setVisibility(View.GONE);

                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                user.updateProfile(request);
                                user.sendEmailVerification();
                                showVerificationAlertDialogue();
                            }
                            else{
                                if(task.getException() instanceof  FirebaseAuthUserCollisionException){
                                    if(auth.getCurrentUser()!=null && auth.getCurrentUser().isEmailVerified()){
                                        Log.d(TAG, "onComplete: login alert started");
                                        showLoginAlertDialogue();
                                        return;
                                    }
                                    else if(auth.getCurrentUser()!=null && auth.getCurrentUser().isEmailVerified()==false){
                                        Log.d(TAG, "onComplete: verification alert started");
                                        showVerificationAlertDialogue();
                                    }
                                    mail_warning.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(SignupActivity.this, "Please fill all the Credentials...", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void showVerificationAlertDialogue() {
        FirebaseUser user = auth.getCurrentUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this)
                .setTitle("Email Verification")
                .setMessage("Please Verify your registered email.\nCheck your Email for Verification link...")
                .setNegativeButton("Resend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!user.isEmailVerified()){
                            user.sendEmailVerification();
                        }
                    }
                })
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(SignupActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }

    private void showLoginAlertDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Thanks For Verification...")
                .setMessage("You can Login successfully now. Go to the Login Page... ")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }

    private void initViews() {
        edt_confirm_pass = findViewById(R.id.signup_edt_confirm_password);
        edt_mail = findViewById(R.id.signup_edt_email);
        edt_name = findViewById(R.id.signup_edt_name);
        edt_pass =findViewById(R.id.signup_edt_password);
        signup_btn = findViewById(R.id.signUp_floating_btn);
        pass_warning=findViewById(R.id.signup_txt_confirm_password_wraning);
        mail_warning=findViewById(R.id.signup_txt_email_warning);
    }
}