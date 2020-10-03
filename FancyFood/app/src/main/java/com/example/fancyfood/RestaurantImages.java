package com.example.fancyfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class RestaurantImages extends AppCompatActivity {
    final int PICK_IMAGE = 1;
    RecyclerView rv;
    RecyclerView.LayoutManager rvLayout;
    ImagesAdapter rvAdapter;
    ArrayList<Images> imageList ;
    Bundle b = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_images);
        imageList = new ArrayList<>();
        b = getIntent().getExtras();
        buildRecyclerView();
    }

    public void buildRecyclerView(){
        rv = findViewById(R.id.id_recycler_view);
    rvLayout = new LinearLayoutManager(this);
    rvAdapter = new ImagesAdapter(imageList);
    rv.setHasFixedSize(true);
    rv.setLayoutManager(rvLayout);
    rv.setAdapter(rvAdapter);
    rvAdapter.setListener(new ImagesAdapter.OnItemListener() {
        @Override
        public void onDeleteItem(int position) {
            deleteImage(position);
        }
    });
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
            addImages(data.getData(),data.getData().getLastPathSegment().toString());
        }
    }
    public void addImages(Uri uri,String imageName){
        imageList.add(new Images(uri,imageName));
        rvAdapter.notifyDataSetChanged();
    }
    public void deleteImage(int position){
        imageList.remove(position);
        rvAdapter.notifyDataSetChanged();
    }
    public void nextButton(MenuItem menuItem){
        ArrayList<Uri> imageUri = new ArrayList<>();
        ArrayList<String> imageNames = new ArrayList<>();
        for(int i= 0;i<imageList.size();i++){
            Images im = imageList.get(i);
            Uri u = im.getImageUri();
            String s = im.getImageName();
            imageUri.add(u);
            imageNames.add(s);
        }
        Intent i = new Intent(RestaurantImages.this,ReviewDetails.class);
        i.putExtra("uri",imageUri);
        i.putExtra("imageNames",imageNames);
        i.putExtras(b);
        startActivity(i);
    }
    public void reviewDetails(MenuItem menuItem){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.review_details,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}