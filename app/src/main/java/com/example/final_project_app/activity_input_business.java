package com.example.final_project_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project_app.helpers.Business;
import com.example.final_project_app.helpers.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.final_project_app.R.layout.support_simple_spinner_dropdown_item;
import static com.example.final_project_app.helpers.FBshortcut.refBusiness;
import static com.example.final_project_app.helpers.FBshortcut.refClients;

public class activity_input_business extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    Intent gi;
    TextView title;
    LinearLayout nameL, addressL, cityL, logoL, serviceL;
    EditText nameE, addressE;
    Spinner spinnerCity;
    ListView show_services;
    ImageButton logoI;
    AlertDialog.Builder adb;
    ArrayAdapter<String> serviceAdp;

    Uri image;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    String user_id, name, address,city;
    String nameService, priceService, timeService;
    String[] arr;
    ArrayList<Service> business_services = new ArrayList<Service>();
    ArrayList<String> business_services_show = new ArrayList<String>();

    int p = 0;
    boolean mode = true;
    boolean haveLogo = false;
    boolean haveOneService = false;
    boolean imgUploaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_business);

        title = (TextView) findViewById(R.id.title_business);

        nameL = (LinearLayout) findViewById(R.id.namebLL);
        addressL = (LinearLayout) findViewById(R.id.addressLL);
        cityL = (LinearLayout) findViewById(R.id.cityLL);
        logoL = (LinearLayout) findViewById(R.id.logoLL);
        serviceL = (LinearLayout) findViewById(R.id.serviceLL);

        nameE = (EditText) findViewById(R.id.business_name);
        addressE = (EditText) findViewById(R.id.address_business);

        logoI = (ImageButton) findViewById(R.id.logoIF);

        spinnerCity = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        spinnerCity.setAdapter(spinner_adapter);
        spinnerCity.setOnItemSelectedListener(this);

        show_services = (ListView) findViewById(R.id.servicesSammery);
        show_services.setOnItemClickListener(this);
        show_services.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        serviceAdp = new ArrayAdapter<String>(this, support_simple_spinner_dropdown_item,business_services_show);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        gi = getIntent();
        user_id = gi.getStringExtra("user_id");

        serviceL.setVisibility(View.GONE);

        Log.i("user_id",user_id);
    }


    public void next_or_save(View view) {
        if (mode){
            name = nameE.getText().toString().trim();
            address = addressE.getText().toString().trim();
            if (validate_data()){
                nameL.setVisibility(View.GONE);
                addressL.setVisibility(View.GONE);
                cityL.setVisibility(View.GONE);
                logoL.setVisibility(View.GONE);
                serviceL.setVisibility(View.VISIBLE);
                mode = false;
            }
        }else{
            if (validate_data()){
                final String business_id = UUID.randomUUID().toString();
                final String imgName = UUID.randomUUID().toString();
                refClients.child(user_id).child("client_business").setValue(business_id);
                uploadPic(imgName);
                Business b = new Business(name, imgName, address, city, user_id, business_services);
                refBusiness.child(business_id).setValue(b);
                finish();
            }

        }
    }

    private void uploadPic(String imgName) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("uploading Image...");
        StorageReference ref = storageReference.child("logos/"+imgName+".png");
        ref.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Image Uploaded.", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Failed to upload", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.000 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount() );
                pd.setMessage((int) progressPercent +"%");
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        p = position;
        if (position>1){
            city = String.valueOf(position);
        }else{
            city = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addLogo(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            image = data.getData();
            logoI.setImageURI(image);
            haveLogo = true;
        }
    }

        public boolean validate_data(){
        if (mode){
            if (name.equals("") || address.equals("") || p < 1 || !haveLogo){
                return false;
            }
        }else{
            if (!haveOneService){
                Toast.makeText(this, "הוסף שירות אחד לפחות!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void addServiceB(View view) {
        adb = new AlertDialog.Builder(activity_input_business.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_service,null);
        EditText nameServiceE = (EditText) mView.findViewById(R.id.editTextTextPersonName);
        EditText priceServiceE =(EditText) mView.findViewById(R.id.editTextNumber);
        EditText timeServiceE =(EditText) mView.findViewById(R.id.editTextNumber2);
        Button addService =  (Button) mView.findViewById(R.id.button3);
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameService = nameServiceE.getText().toString().trim();
                priceService = priceServiceE.getText().toString().trim();
                timeService =timeServiceE.getText().toString().trim();
                if (!nameService.isEmpty() && !priceService.isEmpty() && !timeService.isEmpty()){
                    Service s = new Service(nameService, priceService, timeService);
                    business_services.add(s);
                    String dis = nameService+", "+timeService+"ד',"+priceService+" שח ";
                    business_services_show.add(dis);
                    Toast.makeText(activity_input_business.this, "השירות הוסף", Toast.LENGTH_SHORT).show();
                    arr = new String [business_services_show.size()];
                    show_services.setAdapter(serviceAdp);
                    haveOneService = true;
                }else{
                    Toast.makeText(activity_input_business.this, "אחד מהשדות ריקים", Toast.LENGTH_SHORT).show();
                }

            }
        });
        adb.setView(mView);
        AlertDialog dialog = adb.create();
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}