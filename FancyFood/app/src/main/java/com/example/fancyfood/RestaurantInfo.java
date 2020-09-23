package com.example.fancyfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RestaurantInfo extends AppCompatActivity {
    EditText rName,rAddress,rPhoneNo,rYear,rCuisines,rArea;
    Button btnBrowseFiles;
    public ArrayList<Uri> imageUriList = new ArrayList<>();
    final int PICK_IMAGE = 1;
    LinearLayout linearLayout;
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        linearLayout = findViewById(R.id.rela);
        rName = findViewById(R.id.restaurant_name);
        rAddress = findViewById(R.id.restaurant_address);
        rPhoneNo = findViewById(R.id.phone_number);
        rYear = findViewById(R.id.restaurant_year_started);
        rCuisines = findViewById(R.id.cuisines_offered);
        rArea = findViewById(R.id.area);


    }
    public void browsingFiles(View view){
        btnBrowseFiles = findViewById(R.id.btn_add_photos);
        btnBrowseFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery,"Pick an image"),PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data.getData()) {
            imageUriList.add(data.getData());
            viewImages();
        }
    }
    public void viewImages(){
        ImageView iv = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);
        iv.setImageURI(imageUriList.get(imageUriList.size()-1));
        linearLayout.addView(iv);
    }
   public void nextButton(View view){
        FloatingActionButton btnNext = findViewById(R.id.floatingActionButton);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rName.length()==0)
                    rName.setError("Name cannot be empty");
                if(rAddress.length()==0)
                    rAddress.setError("Address cannot be empty");
                if(rPhoneNo.length()==0)
                    rPhoneNo.setError("Phone number cannot be empty");
                if(rYear.length()==0)
                    rYear.setError("Year cannot be empty");
                if(rCuisines.length()==0)
                    rCuisines.setError("Cuisines cannot be empty");
                else{
                    resInfo();
                    Intent i = new Intent(RestaurantInfo.this,ReviewDetails.class);
                    i.putExtras(bundle);
                    i.putExtra("uriList",imageUriList);
                    startActivity(i);
                }
            }
        });
    }
    public void resInfo(){
        String name,address,year,area,phoneno,cuisines;
        name = rName.getText().toString();
        address = rAddress.getText().toString().trim();
        year = rYear.getText().toString().trim();
        area = rArea.getText().toString().trim();
        phoneno = rPhoneNo.getText().toString().trim();
        cuisines =rCuisines.getText().toString().trim();
        bundle.putString("resName",name);
        bundle.putString("resAddress",address);
        bundle.putString("resYear",year);
        bundle.putString("resArea",area);
        bundle.putString("resPhoneno",phoneno);
        bundle.putString("resCuisines",cuisines);
    }
}