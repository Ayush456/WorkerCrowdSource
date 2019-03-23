package com.example.asus.workercrowdsource;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       BottomNavigationView mBottomNav = findViewById(R.id.bottom_nav);

       mBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
           @Override
           public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
             if (menuItem.getItemId() == R.id.view_recommended_job){



             }else if(menuItem.getItemId() ==R.id.view_contractors){

             }else{
                 startActivity(new Intent(HomeActivity.this,MyProfileActivity.class));
             }
           }
       });

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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
}
