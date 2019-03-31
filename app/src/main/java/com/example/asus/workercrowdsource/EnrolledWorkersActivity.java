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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EnrolledWorkersActivity extends AppCompatActivity {
    private RecyclerView enrolled_list;
    private String user_id;
    private DatabaseReference enroll,worker;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_workers);


        enrolled_list = (RecyclerView) findViewById(R.id.enrolled_list);
        enrolled_list.setHasFixedSize(true);
        enrolled_list.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser current_user = mAuth.getCurrentUser();
        user_id = current_user.getUid();
        enroll = FirebaseDatabase.getInstance().getReference().child("EnrolledWorkers").child(user_id);
        worker = FirebaseDatabase.getInstance().getReference().child("Worker");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Worker> options =
                new FirebaseRecyclerOptions.Builder<Worker>()
                        .setQuery(enroll, Worker.class)
                        .build();

        FirebaseRecyclerAdapter<Worker,EnrolledViewHolder> enrolledadapter = new FirebaseRecyclerAdapter<Worker, EnrolledViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final EnrolledViewHolder holder, int position, @NonNull Worker model) {

                final String user_list = getRef(position).getKey();
                DatabaseReference getTypeRef = getRef(position).child("Status").getRef();

                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            String type = dataSnapshot.getValue().toString();

                            if(type.equals("Enrolled")){

                                worker.child(user_list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        final String workername = dataSnapshot.child("Name").getValue().toString();
                                        final String workerjob = dataSnapshot.child("Job1").getValue().toString();
                                        final String workercontact = dataSnapshot.child("ContactNo").getValue().toString();
                                        final String workeraddress = dataSnapshot.child("Address").getValue().toString();
                                        final String workercity = dataSnapshot.child("City").getValue().toString();
                                        final String workerprofile = dataSnapshot.child("PPLink").getValue().toString();

                                        holder.name.setText(workername);
                                        holder.job.setText(workerjob);
                                        holder.contact.setText(workercontact);
                                        holder.address.setText(workeraddress);
                                        holder.city.setText(workercity);
                                        Picasso.get().load(workerprofile).into(holder.profileimage);


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

            }

            @NonNull
            @Override
            public EnrolledViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.enrolled_workers_row,viewGroup,false);
                EnrolledViewHolder viewHolder = new EnrolledViewHolder(view);
                return viewHolder;
            }
        };

        enrolled_list.setAdapter(enrolledadapter);
        enrolledadapter.startListening();
    }

    public static class EnrolledViewHolder extends RecyclerView.ViewHolder{

        TextView name,contact,address,city,job;
        CircleImageView profileimage;

        public EnrolledViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.enrolled_name);
            contact = itemView.findViewById(R.id.enrolled_contact);
            address = itemView.findViewById(R.id.enrolled_address);
            city = itemView.findViewById(R.id.enrolled_city);
            job = itemView.findViewById(R.id.enrolled_profession);
            profileimage = itemView.findViewById(R.id.enrolled_image);
        }
    }
}
