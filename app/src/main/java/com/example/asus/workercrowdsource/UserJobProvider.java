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

public class UserJobProvider extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String arr1[] = {"Painter","Plumber","Mechanic","Pest Controller","Laundary","House Cleaner","Electrician","AC mechanic","Carpenter","Other"};
    private Spinner mJob1;
    private EditText sal;
    private EditText startdate;
    private EditText duration;
    private EditText workers;
    private EditText customJob;
    private Button jobsubmit;
    String result;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public Jobs jb;
    String status = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_provider);

        final String UserID = mAuth.getCurrentUser().getUid();

        mJob1 = (Spinner) findViewById(R.id.jobSpinner);
        sal = (EditText) findViewById(R.id.eseditText);
        startdate = (EditText) findViewById(R.id.sdeditText);
        duration = (EditText) findViewById(R.id.dueditText);
        workers = (EditText) findViewById(R.id.eweditText);
        customJob = (EditText) findViewById(R.id.custjobeditText);
        jobsubmit = (Button) findViewById(R.id.jobbutton);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arr1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJob1.setAdapter(aa);
        mJob1.setOnItemSelectedListener(this);

        jobsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Job = result;
                String custJob = customJob.getText().toString().trim();
                String salary = sal.getText().toString().trim();
                String start = startdate.getText().toString().trim();
                String dur = duration.getText().toString().trim();
                String nworkers = workers.getText().toString().trim();


                if(TextUtils.isEmpty(custJob)){
                    jb= new Jobs(Job,salary,start,dur,nworkers,status);
                }
                if(Job.equals("Other")){
                    jb = new Jobs(custJob,salary,start,dur,nworkers,status);
                }

                mDatabase.child("UserJobsAvailble").child(UserID).push().setValue(jb).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UserJobProvider.this,"Job Posted Successfully",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(UserJobProvider.this,"UnSuccessful",Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString().trim();
        if (parent.getId() == R.id.spinner2){
            if (item.equalsIgnoreCase("Other")){
                customJob.setEnabled(true);
                result = mJob1.getSelectedItem().toString();
            }

            if(!item.equalsIgnoreCase("Other")){
                customJob.setEnabled(false);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),"Please select any item!",Toast.LENGTH_SHORT).show();
        mJob1.requestFocus();
    }


}
