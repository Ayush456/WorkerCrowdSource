package com.example.asus.workercrowdsource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterInterest extends RecyclerView.Adapter<AdapterInterest.AdapterInterestViewHolder> {
    private Context ctx;
    ArrayList<PostJobsObject> postsList;

    public AdapterInterest(Context ctx, ArrayList<PostJobsObject> postsJob){
        this.postsList = postsJob;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterInterestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vi = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_job_card,viewGroup,false);
        return new AdapterInterestViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInterestViewHolder adapterInterestViewHolder, int i) {
        final PostJobsObject post = postsList.get(i);
        String postJobName = post.getJob();
        adapterInterestViewHolder.setJobName(postJobName);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class AdapterInterestViewHolder extends RecyclerView.ViewHolder {
        View view;
        public AdapterInterestViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }


        public void setJobName(final String job) {
            TextView myJob = view.findViewById(R.id.job_name);
            myJob.setText(job);
        }
    }
}
