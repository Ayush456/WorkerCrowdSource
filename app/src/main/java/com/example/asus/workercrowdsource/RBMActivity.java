package com.example.asus.workercrowdsource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModelSource;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelOptions;

public class RBMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rbm);


        FirebaseLocalModelSource localModelSource =  new FirebaseLocalModelSource.Builder("RBM").setAssetFilePath("rbm.py").build();

        FirebaseModelManager.getInstance().registerLocalModelSource(localModelSource);

        FirebaseModelOptions options = new FirebaseModelOptions.Builder().setCloudModelName("rbm").setLocalModelName("rbm.py").build();

        try {
            FirebaseModelInterpreter firebaseModelInterpreter = FirebaseModelInterpreter.getInstance(options);
        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }

    }
}
