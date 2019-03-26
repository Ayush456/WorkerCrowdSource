package com.example.asus.workercrowdsource;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileDetailsActivity extends AppCompatActivity {
    private FirebaseDatabase mDB;
    private DatabaseReference mAllUser,mCurrentUser;
    private FirebaseAuth mAuthenticator;
    private FirebaseUser mUser;
    private Button msignout;
    private ImageButton mChangeName,mChangeEmail,mChangeCity,mChangeContactNo;

    private TextView mName,mEmail,mContactNo,mCity;
    private ImageView mImage;
    private ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);


        mImage = findViewById(R.id.imageView4);
        mName = findViewById(R.id.textView32);
        mEmail = findViewById(R.id.textView39);
        mContactNo = findViewById(R.id.textView41);
        mCity = findViewById(R.id.textView43);
        mChangeName = findViewById(R.id.imageButton5);
        mChangeEmail = findViewById(R.id.imageButton4);
        mChangeContactNo = findViewById(R.id.imageButton3);
        mChangeCity = findViewById(R.id.imageButton2);


        msignout = findViewById(R.id.button10);
//        mProgress = findViewById(R.id.progressBar4);
//        mProgress.setVisibility(View.INVISIBLE);

        mAuthenticator = FirebaseAuth.getInstance();

        msignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mAuthenticator.signOut();
              startActivity(new Intent(ProfileDetailsActivity.this,MainActivity.class));
            }
        });

        mDB = FirebaseDatabase.getInstance();
        String UID = mAuthenticator.getUid();
      //  String url;
        //Toast.makeText(ProfileDetailsActivity.this,UID,Toast.LENGTH_SHORT).show();

        mAllUser = mDB.getReference().child("ALL_USERS");
        //Toast.makeText(ProfileDetailsActivity.this, mAllUser.toString() ,Toast.LENGTH_SHORT).show();

        mCurrentUser = mAllUser.child(UID);
       // Toast.makeText(ProfileDetailsActivity.this, mCurrentUser.toString() ,Toast.LENGTH_SHORT).show();
     //  final HashMap<String,String> allUsers = new HashMap<>();
        mCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AllUsers Thisuser = dataSnapshot.getValue(AllUsers.class);
                Picasso.get().load(Uri.parse(Thisuser.getPPLink())).fit().into(mImage);
                mEmail.setText(Thisuser.getUsername());
                mName.setText(Thisuser.getName());
                mContactNo.setText(Thisuser.getContactNo());
                mCity.setText(Thisuser.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toast.makeText(ProfileDetailsActivity.this, mCurrentUser.toString() ,Toast.LENGTH_SHORT).show();

        mChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             startActivity(new Intent(ProfileDetailsActivity.this,ChangeDetailsActivity.class));
            }
        });

        mChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileDetailsActivity.this,ChangeDetailsActivity.class));

            }
        });

        mChangeContactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileDetailsActivity.this,ChangeDetailsActivity.class));

            }
        });

        mChangeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileDetailsActivity.this,ChangeDetailsActivity.class));

            }
        });

    }


}
