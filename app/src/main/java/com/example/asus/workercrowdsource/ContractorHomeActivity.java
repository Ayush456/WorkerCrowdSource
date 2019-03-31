package com.example.asus.workercrowdsource;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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
                if (menuItem.getItemId() == R.id.post_contractorjob){
                  startActivity(new Intent(ContractorHomeActivity.this,ContractorPostJobs.class));
                }else if (menuItem.getItemId() == R.id.view_contractorjob){

                }else if((menuItem.getItemId() == R.id.view_workerrequests)){
                    startActivity(new Intent(ContractorHomeActivity.this,DisplayContractorRequests.class));

                }else if((menuItem.getItemId() ==R.id.view_myworkers)){
                    startActivity(new Intent(ContractorHomeActivity.this,EnrolledWorkersActivity.class));
                }else{
                    startActivity(new Intent(ContractorHomeActivity.this,ProfileDetailsActivity.class));
                }

            }
        });

    }

}
