package com.example.asus.workercrowdsource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    Context ctx;
    ArrayList<PostJobsObject> postJob;
    public MyAdapter(Context ctx,ArrayList<PostJobsObject> postJob){

        this.ctx = ctx;
        this.postJob = postJob;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_job_card,viewGroup,false);
        return new MyAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterViewHolder myAdapterViewHolder, int i) {
        final PostJobsObject post = postJob.get(i);
        myAdapterViewHolder.setJobName(post.getJob());
        myAdapterViewHolder.setJobAddress(post.getAddress());
        myAdapterViewHolder.setSal(post.getSalary());
        myAdapterViewHolder.setStart(post.getStartDate());
        myAdapterViewHolder.setEnd(post.getEndDate());
        myAdapterViewHolder.setWorkerCount(post.getEstNoOfWorker());

        //complex stuffs
        myAdapterViewHolder.setJobProviderName(post.getId());
        myAdapterViewHolder.setWorkersEnrolled(post.getId());

        //onclick on the card
        myAdapterViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(myAdapterViewHolder.view.getContext(),post.getId(),Toast.LENGTH_SHORT).show();
                Intent expandedRecJobs = new Intent(myAdapterViewHolder.view.getContext(),ExpandedRecJobsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PostId",post.getId());
                bundle.putString("JobName",post.getJob());
                bundle.putString("Salary",post.getSalary());
                bundle.putString("StartDate",post.getStartDate());
                bundle.putString("EndDate",post.getEndDate());
                bundle.putString("NoOfWorker",post.getEstNoOfWorker());
                bundle.putString("Address",post.getAddress());
                expandedRecJobs.putExtras(bundle);
                ctx.startActivity(expandedRecJobs);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postJob.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        View view;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setJobName(String Name){
            TextView mText = view.findViewById(R.id.job_name);
            mText.setText(Name);
        }

        public void setJobAddress(String Addr){
            TextView Address = view.findViewById(R.id.job_address);
            Address.setText(Addr);
        }

        public void setStart(String start){
            TextView start_field = view.findViewById(R.id.job_start);
            start_field.setText(start);
        }

        public void setEnd(String end){
            TextView end_field = view.findViewById(R.id.job_end);
            end_field.setText(end);
        }

        public void setWorkerCount(String count){
            TextView worker_count_field = view.findViewById(R.id.job_count);
            worker_count_field.setText(count);
        }

        public void setSal(String salary){
            TextView sal_filed = view.findViewById(R.id.job_salary);
            sal_filed.setText(salary);
        }

        public void setJobProviderName(final String currentPostId){
            final TextView mProvider = view.findViewById(R.id.job_provider_name);
            DatabaseReference mdb = FirebaseDatabase.getInstance().getReference().child("USER_POST_JOBS");
            final String[] ProviderKey = new String[1];
            final String[] ProviderName = new String[1];
            mdb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot users : dataSnapshot.getChildren()){

                        if (users.child(currentPostId).exists()){
                            ProviderKey[0] = users.getKey();
                        }

                    }

                    DatabaseReference currentUserPost = FirebaseDatabase.getInstance().getReference().child("ALL_USERS");
                    currentUserPost.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            AllUsers post_user_obj = dataSnapshot.child(ProviderKey[0]).getValue(AllUsers.class);
                            ProviderName[0] = post_user_obj.getName();
                            mProvider.setText(ProviderName[0]);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        public void setDistance(String dist){

        }
        public void setWorkersEnrolled(String JobUid){
            FirebaseDatabase.getInstance().getReference().child("Worker_EnrolledTo_Post").child(JobUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long Count = dataSnapshot.getChildrenCount();


                    TextView postEnrollmentCount = view.findViewById(R.id.job_enroll_count);
                    postEnrollmentCount.setText(""+Count);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

}
