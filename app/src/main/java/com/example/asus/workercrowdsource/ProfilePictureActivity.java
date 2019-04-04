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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfilePictureActivity extends AppCompatActivity {
    private ImageButton mProfilePicture;
    private String username, password;
    private byte[] data2;
    private Button mSave;
    private Uri mImageUri;
    private Uri downloadUri;
    private FirebaseStorage mStorage= FirebaseStorage.getInstance();
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

                startPostingImage(imageBitmap);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            mProfilePicture.setImageBitmap(imageBitmap);
            mImageUri = data.getData();


        }

    }

    public void startPostingImage(Bitmap photo){
       //Uploading is done here
        mProgressBar.setVisibility(View.VISIBLE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        Uri file = Uri.fromFile(new File(""+mImageUri));
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());
        final StorageReference profileRef = mStorage.getReference().child("ProfilePicture/"+format);

       // UploadTask uploadTask = profileRef.putFile(mImageUri);

        UploadTask uploadTask = profileRef.putBytes(imageData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
               // Toast.makeText(ProfilePictureActivity.this,"Success!",Toast.LENGTH_LONG).show();
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return profileRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    downloadUri = task.getResult();

                    Intent prevIntent = getIntent();
                    User user = (User) prevIntent.getSerializableExtra("UserObj");
                    String role = user.getRole();
                    user.setPPLink(downloadUri.toString());



                    if(role.equals("worker")){
                        Bundle bundle = getIntent().getExtras();
                        Intent profilePicAct = new Intent(ProfilePictureActivity.this,EmailAndPassActivity.class);
                        profilePicAct.putExtra("UserObj",user);
                        profilePicAct.putExtras(bundle);
                        startActivity(profilePicAct);
                        Toast.makeText(ProfilePictureActivity.this,""+bundle.getString("job1")+" "+bundle.getString("job2")+ " " +bundle.getString("job3"),Toast.LENGTH_SHORT).show();
                    }else if(role.equals("user")){
                        Intent profilePicAct = new Intent(ProfilePictureActivity.this,EmailAndPassActivity.class);
                        profilePicAct.putExtra("UserObj",user);
                        startActivity(profilePicAct);
                        Toast.makeText(ProfilePictureActivity.this,"user",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent profilePicAct = new Intent(ProfilePictureActivity.this,EmailAndPassActivity.class);
                        profilePicAct.putExtra("UserObj",user);
                        startActivity(profilePicAct);
                        Toast.makeText(ProfilePictureActivity.this,"contractor",Toast.LENGTH_SHORT).show();
                    }







                } else {
                    // Handle failures
                    // ...
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ProfilePictureActivity.this,"Profile Picture Upload Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
