package com.example.fancyfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUp extends AppCompatActivity {
    EditText etEmail,etPassword,etConfirm;
   TextView tvSignIn;
   FloatingActionButton buSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        tvSignIn = findViewById(R.id.tvSignIn);
        buSignUp = findViewById(R.id.buSignUp);
    }


    public void SignUpAction(View view) {

    }
}