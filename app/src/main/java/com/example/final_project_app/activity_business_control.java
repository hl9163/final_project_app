package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.final_project_app.helpers.Business;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;

public class activity_business_control extends AppCompatActivity {
    Intent gi;
    TextView title_control;
    ValueEventListener bListener;

    String businessId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_control);

        gi = getIntent();
        businessId = gi.getStringExtra("business_id");

        title_control = (TextView) findViewById(R.id.textView2);

        bListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String id =  (String) data.getKey();
                    if (id.equals(businessId)){
                        Business businessTemp = data.getValue(Business.class);
                        title_control.setText( businessTemp.getBusiness_name() + "שלום ");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refBusiness.addValueEventListener(bListener);



    }
    @Override
    protected void onPause() {
        super.onPause();
        if (bListener!=null) {
            refBusiness.removeEventListener(bListener);
        }
    }
    /**
     * back button
     * <p>
     */
    public void back_to_main_bcc(View view) {
        finish();
    }
}