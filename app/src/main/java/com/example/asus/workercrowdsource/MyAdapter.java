package com.example.asus.workercrowdsource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull MyAdapterViewHolder myAdapterViewHolder, int i) {
        PostJobsObject post = postJob.get(i);
        myAdapterViewHolder.setJobName(post.getJob());
        myAdapterViewHolder.setJobAddress(post.getAddress());
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

    }

}
