package com.example.final_project_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.final_project_app.helpers.FBshortcut.refBusiness;


public class fragment4 extends Fragment implements AdapterView.OnItemClickListener {
    Button add_service;
    EditText service_nameET, service_priceET, service_timeET;
    ListView serviceList;
    ValueEventListener bListener;
    ArrayAdapter<String> serviceAdp;

    String business_id, service_name, service_price, service_time;;
    ArrayList<Service> business_services = new ArrayList<Service>();
    ArrayList<String> business_services_show = new ArrayList<String>();
    String[] arr;
    int pos = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment4, container, false);
        add_service = (Button) v.findViewById(R.id.add_services);
        service_nameET = (EditText) v.findViewById(R.id.editTextTextService_name);
        service_priceET = (EditText) v.findViewById(R.id.edit_service_price);
        service_timeET = (EditText) v.findViewById(R.id.edit_service_time);
        serviceList = (ListView)  v.findViewById(R.id.edit_service_view);

        serviceList.setOnItemClickListener(this);
        serviceList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        business_id = this.getArguments().getString("business_idB");

        bListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                business_services.clear();
                business_services_show.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    String id = (String) data.getKey();
                    Business businessTemp = data.getValue(Business.class);
                    if (business_id.equals(id)){
                        business_services = businessTemp.getBusiness_services();
                    }
                }
                for (int i =0;i<business_services.size();i++){
                    business_services_show.add(business_services.get(i).getService_name()+", "+
                            business_services.get(i).getService_time()+"ד',"+
                            business_services.get(i).getService_cost()+" שח ");
                }
                arr = new String[business_services_show.size()];
                for (int i =0;i<business_services.size();i++){
                    arr[i] = business_services_show.get(i);
                }
                serviceAdp = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
                serviceList.setAdapter(serviceAdp);
                add_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service_name = service_nameET.getText().toString().trim();
                        service_price = service_priceET.getText().toString().trim();
                        service_time = service_timeET.getText().toString().trim();
                        if (pos == -1){
                            if (!service_name.isEmpty() && !service_price.isEmpty() && !service_time.isEmpty()){
                                Service s = new Service(service_name, service_price, service_time);
                                business_services.add(s);
                                business_services_show.add(service_name+", "+ service_time+"ד',"+ service_price+" שח ");
                                arr = new String[business_services_show.size()];
                                for (int i =0;i<business_services.size();i++){
                                    arr[i] = business_services_show.get(i);
                                }
                                refBusiness.child(business_id).child("business_services").setValue(business_services);
                                serviceAdp = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
                                serviceList.setAdapter(serviceAdp);
                            }
                        }else{
                            if (!service_name.isEmpty() && !service_price.isEmpty() && !service_time.isEmpty()){
                                Service s = new Service(service_name, service_price, service_time);
                                business_services.set(pos, s);
                                String st = service_name+", "+ service_time+"ד',"+ service_price+" שח ";
                                business_services_show.set(pos,st);
                                arr[pos] = business_services_show.get(pos);
                                refBusiness.child(business_id).child("business_services").setValue(business_services);
                                serviceAdp = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
                                serviceList.setAdapter(serviceAdp);

                            }else if (service_name.isEmpty() && service_price.isEmpty() && service_time.isEmpty()){
                                business_services.remove(pos);
                                business_services_show.remove(pos);
                                arr = new String[business_services_show.size()];
                                for (int i =0;i<business_services.size();i++){
                                    arr[i] = business_services_show.get(i);
                                }
                                refBusiness.child(business_id).child("business_services").setValue(business_services);
                                serviceAdp = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
                                serviceList.setAdapter(serviceAdp);
                            }
                            pos = -1;
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refBusiness.addValueEventListener(bListener);

        return v;
    }
    @Override
    public void onPause() {
        super.onPause();
        if (bListener!=null) {
            refBusiness.removeEventListener(bListener);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        Service c = business_services.get(pos);
        service_nameET.setText(c.getService_name());
        service_priceET.setText(c.getService_cost());
        service_timeET.setText(c.getService_time());


    }
}