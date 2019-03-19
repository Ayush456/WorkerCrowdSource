package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
               switch (menuItem.getItemId()){

                   case R.id.view_recommended_job:

                       break;

                   case R.id.view_contractors:

                       break;

                   case R.id.view_profile:
                       startActivity(new Intent(HomeActivity.this,MyProfileActivity.class));
                       break;

               }
           }
       });

    }
}
