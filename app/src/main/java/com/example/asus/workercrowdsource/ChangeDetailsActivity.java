package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.Change;

public class ChangeDetailsActivity extends AppCompatActivity {
    private EditText mEditName,mEditEmail,mEditContactNo,mEditCity,mEditAddress;
    private Button mSubmitName,mSubmitEmail,mSubmitContact,mSubmitCity,mSubmitAddress;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    String user_id;
    private DatabaseReference all_user,role_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);

        //initializing all the editext
        mEditName = findViewById(R.id.editText16);
        mEditEmail = findViewById(R.id.editText17);
        mEditContactNo = findViewById(R.id.editText18);
        mEditCity = findViewById(R.id.editText19);
        mEditAddress = findViewById(R.id.editText20);


        //initializing all the buttons
        mSubmitName = findViewById(R.id.button14);
        mSubmitEmail = findViewById(R.id.button15);
        mSubmitContact = findViewById(R.id.button13);
        mSubmitCity = findViewById(R.id.button16);
        mSubmitAddress = findViewById(R.id.button9);

        //initialzing auth
        mAuth =FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        all_user = mDatabase.getReference().child("ALL_USERS");
        mUser = mAuth.getCurrentUser();
        user_id = mUser.getUid();

        final String[] role = new String[1];

        all_user.child(user_id).child("Role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  role[0] = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSubmitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = mEditName.getText().toString().trim();
                //Toast.makeText(ChangeDetailsActivity.this,role[0],Toast.LENGTH_SHORT).show();
                if (!mName.equals("")){

                    if (role[0].equalsIgnoreCase("User")){
                        mDatabase.getReference().child("User").child(user_id).child("Name").setValue(mName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ChangeDetailsActivity.this,"Changed Name Successfully!",Toast.LENGTH_SHORT).show();
                                  //  startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));
                                }else {
                                    Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                }
                            }
                        }) ;

                        all_user.child(user_id).child("Name").setValue(mName);
                        startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        Toast.makeText(ChangeDetailsActivity.this,"In User Change Name",Toast.LENGTH_SHORT).show();
                    }

                    if (role[0].equalsIgnoreCase("Contractor")){
                        mDatabase.getReference().child("Contractor").child(user_id).child("Name").setValue(mName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ChangeDetailsActivity.this,"Changed Name Successfully!",Toast.LENGTH_SHORT).show();
                                   // startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));

                                }else {
                                    Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                }
                            }
                        }) ;

                        all_user.child(user_id).child("Name").setValue(mName);
                        startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));


                    }

                    if (role[0].equalsIgnoreCase("Worker")){

                        mDatabase.getReference().child("Worker").child(user_id).child("Name").setValue(mName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ChangeDetailsActivity.this,"Changed Name Successfully!",Toast.LENGTH_SHORT).show();
                                   // startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));

                                }else {
                                    Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                }
                            }
                        }) ;

                        all_user.child(user_id).child("Name").setValue(mName);
                        startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));


                    }
                }else{
                    Toast.makeText(ChangeDetailsActivity.this,"Name should not be null to change",Toast.LENGTH_SHORT).show();
                }

            }
        });

        mSubmitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = mEditEmail.getText().toString();
                startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        mSubmitContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String mContact = mEditContactNo.getText().toString().trim();

            if (!mContact.equalsIgnoreCase("")){

                if (role[0].equalsIgnoreCase("User")){
                   // Toast.makeText(ChangeDetailsActivity.this,"in change contact user!",Toast.LENGTH_SHORT).show();

                    mDatabase.getReference().child("User").child(user_id).child("ContactNo").setValue(mContact).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ChangeDetailsActivity.this,"Changed Name Successfully!",Toast.LENGTH_SHORT).show();
                                //  startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));
                            }else {
                                Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                            }
                        }
                    }) ;

                    all_user.child(user_id).child("ContactNo").setValue(mContact);
                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    Toast.makeText(ChangeDetailsActivity.this,"In User Change Name",Toast.LENGTH_SHORT).show();

                }

                if (role[0].equalsIgnoreCase("Worker")){
                    mDatabase.getReference().child("Worker").child(user_id).child("ContactNo").setValue(mContact).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ChangeDetailsActivity.this,"Changed Name Successfully!",Toast.LENGTH_SHORT).show();
                                //  startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));
                            }else {
                                Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                            }
                        }
                    }) ;

                    all_user.child(user_id).child("ContactNo").setValue(mContact);
                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    Toast.makeText(ChangeDetailsActivity.this,"In User Change Name",Toast.LENGTH_SHORT).show();

                }

                if (role[0].equalsIgnoreCase("Contractor")){
                    mDatabase.getReference().child("Contractor").child(user_id).child("ContactNo").setValue(mContact).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ChangeDetailsActivity.this,"Changed Name Successfully!",Toast.LENGTH_SHORT).show();
                                //  startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));
                            }else {
                                Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                            }
                        }
                    }) ;

                    all_user.child(user_id).child("ContactNo").setValue(mContact);
                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    Toast.makeText(ChangeDetailsActivity.this,"In User Change Name",Toast.LENGTH_SHORT).show();

                }


            }else{
                Toast.makeText(ChangeDetailsActivity.this,"Contact Number is Null",Toast.LENGTH_SHORT).show();

            }


            }
        });

        mSubmitCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String mCity = mEditCity.getText().toString().trim();
               if (!mCity.equalsIgnoreCase("")){
                   if (role[0].equals("user")){
                       mDatabase.getReference().child("User").child(user_id).child("City").setValue(mCity).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()){
                                   Toast.makeText(ChangeDetailsActivity.this,"Changed Contact No Successfully!",Toast.LENGTH_SHORT).show();
                                   //startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));

                               }else {
                                   Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                               }
                           }
                       }) ;

                       all_user.child(user_id).child("City").setValue(mCity);
                       startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                   }

                   if (role[0].equalsIgnoreCase("contractor")){

                       mDatabase.getReference().child("Contractor").child(user_id).child("City").setValue(mCity).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()){
                                   Toast.makeText(ChangeDetailsActivity.this,"Changed Contact No Successfully!",Toast.LENGTH_SHORT).show();
                                  // startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));

                               }else {
                                   Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                               }
                           }
                       }) ;

                       all_user.child(user_id).child("City").setValue(mCity);
                       startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));


                   }

                   if (role[0].equalsIgnoreCase("worker")){
                       mDatabase.getReference().child("Worker").child(user_id).child(user_id).child("City").setValue(mCity).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()){
                                   Toast.makeText(ChangeDetailsActivity.this,"Changed Contact No Successfully!",Toast.LENGTH_SHORT).show();
                               }else {
                                   Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                               }
                           }
                       }) ;

                       all_user.child(user_id).child("City").setValue(mCity);
                       startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                   }
               }else{
                   Toast.makeText(ChangeDetailsActivity.this,"City Cannot be Null",Toast.LENGTH_SHORT).show();
               }

            }
        });

        mSubmitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAddress = mEditAddress.getText().toString().trim();

                if (!mAddress.equalsIgnoreCase("")){

                    if (role[0].equalsIgnoreCase("user")){
                        mDatabase.getReference().child("User").child(user_id).child("Address").setValue(mAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ChangeDetailsActivity.this,"Changed Address Successfully!",Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));

                                }else {
                                    Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                }
                            }
                        }) ;

                        all_user.child(user_id).child("Address").setValue(mAddress);
                        startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                    }
                    if (role[0].equalsIgnoreCase("contractor")){
                        mDatabase.getReference().child("Contractor").child(user_id).child("Address").setValue(mAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ChangeDetailsActivity.this,"Changed Address Successfully!",Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));

                                }else {
                                    Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                }
                            }
                        }) ;

                        all_user.child(user_id).child("Address").setValue(mAddress);
                        startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));



                    }
                    if (role[0].equalsIgnoreCase("worker")){
                        mDatabase.getReference().child("Worker").child(user_id).child("Address").setValue(mAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ChangeDetailsActivity.this,"Changed Address Successfully!",Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class));

                                }else {
                                    Toast.makeText(ChangeDetailsActivity.this,"Error in Database Occurred!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                }
                            }
                        }) ;

                        all_user.child(user_id).child("Address").setValue(mAddress);
                        startActivity(new Intent(ChangeDetailsActivity.this,ProfileDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                    }


                }else{
                    Toast.makeText(ChangeDetailsActivity.this,"Address is Empty",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
