package com.example.fancyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

public class Review extends AppCompatActivity {
    private RecyclerView rView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    public ArrayList<Images> imageUriList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ArrayList<Uri> uri = (ArrayList<Uri>) getIntent().getSerializableExtra("uri");
        for(int i=0;i<uri.size();i++)
            imageUriList.add(new Images(uri.get(i)));
        rView =findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new ImagesAdapter(imageUriList);
        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);
    }
}