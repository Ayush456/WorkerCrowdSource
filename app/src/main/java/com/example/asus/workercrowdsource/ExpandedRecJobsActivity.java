package com.example.asus.workercrowdsource;

import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExpandedRecJobsActivity extends AppCompatActivity {
    private TextView mJobId,mJobSal;
    private CircleImageView mImage;
    private TextView mProvider,mSalary,mAddress,mJobTitle,mStartDate,mEndDate,mNoWorkerInterested;
    String postId="";
    String uid="",imageURL="";
     private DatabaseReference ALL_USERS,Worker_EnrolledTo_Post;
     Uri imageuri;
     private Button mBtnInterest;

     private FirebaseAuth mAuth;
     private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_rec_jobs);

        mImage = findViewById(R.id.circleImageView);
        mProvider = findViewById(R.id.textView29);
        mAddress = findViewById(R.id.textView33);
        mJobTitle = findViewById(R.id.textView46);
        mStartDate =findViewById(R.id.textView56);
        mEndDate =findViewById(R.id.textView58);
        mNoWorkerInterested = findViewById(R.id.textView60);
        mSalary = findViewById(R.id.textView54);
        mBtnInterest = findViewById(R.id.button17);

        Bundle prevBundle = getIntent().getExtras();
        final String id = prevBundle.getString("PostId");
        String Sal = prevBundle.getString("Salary");
        String startDate = prevBundle.getString("StartDate");
        String endDate = prevBundle.getString("EndDate");
        String count = prevBundle.getString("NoOfWorker");
        String Addr = prevBundle.getString("Address");
        String jobName = prevBundle.getString("JobName");
        postId = prevBundle.getString("PostId");

        mNoWorkerInterested.setText(count);
        mSalary.setText(Sal);
        mStartDate.setText(startDate);
        mEndDate.setText(endDate);
        mJobTitle.setText(jobName);
        mAddress.setText(Addr);


//      Database Initialization and Refrencing
        FirebaseDatabase mDB = FirebaseDatabase.getInstance();
        ALL_USERS = FirebaseDatabase.getInstance().getReference().child("ALL_USERS");
        final DatabaseReference mDBUserPostJobs = mDB.getReference().child("USER_POST_JOBS");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        final String mCurrentUid = mCurrentUser.getUid();


        uid = "";

        mDBUserPostJobs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot all_user_child : dataSnapshot.getChildren()){
                    if (all_user_child.child(postId).exists() ){
                        uid = all_user_child.getKey();
                       // Toast.makeText(ExpandedRecJobsActivity.this,uid,Toast.LENGTH_SHORT).show();

                        ALL_USERS.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                AllUsers user = dataSnapshot.getValue(AllUsers.class);
                               mProvider.setText(user.getName());
                               Picasso.get().load(Uri.parse(user.getPPLink())).fit().into(mImage);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mBtnInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Worker_EnrolledTo_Post = FirebaseDatabase.getInstance().getReference().child("Worker_EnrolledTo_Post");
                Worker_EnrolledTo_Post.child(postId).child(mCurrentUid).setValue(mCurrentUid);
                Toast.makeText(ExpandedRecJobsActivity.this,"Interest MArked!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
