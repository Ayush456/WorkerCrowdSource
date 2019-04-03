package com.example.asus.workercrowdsource;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public EditText mUsername,mPassowrd;
    private TextView newuser;
    public Button mBtnLogin,mBtnSignup,mBtnForgotPass;
    private ProgressBar mprogressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Boolean statusContractor = false;
    private Boolean statusUser = false;
    private Boolean statusWorker = false;
    private String uid;
    private String role;
    private static final String TAG = "MainActivity";
    public String language="en";

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
        newuser = findViewById(R.id.textView);


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
                                        mprogressBar.setVisibility(View.INVISIBLE);
                                         FirebaseUser mUser = mAuth.getCurrentUser();
                                         uid = mUser.getUid();
                                         updateUI(mUser,uid);

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
              userDetails.putExtra("language",language);
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

    private void updateUI(FirebaseUser mUser, String uid) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference AllUsers = mDatabase.child("ALL_USERS");
        DatabaseReference currentUser = AllUsers.child(uid);
        currentUser.child("Role").addValueEventListener(new ValueEventListener() {
            @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                role = (String) dataSnapshot.getValue();
                if (role.equals("worker")){
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    }
                    else{
                    if (role.equals("contractor")){
                        startActivity(new Intent(MainActivity.this,ContractorHomeActivity.class));
                    }else{
                            startActivity(new Intent(MainActivity.this,UserHomeActivity.class));
                       }
                }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }

    @Override
    protected void onStart() {
        super.onStart();

      mAuth = FirebaseAuth.getInstance();
      FirebaseUser muser = mAuth.getCurrentUser();

      if (muser != null){

          if (muser.isEmailVerified()){
              mprogressBar.setVisibility(View.VISIBLE);
              String uid = muser.getUid();
              DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
              DatabaseReference AllUsers = mDatabase.child("ALL_USERS");
              DatabaseReference currentUser = AllUsers.child(uid);
              currentUser.child("Role").addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      role = (String) dataSnapshot.getValue();
                      if (role.equals("worker")){
                          mprogressBar.setVisibility(View.INVISIBLE);
                          Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                          intent.putExtra("language",language);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(intent);
                          finish();
                          //startActivity(new Intent(MainActivity.this,HomeActivity.class));
                      }
                      if (role.equals("contractor")){
                          mprogressBar.setVisibility(View.INVISIBLE);
                          Intent intent = new Intent(MainActivity.this, ContractorHomeActivity.class);
                          intent.putExtra("language",language);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(intent);
                          finish();

                          //  startActivity(new Intent(MainActivity.this,ContractorHomeActivity.class));
                      }
                      if (role.equals("user")){
                          mprogressBar.setVisibility(View.INVISIBLE);
                          Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                          intent.putExtra("language",language);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(intent);
                          finish();

                          //  startActivity(new Intent(MainActivity.this,UserHomeActivity.class));
                      }

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                  }
              });
          }else{
              startActivity(new Intent(MainActivity.this,PromptToSignupActivity.class));
          }


      }

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.language,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.english) {
            language = "en";
            Locale locale = new Locale("en");
            locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            setLocale("en");
            return true;
        }else{
            language = "hi";
            Locale locale = new Locale("hi");
            locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            setLocale("hi");
            return true;
        }
//        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }

}
