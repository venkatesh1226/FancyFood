package com.example.fancyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewDetails extends AppCompatActivity {
    TextView tv1,tv2;
    Button btn;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
            setContentView(R.layout.activity_review_details);
            database = FirebaseDatabase.getInstance().getReference().child("Hyderabad");

            tv1 = findViewById(R.id.restaurant_name);
            tv2 = findViewById(R.id.restaurant_address);

            btn = findViewById(R.id.btn_get_details);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String resName = dataSnapshot.child("name").getValue().toString();
                            String resAddress = dataSnapshot.child("address").getValue().toString();
                            tv1.setText(resName);
                            tv2.setText(resAddress);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
    }
}