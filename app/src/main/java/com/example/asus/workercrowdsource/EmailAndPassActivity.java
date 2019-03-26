package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Timer;
import java.util.TimerTask;

public class EmailAndPassActivity extends AppCompatActivity {
    private EditText mUname,mPass,mRepeatPass;
    private Button mBtnVerify;
    private String Uname="",Pass="",RepeatPass="";
    private TextView mAlert;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;
    private ProgressBar progressBar;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_pass);

        mUname = (EditText) findViewById(R.id.editText4);
        mPass = (EditText) findViewById(R.id.editText7);
        mRepeatPass = (EditText) findViewById(R.id.editText10);
        mBtnVerify = (Button) findViewById(R.id.button7);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        mBtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uname = mUname.getText().toString().trim();
                Pass = mPass.getText().toString().trim();
                RepeatPass = mRepeatPass.getText().toString().trim();
                mRepeatPass.setError(null);
                mUname.setError(null);
                mPass.setError(null);

                if ( TextUtils.isEmpty(Uname) || (Uname.length() < 7) ){
                    mUname.requestFocus();
                }else if( TextUtils.isEmpty(Pass) || Pass.length() < 6  ){
                    mPass.requestFocus();
                }else if( !Pass.equals(RepeatPass) ){
                    mRepeatPass.setError("Password and Re-entered Password doesn't Match");
                    mRepeatPass.requestFocus();
                }else{

                     progressBar.setVisibility(View.VISIBLE);
                     mAuth.createUserWithEmailAndPassword(Uname, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           try {

                               if (task.isSuccessful()) {
                                   mUser = mAuth.getCurrentUser();
                                   String userID = mUser.getUid();
                                   sendVerificationEmail();
                                   progressBar.setVisibility(View.INVISIBLE);
                                  // mUname.requestFocus();
                                  //Getting data role from bundle
                                   Intent prevIntent = getIntent();
                                   User user =(User) prevIntent.getSerializableExtra("UserObj");
                                   String role = user.getRole();

                                   if (role.equals("worker")){
                                        Bundle bundle = getIntent().getExtras();
                                        String job1 = bundle.getString("job1");
                                        String job2 = bundle.getString("job2");
                                        String job3 = bundle.getString("job3");

                                       DatabaseReference workerRef = mDatabase.getReference().child("Worker").child(userID);
                                       workerRef.child("Name").setValue(user.getName());
                                       workerRef.child("ContactNo").setValue(user.getContactNo());
                                       workerRef.child("Address").setValue(user.getAddr());
                                       workerRef.child("Email").setValue(user.getEmail());
                                       workerRef.child("City").setValue(user.getCity());
                                       workerRef.child("Pincode").setValue(user.getPincode());
                                       workerRef.child("Role").setValue(user.getRole());
                                       workerRef.child("PPLink").setValue(user.getPPLink());
                                       workerRef.child("Username").setValue(Uname);
                                       workerRef.child("Job1").setValue(job1);
                                       workerRef.child("Job2").setValue(job2);
                                       workerRef.child("Job3").setValue(job3);

                                       DatabaseReference AllUsers = mDatabase.getReference().child("ALL_USERS").child(userID);
                                       AllUsers.child("Name").setValue(user.getName());
                                       AllUsers.child("ContactNo").setValue(user.getContactNo());
                                       AllUsers.child("Address").setValue(user.getAddr());
                                       AllUsers.child("Email").setValue(user.getEmail());
                                       AllUsers.child("City").setValue(user.getCity());
                                       AllUsers.child("Pincode").setValue(user.getPincode());
                                       AllUsers.child("Role").setValue(user.getRole());
                                       AllUsers.child("PPLink").setValue(user.getPPLink());
                                       AllUsers.child("Username").setValue(Uname);
                                       AllUsers.child("Job1").setValue(job1);
                                       AllUsers.child("Job2").setValue(job2);
                                       AllUsers.child("Job3").setValue(job3);


                                       new CountDownTimer(6000, 1000) {
                                           public void onFinish() {
                                               FirebaseAuth.getInstance().signOut();
                                               startActivity(new Intent(EmailAndPassActivity.this,MainActivity.class));
                                           }

                                           public void onTick(long millisUntilFinished) {
                                               // millisUntilFinished    The amount of time until finished.
                                           }
                                       }.start();

                                   }else if(role.equals("user")){

                                               DatabaseReference userRef = mDatabase.getReference().child("User").child(userID);

                                               userRef.child("Name").setValue(user.getName());
                                               userRef.child("ContactNo").setValue(user.getContactNo());
                                               userRef.child("Address").setValue(user.getAddr());
                                               userRef.child("Email").setValue(user.getEmail());
                                               userRef.child("City").setValue(user.getCity());
                                               userRef.child("Pincode").setValue(user.getPincode());
                                               userRef.child("Role").setValue(user.getRole());
                                               userRef.child("PPLink").setValue(user.getPPLink());
                                               userRef.child("Username").setValue(Uname);

                                       DatabaseReference AllUsers = mDatabase.getReference().child("ALL_USERS").child(userID);
                                               AllUsers.child("Name").setValue(user.getName());
                                               AllUsers.child("ContactNo").setValue(user.getContactNo());
                                               AllUsers.child("Address").setValue(user.getAddr());
                                               AllUsers.child("Email").setValue(user.getEmail());
                                               AllUsers.child("City").setValue(user.getCity());
                                               AllUsers.child("Pincode").setValue(user.getPincode());
                                               AllUsers.child("Role").setValue(user.getRole());
                                               AllUsers.child("PPLink").setValue(user.getPPLink());
                                               AllUsers.child("Username").setValue(Uname);



                                       new CountDownTimer(6000, 1000) {
                                           public void onFinish() {
                                               FirebaseAuth.getInstance().signOut();
                                               startActivity(new Intent(EmailAndPassActivity.this,MainActivity.class));
                                           }

                                           public void onTick(long millisUntilFinished) {
                                               // millisUntilFinished    The amount of time until finished.
                                           }
                                       }.start();

                                   }else{

                                       DatabaseReference contractorRef = mDatabase.getReference().child("Contractor").child(userID);
                                       contractorRef.child("Name").setValue(user.getName());
                                       contractorRef.child("ContactNo").setValue(user.getContactNo());
                                       contractorRef.child("Address").setValue(user.getAddr());
                                       contractorRef.child("Email").setValue(user.getEmail());
                                       contractorRef.child("City").setValue(user.getCity());
                                       contractorRef.child("Pincode").setValue(user.getPincode());
                                       contractorRef.child("Role").setValue(user.getRole());
                                       contractorRef.child("PPLink").setValue(user.getPPLink());
                                       contractorRef.child("Username").setValue(Uname);

                                       DatabaseReference AllUsers = mDatabase.getReference().child("ALL_USERS").child(userID);
                                       AllUsers.child("Name").setValue(user.getName());
                                       AllUsers.child("ContactNo").setValue(user.getContactNo());
                                       AllUsers.child("Address").setValue(user.getAddr());
                                       AllUsers.child("Email").setValue(user.getEmail());
                                       AllUsers.child("City").setValue(user.getCity());
                                       AllUsers.child("Pincode").setValue(user.getPincode());
                                       AllUsers.child("Role").setValue(user.getRole());
                                       AllUsers.child("PPLink").setValue(user.getPPLink());
                                       AllUsers.child("Username").setValue(Uname);


                                       new CountDownTimer(6000, 1000) {
                                           public void onFinish() {
                                               FirebaseAuth.getInstance().signOut();
                                               startActivity(new Intent(EmailAndPassActivity.this,MainActivity.class));
                                           }

                                           public void onTick(long millisUntilFinished) {
                                               // millisUntilFinished    The amount of time until finished.
                                           }
                                       }.start();


                                   }


                                   Toast.makeText(EmailAndPassActivity.this,"A verification email has been sent to you please click on that link",Toast.LENGTH_LONG).show();


                               }
                               if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                   sendVerificationEmail();
                                   progressBar.setVisibility(View.INVISIBLE);
                                   Toast.makeText(EmailAndPassActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                                   Toast.makeText(EmailAndPassActivity.this,"A verification email has been sent to you please click on that link",Toast.LENGTH_LONG).show();

                                   new CountDownTimer(6000, 1000) {
                                       public void onFinish() {
                                           FirebaseAuth.getInstance().signOut();
                                           startActivity(new Intent(EmailAndPassActivity.this,MainActivity.class));
                                       }

                                       public void onTick(long millisUntilFinished) {
                                           // millisUntilFinished    The amount of time until finished.
                                       }
                                   }.start();

                               }
                           }catch (Exception e){
                                   progressBar.setVisibility(View.INVISIBLE);
                                   mUname.requestFocus();
                                   mUname.setError("Username already Exist or Unexpected Error Happened!");
                           }
                        }
                    });


                }


            }
        });

    }


    public void sendVerificationEmail(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.sendEmailVerification();

    }

}

