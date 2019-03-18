package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PromptToSignupActivity extends AppCompatActivity {
    private Button mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_to_signup);

        mBack = findViewById(R.id.button8);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PromptToSignupActivity.this,MainActivity.class));
            }
        });
    }
}
