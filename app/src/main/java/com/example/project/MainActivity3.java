package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;
import com.example.project.Model.Map;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity3 extends AppCompatActivity {
    BottomNavigationView navigationView;
    MapsFragment mapsFragment= new MapsFragment();
    DetailFragment detailFragment = new DetailFragment();
    ChartFragment chartFragment = new ChartFragment();

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        requestPermissionsIfNecessary(Manifest.permission.WRITE_EXTERNAL_STORAGE);



        navigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mapsFragment).commit();
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mapsFragment).commit();
                        break;
                    case R.id.action_detail:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,detailFragment).commit();
                        break;
                    case R.id.action_chart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,chartFragment).commit();
                        break;
                }
                return true;
            }
        });

    }

    private void requestPermissionsIfNecessary(String permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        permissionsToRequest.add(permissions);
        ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toArray(new String[0]),
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

}