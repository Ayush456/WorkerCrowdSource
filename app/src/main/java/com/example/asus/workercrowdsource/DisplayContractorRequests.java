package com.example.asus.workercrowdsource;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayContractorRequests extends AppCompatActivity  {
    private RecyclerView con_requests;
    private DatabaseReference EnrollReq,WorkersRef,WorkersEnrolled;
    private FirebaseAuth mAutho;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contractor_requests);

        con_requests = (RecyclerView)findViewById(R.id.display_contractor_requests);
        con_requests.setLayoutManager(new LinearLayoutManager(this));

        mAutho = FirebaseAuth.getInstance();
        current_user_id = mAutho.getCurrentUser().getUid();
        EnrollReq = FirebaseDatabase.getInstance().getReference().child("EnrollRequests");
        WorkersRef = FirebaseDatabase.getInstance().getReference().child("Worker");
        WorkersEnrolled = FirebaseDatabase.getInstance().getReference().child("EnrolledWorkers");

        BottomNavigationView mBtnBottom = findViewById(R.id.bottom_nav4);
        mBtnBottom.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.post_contractorjob){
                    startActivity(new Intent(DisplayContractorRequests.this,ContractorPostJobs.class));
                }else if (menuItem.getItemId() == R.id.view_contractorjob){

                }else if((menuItem.getItemId() == R.id.view_workerrequests)){
                  //  startActivity(new Intent(DisplayContractorRequests.this,DisplayContractorRequests.class));

                }else if((menuItem.getItemId() == R.id.view_myworkers)){

                }else{
                    startActivity(new Intent(DisplayContractorRequests.this,ProfileDetailsActivity.class));
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Worker> options = new FirebaseRecyclerOptions.Builder<Worker>()
                .setQuery(EnrollReq.child(current_user_id),Worker.class)
                .build();

        FirebaseRecyclerAdapter<Worker,RequestViewHolder> adapter1 = new FirebaseRecyclerAdapter<Worker, RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RequestViewHolder holder, int position, @NonNull Worker model) {

                holder.itemView.findViewById(R.id.accept_request).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.reject_request).setVisibility(View.VISIBLE);

                final String user_list = getRef(position).getKey();
                DatabaseReference getTypeRef = getRef(position).child("request_type").getRef();

                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            String type = dataSnapshot.getValue().toString();

                            if(type.equals("received")){

                                WorkersRef.child(user_list).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        final String workername = dataSnapshot.child("Name").getValue().toString();
                                        final String workerjob = dataSnapshot.child("Job1").getValue().toString();
                                        final String workercontact = dataSnapshot.child("ContactNo").getValue().toString();
                                        final String workeraddress = dataSnapshot.child("Address").getValue().toString();
                                        final String workercity = dataSnapshot.child("City").getValue().toString();
                                        final String workerprofile = dataSnapshot.child("PPLink").getValue().toString();

                                        holder.wname.setText(workername);
                                        holder.wjob.setText(workerjob);
                                        holder.wcontact.setText(workercontact);
                                        holder.waddress.setText(workeraddress);
                                        holder.wcity.setText(workercity);
                                        Picasso.get().load(workerprofile).into(holder.wprofile);

                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {



                                                CharSequence options[] = new CharSequence[]
                                                        {
                                                                "Accept",
                                                                "Reject"

                                                        };



                                                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayContractorRequests.this);
                                                builder.setTitle(workername + " Enroll Request");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        if(which == 0){

                                                            WorkersEnrolled.child(current_user_id).child(user_list).child("Status").setValue("Enrolled").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if(task.isSuccessful()){

                                                                        EnrollReq.child(current_user_id).child(user_list).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                Toast.makeText(DisplayContractorRequests.this,"Worker Added to your List",Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        });
                                                                    }

                                                                }
                                                            });


                                                        }

                                                        if(which == 1){

                                                            EnrollReq.child(current_user_id).child(user_list).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    Toast.makeText(DisplayContractorRequests.this,"Worker Removed",Toast.LENGTH_SHORT).show();

                                                                }
                                                            });




                                                        }

                                                    }
                                                });
                                                builder.show();
                                            }




                                        });

//
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
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contractor_requests_row, viewGroup,false);
                RequestViewHolder holder = new RequestViewHolder(view);
                return holder;

            }
        };

        con_requests.setAdapter(adapter1);
        adapter1.startListening();



    }


    public static class RequestViewHolder extends RecyclerView.ViewHolder
    {
        TextView wname,wjob,wcontact,waddress,wcity;
        CircleImageView wprofile;
        Button accept,reject;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            wname = itemView.findViewById(R.id.worker_name);
            wjob = itemView.findViewById(R.id.worker_profession);
            wcontact = itemView.findViewById(R.id.worker_contact);
            waddress = itemView.findViewById(R.id.worker_address);
            wcity = itemView.findViewById(R.id.worker_city);

            wprofile = itemView.findViewById(R.id.worker_image);

            accept = itemView.findViewById(R.id.accept_request);
            reject = itemView.findViewById(R.id.reject_request);

        }
    }
}
