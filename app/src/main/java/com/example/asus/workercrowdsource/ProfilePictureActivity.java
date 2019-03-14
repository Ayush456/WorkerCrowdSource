package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfilePictureActivity extends AppCompatActivity {
    private ImageButton mProfilePicture;
    private EditText mUserName,mPassWord;
    private String username, password;
    private Button mSave;
    private Uri mImageUri;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        mStorage = FirebaseStorage.getInstance().getReference(); // storage ref declared

        mUserName = (EditText) findViewById(R.id.editText4);
        mPassWord =(EditText) findViewById(R.id.editText7);
        mSave = (Button) findViewById(R.id.button6);
        mProfilePicture = (ImageButton)findViewById(R.id.imageButton);

        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(username) || username.length() < 5 ){
                    mUserName.setError("Either blank or size less than 5");
                    mUserName.requestFocus();
                }else if (!TextUtils.isEmpty(password) || password.length() < 5){
                    mPassWord.setError("Either blank or size less than 5");
                    mPassWord.requestFocus();
                }else{
                    startPosting();
                }

            }
        });

    }

    private void startPosting() {
        username = mUserName.getText().toString().trim();
        password = mPassWord.getText().toString().trim();

        StorageReference filepath = mStorage.child("profile_pic").child(mImageUri.getLastPathSegment());
        filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            mImageUri = data.getData();
            mProfilePicture.setImageURI(mImageUri);
        }
    }
}
