package com.example.asus.workercrowdsource;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView mJobName,mJobAddress,mJobProvider,mJobSalary,mJobStart,mJobEnd,mJobDistance,mJobEnroll,mJobWorkerCount;
    private RecyclerView jobRecycler;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // For easier finding of the layput and fields
//        jobRecycler = findViewById(R.id.rc_rec_jobs);
//        mJobName = findViewById(R.id.job_name);
//        mJobAddress = findViewById(R.id.job_address);
//        mJobProvider = findViewById(R.id.job_provider_name);
//        mJobSalary = findViewById(R.id.job_salary);
//        mJobStart = findViewById(R.id.job_start);
//        mJobEnd = findViewById(R.id.job_end);
//        mJobDistance = findViewById(R.id.job_enroll_count);
//        mJobWorkerCount = findViewById(R.id.job_count);




       BottomNavigationView mBottomNav = findViewById(R.id.bottom_nav);

       mBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
           @Override
           public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
             if (menuItem.getItemId() == R.id.view_recommended_job){



             }else if(menuItem.getItemId() ==R.id.view_contractors){

                 startActivity(new Intent(HomeActivity.this,ShowContractors.class));

             }else{
                 startActivity(new Intent(HomeActivity.this,ProfileDetailsActivity.class));
             }
           }
       });

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
