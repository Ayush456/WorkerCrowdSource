package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProvideJobsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mJob1;
    private Button mPost;
    private EditText mCustomJob,mSalary,mStartDate,mEndDate,mEstWorker;
    private String arr1[] = {"Painter","Plumber","Mechanic","Pest Controller","Laundary","House Cleaner","Electrician","AC mechanic","Carpenter","Other"};
    private  String jobsToPost = "";
    String item;
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private FirebaseAuth mAu = FirebaseAuth.getInstance();
    private  ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_provide_jobs);

        mJob1 = findViewById(R.id.spinner);
        mCustomJob = findViewById(R.id.editText11);
        mSalary = findViewById(R.id.editText12);
        mStartDate = findViewById(R.id.editText13);
        mEndDate = findViewById(R.id.editText14);
        mPost = findViewById(R.id.button10);
        mEstWorker = findViewById(R.id.editText16);
        mProgress = findViewById(R.id.progressBar4);
        mProgress.setVisibility(View.INVISIBLE);

        mCustomJob.setEnabled(false); // Disabling the custom editetxt

        ArrayAdapter<String> jobs = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arr1);
        jobs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJob1.setAdapter(jobs);
        mJob1.setOnItemSelectedListener( this);

       FirebaseUser mCurrentUser = mAu.getCurrentUser();
       final String userId = mCurrentUser.getUid();
       final DatabaseReference mUserPostJobs = mDB.getReference().child("USER_POST_JOBS");

        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               DatabaseReference currentUserPost = Post.child("Job").setValue(mJob1.);
                mProgress.setVisibility(View.VISIBLE);

                if(item.equals("Other") && !(mCustomJob.getText().toString().trim()).equals("")){
                    jobsToPost = mCustomJob.getText().toString();
                }
//               DatabaseReference pushKey = mUserPostJobs.child(userId);
//               pushKey.child("Job").setValue(jobsToPost);
//               mProgress.setVisibility(View.INVISIBLE);

                DatabaseReference current = mUserPostJobs.child(userId);
                DatabaseReference currentPostKey = current.push();
                currentPostKey.child("Job").setValue(jobsToPost);
                currentPostKey.child("Salary").setValue(mSalary.getText().toString());
                currentPostKey.child("StartDate").setValue(mStartDate.getText().toString());
                currentPostKey.child("Est_Workers").setValue(mEstWorker.getText().toString());
                currentPostKey.child("EndDate").setValue(mEndDate.getText().toString());
                mProgress.setVisibility(View.INVISIBLE);

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString().trim();
        if (item.equals("Other")){
            mCustomJob.setEnabled(true);
            Toast.makeText(UserProvideJobsActivity.this,"Selected Item :"+item,Toast.LENGTH_SHORT).show();
        }else{
            jobsToPost = item;
            mCustomJob.setEnabled(false);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(UserProvideJobsActivity.this,"Please Select Any Item",Toast.LENGTH_SHORT).show();
    }
}
