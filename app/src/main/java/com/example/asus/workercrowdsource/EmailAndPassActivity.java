package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.os.Handler;
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

public class EmailAndPassActivity extends AppCompatActivity {
    private EditText mUname,mPass,mRepeatPass;
    private Button mBtnVerify;
    private String Uname="",Pass="",RepeatPass="";
    private TextView mAlert;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressBar progressBar;
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
                                   progressBar.setVisibility(View.INVISIBLE);
                                   mUname.requestFocus();
                               }
                               if (task.getException() instanceof FirebaseAuthUserCollisionException)
                               {
                                   sendVerificationEmail();
                                   progressBar.setVisibility(View.INVISIBLE);
                                   Toast.makeText(EmailAndPassActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                                   Toast.makeText(EmailAndPassActivity.this,"A verification email has been sent to you please click on that link",Toast.LENGTH_LONG).show();
                                   new Handler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                          FirebaseAuth.getInstance().signOut();
                                          startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                       }
                                   },5000);
                               }
                           }catch (Exception e){
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification();

    }

}

//                                 sendVerificationEmail();
//                                Toast.makeText(EmailAndPassActivity.this,"Verification is sent to Your Email Address and then login again!",Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(EmailAndPassActivity.this,MainActivity.class));
//                                FirebaseAuth.getInstance().signOut();

//                                    mAlert.setError("Username already exist!");
//                                    mUname.setText("");
//                                    mPass.setText("");
//                                    mUname.requestFocus();