package com.example.asus.workercrowdsource;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView mJobName,mJobAddress,mJobProvider,mJobSalary,mJobStart,mJobEnd,mJobDistance,mJobEnroll,mJobWorkerCount;
    private RecyclerView RecJobList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private String UserId="";
    private DatabaseReference UserPostedJobs,All_USERS_DB,Current_User;
    private BottomNavigationView mBottomNav;
    private String Job1,Job2;
    FirebaseRecyclerOptions<PostJobsObject> options;
//    FirebaseRecyclerAdapter<PostJobsObject,PostJobViewHolder> adapter;

    private ArrayList<PostJobsObject> filteredJobs;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       //Recycler view creation
       RecJobList = findViewById(R.id.rc_rec_jobs);
       RecJobList.setHasFixedSize(true);
       RecJobList.setLayoutManager(new LinearLayoutManager(this));

        filteredJobs = new ArrayList<>();

       //setting the bottom navigation bar
       mBottomNav = findViewById(R.id.bottom_nav);
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

      //creating the Auth database reference
       mAuth = FirebaseAuth.getInstance();
       mUser = mAuth.getCurrentUser();
       UserId = mUser.getUid();

       //creating the database reference
       mDatabase = FirebaseDatabase.getInstance();
       All_USERS_DB = mDatabase.getReference().child("ALL_USERS");
       UserPostedJobs = mDatabase.getReference().child("USER_POST_JOBS");
       Current_User = All_USERS_DB.child(UserId);

       Current_User.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               WorkerDetails worker = dataSnapshot.getValue(WorkerDetails.class);
               Job1 = worker.getJob1();
               Job2 = worker.getJob2();
              // Toast.makeText(HomeActivity.this," "+Job1+" "+Job2,Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

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

    @Override
    protected void onStart() {
        super.onStart();

        //Query q = UserPostedJobs.orderByChild("Job").equalTo(Job1);
        //Query q2 = UserPostedJobs.orderByChild("Job").equalTo(Job2);



        UserPostedJobs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChildren()){
                    filteredJobs.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren()){
                        final PostJobsObject postJobsObject = dss.getValue(PostJobsObject.class);

                    if (!Job1.equals(Job2)){
                        if ((postJobsObject.getJob()).equals(Job1)){
                            filteredJobs.add(postJobsObject);
                        }else{
                            if ((postJobsObject.getJob().equals(Job2))){
                                filteredJobs.add(postJobsObject);
                            }else{

                            }
                        }
                    }else{
                        String start = String.valueOf(Job1.charAt(0));
                        if (postJobsObject.getJob().startsWith(start)){
                            filteredJobs.add(postJobsObject);
                        }else{
                            String firstTwo = Job1.substring(0,2);
                            if (postJobsObject.getJob().contains(firstTwo)){
                                filteredJobs.add(postJobsObject);
                            }
                        }
                    }


                    }

                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(),filteredJobs);
                    RecJobList.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





   }


}
