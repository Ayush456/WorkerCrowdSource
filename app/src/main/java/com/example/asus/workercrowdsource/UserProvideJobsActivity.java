package com.example.asus.workercrowdsource;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UserProvideJobsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mJob1;
    private Button mPost;
    private EditText mCustomJob,mSalary,mEstWorker,mAddress;
    private TextView mStart,mEnd;
    private String arr1[] = {"Painter","Plumber","Mechanic","Pest Controller","Laundary","House Cleaner","Electrician","AC mechanic","Carpenter","Other"};
    private  String jobsToPost = "",mStartDate="",mEndDate="",salary="",NoOfWorker = "",Address="";
    String item;
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private FirebaseAuth mAu = FirebaseAuth.getInstance();
    private ProgressBar progressBar;

    private int sDay,sMonth,sYear,eDay,eMonth,eYear;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_provide_jobs);

        mStart = findViewById(R.id.textView21);
        mEnd = findViewById(R.id.textView22);
        mJob1 = findViewById(R.id.spinner);
        mCustomJob = findViewById(R.id.editText11);
        mSalary = findViewById(R.id.editText12);
        mPost = findViewById(R.id.button12);
        mEstWorker = findViewById(R.id.editText15);
        progressBar = findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.INVISIBLE);
        mAddress = findViewById(R.id.editText13);


        mCustomJob.setEnabled(false); // Disabling the custom editetxt
        calendar = Calendar.getInstance(); //initializing the calender

        sDay = calendar.get(Calendar.DAY_OF_MONTH);
        sMonth=calendar.get(Calendar.MONTH);
        sYear = calendar.get(Calendar.YEAR);

        eDay = calendar.get(Calendar.DAY_OF_MONTH);
        eMonth=calendar.get(Calendar.MONTH);
        eYear = calendar.get(Calendar.YEAR);

        ArrayAdapter<String> jobs = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arr1);
        jobs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJob1.setAdapter(jobs);
        mJob1.setOnItemSelectedListener( this);

       FirebaseUser mCurrentUser = mAu.getCurrentUser();
       final String userId = mCurrentUser.getUid();
       final DatabaseReference mUserPostJobs = mDB.getReference().child("USER_POST_JOBS");
       final DatabaseReference mCurrentUserPost = mUserPostJobs.child(userId);
       mStart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               DatePickerDialog datePickerDialog = new DatePickerDialog(UserProvideJobsActivity.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                       month = month+1;
                       mStartDate = dayOfMonth+"/"+month+"/"+year;
                       mStart.setText(dayOfMonth+"/"+month+"/"+year);

                   }
               },sDay,sMonth,sYear);
               datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
               datePickerDialog.show();
           }
       });


        mEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserProvideJobsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        mEndDate = dayOfMonth+"/"+month+"/"+year;
                        mEnd.setText(dayOfMonth+"/"+month+"/"+year);


                    }
                },eDay,eMonth,eYear);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                salary = mSalary.getText().toString().trim();
                Address = mAddress.getText().toString().trim();
                if (item.equals("Other")){
                    jobsToPost = mCustomJob.getText().toString().trim();
                }else{
                    jobsToPost = item;
                }

                NoOfWorker = mEstWorker.getText().toString().trim();
                if (!TextUtils.isEmpty(mStartDate) && !TextUtils.isEmpty(mEndDate) && !TextUtils.isEmpty(jobsToPost) && !TextUtils.isEmpty(salary)
                        && !TextUtils.isEmpty(NoOfWorker) && !TextUtils.isEmpty(Address)){
                    //Storing into the database has to b done here

                    DatabaseReference pushPostKey = mCurrentUserPost.push();
                    pushPostKey.child("Job").setValue(jobsToPost);
                    pushPostKey.child("Salary").setValue(salary);
                    pushPostKey.child("StartDate").setValue(mStartDate);
                    pushPostKey.child("EndDate").setValue(mEndDate);
                    pushPostKey.child("Address").setValue(Address);
                    pushPostKey.child("EstNoOfWorker").setValue(NoOfWorker);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(UserProvideJobsActivity.this,"Job is been posted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserProvideJobsActivity.this,UserHomeActivity.class));

                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(UserProvideJobsActivity.this,"Some Details are missing",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item =  mJob1.getSelectedItem().toString();
        Toast.makeText(UserProvideJobsActivity.this,"Selected Item :"+item,Toast.LENGTH_SHORT).show();

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
