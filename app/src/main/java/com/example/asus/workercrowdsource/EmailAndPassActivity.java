package com.example.asus.workercrowdsource;

import android.content.Intent;
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
    private EditText mUname,mPass;
    private Button mBtnVerify;
    private String Uname="",Pass="";
    private TextView mAlert;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_pass);

        mUname = (EditText) findViewById(R.id.editText4);
        mPass = (EditText) findViewById(R.id.editText4);
        mBtnVerify = (Button) findViewById(R.id.button7);
        mAlert = (TextView) findViewById(R.id.textView9);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

    }

    protected void signup(View view){
        progressBar.setVisibility(View.VISIBLE);
        Uname = mUname.getText().toString().trim();
        Pass = mPass.getText().toString().trim();
        if (TextUtils.isEmpty(Uname) || (Uname.length() < 7) ){
            mAlert.setError("Username is Null or Length less than 7 char");
            mUname.requestFocus();
        }else if(!TextUtils.isEmpty(Pass) || Pass.length() < 6  ){
            mAlert.setError("Password is null or size less than 6 digits");
            mPass.requestFocus();
        }else{

            mAuth.createUserWithEmailAndPassword(Uname, Pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    mAlert.setError("Username already exist!");
                                    mUname.setText("");
                                    mPass.setText("");
                                    mUname.requestFocus();
                                }


                            } else {
                                progressBar.setVisibility(View.GONE);
                                sendVerificationEmail();
                                Toast.makeText(EmailAndPassActivity.this,"Verification is sent to Your Email Address and then login again!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(EmailAndPassActivity.this,MainActivity.class));
                                FirebaseAuth.getInstance().signOut();

                           }

                        }
                    });
        }

    }

    public void sendVerificationEmail(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification();

    }

}
