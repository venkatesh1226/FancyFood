package com.example.fancyfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.UUID;

public class ReviewDetails extends AppCompatActivity{
    LinearLayout linearLayout,ll;
    TextView resName,resAddress,resYear,resArea,resPhoneno,resCuisines;
    String uid = UUID.randomUUID().toString();
    ArrayList<Uri> imageUri;
    ArrayList<String> imageNames;
    Bundle b= new Bundle();
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400);
    String name,address,year,phoneno,area,cuisines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
        imageUri = (ArrayList<Uri>) getIntent().getSerializableExtra("uri");
        imageNames = getIntent().getStringArrayListExtra("imageNames");
        b = getIntent().getExtras();
        linearLayout = findViewById(R.id.ll_images);
        ll = findViewById(R.id.review_res_info);
        resName = findViewById(R.id.review_res_name);
        resAddress = findViewById(R.id.review_res_address);
        resYear = findViewById(R.id.review_res_year);
        resArea = findViewById(R.id.review_res_area);
        resPhoneno = findViewById(R.id.review_res_phoneno);
        resCuisines = findViewById(R.id.review_res_cuisines);
        name = b.getString("name");
        address = b.getString("address");
        year = b.getString("year");
        phoneno = b.getString("phoneno");
        area = b.getString("area");
        cuisines = b.getString("cuisines");
        resName.setText(name);
        resYear.setText(year);
        resPhoneno.setText(phoneno);
        resCuisines.setText(cuisines);
        resAddress.setText(address);
        resArea.setText(area);
        for(int i = 0 ;i<imageUri.size();i++){
            createImages(imageUri.get(i));
        }
    }
    public void createImages(Uri uri){
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(params);
        iv.setImageURI(uri);
        linearLayout.addView(iv);
    }
    public void reviewImages(MenuItem menuItem)
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.review_images,menu);
        return true;
    }
    public void completedDetails(View view){
        uploadDetails();
        uploadImages();
    }
    public void uploadDetails(){
        CreateUser cUser = new CreateUser(name,address,year,phoneno,area,cuisines);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Restaurant");
        database.child(uid).setValue(cUser);
    }
    public void uploadImages(){
        StorageReference storage = FirebaseStorage.getInstance().getReference("Restaurant Images").child(uid);
        for(int i=0;i<imageUri.size();i++)
        {
            storage.child(imageNames.get(i)).putFile(imageUri.get(i));
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}