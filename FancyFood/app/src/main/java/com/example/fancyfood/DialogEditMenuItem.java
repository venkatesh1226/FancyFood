package com.example.fancyfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DialogEditMenuItem extends DialogFragment {
    EditText edtName,edtPrice;
    ImageView dishImage;
    Button browse,edit,cancel;
    Item item;
    Uri uri;
    int changedImage;
    public static final int PICK_IMAGE=404;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.dialog_editmenu_item,container,false);
        AlertDialog.Builder builder =new AlertDialog.Builder(getContext()).setView(view);
        builder.setCancelable(false);

        item=getArguments().getParcelable("MENU_ITEM");
        init(view);
        listeners();
        return view;
    }

    private void listeners() {
        browse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i,"Select Image -"),PICK_IMAGE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().length()==0){
                    edtName.setError("Name Required");
                }
                else if (edtPrice.getText().toString().length()==0){
                    edtPrice.setError("Price Required");
                }
                else{
                    final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Menu").child(item.getId());
                    final StorageReference imgRef= FirebaseStorage.getInstance().getReference().child("Menu").child(item.getId());
                    Item i=new Item(item.getId(),edtName.getText().toString(),Integer.valueOf(edtPrice.getText().toString()),item.getImage(),item.getHotelId());

                    if (changedImage==1){
                        ref.setValue(i);
                        imgRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        ref.child("image").setValue(uri.toString());
                                    }
                                });
                            }
                        });

                    }
                    else {
                        if (i.equals(item)){
                            Toast.makeText(getContext(),"Change Values To Update",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ref.setValue(i);
                        }

                    }
                        dismiss();

                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            if (requestCode==PICK_IMAGE&&data.getData()!=null&&resultCode== Activity.RESULT_OK){
                dishImage.setImageURI(data.getData());
                uri=data.getData();
                changedImage=1;
            }
        }
        catch(Exception e){
            super.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(View v) {
        edtName=v.findViewById(R.id.edt_name);
        edtPrice=v.findViewById(R.id.edt_price);
        dishImage=v.findViewById(R.id.dish_item);
        browse=v.findViewById(R.id.btn_browse);
        edit=v.findViewById(R.id.update);
        cancel=v.findViewById(R.id.btn_cancel);

        edtName.setText(item.getName());
        edtPrice.setText(String.valueOf(item.getPrice()));
        Glide.with(getContext()).load(item.getImage()).into(dishImage);
        changedImage=-1;



    }

}
