package com.example.final_project_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.Client;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.example.final_project_app.helpers.FBshortcut.refBusiness;
import static com.example.final_project_app.helpers.FBshortcut.refClients;

public class fragment1 extends Fragment {
    EditText name_editT, address_editT;
    ImageView logo_field;
    ImageButton imgbut1, imgbut2;
    Button changeLogo;
    ValueEventListener bListener;

    String business_id, name, address,  logo_link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment1, container, false);

        name_editT = (EditText)v.findViewById(R.id.editTextTextPersonName_name);
        address_editT = (EditText)v.findViewById(R.id.edit_address);
        logo_field = (ImageView) v.findViewById(R.id.logo_display);
        imgbut1 = (ImageButton) v.findViewById(R.id.imageButton_edit1);
        imgbut2 = (ImageButton) v.findViewById(R.id.imageButton_edit2);
        changeLogo = (Button) v.findViewById(R.id.change_logoB);


        imgbut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(1);
            }
        });
        imgbut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(2);
            }
        });
        changeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_new_pic();
            }
        });

        business_id = this.getArguments().getString("business_idB");

        bListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String id = (String) data.getKey();
                    Business businessTemp = data.getValue(Business.class);
                    if (business_id.equals(id)){
                        name_editT.setText(businessTemp.getBusiness_name());
                        address_editT.setText(businessTemp.getBusiness_address());
                        display_logo(businessTemp.getBusiness_logoLink());
                        logo_link = businessTemp.getBusiness_logoLink();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refBusiness.addValueEventListener(bListener);

        return v;
    }

    public void display_logo(String link){
        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child("logos/"+link+".png");
        try {
            File local_file = File.createTempFile("tempFile",".jpg");
            ref.getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    logo_field.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void upload_new_pic(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            FirebaseStorage storage;
            StorageReference storageReference;
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            Uri image = data.getData();
            final String randomKey = UUID.randomUUID().toString();
            StorageReference ref = storageReference.child("logos/"+randomKey+".png");
            ref.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    logo_field.setImageURI(image);
                    logo_link = randomKey;
                    saveData(3);
                }
            });
        }
    }

    public void saveData(int requestCode){
        if (requestCode == 1){
            name = name_editT.getText().toString().trim();
            if (!name.isEmpty()){
                refBusiness.child(business_id).child("business_name").setValue(name);
            }
        }else if (requestCode == 2){
            address = address_editT.getText().toString().trim();
            if (!address.isEmpty()){
                refBusiness.child(business_id).child("business_address").setValue(address);
            }
        }else if (requestCode == 3){
            refBusiness.child(business_id).child("business_logoLink").setValue(logo_link);
        }

    }

}