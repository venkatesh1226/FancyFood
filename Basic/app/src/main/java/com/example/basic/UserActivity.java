package com.example.basic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    TextView name;
    Button logout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        name=findViewById(R.id.txt_name);
        logout =findViewById(R.id.logout);
        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase base =FirebaseDatabase.getInstance();
        DatabaseReference ref=base.getReference("name");

        name.setText(mAuth.getCurrentUser().getDisplayName()+"Signed Up With"+mAuth.getCurrentUser().getEmail()+"password"+ref.getKey());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i=new Intent(UserActivity.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        ArrayList<Integer> a=new ArrayList<>();
        for(int i=0;i<10;i++)
            a.add(i);
        DatabaseReference mref =FirebaseDatabase.getInstance().getReference().child("Numbers").push();
        mref.setValue(a);

        final ArrayList<Integer>[] b = new ArrayList[]{new ArrayList<>()};
        final DatabaseReference getref=FirebaseDatabase.getInstance().getReference().child("Numbers");
        getref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                           b[0] = (ArrayList<Integer>) ds.getValue();
                        Log.e("ERROR",ds.getKey()+ b[0].toString());

//                            Log.e("ERROR",ds.getKey()+ b[0].toString());
//                        getref.child("/"+ds.getKey()).removeValue();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference r=FirebaseDatabase.getInstance().getReference();

        r=r.child("Numbers");


        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    Log.e("ERROR1",  d.getValue().toString());

                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Numbers/");
                    reference.removeValue();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}