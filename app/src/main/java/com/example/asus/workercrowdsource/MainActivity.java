package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    public EditText mUsername,mPassowrd;
    public Button mBtnLogin,mBtnSignup,mBtnForgotPass;
    private ProgressBar mprogressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Boolean statusContractor = false;
    private Boolean statusUser = false;
    private Boolean statusWorker = false;
    private String uid;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization of member object
        mUsername = (EditText) findViewById(R.id.editText);
        mPassowrd = (EditText) findViewById(R.id.editText2);
        mBtnLogin = (Button) findViewById(R.id.button);
        mBtnSignup = (Button) findViewById(R.id.button2);
        mBtnForgotPass = (Button) findViewById(R.id.button3);
        mprogressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();  // firebase Auth initialization
        mprogressBar.setVisibility(View.GONE);



        //user does a login from this side
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((mUsername.getText().toString().trim()).equals("")){
                    mUsername.setError("UserName required with length greater than 8 char! ");
                    mUsername.requestFocus();
                }else if( (mPassowrd.getText().toString().trim()).equals("")){
                    mPassowrd.setError("Password required with length atleast 5 char");
                    mPassowrd.requestFocus();
                }else{
                    mprogressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(mUsername.getText().toString().trim(), mPassowrd.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //registeration is successful

                                        Intent HomeActivity = new Intent(MainActivity.this,HomeActivity.class);
                                        Intent UserActivity = new Intent(MainActivity.this,UserHomeActivity.class);
                                        Intent ContractorActivity = new Intent(MainActivity.this,ContractorHomeActivity.class);

                                        FirebaseUser mUser = mAuth.getCurrentUser();
                                        uid = mUser.getUid();

                                        if (checkContractor(uid)){
                                            mprogressBar.setVisibility(View.INVISIBLE);
                                            startActivity(ContractorActivity);

                                        }
                                        if(checkUser(uid)){
                                            mprogressBar.setVisibility(View.INVISIBLE);
                                            startActivity(UserActivity);

                                        }
                                       if (checkWorker(uid)){
                                           mprogressBar.setVisibility(View.INVISIBLE);
                                            startActivity(HomeActivity);
                                        }

                                    } else {
                                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        mprogressBar.setVisibility(View.INVISIBLE);
                                    }

                                }
                            });

                }

            }
        });


        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent userDetails = new Intent(MainActivity.this,UserDetailsActivity.class);
              startActivity(userDetails);

            }

        });

        mBtnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mUsername.getText().toString().trim()).equalsIgnoreCase("")){
                    mprogressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Please enter your email address to reset the password",Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(mUsername.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mprogressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(MainActivity.this,"Password Reset Link is Sent",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = mUser.getUid();

        Intent HomeActivity = new Intent(MainActivity.this,HomeActivity.class);
        Intent UserActivity = new Intent(MainActivity.this,UserHomeActivity.class);
        Intent ContractorActivity = new Intent(MainActivity.this,ContractorHomeActivity.class);


        if (mUser != null){
          //  FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid = mUser.getUid();

            if (checkContractor(uid)){
                startActivity(ContractorActivity);
            }
            if(checkUser(uid)){
                startActivity(UserActivity);
            }
            if(checkWorker(uid)){
                startActivity(HomeActivity);
            }
        }

    }

    public Boolean checkWorker(final String uid){

        DatabaseReference worker = FirebaseDatabase.getInstance().getReference().child("Worker");
        worker.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uid)){
                    statusWorker = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return statusWorker;
    }

    public Boolean checkUser(final String uid){
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("User");
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)){
                    statusUser = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return statusUser;
    }

    public Boolean checkContractor(final String uid){
       DatabaseReference contractor = FirebaseDatabase.getInstance().getReference().child("Contractor");
       contractor.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.hasChild(uid)){
                   statusContractor = true;
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        return statusContractor;
    }


}
