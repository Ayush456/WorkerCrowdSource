package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;

public class ProfilePictureActivity extends AppCompatActivity {
    private ImageButton mProfilePicture;
    private String username, password;
    private byte[] data2;
    private Button mSave;
    private Uri mImageUri;
    private StorageReference mStorage;
    UploadTask uploadTask;
    Bitmap imageBitmap;
    private static final int GALLERY_INTENT =2;
    private ProgressBar mProgressBar;

    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_CAPTURE_IMAGE = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);


        mProfilePicture = findViewById(R.id.imageButton);
        mProgressBar = findViewById(R.id.progressBar3);
        mProgressBar.setVisibility(View.INVISIBLE);  //making progressBar invisible
        mSave = findViewById(R.id.button6);

        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                startPostingImage(imageBitmap);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            mProfilePicture.setImageBitmap(imageBitmap);


        }

    }

    public void startPostingImage(Bitmap photo){

    }
}
