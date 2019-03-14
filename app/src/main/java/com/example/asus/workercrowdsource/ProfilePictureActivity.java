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

                    startPosting();


            }
        });

    }

    private void startPosting() {


        StorageReference filepath = mStorage.child("profile_pic").child(mImageUri.getLastPathSegment());
//        filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//            }
//        });
        Bundle bundle = getIntent().getExtras();
        startActivity(new Intent(ProfilePictureActivity.this,EmailAndPassActivity.class),bundle);

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
