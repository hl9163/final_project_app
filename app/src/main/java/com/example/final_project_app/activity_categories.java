package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;

public class activity_categories extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    TextView titleC;
    Spinner spinnerCity;
    ListView list;
    Intent gi;
    CategoriesBusinessAdapter customadp;
    ValueEventListener bListener;

    ArrayList<Business> businessList = new ArrayList<Business>();
    ArrayList<String> businessIdList = new ArrayList<String>();
    int posCategory;
    String subject;
    String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        titleC = (TextView) findViewById(R.id.titleC);

        gi = getIntent();
        posCategory = gi.getIntExtra("cat_number",-1);
        if (posCategory == -1){
            finish();
        }
        subject = getResources().getStringArray(R.array.subjects)[posCategory];
        titleC.setText(subject);

        spinnerCity = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        spinnerCity.setAdapter(spinner_adapter);
        spinnerCity.setOnItemSelectedListener(this);
        Service s = new Service("sdfdsf","sdfsdf","sdfdsfd");
        ArrayList<Service> business_services = new ArrayList<Service>();
        business_services.add(s);
        Business b = new Business("a","dfgdfgdfg","addddd","city","dfgdfgdfg",business_services,"fgfdg",true);

        Business [] l = {b};
        list = (ListView) findViewById(R.id.listElements);
        list.setOnItemClickListener(this);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);







    }
    @Override
    protected void onPause() {
        super.onPause();
        if (bListener!=null) {
            refBusiness.removeEventListener(bListener);
        }

    }

    public void back_to_menu_c(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int positionCity, long id) {
        if (positionCity != 0){
            city = getResources().getStringArray(R.array.cities)[positionCity];
            bListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    businessList.clear();
                    businessIdList.clear();
                    for (DataSnapshot data: snapshot.getChildren()){
                        String id = (String) data.getKey();
                        Business businessTemp = data.getValue(Business.class);
                        if (businessTemp.getSubject().equals(subject) && businessTemp.getBusiness_city().equals(city) && businessTemp.isIs_open()){
                            businessList.add(businessTemp);
                            businessIdList.add(id);
                        }
                    }
                    Business [] businessList_adp = new Business[businessList.size()];
                    String [] businessIdList_adp = new String[businessIdList.size()];
                    for (int i=0;i<businessList_adp.length;i++){
                        businessList_adp[i] = businessList.get(i);
                        businessIdList_adp[i] = businessIdList.get(i);
                    }
                    customadp = new CategoriesBusinessAdapter(getApplicationContext(), businessList_adp);
                    list.setAdapter(customadp);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            refBusiness.addValueEventListener(bListener);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}