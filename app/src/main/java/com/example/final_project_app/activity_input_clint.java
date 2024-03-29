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
import com.example.final_project_app.helpers.Queue;
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
    LinearLayout nameL, phoneL, password2L;
    EditText nameE, phoneE, emailE, passwordE, password2E;
    Button change_mode;
    Intent gi;
    private FirebaseAuth mAuth;
    ValueEventListener ClientsListener;

    String name, phone, email, password, password2;
    boolean mode = true;
    ArrayList<Client> client_list = new ArrayList<Client>();
    ArrayList<Queue> client_queues = new ArrayList<Queue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_clint);

        title = (TextView) findViewById(R.id.titleUpdate);
        nameL = (LinearLayout) findViewById(R.id.nameLL);
        phoneL = (LinearLayout) findViewById(R.id.phoneLL);
        password2L = (LinearLayout) findViewById(R.id.password2LL);
        change_mode = (Button) findViewById(R.id.button2);

        nameE = (EditText) findViewById(R.id.name);
        phoneE = (EditText) findViewById(R.id.Phone);
        emailE = (EditText) findViewById(R.id.email);
        passwordE = (EditText) findViewById(R.id.password);
        password2E = (EditText) findViewById(R.id.password2);

        nameL.setVisibility(View.GONE);
        phoneL.setVisibility(View.GONE);
        password2L.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();


    }
    /**
     * change the mode
     * mode = true - log in
     * mode = false - register
     * @param view button
     * <p>
     */
    public void change_the_mode(View view) {
        mode = !mode;
        if (mode){
            title.setText("התחברות");
            nameL.setVisibility(View.GONE);
            phoneL.setVisibility(View.GONE);
            password2L.setVisibility(View.GONE);
            change_mode.setText("לחץ פה כדי להירשם");
        }else{
            title.setText("הירשם");
            nameL.setVisibility(View.VISIBLE);
            phoneL.setVisibility(View.VISIBLE);
            password2L.setVisibility(View.VISIBLE);
            change_mode.setText("לחץ פה כדי להתחבר");
        }
    }
    /**
     * save the data and move it the the main activity
     * @param view button
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
            if(validate_data()){
//                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(activity_input_clint.this, "user already exist", Toast.LENGTH_LONG).show();
//                            return;
//                        }else{
//                            create_user();
//                        }
//                    }
//                });
                create_user();
            }else{
                alertToast.show();
            }
        }else{
            if (validate_data()){
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity_input_clint.this, "welcome!", Toast.LENGTH_LONG).show();


                        }if (task.isCanceled()){
                            gi = getIntent();
                            gi.putExtra("user_id","error");
                            setResult(RESULT_OK,gi);
                            finish();
                        }
                    }
                });
                gi = getIntent();
                gi.putExtra("user_id",FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                    password.equals("")|| password2.equals("") || !password.equals(password2)){
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
    /**
     * create the user after all the validation
     * <p>
     */
    public void create_user(){

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            Client client1 = new Client(name, email, password, phone,"null",client_queues);
                            String id = (String) FirebaseAuth.getInstance().getCurrentUser().getUid();
                            refClients.child(id).setValue(client1);
                        }
                    }
                });
        finish();
    }
    /**
     * back to the menu
     * @param view button
     * <p>
     */
    public void back_to_main_menu(View view) {
        finish();
    }
}