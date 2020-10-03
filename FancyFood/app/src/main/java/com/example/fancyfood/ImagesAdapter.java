package com.example.fancyfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.Viewholder> {
    public ArrayList<Images> imagesList ;
    private OnItemListener listener;
    public interface OnItemListener{
        void onDeleteItem(int position);
    }

    public void setListener(OnItemListener listener) {
        this.listener = listener;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        public ImageView iv;
        public Button b;
        public Viewholder(@NonNull View itemView, final OnItemListener Mlistener) {
            super(itemView);
            iv = itemView.findViewById(R.id.image_view);
            b = itemView.findViewById(R.id.delete_button);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Mlistener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                            Mlistener.onDeleteItem(position);
                    }
                }
            });
        }
    }

    public ImagesAdapter(ArrayList<Images> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_images,parent,false);
        Viewholder vh = new Viewholder(v,listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Images currentImage = imagesList.get(position);
        holder.iv.setImageURI(currentImage.getImageUri());
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
