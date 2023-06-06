package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.Client;
import com.example.final_project_app.helpers.Queue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;
import static com.example.final_project_app.helpers.FBshortcut.refClients;

public class activity_show_queues extends AppCompatActivity {
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		27/05/2023
     * show all the saved queues (mode 0 - clients, mode 1- business)
     */
    ListView queues_list;
    Intent gi;
    ValueEventListener cListener, bListener;

    int mode;
    String c_b_id;
    ArrayList<Queue> queues_list_data;
    ArrayList<String> queues_list_to_display = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_queues);
        gi = getIntent();

        queues_list = (ListView) findViewById(R.id.queues_list_c_b);


        mode = gi.getIntExtra("mode",-1);
        c_b_id = gi.getStringExtra("Id");
        if (mode == 0){
            show_client_queues();
        }else if (mode == 1){
            show_business_queues();
        }

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
    /**
     * load the clients queues and display them
     * <p>
     */
    public void show_client_queues(){
        cListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String id = (String) data.getKey();
                    Client clientTemp = data.getValue(Client.class);
                    if (c_b_id.equals(id)){
                        queues_list_data = clientTemp.getClient_queues();
                    }

                }
                display_queues();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refClients.addValueEventListener(cListener);
    }
    /**
     * load the business queues and display them
     * <p>
     */
    public void show_business_queues(){
        bListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String id = (String) data.getKey();
                    Business clientTemp = data.getValue(Business.class);
                    if (c_b_id.equals(id)){
                        queues_list_data = clientTemp.getBusiness_queues();
                    }

                }
                display_queues();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refBusiness.addValueEventListener(bListener);
    }

    public String[] list_to_arr(ArrayList<String> al){
        String [] arr = new String[al.size()];
        for (int i = 0; i <al.size();i++){
            arr[i] = al.get(i);
        }
        return arr;
    }
    /**
     * display all in the listView
     * <p>
     */
    public void display_queues(){
        for (int i=0;i<queues_list_data.size();i++){
            Queue ob = queues_list_data.get(i);
            queues_list_to_display.add(ob.getService().getService_name()+" בתאריך "+ob.getDate()+" בשעה "+ob.getTime());
        }
        String [] Qarr = list_to_arr(queues_list_to_display);
        ArrayAdapter<String> qAdp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Qarr);
        queues_list.setAdapter(qAdp);
    }
}