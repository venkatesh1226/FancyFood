package com.example.fancyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewDetails extends AppCompatActivity {

    ArrayList<Uri> imagesUriList;
    LinearLayout linearLayout,ll;
    TextView resName,resAddress,resYear,resArea,resPhoneno,resCuisines;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
        Bundle bundle = getIntent().getExtras();
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
}