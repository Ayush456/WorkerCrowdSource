package com.example.asus.workercrowdsource;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InterestedJobsActivity extends AppCompatActivity {
    private RecyclerView Rv_Job_Interested;
    private BottomNavigationView mBottomNav;
    private FirebaseDatabase mDB;
    private FirebaseUser mUser;
    private FirebaseAuth mAuthUser;
    ArrayList<PostJobsObject> InterestJobs;
    String UID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_jobs);


        Rv_Job_Interested = findViewById(R.id.rc_interested_jobs);
        Rv_Job_Interested.setLayoutManager(new LinearLayoutManager(this));
        Rv_Job_Interested.setHasFixedSize(true);
        mBottomNav = findViewById(R.id.bottomNavigationView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(InterestedJobsActivity.this,UID,Toast.LENGTH_SHORT).show();
        InterestJobs = new ArrayList<>();
        InterestJobs.clear();

//          DatabaseReference mDBWorkerEnrolled = FirebaseDatabase.getInstance().getReference().child("Worker_EnrolledTo_Post");
//          mDBWorkerEnrolled.addListenerForSingleValueEvent(new ValueEventListener() {
//              @Override
//              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                 for (DataSnapshot data : dataSnapshot.getChildren()){
//                     if (data.child(UID).exists()){
//                         PostJobsObject post = dataSnapshot.child(UID).getValue(PostJobsObject.class);
//                         InterestJobs.add(post);
//                         Toast.makeText(InterestedJobsActivity.this,post.getId(),Toast.LENGTH_SHORT).show();
//                     }else{
//                         Toast.makeText(InterestedJobsActivity.this,"False",Toast.LENGTH_SHORT).show();
//                     }
//                 }
//                  AdapterInterest myInterestedAdapter = new AdapterInterest(getApplicationContext(),InterestJobs);
//                  Rv_Job_Interested.setAdapter(myInterestedAdapter);
//                  myInterestedAdapter.notifyDataSetChanged();
//              }
//
//              @Override
//              public void onCancelled(@NonNull DatabaseError databaseError) {
//
//              }
//          });

        FirebaseDatabase.getInstance().getReference().child("Worker_EnrolledTo_Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot markedInterestPost : dataSnapshot.getChildren()){
                  if (markedInterestPost.child(UID).exists()){
                   PostJobsObject post = markedInterestPost.child(UID).getValue(PostJobsObject.class);
                   InterestJobs.add(post);

                  }
                }
                AdapterInterest myInterest = new AdapterInterest(getApplicationContext(),InterestJobs);
                Rv_Job_Interested.setAdapter(myInterest);
                myInterest.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
