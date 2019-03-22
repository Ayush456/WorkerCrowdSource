package com.example.asus.workercrowdsource;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserJobProvider extends AppCompatActivity{

//    private String arr1[] = {"Painter","Plumber","Mechanic","Pest Controller","Laundary","House Cleaner","Electrician","AC mechanic","Carpenter","Other"};
//    private Spinner mJob1;
//    private EditText sal;
//    private EditText startdate;
//    private EditText duration;
//    private EditText workers;
//    private EditText customJob;
//    private Button jobsubmit;
//    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_provider);

//        customJob = findViewById(R.id.custjobeditText);
//        customJob.setEnabled(false);
//        mJob1 = findViewById(R.id.jobSpinner);
//        ArrayAdapter<String> jobs = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arr1);
//        jobs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mJob1.setAdapter(jobs);
//        mJob1.setOnItemSelectedListener(this);
//


   }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String item = parent.getItemAtPosition(position).toString().trim();
//        if (parent.getId() == R.id.jobSpinner){
//            if (item.equals("Other")){
//                customJob.setEnabled(true);
//            }else {
//                customJob.setEnabled(false);
//            }
//
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//        Toast.makeText(UserJobProvider.this,"Please Select a Job",Toast.LENGTH_LONG).show();
//    }
}
