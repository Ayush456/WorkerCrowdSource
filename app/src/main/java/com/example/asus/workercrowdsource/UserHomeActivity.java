package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    DatabaseReference mUserJobs;
    private FirebaseUser mUser;
    private String myUiD="",myJobSal="",myAddr="",myJobStart="",myJobEnd="",myJobCount="";
    private RecyclerView MyJobsList;

    FirebaseRecyclerOptions<PostJobsObject> options;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mDB = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myUiD = mUser.getUid();
        mUserJobs = mDB.getReference().child("USER_POST_JOBS").child(myUiD);

        MyJobsList = (RecyclerView)findViewById(R.id.rv_show_jobs);
        MyJobsList.setHasFixedSize(true);
        MyJobsList.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView mBottomNav = findViewById(R.id.user_bottom_nav_id);

        mBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
               if (menuItem.getItemId() ==R.id.post_userjob ){
                    startActivity(new Intent(UserHomeActivity.this,UserProvideJobsActivity.class));
               }else if(menuItem.getItemId() == R.id.view_userjob){



               }else {
                   startActivity(new Intent(UserHomeActivity.this,ProfileDetailsActivity.class));
               }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        options = new FirebaseRecyclerOptions.Builder<PostJobsObject>()
                        .setQuery(mUserJobs, PostJobsObject.class)
                        .build();

       firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PostJobsObject, PostViewHolder>(options) {
          @Override
          protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull PostJobsObject model) {
              holder.setAddress(model.getAddress());
              holder.setEndDate(model.getEndDate());
              holder.setStartDate(model.getStartDate());
              holder.setJobTitle(model.getJob());
              holder.setWorkerCount(model.getEstNoOfWorker());
              holder.setSalary(model.getSalary());
          }



          @NonNull
          @Override
          public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
             View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_posted_job,viewGroup,false);

              return new PostViewHolder(view);
          }
      };

        firebaseRecyclerAdapter.startListening();
        MyJobsList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
       View mView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

       public void setJobTitle(String Title){
           TextView jobTitle = mView.findViewById(R.id.post_job_title);
           jobTitle.setText(Title);
       }

       public void setSalary(String Sal){
            TextView post_sal = mView.findViewById(R.id.post_salary);
            post_sal.setText(Sal);
       }

       public void setAddress(String Addr){
            TextView post_addr = mView.findViewById(R.id.post_address);
            post_addr.setText(Addr);
       }

       public void setWorkerCount(String count){
            TextView post_worker_count = mView.findViewById(R.id.post_worker_count);
       }

       public void setContactNo(String ContactNo){
            TextView post_contactNo = mView.findViewById(R.id.post_contact_no);
       }
       public void setEmailId(String Email){
            TextView post_email_id = mView.findViewById(R.id.post_email);
       }
       public void setStartDate(String StartDate){
            TextView post_start_date = mView.findViewById(R.id.post_start_date);
       }
       public void setEndDate(String EndDate){
            TextView post_end_date = mView.findViewById(R.id.post_end_date);
       }

    }
}
