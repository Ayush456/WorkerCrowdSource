package com.example.asus.workercrowdsource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ShowContractors extends AppCompatActivity {

    private RecyclerView contractors;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contractors);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Contractor");

        contractors = (RecyclerView) findViewById(R.id.contractor_list);
        contractors.setHasFixedSize(true);
        contractors.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contractor> options =
                new FirebaseRecyclerOptions.Builder<Contractor>()
                        .setQuery(mDatabase, Contractor.class)
                        .build();

        FirebaseRecyclerAdapter<Contractor, ContractorViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Contractor, ContractorViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ContractorViewHolder holder, int position, @NonNull Contractor model) {

                holder.setName(model.getName());
                holder.setContactNo(model.getContactNo());
                holder.setCity(model.getCity());
                holder.setPPLink(getApplicationContext(),model.getPPLink());

            }

            @NonNull
            @Override
            public ContractorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }
        };

        contractors.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ContractorViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ContractorViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String Name){

            TextView post_name = (TextView) mView.findViewById(R.id.show_name);
            post_name.setText(Name);

        }

        public void setContactNo(String ContactNo){

            TextView post_contact = (TextView) mView.findViewById(R.id.show_contact);
            post_contact.setText(ContactNo);

        }


        public void setCity(String City){

            TextView post_location = (TextView) mView.findViewById(R.id.show_location);
            post_location.setText(City);

        }

        public void setPPLink(Context ctx, String PPLink){

            ImageView post_image = (ImageView) mView.findViewById(R.id.contractor_image);
          //  Picasso.with(ctx).load(PPLink).into(post_image);

        }
    }
}
