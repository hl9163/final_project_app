package com.example.final_project_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.final_project_app.helpers.WorkWeek;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;

public class fragment2 extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{
    ListView show_time;
    Spinner frequency_spinner;
    ArrayAdapter<String> adp;
    EditText openTime_et,closeTime_et;
    Button sun, mon, tue, wed, tru, fri, sat, saveData;

    ArrayList<Boolean> days_of_work =  new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false, false));
    ArrayList<String> timeWindows = new ArrayList<String>();
    String business_id, openTime, closeTime;
    int pos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment2, container, false);
        openTime_et = (EditText) v.findViewById(R.id.openTimeEt);
        closeTime_et = (EditText) v.findViewById(R.id.closeTimeEt);
        frequency_spinner = (Spinner) v.findViewById(R.id.freqSpinner);
        sun = (Button) v.findViewById(R.id.sunButton);
        mon = (Button) v.findViewById(R.id.monButton);
        tue = (Button) v.findViewById(R.id.tueButton);
        wed = (Button) v.findViewById(R.id.wedButton);
        tru = (Button) v.findViewById(R.id.truButton);
        fri = (Button) v.findViewById(R.id.friButton);
        sat = (Button) v.findViewById(R.id.satButton);
        show_time = (ListView) v.findViewById(R.id.showTimes);
        saveData = (Button) v.findViewById(R.id.saveHouers);

        business_id = this.getArguments().getString("business_idB");

        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.freq, android.R.layout.simple_spinner_item);
        frequency_spinner.setAdapter(spinner_adapter);
        frequency_spinner.setOnItemSelectedListener(this);

        show_time.setOnItemClickListener(this);
        show_time.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    days_of_work.set(0,!days_of_work.get(0));
                    if (days_of_work.get(0)){
                        sun.setText("יום א - פתוח");
                    }else{
                        sun.setText("יום א - סגור");
                    }

                }
            }
        });
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    days_of_work.set(1,!days_of_work.get(1));
                    if (days_of_work.get(1)){
                        mon.setText("יום ב - פתוח");
                    }else{
                        mon.setText("יום ב - סגור");
                    }

                }
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    days_of_work.set(2,!days_of_work.get(2));
                    if (days_of_work.get(2)){
                        tue.setText("יום ג - פתוח");
                    }else{
                        tue.setText("יום ג - סגור");
                    }

                }
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    days_of_work.set(3,!days_of_work.get(3));
                    if (days_of_work.get(3)){
                        wed.setText("יום ד - פתוח");
                    }else{
                        wed.setText("יום ד - סגור");
                    }

                }
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    days_of_work.set(4,!days_of_work.get(4));
                    if (days_of_work.get(4)){
                        tru.setText("יום ה - פתוח");
                    }else{
                        tru.setText("יום ה - סגור");
                    }

                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    days_of_work.set(5,!days_of_work.get(5));
                    if (days_of_work.get(5)){
                        fri.setText("יום ו - פתוח");
                    }else{
                        fri.setText("יום ו - סגור");
                    }

                }
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    days_of_work.set(6,!days_of_work.get(6));
                    if (days_of_work.get(6)){
                        sat.setText("יום ש - פתוח");
                    }else{
                        sat.setText("יום ש - סגור");
                    }

                }
            }
        });
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime = openTime_et.getText().toString().trim();
                closeTime = closeTime_et.getText().toString().trim();
                if (is_good()){
                    save_info();
                }
            }
        });



        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        openTime = openTime_et.getText().toString().trim();
        closeTime = closeTime_et.getText().toString().trim();
        if (is_good()){
            get_timeFrames(openTime,closeTime,position);
            String [] arr = new String[timeWindows.size()];
            for (int x = 0; x< timeWindows.size();x++){
                arr[x] = timeWindows.get(x);
            }
            adp = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
            show_time.setAdapter(adp);
            pos = position;

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public  void get_timeFrames(String start, String end, int freq_index){
        boolean is_start = true;
        int jumps = 0;
        for (int i = Integer.parseInt(start); i<Integer.parseInt(end);i++){
            int j = 0;
            while (j < 60){
                if (jumps == freq_index || is_start){
                    is_start = false;
                    if (j==0){
                        timeWindows.add(String.valueOf(i) + ":" + String.valueOf(j)+"0");
                    }else{
                        timeWindows.add(String.valueOf(i) + ":" + String.valueOf(j));
                    }
                    jumps = 0;
                }else{
                    jumps++;
                }
                j+=10;
            }
        }
    }
    public boolean is_good(){
        if (!openTime.isEmpty() || !closeTime.isEmpty()){
            if (Integer.parseInt(openTime)<24 && Integer.parseInt(closeTime)<24) {
                if (Integer.parseInt(openTime) < Integer.parseInt(closeTime) || Integer.parseInt(closeTime) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public void save_info(){
        WorkWeek w = new WorkWeek(days_of_work,timeWindows,pos);
        refBusiness.child(business_id).child("work_schedule").setValue(w);
        boolean is_open = false;
        for (int i =0;i<days_of_work.size();i++){
            if(days_of_work.get(i)){
                is_open = true;
            }
        }
        if (is_open){
            refBusiness.child(business_id).child("is_open").setValue(true);
        }else{
            refBusiness.child(business_id).child("is_open").setValue(false);
        }

    }


}