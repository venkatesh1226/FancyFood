package com.example.fancyfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import static com.example.fancyfood.R.layout.menu_item;

public class MenuItem  extends FirebaseRecyclerAdapter<Item, MenuItem.MenuViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MenuItem(@NonNull FirebaseRecyclerOptions<Item> options,ArrayList<Item> menu) {
        super(options);
        this.menu=menu;
    }

    private Context context;
    private ArrayList<Item> menu;

    @Override
    protected void onBindViewHolder(@NonNull MenuViewHolder holder, final int i, @NonNull Item item) {

        Glide.with(context).load(menu.get(i).getImage()).into(holder.dishImage);
        holder.txtName.setText(menu.get(i).getName());
        holder.txtPrice.setText(menu.get(i).getPrice());
        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                delete(i);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                edit(i);
            }
        });
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        final View view = LayoutInflater.from(context).inflate(menu_item,parent,false);

        return new MenuViewHolder(view);
    }

    private void delete(int i){
        Toast.makeText(context, "Create Dialog To Delete", Toast.LENGTH_SHORT).show();
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

