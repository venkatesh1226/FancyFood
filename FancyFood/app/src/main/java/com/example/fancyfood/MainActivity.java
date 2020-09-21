package com.example.fancyfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.URI;
import java.util.ArrayList;

import static android.content.Intent.*;

public class MainActivity extends AppCompatActivity {
    EditText rName,rAddress,rPhoneNo,rYear,rCuisines;
    Button btnBrowseFiles;
    ArrayList<Uri> imageUriList = new ArrayList<>();
    ImageView resImage;
    final int PICK_IMAGE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rName = findViewById(R.id.restaurant_name1);
        rAddress = findViewById(R.id.restaurant_address1);
        rPhoneNo = findViewById(R.id.phone_number);
        rYear = findViewById(R.id.restaurant_year_started);
        rCuisines = findViewById(R.id.cuisines_offered);
        resImage = findViewById(R.id.res_image);

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data.getData()) {
            resImage.setVisibility(View.VISIBLE);
            imageUriList.add(data.getData());
            resImage.setImageURI(data.getData());
        }
    }
    /* public void nextButton(View view){
        Button btnNext = findViewById(R.id.next_button);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ReviewDetails.class));
            }
        });
    }*/
}