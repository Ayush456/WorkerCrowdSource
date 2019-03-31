package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnrolledWorkerJobs extends AppCompatActivity {
    private String contractor_id;
    private RecyclerView enrolledjobs;
    private DatabaseReference jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_worker_jobs);

        Intent previntent = getIntent();
        contractor_id = previntent.getStringExtra("contractor_id");


        enrolledjobs = (RecyclerView) findViewById(R.id.display_enrolled_jobs);
        enrolledjobs.setHasFixedSize(true);
        enrolledjobs.setLayoutManager(new LinearLayoutManager(this));

        jobs = FirebaseDatabase.getInstance().getReference().child("CONTRACTOR_POST_JOBS").child(contractor_id);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<EnrolledJobs> options =
                new FirebaseRecyclerOptions.Builder<EnrolledJobs>()
                        .setQuery(jobs, EnrolledJobs.class)
                        .build();

        FirebaseRecyclerAdapter<EnrolledJobs, EnrolledJobsViewHolder> adapter11 = new FirebaseRecyclerAdapter<EnrolledJobs, EnrolledJobsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EnrolledJobsViewHolder holder, int position, @NonNull EnrolledJobs model) {

                holder.jaddress.setText(model.getAddress());
                holder.jsd.setText(model.getStartDate());
                holder.jed.setText(model.getEndDate());
                holder.jobs.setText(model.getJob());
                holder.salary.setText(model.getSalary());
                holder.est.setText(model.getEstNoOfWorker());

            }

            @NonNull
            @Override
            public EnrolledJobsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.enrolled_worker_jobs_display,viewGroup,false);
                EnrolledJobsViewHolder viewHolder = new EnrolledJobsViewHolder(view);
                return viewHolder;
            }
        };

        enrolledjobs.setAdapter(adapter11);
        adapter11.startListening();

    }

    public static class EnrolledJobsViewHolder extends RecyclerView.ViewHolder{


        TextView jaddress,jsd,jed,jobs,est,salary;

        public EnrolledJobsViewHolder(@NonNull View itemView) {
            super(itemView);

            jaddress = itemView.findViewById(R.id.post_address);
            jsd = itemView.findViewById(R.id.post_start_date);
            jed = itemView.findViewById(R.id.post_end_date);
            jobs = itemView.findViewById(R.id.post_job_title);
            est = itemView.findViewById(R.id.post_worker_count);
            salary = itemView.findViewById(R.id.post_salary);

        }

    }
}
