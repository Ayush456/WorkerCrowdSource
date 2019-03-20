package com.example.asus.workercrowdsource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ContractorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_home);

        BottomNavigationView mBottomNav = findViewById(R.id.contractor_bottom_nav_id);

        mBottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.post_contractorjob:


                        break;

                    case R.id.view_contractorjob:


                        break;

                    case R.id.view_contractorprofile:
                        startActivity(new Intent(ContractorHomeActivity.this,MyProfileActivity.class));
                        break;

                }
            }
        });

    }
}
