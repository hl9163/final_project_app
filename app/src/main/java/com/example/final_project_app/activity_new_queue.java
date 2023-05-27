package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.Client;
import com.example.final_project_app.helpers.Queue;
import com.example.final_project_app.helpers.Service;
import com.example.final_project_app.helpers.WorkWeek;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;
import static com.example.final_project_app.helpers.FBshortcut.refClients;

public class activity_new_queue extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    Intent gi;
    Button w1,w2,w3,w4,w5,w6,w7, saveing;
    ListView services_listV;
    Spinner time_frames;
    ValueEventListener bListener, cListener;

    int index_date_pos, service_pos, time_pos = -1;
    String business_id, user_id;
    String date, service_name, time,day_name;
    ArrayList<Service> business_services;
    WorkWeek week;
    ArrayList<Queue> queues_list_b = new ArrayList<Queue>();
    ArrayList<Queue> queues_list_c = new ArrayList<Queue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_queue);

        gi = getIntent();
        business_id = gi.getStringExtra("businessId");
        user_id = gi.getStringExtra("user_id");

        w1 = (Button) findViewById(R.id.buttonF1);
        w2 = (Button) findViewById(R.id.buttonF2);
        w3 = (Button) findViewById(R.id.buttonF3);
        w4 = (Button) findViewById(R.id.buttonF4);
        w5 = (Button) findViewById(R.id.buttonF5);
        w6 = (Button) findViewById(R.id.buttonF6);
        w7 = (Button) findViewById(R.id.buttonF7);
        saveing = (Button) findViewById(R.id.saveQ);
        services_listV = (ListView) findViewById(R.id.serviceListQ);
        time_frames = (Spinner) findViewById(R.id.spinnerTimes);

        time_frames.setOnItemSelectedListener(this);

        services_listV.setOnItemClickListener(this);
        services_listV.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        set_dates();

        bListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String id = (String) data.getKey();
                    Business businessTemp = data.getValue(Business.class);
                    if (business_id.equals(id)){
                        business_services = businessTemp.getBusiness_services();
                        week = businessTemp.getWork_schedule();
                        queues_list_b = businessTemp.getBusiness_queues();
                    }
                }
                show_services();

                w1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index_date_pos = 0;
                        setSpinner(index_date_pos);
                        Log.i("ok","dfdsf");
                    }
                });
                w2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index_date_pos = 1;
                        setSpinner(index_date_pos);

                    }
                });
                w3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index_date_pos = 2;
                        setSpinner(index_date_pos);

                    }
                });
                w4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index_date_pos = 3;
                        setSpinner(index_date_pos);

                    }
                });
                w5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index_date_pos = 4;
                        setSpinner(index_date_pos);

                    }
                });
                w6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index_date_pos = 5;
                        setSpinner(index_date_pos);

                    }
                });
                w7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index_date_pos = 6;
                        setSpinner(index_date_pos);

                    }
                });
                saveing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (validate_data(time_pos,index_date_pos,service_pos,week,business_services)){
                            ArrayList<String> work_hours = return_day_list(week,index_date_pos);
                            Log.i("ok",String.valueOf(time_pos));
                            int q_m;
                            if(get_x_days_from_today(index_date_pos) <7){
                                q_m =new Date().getMonth();
                            }else{
                                q_m =new Date().getMonth()+1;
                            }
                            Log.i("ok",user_id+" "+business_id+" "+String.valueOf(get_x_days_from_today(index_date_pos))+"/"+String.valueOf(q_m)+" "+ work_hours.get(time_pos)+" "+business_services.get(service_pos));
                            Queue new_q = new Queue(user_id,business_id,String.valueOf(get_x_days_from_today(index_date_pos))+"/"+String.valueOf(q_m), work_hours.get(time_pos),business_services.get(service_pos));
                            queues_list_b.add(new_q);
                            refBusiness.child(business_id).child("business_queues").setValue(queues_list_b);
                            save_new_s(work_hours,index_date_pos,time_pos);
                            save_to_client(new_q);

                        }
                    }
                });
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
        if (cListener!=null) {
            refClients.removeEventListener(cListener);
        }

    }

    public void set_dates(){
        w1.setText(String.valueOf(get_x_days_from_today(0)));
        w2.setText(String.valueOf(get_x_days_from_today(1)));
        w3.setText(String.valueOf(get_x_days_from_today(2)));
        w4.setText(String.valueOf(get_x_days_from_today(3)));
        w5.setText(String.valueOf(get_x_days_from_today(4)));
        w6.setText(String.valueOf(get_x_days_from_today(5)));
        w7.setText(String.valueOf(get_x_days_from_today(6)));
    }
    public void setSpinner(int index_date){
//        time_pos = -1;
        day_name = get_day_name_in_x_days_from_today(index_date);
        Log.i("ok",day_name);
        if (day_name.equals("יום ראשון") || day_name.equals("Sunday")){
            if (week.getDays_of_work().get(0)){
                ArrayAdapter<String> time_adp = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item,list_to_arr(week.getSunday_queues()));
                time_frames.setAdapter(time_adp);
            }

        }
        if (day_name.equals("יום שני") || day_name.equals("Monday")){
            if (week.getDays_of_work().get(1)){
                ArrayAdapter<String> time_adp = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item,list_to_arr(week.getMonday_queues()));
                time_frames.setAdapter(time_adp);
            }
        }
        if (day_name.equals("יום שלישי") || day_name.equals("Tuesday")){
            if (week.getDays_of_work().get(2)){
                ArrayAdapter<String> time_adp = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item,list_to_arr(week.getTuesday_queues()));
                time_frames.setAdapter(time_adp);
            }
        }
        if (day_name.equals("יום רביעי") || day_name.equals("Wednesday")){
            if (week.getDays_of_work().get(3)){
                ArrayAdapter<String> time_adp = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item,list_to_arr(week.getWednesday_queues()));
                time_frames.setAdapter(time_adp);
            }

        }
        if (day_name.equals("יום חמישי") || day_name.equals("Thursday")){
            if (week.getDays_of_work().get(4)){
                ArrayAdapter<String> time_adp = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item,list_to_arr(week.getThursday_queues()));
                time_frames.setAdapter(time_adp);
            }

        }
        if (day_name.equals("יום שישי") || day_name.equals("Friday")){
            if (week.getDays_of_work().get(5)){
                ArrayAdapter<String> time_adp = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item,list_to_arr(week.getFriday_queues()));
                time_frames.setAdapter(time_adp);
            }

        }
        if (day_name.equals("יום שבת") || day_name.equals("Saturday")){
            if (week.getDays_of_work().get(6)){
                ArrayAdapter<String> time_adp = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item,list_to_arr(week.getSaturday_queues()));
                time_frames.setAdapter(time_adp);
            }

        }

   }
   public String[] list_to_arr(ArrayList<String> al){
       String [] arr = new String[al.size()];
       for (int i = 0; i <al.size();i++){
           arr[i] = al.get(i);
       }
       return arr;
   }


    public static int get_x_days_from_today(int x){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, x);
        String[] arr = c.getTime().toString().split(" ");
        int date = Integer.parseInt(arr[2]);
        return date;
    }
    public static String get_day_name_in_x_days_from_today(int x){
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        Calendar d = Calendar.getInstance();
        d.add(Calendar.DATE, x);
        return dayNames[d.get(Calendar.DAY_OF_WEEK)];
    }
    public void show_services(){
        String [] arr = new String[business_services.size()];
        for (int i = 0; i <business_services.size();i++){
            arr[i] = business_services.get(i).getService_name()+", "+
                    business_services.get(i).getService_time()+"ד',"+
                    business_services.get(i).getService_cost()+" שח ";
        }
        ArrayAdapter<String> serviceAdp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
        services_listV.setAdapter(serviceAdp);
    }

    public boolean validate_data (int time_pos, int index_date_pos, int service_pos, WorkWeek week, ArrayList<Service> business_services){
        if (time_pos != -1 && service_pos != -1 && index_date_pos != -1){
            String time_service_selected = business_services.get(time_pos).getService_time();
            int time = Integer.parseInt(time_service_selected);
            Log.i("ok",String.valueOf(time));
            int one_q_time = (week.getFrequency_index()+1)*10;
            int amount_of_qs = time / one_q_time;
            ArrayList<String> work_hours = return_day_list(week,index_date_pos);
            if (time_pos+amount_of_qs < work_hours.size() && !work_hours.get(time_pos).equals("saved")){
                for (int i =0;i<amount_of_qs;i++){
                    if (work_hours.get(time_pos+i).equals("saved")){
                        return false;
                    }
                }
                Log.i("ok","work");
                return true;
            }
            return false;
        }
        return false;
    }
    public ArrayList<String> return_day_list(WorkWeek week, int index_date_pos){
        if (index_date_pos == 0){
            return week.getSunday_queues();
        }else if (index_date_pos == 1){
            return week.getMonday_queues();
        }else if (index_date_pos == 2){
            return week.getTuesday_queues();
        }else if (index_date_pos == 3){
            return week.getWednesday_queues();
        }else if (index_date_pos == 4){
            return week.getThursday_queues();
        }else if (index_date_pos == 5){
            return week.getFriday_queues();
        }else{
            return week.getSaturday_queues();
        }
    }
    public void save_new_s(ArrayList<String> day,int index_date_pos, int time_pos){
        day.set(time_pos,"saved");
        if (index_date_pos == 0){
            refBusiness.child(business_id).child("work_schedule").child("sunday_queues").setValue(day);
        }else if (index_date_pos == 1){
            refBusiness.child(business_id).child("work_schedule").child("monday_queues").setValue(day);
        }else if (index_date_pos == 2){
            refBusiness.child(business_id).child("work_schedule").child("tuesday_queues").setValue(day);
        }else if (index_date_pos == 3){
            refBusiness.child(business_id).child("work_schedule").child("wednesday_queues").setValue(day);
        }else if (index_date_pos == 4){
            refBusiness.child(business_id).child("work_schedule").child("thursday_queues").setValue(day);
        }else if (index_date_pos == 5){
            refBusiness.child(business_id).child("work_schedule").child("friday_queues").setValue(day);
        }else{
            refBusiness.child(business_id).child("work_schedule").child("saturday_queues").setValue(day);
        }
    }
    public void save_to_client(Queue queue){
        cListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String id = (String) data.getKey();
                    Client clientTemp = data.getValue(Client.class);
                    if (user_id.equals(id)){
                        queues_list_c = clientTemp.getClient_queues();
                    }
                }
                queues_list_c.add(queue);
                refClients.child(user_id).child("client_queues").setValue(queues_list_c);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refClients.addValueEventListener(cListener);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        service_pos = position;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        time_pos = position;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}