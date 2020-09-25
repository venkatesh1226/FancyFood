package com.example.fancyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.UUID;

public class ReviewDetails extends AppCompatActivity {

    public ArrayList<Uri> imagesUriList;
    Bundle bundle;
    LinearLayout linearLayout,ll;
    TextView resName,resAddress,resYear,resArea,resPhoneno,resCuisines;
    FirebaseDatabase fd;
    FirebaseStorage fStorage;
    DatabaseReference database;
    StorageReference storage;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
        bundle = getIntent().getExtras();
        imagesUriList = (ArrayList<Uri>) getIntent().getSerializableExtra("uriList");
        linearLayout = findViewById(R.id.ll_images);
        ll = findViewById(R.id.review_res_info);
        resName = findViewById(R.id.review_res_name);
        resAddress = findViewById(R.id.review_res_address);
        resYear = findViewById(R.id.review_res_year);
        resArea = findViewById(R.id.review_res_area);
        resPhoneno = findViewById(R.id.review_res_phoneno);
        resCuisines = findViewById(R.id.review_res_cuisines);
        resName.setText(bundle.getString("resName"));
        resAddress.setText(bundle.getString("resAddress"));
        resYear.setText(bundle.getString("resYear"));
        resArea.setText(bundle.getString("resArea"));
        resPhoneno.setText(bundle.getString("resPhoneno"));
        resCuisines.setText(bundle.getString("resCuisines"));
        if(imagesUriList.size()!=0)
        {
            for(int i=0;i<imagesUriList.size();i++)
                createImages(imagesUriList.get(i));
        }
    }
    public void createImages(Uri uri){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(params);
        iv.setImageURI(uri);
        linearLayout.addView(iv);
    }
    public void createUser(View view){
        uploadResDetails();
    }
    public void uploadResDetails(){
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setTitle("Uploading Details....");
        pDialog.show();
        String rName = bundle.getString("resName");
        String rAddress = bundle.getString("resAddress");
        String rArea = bundle.getString("reArea");
        String rYear = bundle.getString("resYear");
        String rPhoneno = bundle.getString("resPhoneno");
        String rCuisines = bundle.getString("resCuisines");
        final String uid = UUID.randomUUID().toString();
        CreateUser cUser = new CreateUser(rName,rAddress,rYear,rPhoneno,rArea,rCuisines,uid);
        fd = FirebaseDatabase.getInstance();
        fStorage = FirebaseStorage.getInstance();
        database = fd.getReference("Restaurant");
        database.child(uid).setValue(cUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pDialog.dismiss();
                Toast.makeText(ReviewDetails.this,"Uploading details is successful",Toast.LENGTH_LONG).show();

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pDialog.dismiss();
                Toast.makeText(ReviewDetails.this,"Uploading details is unsuccessful",Toast.LENGTH_LONG).show();
            }
        });
        uploadResImages(uid);
    }
    public void uploadResImages(String uid){
        final ProgressDialog piDialog = new ProgressDialog(this);
        piDialog.setTitle("Uploading Restaurant Images");
        piDialog.show();
        storage = fStorage.getReference("Restaurant Images");
        StorageReference ref =storage.child(uid);
        for(int i=0;i<imagesUriList.size();i++) {
            ref.child("image " + (i + 1)).putFile(imagesUriList.get(i));
        }
            piDialog.dismiss();
            Toast.makeText(ReviewDetails.this,"Uploading images is successful",Toast.LENGTH_LONG).show();
    }
    public void review(MenuItem menuItem)
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = new MenuInflater(this);
        m.inflate(R.menu.revirew,menu);
        return true;
    }
}