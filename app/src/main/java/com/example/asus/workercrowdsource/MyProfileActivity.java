package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {
    private ImageView mImage;
    private TextView mName,mEmail,mContact;
    private Button mSignout;
    private String UID;
    String ppLink = "",Name="",Email="",Contact="",Role="";

    private int statusWorker=0,statusUser=0,statusContractor=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mImage = findViewById(R.id.imageView3);
        mName = findViewById(R.id.textView10);
        mEmail = findViewById(R.id.textView15);
        mContact = findViewById(R.id.textView16);

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        String UID = mUser.getUid();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        mSignout = findViewById(R.id.button9);

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MyProfileActivity.this,MainActivity.class));
            }
        });


        //Toast.makeText(MyProfileActivity.this,UID,Toast.LENGTH_LONG).show();

        if (checkWorker(UID) == 1){
            Toast.makeText(MyProfileActivity.this,UID,Toast.LENGTH_LONG).show();
        }
        if (checkUser(UID) == 1){
            Toast.makeText(MyProfileActivity.this,UID,Toast.LENGTH_LONG).show();
        }
        if (checkContractor(UID) == 1){
            Toast.makeText(MyProfileActivity.this,UID,Toast.LENGTH_LONG).show();
        }







    }

    public int checkWorker(final String uid){
        DatabaseReference worker =FirebaseDatabase.getInstance().getReference().child("Worker");
        worker.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uid)){
                    statusWorker = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return statusWorker;
    }

    public int checkUser(final String uid){
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User");
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)){
                    statusUser = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return statusUser;
    }

    public int checkContractor(final String uid){
        DatabaseReference contractor = FirebaseDatabase.getInstance().getReference().child("Contractor");
        contractor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)){
                    statusContractor = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return statusContractor;
    }


}
