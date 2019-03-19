package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfileActivity extends AppCompatActivity {

    private Button mSignout;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mSignout = findViewById(R.id.button9);

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MyProfileActivity.this,MainActivity.class));
            }
        });



    }
}
