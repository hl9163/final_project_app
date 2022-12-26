package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project_app.helpers.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.final_project_app.helpers.FBshortcut.refClients;

public class activity_input_clint extends AppCompatActivity {
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		24/12/2022
     * input client details
     */
    TextView title;
    LinearLayout nameL, phoneL, password2L, cityL;
    EditText nameE, phoneE, emailE, passwordE, password2E, cityE;
    Button change_mode;
    Intent gi;
    private FirebaseAuth mAuth;
    ValueEventListener ClientsListener;

    String name, phone, email, password, password2, city, id;
    boolean mode = true;
    boolean lock = true;
    ArrayList<Client> client_list = new ArrayList<Client>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_clint);

        title = (TextView) findViewById(R.id.titleUpdate);
        nameL = (LinearLayout) findViewById(R.id.nameLL);
        phoneL = (LinearLayout) findViewById(R.id.phoneLL);
        password2L = (LinearLayout) findViewById(R.id.password2LL);
        cityL = (LinearLayout) findViewById(R.id.cityLL);
        change_mode = (Button) findViewById(R.id.button2);

        nameE = (EditText) findViewById(R.id.name);
        phoneE = (EditText) findViewById(R.id.Phone);
        emailE = (EditText) findViewById(R.id.email);
        passwordE = (EditText) findViewById(R.id.password);
        password2E = (EditText) findViewById(R.id.password2);
        cityE = (EditText) findViewById(R.id.city);

        nameL.setVisibility(View.GONE);
        phoneL.setVisibility(View.GONE);
        password2L.setVisibility(View.GONE);
        cityL.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();


    }
    /**
     * change the mode
     * mode = true - log in
     * mode = false - register
     * <p>
     */
    public void change_the_mode(View view) {
        mode = !mode;
        if (mode){
            nameL.setVisibility(View.GONE);
            phoneL.setVisibility(View.GONE);
            password2L.setVisibility(View.GONE);
            cityL.setVisibility(View.GONE);
            change_mode.setText("לחץ פה כדי להירשם");
        }else{
            nameL.setVisibility(View.VISIBLE);
            phoneL.setVisibility(View.VISIBLE);
            password2L.setVisibility(View.VISIBLE);
            cityL.setVisibility(View.VISIBLE);
            change_mode.setText("לחץ פה כדי להתחבר");
        }
    }
    /**
     * save the data and move it the the main activity
     * <p>
     */
    public void save(View view) {
        Toast alertToast = Toast.makeText(this, "something is missing", Toast.LENGTH_LONG);
        email = emailE.getText().toString().trim();
        password = passwordE.getText().toString().trim();
        if (!mode){
            name = nameE.getText().toString().trim();
            phone = phoneE.getText().toString().trim();
            password2 = password2E.getText().toString().trim();
            city = cityE.getText().toString().trim();
            if(validate_data()){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity_input_clint.this, "user already exist", Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            mAuth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if( task.isSuccessful()){
                                                Client client1 = new Client(name, email, password, phone, city);
                                                refClients.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(client1);

                                            }
                                        }
                                    });
                            gi = getIntent();
                            gi.putExtra("user_id",email);
                            setResult(RESULT_OK,gi);
                            finish();
                        }
                    }
                });
            }else{
                alertToast.show();
            }
        }else{
            if (validate_data()){
                gi = getIntent();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity_input_clint.this, "welcome!", Toast.LENGTH_LONG).show();
                            ClientsListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot data : snapshot.getChildren()) {
                                        String phone1 = (String) data.getKey();
                                        Client c = data.getValue(Client.class);
                                        if (c.getClient_email().equals(email)){
                                            Log.i("user_id",phone1);
                                            gi.putExtra("user_id",phone1);
                                            setResult(RESULT_OK,gi);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            };

                        }else{
                            gi.putExtra("user_id","gh");
                            setResult(RESULT_OK,gi);
                            finish();
                        }
                    }
                });
                setResult(RESULT_OK,gi);
                finish();
            }
        }
    }
    /**
     * validate the data
     * <p>
     */
    public boolean validate_data(){
        if (!mode){

            if (name.equals("") || email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches() ||phone.equals("") ||
                    city.equals("") || password.equals("")|| password2.equals("") || !password.equals(password2)){
                return false;
            }

            return true;
        }else{
            if (email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()|| password.equals("")){
                return false;
            }
            return true;
        }
    }
}