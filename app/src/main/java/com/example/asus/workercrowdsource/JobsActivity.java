package com.example.asus.workercrowdsource;

import android.content.Intent;
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

public class JobsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String arr1[] = {"Painter","Plumber","Mechanic","Pest Controller","Laundary","House Cleaner","Electrician","AC mechanic","Carpenter","Other"};
    String arr2[] = {"Painter","Plumber","Mechanic","Pest Controller","Laundary","House Cleaner","Electrician","AC mechanic","Carpenter","Other","None"};
    private Spinner mJob1,mJob2;
    private String job1="",job2="",job3="";
    private EditText customJob;
    private Button mSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        mJob1 =  findViewById(R.id.spinner2);
        mJob2 =  findViewById(R.id.spinner4);
        customJob = findViewById(R.id.custEditText);


        // For spinner Job1
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arr1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJob1.setAdapter(aa);
        mJob1.setOnItemSelectedListener(this);

        // For spinner Job2
        ArrayAdapter<String> bb = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arr2);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJob2.setAdapter(bb);
        mJob2.setOnItemSelectedListener(this);





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString().trim();
        if (parent.getId() == R.id.spinner2){
            if ( item.equalsIgnoreCase("Other")){
                customJob.setEnabled(true);
                mJob2.setEnabled(false);

                job1 = item;
                Toast.makeText(JobsActivity.this,""+job1,Toast.LENGTH_SHORT).show();
            }else {
                customJob.setEnabled(false);
                mJob2.setEnabled(true);

                job1 = item;
                Toast.makeText(JobsActivity.this,""+job1,Toast.LENGTH_SHORT).show();
            }
        }
        if (parent.getId() == R.id.spinner4){

            if (!item.equalsIgnoreCase("Other")){
                customJob.setEnabled(false);
                job2 = item;
            }else{
                customJob.setEnabled(true);
                job2 = item;
            }
            Toast.makeText(JobsActivity.this,""+job2,Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),"Please select any item!",Toast.LENGTH_SHORT).show();
    }

    public void submitForm(View view){
        Intent intent = new Intent(JobsActivity.this,ProfilePictureActivity.class);

        if( job1.equals("Other")  ){
            if( TextUtils.isEmpty(customJob.getText().toString().trim()) ){
                Toast.makeText(JobsActivity.this, "your custom job input is null", Toast.LENGTH_SHORT).show();
                job1 = customJob.getText().toString().trim();
                job2 = job1;
                job3 = job1;
            }
        }else if(!job1.equals("Other") && job2.equals("None") ){
            job2 = job1;
            job3 = job1;
        }else if(!job1.equals("Other") && job2.equals("Other") ){
            job2 =  customJob.getText().toString().trim();
            job3 = job2;
        }else if (!job1.equals("Other") && !job2.equals("Other")){
            job1 = job1;
            job2 = job2;
            job3 = job2;
        }else {
            job3 = job2 = job1;
        }


        //getting data from previous activity
        Intent prevIntent = getIntent();
        User user = (User) prevIntent.getSerializableExtra("UserObj");
        intent.putExtra("UserObj",user);

        //creating bundle and passing extra data
        Bundle bundle = new Bundle();
        bundle.putString("job1",job1);
        bundle.putString("job2",job2);
        bundle.putString("job3",job3);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
