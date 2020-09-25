package com.example.fancyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignIn extends AppCompatActivity {

    EditText etLogInEmail,etLogInPass;
    Button buSignIn,buSignUpAction;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etLogInEmail = findViewById(R.id.etEmail);
        etLogInPass = findViewById(R.id.etPassword);
        buSignIn = findViewById(R.id.buSignIn);
        buSignUpAction = findViewById(R.id.buSignInAction);
        fAuth = FirebaseAuth.getInstance();
        buSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLogInEmail.getText().toString().trim();
                String password = etLogInPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    etLogInEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etLogInPass.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    etLogInPass.setError("Password must be atleast 6 characters");
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignIn.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Welcome.class));

                        }
                        else{
                            Toast.makeText(SignIn.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),SignIn.class));
                            return;
                        }



                    }
                });
            }
        });

    }




    public void SignUpAction(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp.class));
        finish();
    }
}