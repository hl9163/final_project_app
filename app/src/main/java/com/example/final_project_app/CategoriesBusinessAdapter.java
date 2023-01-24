package com.example.final_project_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.final_project_app.helpers.Business;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class CategoriesBusinessAdapter extends BaseAdapter {
    Context context;
    Business[] businessList;
    LayoutInflater inflter;

    public CategoriesBusinessAdapter(Context applicationContext, Business[] businessList) {
        this.context = applicationContext;
        this.businessList = businessList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return businessList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void return_businessId(int position){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custom_list_layout,null);
        TextView name = (TextView) convertView.findViewById(R.id.title);
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);
        TextView address = (TextView) convertView.findViewById(R.id.subTitle);
        name.setText(businessList[position].getBusiness_name());
        address.setText(businessList[position].getBusiness_address());

        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child("logos/"+businessList[position].getBusiness_logoLink()+".png");
        try {
            File local_file = File.createTempFile("tempFile",".jpg");
            ref.getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    logo.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //logo.setImageResource(R.drawable.ic_launcher_background);
        return convertView;
    }
}
