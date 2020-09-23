package com.example.fancyfood;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.fancyfood.R.layout.menu_item;

public class MenuItem  extends FirebaseRecyclerAdapter<Item, MenuItem.MenuViewHolder> {

    DatabaseReference ref;

    public MenuItem(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    private Context context;

    @Override
    protected void onBindViewHolder(@NonNull MenuViewHolder holder, final int i, @NonNull final Item item) {


        //holder.dishImage.setImageURI(Uri.parse(item.getImage()));
        holder.txtName.setText("Name -"+item.getName());
        holder.txtPrice.setText("Price -"+String.valueOf(item.getPrice()));
        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                delete(item);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                edit(i);
            }
        });
        Log.e("Error",item.toString());
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        final View view = LayoutInflater.from(context).inflate(menu_item,parent,false);
        ref=FirebaseDatabase.getInstance().getReference();
        return new MenuViewHolder(view);
    }

    private void delete(final Item item){

        AlertDialog.Builder builder=
                new AlertDialog.Builder(context)
                        .setTitle("Are You Sure You Want To Delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ref.child(item.getId()).removeValue();
                        Toast.makeText(context, "Deleted "+item.getName(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }

    private void edit(int i){

    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{
        ImageView dishImage;
        TextView txtName,txtPrice;
        Button edit,delete;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
             dishImage=itemView.findViewById(R.id.img_dish);
             txtName=itemView.findViewById(R.id.txt_name);
             txtPrice=itemView.findViewById(R.id.txt_price);
             edit=itemView.findViewById(R.id.edit);
             delete=itemView.findViewById(R.id.delete);
        }
    }
}

