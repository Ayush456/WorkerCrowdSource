package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class UserHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        BottomNavigationView mBottomNav = findViewById(R.id.user_bottom_nav_id);

        mBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.post_userjob:

                        break;

                    case R.id.view_userjob:

                        break;

                    case R.id.view_userprofile:
                        startActivity(new Intent(UserHomeActivity.this,MyProfileActivity.class));
                        break;

                }
            }
        });

    }
}
