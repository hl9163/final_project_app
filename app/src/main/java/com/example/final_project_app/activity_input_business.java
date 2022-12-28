package com.example.final_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class activity_input_business extends AppCompatActivity {
    Intent gi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_business);
        gi = getIntent();
        String x1 = gi.getStringExtra("user_id");
        Log.i("user_id",x1);
    }
}