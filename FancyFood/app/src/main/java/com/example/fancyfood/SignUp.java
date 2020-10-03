package com.example.fancyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    EditText etEmail,etPassword,etConfirm;
    ProgressBar progressBar;
   FirebaseAuth fAuth;
   Button buRegister,buSignInAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        buSignInAction = findViewById(R.id.buSignInAction);
        buRegister = findViewById(R.id.buRegister);
        buSignInAction = findViewById(R.id.buSignInAction);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MenuItemsActivity.class));
            finish();
        }
        buRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirm = etConfirm.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    etPassword.setError("Password must be atleast 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),MenuItemsActivity.class));
                            finish();

                        }
                    }
                });

            }
        });
    }


    public void SignInAction(View view) {
        startActivity(new Intent(getApplicationContext(),SignIn.class));
        finish();
    }
}