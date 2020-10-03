package com.example.fancyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuItemsActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView menu;
    DatabaseReference ref;
    MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_items);
        init();
        listeners();
    }
    void init(){
        fab=findViewById(R.id.fab_add);
        menu=findViewById(R.id.list);
        ref=FirebaseDatabase.getInstance().getReference().child("Menu/");
        FirebaseRecyclerOptions<Item> options=
                new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(ref,Item.class)
                .build();
        menuItem=new MenuItem(options);
        menu.setLayoutManager(new LinearLayoutManager(this));
        menu.setAdapter(menuItem);

    }



    void listeners(){
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                DialogAddMenuItem dialog =new DialogAddMenuItem();
                dialog.show(getSupportFragmentManager(),"CreateAddMenuDialog");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        menuItem.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        menuItem.stopListening();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}