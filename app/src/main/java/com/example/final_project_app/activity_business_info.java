package com.example.final_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class activity_business_info extends AppCompatActivity {
    Intent gi;
    String business_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);

         gi = getIntent();
         business_id = gi.getStringExtra("businessId");


    }
}