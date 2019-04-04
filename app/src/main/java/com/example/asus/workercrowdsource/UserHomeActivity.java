package com.example.asus.workercrowdsource;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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

        MyJobsList = (RecyclerView) findViewById(R.id.rv_show_jobs);
        MyJobsList.setHasFixedSize(true);
        MyJobsList.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView mBottomNav = findViewById(R.id.user_bottom_nav_id);

        mBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
               if (menuItem.getItemId() == R.id.post_userjob ){

                   Intent intent = new Intent(UserHomeActivity.this,UserProvideJobsActivity.class);
                   startActivity(intent);

               }else{
                   if(menuItem.getItemId() == R.id.view_userjob){

                   }else {
                       startActivity(new Intent(UserHomeActivity.this,ProfileDetailsActivity.class));
                   }
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

              final String post_key = getRef(position).getKey().toString();
              final String post_parent = getRef(position).getParent().getKey().toString();
              holder.setAddress(model.getAddress());
              holder.setEndDate(model.getEndDate());
              holder.setStartDate(model.getStartDate());
              holder.setJobTitle(model.getJob());
              holder.setWorkerCount(model.getEstNoOfWorker());
              holder.setSalary(model.getSalary());
              holder.setContactNo(post_parent);
              holder.setEmailId(post_parent);

              holder.mView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Toast.makeText(UserHomeActivity.this, post_key,Toast.LENGTH_LONG ).show();
                  }
              });

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
       FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
       String current_user_id = current_user.getUid();

      // DatabaseReference Current_User_Details = FirebaseDatabase.getInstance().getReference().child("ALL_USERS").child(current_user_id);
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
            post_worker_count.setText(count);
       }

       public void setContactNo(String uid){
            final TextView post_contactNo = mView.findViewById(R.id.post_contact_no);
            DatabaseReference Current_user_details = FirebaseDatabase.getInstance().getReference().child("ALL_USERS").child(uid);
            Current_user_details.keepSynced(true);

            final String[] contact = new String[1];
            Current_user_details.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    AllUsers user = dataSnapshot.getValue(AllUsers.class);
                    contact[0] = user.getContactNo();
                    post_contactNo.setText(contact[0]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


       }
       public void setEmailId(String uid){
            final TextView post_email_id = mView.findViewById(R.id.post_email);
           DatabaseReference Current_user_details = FirebaseDatabase.getInstance().getReference().child("ALL_USERS").child(uid);
           String email;

           Current_user_details.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   AllUsers user = dataSnapshot.getValue(AllUsers.class);
                   post_email_id.setText(user.getUsername());
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });


       }
       public void setStartDate(String StartDate){
            TextView post_start_date = mView.findViewById(R.id.post_start_date);
            post_start_date.setText(StartDate);
       }
       public void setEndDate(String EndDate){
            TextView post_end_date = mView.findViewById(R.id.post_end_date);
            post_end_date.setText(EndDate);
       }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(UserHomeActivity.this);
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
