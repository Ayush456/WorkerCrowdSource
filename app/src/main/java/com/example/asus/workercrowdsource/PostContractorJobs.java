package com.example.asus.workercrowdsource;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostContractorJobs extends AppCompatActivity {

    private String contractor_id;

    private RecyclerView contractorjobs;

    private DatabaseReference jobs;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_contractor_jobs);

        mAuth = FirebaseAuth.getInstance();

        contractor_id = mAuth.getCurrentUser().getUid();

        contractorjobs = (RecyclerView) findViewById(R.id.display_contractor_jobs);
        contractorjobs.setHasFixedSize(true);
        contractorjobs.setLayoutManager(new LinearLayoutManager(this));

        jobs = FirebaseDatabase.getInstance().getReference().child("CONTRACTOR_POST_JOBS").child(contractor_id);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<EnrolledJobs> options =
                new FirebaseRecyclerOptions.Builder<EnrolledJobs>()
                        .setQuery(jobs, EnrolledJobs.class)
                        .build();

        FirebaseRecyclerAdapter<EnrolledJobs,ContractorJobsViewHolder> adapter15 = new FirebaseRecyclerAdapter<EnrolledJobs, ContractorJobsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ContractorJobsViewHolder holder, int position, @NonNull EnrolledJobs model) {

                holder.jaddress.setText(model.getAddress());
                holder.jsd.setText(model.getStartDate());
                holder.jed.setText(model.getEndDate());
                holder.jobs.setText(model.getJob());
                holder.salary.setText(model.getSalary());
                holder.est.setText(model.getEstNoOfWorker());

            }

            @NonNull
            @Override
            public ContractorJobsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contrwctor_jobs_row,viewGroup,false);
                ContractorJobsViewHolder viewHolder = new ContractorJobsViewHolder(view);
                return viewHolder;
            }
        };

        contractorjobs.setAdapter(adapter15);
        adapter15.startListening();



    }

    public static class ContractorJobsViewHolder extends RecyclerView.ViewHolder{


        TextView jaddress,jsd,jed,jobs,est,salary;

        public ContractorJobsViewHolder(@NonNull View itemView) {
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
