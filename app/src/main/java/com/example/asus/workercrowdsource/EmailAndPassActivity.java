package com.example.asus.workercrowdsource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EmailAndPassActivity extends AppCompatActivity {
    private EditText mUname,mPass;
    private Button mBtnVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_pass);

        mUname = (EditText) findViewById(R.id.editText4);
        mPass = (EditText) findViewById(R.id.editText4);
        mBtnVerify = (Button) findViewById(R.id.button7);
        

    }
}
