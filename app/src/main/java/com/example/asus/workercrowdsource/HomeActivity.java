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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView mJobName,mJobAddress,mJobProvider,mJobSalary,mJobStart,mJobEnd,mJobDistance,mJobEnroll,mJobWorkerCount;
    private RecyclerView RecJobList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private String UserId="";
    private DatabaseReference UserPostedJobs,All_USERS_DB,Current_User;
    private String Job1="",Job2="";
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


       mAuth = FirebaseAuth.getInstance();
       mUser = mAuth.getCurrentUser();
       UserId = mUser.getUid();
       mDatabase = FirebaseDatabase.getInstance();

       FirebaseRecyclerOptions<WorkerDetails> options;
       FirebaseRecyclerAdapter firebaseRecyclerAdapter;


       BottomNavigationView mBottomNav = findViewById(R.id.bottom_nav);

       mBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
           @Override
           public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
             if (menuItem.getItemId() == R.id.view_recommended_job){



             }else if(menuItem.getItemId() == R.id.view_contractors){

                 //startActivity(new Intent(HomeActivity.this,ShowContractors.class));

             }else{
                 startActivity(new Intent(HomeActivity.this,ProfileDetailsActivity.class));
             }
           }
       });

       //Reference to user
       UserPostedJobs = mDatabase.getReference().child("USER_POST_JOBS");
       All_USERS_DB = mDatabase.getReference().child("ALL_USERS");
       Current_User = All_USERS_DB.child(UserId);



       //Getting Job profile of worker
       Current_User.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               WorkerDetails worker = dataSnapshot.getValue(WorkerDetails.class);
               Job1 = worker.getJob1();
               Job2 = worker.getJob2();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

       Toast.makeText(HomeActivity.this,Job1+" "+Job2,Toast.LENGTH_SHORT).show();


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
