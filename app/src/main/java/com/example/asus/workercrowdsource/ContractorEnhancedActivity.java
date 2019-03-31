package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class ContractorEnhancedActivity extends AppCompatActivity {

    private String contractor_id,Current_state;

    private CircleImageView contractor_image;
    private TextView cname;
    private TextView ccontact;
    private TextView caddress;
    private TextView cemail;
    private TextView ccity;
    private Button enrollc;

    private String current_worker_id;

    private DatabaseReference mCon,EnrollRequest;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_enhanced);

        Intent previntent3 = getIntent();
        contractor_id = previntent3.getStringExtra("contractor_id");

        mCon = FirebaseDatabase.getInstance().getReference().child("Contractor");
        EnrollRequest = FirebaseDatabase.getInstance().getReference().child("EnrollRequests");


        mAuth = FirebaseAuth.getInstance();

        current_worker_id = mAuth.getCurrentUser().getUid();

        contractor_image = (CircleImageView) findViewById(R.id.cont_image);
        cname = (TextView) findViewById(R.id.contr_name);
        ccontact = (TextView) findViewById(R.id.contr_contact);
        caddress = (TextView) findViewById(R.id.contr_address);
        cemail = (TextView) findViewById(R.id.contr_email);
        ccity = (TextView) findViewById(R.id.contr_city);
        enrollc = (Button) findViewById(R.id.enroll);
        Current_state = "new";

        RetrieveContractorInformation();

    }

    private void RetrieveContractorInformation() {

        mCon.child(contractor_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String coimage = dataSnapshot.child("PPLink").getValue().toString();
                String coname = dataSnapshot.child("Name").getValue().toString();
                String cocontact = dataSnapshot.child("ContactNo").getValue().toString();
                String coemail = dataSnapshot.child("Username").getValue().toString();
                String coaddress = dataSnapshot.child("Address").getValue().toString();
                String cocity = dataSnapshot.child("City").getValue().toString();

                Picasso.get().load(coimage).placeholder(R.drawable.profile).into(contractor_image);
                cname.setText(coname);
                ccontact.setText(cocontact);
                cemail.setText(coemail);
                caddress.setText(coaddress);
                ccity.setText(cocity);

                ManageEnrollRequests();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ManageEnrollRequests() {

        EnrollRequest.child(current_worker_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(contractor_id)){

                    String request_type = dataSnapshot.child(contractor_id).child("request_type").getValue().toString();

                    if(request_type.equals("sent")){

                        Current_state = "request_sent";
                        enrollc.setText("Cancel Enroll Reuqest");
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        enrollc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enrollc.setEnabled(false);

                if(Current_state.equals("new")){

                    SendEnrollRequest();
                }

                if(Current_state.equals("request_sent")){

                    CancelCharReques();
                }


            }
        });



    }

    private void CancelCharReques() {

        EnrollRequest.child(current_worker_id).child(contractor_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    EnrollRequest.child(contractor_id).child(current_worker_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                enrollc.setEnabled(true);
                                Current_state = "new";
                                enrollc.setText("Sent Enroll Request");
                            }

                        }
                    });

                }

            }
        });

    }


    private void SendEnrollRequest() {

        EnrollRequest.child(current_worker_id).child(contractor_id).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    EnrollRequest.child(contractor_id).child(current_worker_id).child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                enrollc.setEnabled(true);
                                Current_state = "request_sent";
                                enrollc.setText("Cancel Enroll Reuqest");
                            }
                        }
                    });
                }

            }
        });

    }
}
