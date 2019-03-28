package com.example.asus.workercrowdsource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExpandedRecJobsActivity extends AppCompatActivity {
    private TextView mJobId,mJobSal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_rec_jobs);

        mJobId = findViewById(R.id.textView10);
        mJobSal = findViewById(R.id.textView17);
        Bundle prevBundle = getIntent().getExtras();
        String id = prevBundle.getString("PostId");
        String Sal = prevBundle.getString("Salary");

        mJobId.setText(id);
        mJobSal.setText(Sal);
    }

}
