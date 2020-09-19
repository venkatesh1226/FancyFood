package com.example.basic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText email,password,name;
    Button signup;
    TextView login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            Intent i= new Intent(MainActivity.this,UserActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    void init(){
        mAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.login);
        login=findViewById(R.id.txt_login);
    }
    void listeners(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent (MainActivity.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length()==0){
                    name.setError("Name Required");
                }
                if (email.getText().toString().trim().length()==0){
                    email.setError("Email Required");
                }
               else  if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                     email.setError("Enter Correct Email");
                 }

               else if (password.getText().toString().trim().length()<6){
                   password.setError("Enter password of more than 6 characters");
                }

               else{
                   mAuth.createUserWithEmailAndPassword
                           (email.getText().toString(), password.getText().toString())
                           .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()){

                                       Toast.makeText(getApplicationContext(), "Signup successfull", Toast.LENGTH_SHORT).show();
                                       setDisplayName(name.getText().toString());
                                       store();

                                   }
                                   else
                                   {
                                       Toast.makeText(MainActivity.this,"Something Went Wrong :(" , Toast.LENGTH_SHORT).show();
                                       Log.e("ERROR",task.getException().getMessage());
                                   }

                               }
                           });



                }
            }
        });
    }
    void setDisplayName(String name){
        UserProfileChangeRequest profile =new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        mAuth.getCurrentUser().updateProfile(profile).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR",task.getException().getMessage());
                }
            }
        });
    }

    void store(){
        FirebaseDatabase base=FirebaseDatabase.getInstance();
        DatabaseReference ref=base.getReference("name");
        ref.setValue(password.getText().toString());
    }
}