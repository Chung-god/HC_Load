package com.company.hc_load;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;//권한 승인 코드
    private boolean permissionState;//권한 상태

    // private Button strBtn = findViewById(R.id.startingBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button strBtn = findViewById(R.id.startingBtn);

        askPermission();

        if(permissionState) {
            GPS loc = GPS.getInstance();
            loc.getLocation(MainActivity.this);
            Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
            resultIntent.putExtra("lat", loc.getLatitute());
            resultIntent.putExtra("lon", loc.getLongitute());
            strBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent selectIntent = new Intent(MainActivity.this, selectActivity.class);
                    startActivity(selectIntent);
                }
            });

        }
//        else {
//            Intent permissionIntent = new Intent(this, permissionActivity.class);
//            startActivity(permissionIntent);
//        }
    }

    private void askPermission(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_ACCESS_FINE_LOCATION);
            }
            else if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_ACCESS_COARSE_LOCATION);
            }
            else permissionState = true;
        }
    }
}