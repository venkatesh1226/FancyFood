package com.example.fancyfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RestaurantInfo extends AppCompatActivity {
    EditText rName,rAddress,rPhoneNo,rYear,rCuisines,rArea;
    public ArrayList<Uri> imageUri = new ArrayList<>();
    final int PICK_IMAGE = 1;
    LinearLayout linearLayout;
    Bundle bundle = new Bundle();
    int i = 1 , j = 51;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        linearLayout = findViewById(R.id.ll);
        rName = findViewById(R.id.restaurant_name);
        rAddress = findViewById(R.id.restaurant_address);
        rPhoneNo = findViewById(R.id.phone_number);
        rYear = findViewById(R.id.restaurant_year_started);
        rCuisines = findViewById(R.id.cuisines_offered);
        rArea = findViewById(R.id.area);


    }
    public void browsingFiles(View view){
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery,"Pick an image"),PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data.getData()) {
            viewImages(data.getData());
        }
    }
    public void viewImages(Uri uri){
        imageUri.add(uri);
        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);
        iv.setImageURI(uri);
        linearLayout.addView(iv);
    }
   public void nextButton(View view){
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
                if(rArea.length()==0)
                    rArea.setError("Area cannot be empty");
                if(imageUri.size()==0)
                    Toast.makeText(this,"Please provide some photos",Toast.LENGTH_LONG).show();
                if(rName.length()!=0&&rAddress.length()!=0&&rPhoneNo.length()!=0&&rYear.length()!=0&&rCuisines.length()!=0&&rArea.length()!=0&&imageUri.size()!=0){
                    resInfo();
                    Intent i = new Intent(RestaurantInfo.this,ReviewDetails.class);
                    i.putExtras(bundle);
                    i.putExtra("uriList",imageUri);
                    startActivity(i);
                }
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