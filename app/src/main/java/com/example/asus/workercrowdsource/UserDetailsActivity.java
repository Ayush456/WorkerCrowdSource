package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Locale;

public class UserDetailsActivity extends AppCompatActivity {
    private EditText mName,mCtNo,mAddr,mCity,mPin;
    private String Name,Contact,Addr,City,Pin,Role;
    private Button mBtnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        mName = (EditText)findViewById(R.id.editText3);
        mCtNo = (EditText) findViewById(R.id.editText5);
        mAddr = (EditText)findViewById(R.id.editText6);
        mCity = (EditText) findViewById(R.id.editText8);
        mPin = (EditText) findViewById(R.id.editText9);
        mBtnSubmit = (Button) findViewById(R.id.button4);
        Role="worker";
        // onclick listener for submit button

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = mName.getText().toString().trim();
                Contact = mCtNo.getText().toString().trim();
                Addr = mAddr.getText().toString().trim();
                City = mCity.getText().toString().trim();
                Pin = mPin.getText().toString().trim();

                if( !TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Contact) && !TextUtils.isEmpty(Addr) && !TextUtils.isEmpty(City) && !TextUtils.isEmpty(Pin)){
                    Intent jobsAct = new Intent(UserDetailsActivity.this,JobsActivity.class);  // for worker
                    Intent profilePicture = new Intent(UserDetailsActivity.this,ProfilePictureActivity.class);  //for users


                    // adding data in bundle
                    //By java Object
                    User user = new User();
                    user.setName(Name); user.setContactNo(Contact); user.setAddr(Addr); user.setCity(City); user.setPincode(Pin);

//                    jobsAct.putExtras(bundle);
                    //starting activity
                    if(Role.equalsIgnoreCase("user")){
                       // profilePicture.putExtras(bundle);
                      //  startActivity(profilePicture);
                        user.setRole("user");
                        profilePicture.putExtra("UserObj", user);
                        startActivity(profilePicture);

                    }else if (Role.equalsIgnoreCase("worker")){
                        user.setRole("worker");
                        jobsAct.putExtra("UserObj", user);
                        startActivity(jobsAct);
                    }else {
                        user.setRole("contractor");
                        profilePicture.putExtra("UserObj", user);
                        startActivity(profilePicture);
                    }



                }else{
                    Toast.makeText( UserDetailsActivity.this ,"Some Fileds are missing",Toast.LENGTH_SHORT).show();
                }


            }
        });





    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioButton:
                if (checked) {
                    Role = "worker";
                    Toast.makeText(UserDetailsActivity.this, "Role: Worker selected!", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.radioButton2:
                if (checked){
                    Role = "user";
                    Toast.makeText(UserDetailsActivity.this,"Role: User selected!",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.radioButton3:
                if(checked){
                    Role = "contractor";
                    Toast.makeText(UserDetailsActivity.this,"Role: Contractor Selected",Toast.LENGTH_SHORT).show();

                }
        }
    }

}
