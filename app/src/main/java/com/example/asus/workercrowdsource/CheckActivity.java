package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckActivity extends AppCompatActivity {

    private DatabaseReference enrolled;
    private FirebaseAuth mAuth;
    private String current_user;
    private String current_contractor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser().getUid();
        enrolled = FirebaseDatabase.getInstance().getReference().child("EnrolledWorkers");

        enrolled.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot AllContractor : dataSnapshot.getChildren()){

                    if (AllContractor.child(current_user).exists()){

                        current_contractor_id = AllContractor.getKey();

                        Intent enrolledjobs = new Intent(CheckActivity.this,EnrolledWorkerJobs.class);
                        enrolledjobs.putExtra("contractor_id",current_contractor_id);
                        startActivity(enrolledjobs);

                    }
                    else{
                        startActivity(new Intent(CheckActivity.this,ShowContractors.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
