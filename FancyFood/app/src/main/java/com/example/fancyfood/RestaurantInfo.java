package com.example.fancyfood;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class RestaurantInfo extends AppCompatActivity {
    String name, address, year, area, phoneno, cuisines;
    EditText rName, rAddress, rPhoneNo, rYear, rCuisines, rArea;
    public ArrayList<Uri> imageUri = new ArrayList<>();
    final int PICK_IMAGE = 1;
    String  uid = UUID.randomUUID().toString();
    LinearLayout linearLayout;
    int i = 0, j = 50;
    Uri[] uriArray = new Uri[50];
    Bundle bundle = new Bundle();
    FirebaseDatabase fd;
    FirebaseStorage fStorage;
    DatabaseReference database;
    StorageReference storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        for (Uri u : uriArray) {
            u = null;
        }
        linearLayout = findViewById(R.id.ll);
        rName = findViewById(R.id.restaurant_name);
        rAddress = findViewById(R.id.restaurant_address);
        rPhoneNo = findViewById(R.id.phone_number);
        rYear = findViewById(R.id.restaurant_year_started);
        rCuisines = findViewById(R.id.cuisines_offered);
        rArea = findViewById(R.id.area);


    }

    public void browsingFiles(View view) {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(Intent.createChooser(gallery, "Pick an image"), PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data.getData()) {
            viewImages(data.getData());
        }
    }

    public void viewImages(final Uri uri) {
        uriArray[i] = uri;
        final Button b = new Button(this);
        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv.setImageURI(uri);
        b.setBackgroundResource(R.drawable.ic_delete1);
        iv.setId(i);
        b.setId(j);
        i++;
        j++;
        linearLayout.addView(iv);
        linearLayout.addView(b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("id", String.valueOf(b.getId()));
                linearLayout.removeView(findViewById((b.getId()) - 50));
                linearLayout.removeView(findViewById(b.getId()));
                uriArray[(b.getId()) - 50] = null;
                uploadImages(phoneno);
            }
        });
    }

    public void nextButton(View view) {
        for (Uri uri : uriArray) {
            if (uri != null) {
                imageUri.add(uri);
                uri = null;
            }
        }
        Set<Uri> s = new LinkedHashSet<>();
        s.addAll(imageUri);
        imageUri.clear();
        imageUri.addAll(s);
        if (rName.length() == 0)
            rName.setError("Name cannot be empty");
        if (rAddress.length() == 0)
            rAddress.setError("Address cannot be empty");
        if (rPhoneNo.length() == 0)
            rPhoneNo.setError("Phone number cannot be empty");
        if (rYear.length() == 0)
            rYear.setError("Year cannot be empty");
        if (rCuisines.length() == 0)
            rCuisines.setError("Cuisines cannot be empty");
        if (rArea.length() == 0)
            rArea.setError("Area cannot be empty");
        if (imageUri.size() == 0)
            Toast.makeText(this, "Please provide some photos", Toast.LENGTH_LONG).show();
        if (rName.length() != 0 && rAddress.length() != 0 && rPhoneNo.length() != 0 && rYear.length() != 0 && rCuisines.length() != 0 && rArea.length() != 0 && imageUri.size() != 0) {
            resInfo();
            storage = fStorage.getReference("Restaurant Images");
            StorageReference ref = storage.child(uid).child(imageUri.get(0).toString());
            ref.delete();
            Intent i = new Intent(RestaurantInfo.this, ReviewDetails.class);
            i.putExtras(bundle);
            i.putExtra("uriList", imageUri);
            startActivity(i);
        }
    }

    public void resInfo() {
        name = rName.getText().toString();
        address = rAddress.getText().toString().trim();
        year = rYear.getText().toString().trim();
        area = rArea.getText().toString().trim();
        phoneno = rPhoneNo.getText().toString().trim();
        cuisines = rCuisines.getText().toString().trim();
        uploadDetails();
    }
    public void uploadDetails(){
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setTitle("Uploading Details....");
        pDialog.show();
        CreateUser cUser = new CreateUser(name,address,year,phoneno,area,cuisines);
        fd = FirebaseDatabase.getInstance();
        fStorage = FirebaseStorage.getInstance();
        database = fd.getReference("Restaurant");
        database.child(uid).setValue(cUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pDialog.dismiss();
                Toast.makeText(RestaurantInfo.this,"Uploading details is successful",Toast.LENGTH_LONG).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pDialog.dismiss();
                        Toast.makeText(RestaurantInfo.this,"Uploading details is unsuccessful",Toast.LENGTH_LONG).show();
                    }
                });
        uploadImages(uid);
    }
    public void uploadImages(String uid){
        storage = fStorage.getReference("Restaurant Images");
        StorageReference ref =storage.child(uid);
        for(int i=0;i<imageUri.size();i++) {
            ref.child("image " + (i + 1)).putFile(imageUri.get(i));
        }
        Toast.makeText(RestaurantInfo.this,"Uploading images is successful",Toast.LENGTH_LONG).show();
    }
}