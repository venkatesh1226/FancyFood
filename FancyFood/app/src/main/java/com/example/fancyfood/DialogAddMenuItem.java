package com.example.fancyfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class DialogAddMenuItem extends DialogFragment {
    ImageView imgDish;
    EditText edtName,edtPrice;
    Button btnBrowse,btnCancel,btnAdd;
    TextView txtImage;
    DatabaseReference ref;
    Uri uri;
    Uri uri2;
    public static final int PICK_IMAGE=404;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.dialog_addmenu_item,container,false);
       AlertDialog.Builder builder =new AlertDialog.Builder(getContext()).setView(view);
       builder.setCancelable(false);
        init(view);
        listeners();
        return view;
    }
   private void init(View v){
        imgDish=v.findViewById(R.id.dish_item);
        edtName=v.findViewById(R.id.edt_name);
        edtPrice=v.findViewById(R.id.edt_price);
        btnBrowse=v.findViewById(R.id.btn_browse);
        btnCancel=v.findViewById(R.id.btn_cancel);
        btnAdd=v.findViewById(R.id.add);
        txtImage=v.findViewById(R.id.edt_browse);
    }

    private void listeners(){
        btnBrowse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType( "image/*");
                startActivityForResult(Intent.createChooser(intent, "pick an image"), PICK_IMAGE);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().trim().length()==0){
                    edtName.setError("Name Is Required");
                }
                else if(edtPrice.getText().toString().trim().length()==0){
                    edtPrice.setError("Enter Price of Dish");
                }
                else if (imgDish.getVisibility()==View.GONE){
                    Toast.makeText(getContext(),"Set Images",Toast.LENGTH_SHORT).show();

                }
                else{
                    ref= FirebaseDatabase.getInstance().getReference();
                    final String id=ref.push().getKey();
                    Item item=new Item(id,
                            edtName.getText().toString(),
                            Integer.valueOf(edtPrice.getText().toString()),
                            "",UUID.randomUUID().toString());
                    ref.child("Menu").child(id).setValue(item);
                    final StorageReference imgRef= FirebaseStorage.getInstance().getReference().child("Menu").child(id);
                    imgRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri link) {
                                    uri2=link;
                                    Log.e("Link",link.toString());
                                    ref.child("Menu").child(id).child("image").setValue(link.toString());
                                }
                            });

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getContext(),exception.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });


                    Toast.makeText(getContext(), "Successfully Added "+edtName.getText().toString(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==PICK_IMAGE&&resultCode== Activity.RESULT_OK&&null !=data.getData()){
            imgDish.setVisibility(View.VISIBLE);
            imgDish.setImageURI(data.getData());
            uri=data.getData();
            txtImage.setText("Edit Photo");

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
