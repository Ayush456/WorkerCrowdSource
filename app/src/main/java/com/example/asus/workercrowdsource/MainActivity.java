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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public EditText mUsername,mPassowrd;
    public Button mBtnLogin,mBtnSignup,mBtnForgotPass;
    private ProgressBar mprogressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

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
                                        mprogressBar.setVisibility(View.INVISIBLE);
                                        Intent homeActivity = new Intent(MainActivity.this,HomeActivity.class);
                                        startActivity(homeActivity);
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

    }
}
