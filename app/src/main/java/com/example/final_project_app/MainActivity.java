package com.example.final_project_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		24/12/2022
     * main screen
     */

    ListView cList;
    ImageButton account;
    Intent si;

    String[] categories = {"קוסמטיקאיות","מורים פרטיים","ספרים"};
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cList = (ListView) findViewById(R.id.cList);
        account = (ImageButton) findViewById(R.id.imageButton);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_id == null){
                    si = new Intent(MainActivity.this, activity_input_clint.class);
                    startActivityForResult(si, 1);
                }
            }
        });

        cList.setOnItemClickListener(this);
        cList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adp = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, categories);
        cList.setAdapter(adp);


    }

    /**
     * categories list view listener
     * <p>
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    /**
     * get the data from another activates
     * <p>
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            user_id = data.getStringExtra("user_id");
            Log.i("user_id", user_id);

        }
    }
}