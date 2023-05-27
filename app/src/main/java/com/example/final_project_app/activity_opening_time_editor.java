package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.WorkWeek;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;

public class activity_opening_time_editor extends AppCompatActivity{
    //  implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener
//    ListView show_time;
//    Spinner frequency_spinner;
//    ArrayAdapter<String> adp;
//    EditText openTime_et,closeTime_et;
//    Button sun, mon, tue, wed, tru, fri, sat, saveData;
//    ValueEventListener bListener;
//    Intent gi;
//
//    ArrayList<Boolean> days_of_work =  new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false, false));
//    ArrayList<String> timeWindows = new ArrayList<String>();
//    String business_id, openTime, closeTime;
//    int pos;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_opening_time_editor);
//
//        openTime_et = (EditText) findViewById(R.id.openTimeEt);
//        closeTime_et = (EditText) findViewById(R.id.closeTimeEt);
//        frequency_spinner = (Spinner) findViewById(R.id.freqSpinner);
//        sun = (Button) findViewById(R.id.sunButton);
//        mon = (Button) findViewById(R.id.monButton);
//        tue = (Button) findViewById(R.id.tueButton);
//        wed = (Button) findViewById(R.id.wedButton);
//        tru = (Button) findViewById(R.id.truButton);
//        fri = (Button) findViewById(R.id.friButton);
//        sat = (Button) findViewById(R.id.satButton);
//        show_time = (ListView) findViewById(R.id.showTimes);
//        saveData = (Button) findViewById(R.id.saveHouers);
//
//        gi = getIntent();
//        business_id = gi.getStringExtra("business_id");
//
//        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.freq, android.R.layout.simple_spinner_item);
//        frequency_spinner.setAdapter(spinner_adapter);
//        frequency_spinner.setOnItemSelectedListener(this);
//
//        show_time.setOnItemClickListener(this);
//        show_time.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//
//        saveData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openTime = openTime_et.getText().toString().trim();
//                closeTime = closeTime_et.getText().toString().trim();
//                if (is_good()){
//                    save_info();
//                }
//            }
//        });
//
////        bListener = new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                for (DataSnapshot data: snapshot.getChildren()){
////                    String id = (String) data.getKey();
////                    if (business_id.equals(id)){
////                        Business businessTemp = data.getValue(Business.class);
////                        WorkWeek s = businessTemp.getWork_schedule();
////                        ArrayList<Boolean> days_of_work_list = s.getDays_of_work();
////                        openTime_et.setText(s.getRegular_day().get(0));
////                        closeTime_et.setText(s.getFriday_queues().get(s.getRegular_day().size()-1));
////                        frequency_spinner.setSelection(s.getFrequency_index());
////                        if (days_of_work_list.get(0)){
////                            sun.performClick();
////                        }
////                        if (days_of_work_list.get(1)){
////                            mon.performClick();
////                        }
////                        if (days_of_work_list.get(2)){
////                            tue.performClick();
////                        }
////                        if (days_of_work_list.get(3)){
////                            wed.performClick();
////                        }
////                        if (days_of_work_list.get(4)){
////                            tru.performClick();
////                        }
////                        if (days_of_work_list.get(5)){
////                            fri.performClick();
////                        }
////                        if (days_of_work_list.get(6)){
////                            sat.performClick();
////                        }
////                        break;
////                    }
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        };
////        refBusiness.addListenerForSingleValueEvent(bListener);
////        refBusiness.removeEventListener(bListener);
//
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            get_timeFrames(openTime,closeTime,position);
//            String [] arr = new String[timeWindows.size()];
//            for (int x = 0; x< timeWindows.size();x++){
//                arr[x] = timeWindows.get(x);
//            }
//            adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
//            show_time.setAdapter(adp);
//            pos = position;
//
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//
//    public void sun_button(View view) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            days_of_work.set(0,!days_of_work.get(0));
//            if (days_of_work.get(0)){
//                sun.setText("יום א - פתוח");
//                Toast myToast = Toast.makeText(this, "I'm a toast!", Toast.LENGTH_LONG);
//                myToast.show();
//            }else{
//                sun.setText("יום א - סגור");
//            }
//        }
//
//    }
//
//    public void mon_button(View view) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            days_of_work.set(1,!days_of_work.get(1));
//            if (days_of_work.get(1)){
//                mon.setText("יום ב - פתוח");
//            }else{
//                mon.setText("יום ב - סגור");
//            }
//
//        }
//    }
//
//    public void tue_button(View view) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            days_of_work.set(2,!days_of_work.get(2));
//            if (days_of_work.get(2)){
//                tue.setText("יום ג - פתוח");
//            }else{
//                tue.setText("יום ג - סגור");
//            }
//
//        }
//    }
//
//    public void wed_button(View view) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            days_of_work.set(3,!days_of_work.get(3));
//            if (days_of_work.get(3)){
//                wed.setText("יום ד - פתוח");
//            }else{
//                wed.setText("יום ד - סגור");
//            }
//
//        }
//    }
//
//    public void tru_button(View view) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            days_of_work.set(4,!days_of_work.get(4));
//            if (days_of_work.get(4)){
//                tru.setText("יום ה - פתוח");
//            }else{
//                tru.setText("יום ה - סגור");
//            }
//
//        }
//    }
//
//    public void fri_button(View view) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            days_of_work.set(5,!days_of_work.get(5));
//            if (days_of_work.get(5)){
//                fri.setText("יום ו - פתוח");
//            }else{
//                fri.setText("יום ו - סגור");
//            }
//
//        }
//    }
//
//    public void sat_button(View view) {
//        openTime = openTime_et.getText().toString().trim();
//        closeTime = closeTime_et.getText().toString().trim();
//        if (is_good()){
//            days_of_work.set(6,!days_of_work.get(6));
//            if (days_of_work.get(6)){
//                sat.setText("יום ש - פתוח");
//            }else{
//                sat.setText("יום ש - סגור");
//            }
//
//        }
//    }
//    public  void get_timeFrames(String start, String end, int freq_index){
//        boolean is_start = true;
//        int jumps = 0;
//        for (int i = Integer.parseInt(start); i<Integer.parseInt(end);i++){
//            int j = 0;
//            while (j < 60){
//                if (jumps == freq_index || is_start){
//                    is_start = false;
//                    if (j==0){
//                        timeWindows.add(String.valueOf(i) + ":" + String.valueOf(j)+"0");
//                    }else{
//                        timeWindows.add(String.valueOf(i) + ":" + String.valueOf(j));
//                    }
//                    jumps = 0;
//                }else{
//                    jumps++;
//                }
//                j+=10;
//            }
//        }
//    }
//    public boolean is_good(){
//        if (!openTime.isEmpty() || !closeTime.isEmpty()){
//            if (Integer.parseInt(openTime)<24 && Integer.parseInt(closeTime)<24) {
//                if (Integer.parseInt(openTime) < Integer.parseInt(closeTime) || Integer.parseInt(closeTime) == 0) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//    public void save_info(){
//        WorkWeek w = new WorkWeek(days_of_work,timeWindows,pos);
//        refBusiness.child(business_id).child("work_schedule").setValue(w);
//        boolean is_open = false;
//        for (int i =0;i<days_of_work.size();i++){
//            if(days_of_work.get(i)){
//                is_open = true;
//            }
//        }
//        if (is_open){
//            refBusiness.child(business_id).child("is_open").setValue(true);
//        }else{
//            refBusiness.child(business_id).child("is_open").setValue(false);
//        }
//
//    }
}