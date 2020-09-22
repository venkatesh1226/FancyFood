package com.example.fancyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
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
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
        TextView resName = new TextView(this);
        TextView th=findViewById(R.id.hello);
        th.setText("tuytuyuy");
        TextView resAddress = new TextView(this);
        TextView resYear = new TextView(this);
        TextView resArea = new TextView(this);
        TextView resPhoneno = new TextView(this);
        TextView resCuisines = new TextView(this);
        Bundle bundle = getIntent().getExtras();
        imagesUriList = (ArrayList<Uri>) getIntent().getSerializableExtra("uriList");
        linearLayout = findViewById(R.id.ll_images);
        ll = findViewById(R.id.review_res_info);
        resName.setLayoutParams(params);
        resAddress.setLayoutParams(params);
        resYear.setLayoutParams(params);
        resArea.setLayoutParams(params);
        resPhoneno.setLayoutParams(params);
        resCuisines.setLayoutParams(params);
        if(bundle!=null) {
            resName.setText(bundle.getString("resName"));
            resAddress.setText(bundle.getString("resAddress"));
            resYear.setText(bundle.getString("resYear"));
            resArea.setText(bundle.getString("resArea"));
            resPhoneno.setText(bundle.getString("resPhoneno"));
            resCuisines.setText(bundle.getString("resCuisines"));
        }
        ll.addView(resName);
        ll.addView(resAddress);
        ll.addView(resYear);
        ll.addView(resArea);
        ll.addView(resPhoneno);
        ll.addView(resCuisines);
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