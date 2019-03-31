package com.example.asus.workercrowdsource;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ContractorPostJobs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mJob1;
    private Button mPost;
    private EditText mCustomJob,mSalary,mEstWorker,mAddress;
    private TextView mStart,mEnd;
    private String arr1[] = {"Painter","Plumber","Mechanic","Pest Controller","Laundary","House Cleaner","Electrician","AC mechanic","Carpenter","Other"};
    private  String jobsToPost = "",mStartDate="",mEndDate="",salary="",NoOfWorker = "",Address="";
    String item="";
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private FirebaseAuth mAu = FirebaseAuth.getInstance();
    private ProgressBar MprogressBar;
    private int sDay,sMonth,sYear,eDay,eMonth,eYear;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_post_jobs);

        mStart = findViewById(R.id.textView72);
        mEnd = findViewById(R.id.textView73);
        mJob1 = findViewById(R.id.spinner10);
        mCustomJob = findViewById(R.id.editText70);
        mSalary = findViewById(R.id.editText71);
        mPost = findViewById(R.id.button30);
        mEstWorker = findViewById(R.id.editText100);
        MprogressBar = (ProgressBar)findViewById(R.id.progressBar5);
        MprogressBar.setVisibility(View.INVISIBLE);
        mAddress = findViewById(R.id.editText72);


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
        final DatabaseReference mUserPostJobs = mDB.getReference().child("CONTRACTOR_POST_JOBS");
        final DatabaseReference mCurrentUserPost = mUserPostJobs.child(userId);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(ContractorPostJobs.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(ContractorPostJobs.this, new DatePickerDialog.OnDateSetListener() {
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
                //getting all the required data
                salary = mSalary.getText().toString().trim();
                Address = mAddress.getText().toString().trim();
                if (item.equals("Other")){
                    jobsToPost = mCustomJob.getText().toString().trim();
                }else{
                    jobsToPost = item;
                }
                NoOfWorker = mEstWorker.getText().toString().trim();

                // cross validation
                if (item.equals("")){
                    Toast.makeText(ContractorPostJobs.this,"Please Input job field",Toast.LENGTH_SHORT).show();
                }else{
                    if (salary.equals("")){
                        mSalary.setError("Salary cannot be Null");
                        mSalary.requestFocus();
                    }else {
                        if (NoOfWorker.equals("")){
                            mEstWorker.setError("No of workers expected cannot be null");
                            mEstWorker.requestFocus();
                        }else {
                            if (Address.equals("")){
                                mAddress.setError("Address cannot be Null");
                                mAddress.requestFocus();
                            }else{
                                if (mStartDate.equals("") || mEndDate.equals("")){
                                    Toast.makeText(ContractorPostJobs.this,"Start Or End Date is Missing",Toast.LENGTH_SHORT).show();
                                }else{
                                    MprogressBar.setVisibility(View.VISIBLE);
                                    DatabaseReference pushPostKey = mCurrentUserPost.push();
                                    pushPostKey.child("Job").setValue(jobsToPost);
                                    pushPostKey.child("Salary").setValue(salary);
                                    pushPostKey.child("StartDate").setValue(mStartDate);
                                    pushPostKey.child("EndDate").setValue(mEndDate);
                                    pushPostKey.child("Address").setValue(Address);
                                    pushPostKey.child("EstNoOfWorker").setValue(NoOfWorker);
                                    MprogressBar.setVisibility(View.INVISIBLE);
                                    Intent contractorHomeIntent = new Intent(ContractorPostJobs.this,ContractorHomeActivity.class);
                                    contractorHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(contractorHomeIntent);

                                }
                            }
                        }
                    }
                }

            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item =  mJob1.getSelectedItem().toString();
        Toast.makeText(ContractorPostJobs.this,"Selected Item :"+item,Toast.LENGTH_SHORT).show();

        if (item.equals("Other")){
            mCustomJob.setEnabled(true);
            Toast.makeText(ContractorPostJobs.this,"Selected Item :"+item,Toast.LENGTH_SHORT).show();
        }else{
            jobsToPost = item;
            mCustomJob.setEnabled(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(ContractorPostJobs.this,"Please Select Any Item",Toast.LENGTH_SHORT).show();
    }
}
