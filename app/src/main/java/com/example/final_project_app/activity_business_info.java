package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.Service;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;

public class activity_business_info extends AppCompatActivity {
    TextView nameF, addressF;
    ListView service_listF;
    ImageView logoF;
    ValueEventListener bListener;
    Intent gi;
    Intent si;
    AlertDialog.Builder adb;

    String business_id;
    String user_id;
    String name;
    String address;
    String city;
    String logo_link;
    ArrayList<Service> business_services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);

         gi = getIntent();
         business_id = gi.getStringExtra("businessId");
         user_id = gi.getStringExtra("user_id");

         nameF = (TextView) findViewById(R.id.nameTV);
         addressF = (TextView) findViewById(R.id.AddressTV);
         service_listF = (ListView) findViewById(R.id.show_service);
         logoF = (ImageView) findViewById(R.id.logoVeiw);

         bListener = new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot data : snapshot.getChildren()){
                     String id = (String) data.getKey();
                     Business businessTemp = data.getValue(Business.class);
                     if (business_id.equals(id)){
                         name = businessTemp.getBusiness_name();
                         address = businessTemp.getBusiness_address();
                         city = businessTemp.getBusiness_city();
                         business_services = businessTemp.getBusiness_services();
                         logo_link = businessTemp.getBusiness_logoLink();
                         show_info();

                     }
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         };
        refBusiness.addValueEventListener(bListener);

    }
    public void show_info(){
        nameF.setText(name);
        String add = address+ "," + city;
        addressF.setText(add);
        display_logo(logo_link);
        String [] arr = new String[business_services.size()];
        for (int i = 0; i <business_services.size();i++){
            arr[i] = business_services.get(i).getService_name()+", "+
                    business_services.get(i).getService_time()+"ד',"+
                    business_services.get(i).getService_cost()+" שח ";
        }
        ArrayAdapter<String>serviceAdp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
        service_listF.setAdapter(serviceAdp);

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
                    logoF.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void make_new_q(View view) {
        if (user_id != null){
            si = new Intent(this,activity_new_queue.class);
            si.putExtra("businessId", business_id);
            si.putExtra("user_id", user_id);
            startActivity(si);
        }else{
            adb = new AlertDialog.Builder(this);
            adb.setTitle("עליך להתחבר!");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog ad = adb.create();
            ad.show();
        }
    }
}