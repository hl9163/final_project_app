package com.example.final_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class activity_categories extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView titleC;
    Spinner spinnerCity;
    Intent gi;

    int pos;
    String subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        titleC = (TextView) findViewById(R.id.titleC);

        gi = getIntent();
        pos = gi.getIntExtra("cat_number",-1);
        if (pos == -1){
            finish();
        }
        subject = getResources().getStringArray(R.array.subjects)[pos];
        titleC.setText(subject);

        spinnerCity = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        spinnerCity.setAdapter(spinner_adapter);
        spinnerCity.setOnItemSelectedListener(this);





    }

    public void back_to_menu_c(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}