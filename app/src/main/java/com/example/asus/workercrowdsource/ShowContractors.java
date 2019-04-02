package com.example.asus.workercrowdsource;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowContractors extends AppCompatActivity {

    private RecyclerView contractors;
    private DatabaseReference mDatabase;
    private BottomNavigationView mbottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contractors);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Contractor");

        contractors = (RecyclerView) findViewById(R.id.contractor_list);
        contractors.setHasFixedSize(true);
        contractors.setLayoutManager(new LinearLayoutManager(this));
        mbottomNav = findViewById(R.id.bottom_nav3);

        mbottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.view_recommended_job){
                    startActivity(new Intent(ShowContractors.this,HomeActivity.class));

                }else if(menuItem.getItemId() == R.id.view_workers_interested_jobs){
                    startActivity(new Intent(ShowContractors.this,InterestedJobsActivity.class));
                }else if(menuItem.getItemId() == R.id.view_contractors){

                }else{
                    startActivity(new Intent(ShowContractors.this,ProfileDetailsActivity.class));
                }
            }
        });

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
            protected void onBindViewHolder(@NonNull ContractorViewHolder holder, final int position, @NonNull Contractor model) {

                holder.name.setText(model.getName());
                holder.contact.setText(model.getContactNo());
                holder.address.setText(model.getAddress());
                holder.city.setText(model.getCity());
                holder.email.setText(model.getUsername());
                Picasso.get().load(model.getPPLink()).into(holder.profileimage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Contractor_id = getRef(position).getKey();

                        Intent contractorenhanced = new Intent(ShowContractors.this,ContractorEnhancedActivity.class);
                        contractorenhanced.putExtra("contractor_id",Contractor_id);
                        startActivity(contractorenhanced);

                    }
                });
            }

            @NonNull
            @Override
            public ContractorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contractor_row,viewGroup,false);
                ContractorViewHolder viewHolder = new ContractorViewHolder(view);
                return viewHolder;
            }
        };

        contractors.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class ContractorViewHolder extends RecyclerView.ViewHolder{

        TextView name,contact,address,city,email;
        CircleImageView profileimage;

        public ContractorViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.show_name);
            contact = itemView.findViewById(R.id.show_contact);
            address = itemView.findViewById(R.id.show_address);
            city = itemView.findViewById(R.id.show_city);
            email = itemView.findViewById(R.id.show_email);
            profileimage = itemView.findViewById(R.id.contractor_image);

        }



    }
}
