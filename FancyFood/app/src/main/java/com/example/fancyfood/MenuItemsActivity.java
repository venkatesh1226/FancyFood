package com.example.fancyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuItemsActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView menu;
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
        menu.setLayoutManager(new LinearLayoutManager(this));

    }


}