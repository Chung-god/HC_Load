package com.company.hc_load;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class permissionActivity extends AppCompatActivity {
    private CheckBox cbCoarseLoc;
    private CheckBox cbFineLoc;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;//권한 승인 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Button btnOK = findViewById(R.id.okBtn);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCoarseLoc.isChecked() && cbFineLoc.isChecked()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_scale_in);
                    finish();
                } else {
                    if (!cbCoarseLoc.isChecked()) {
                        Toast.makeText(getApplicationContext(), "권한이 필요합니다.", Toast.LENGTH_LONG).show();
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_ACCESS_COARSE_LOCATION);
                    }
                    if (!cbFineLoc.isChecked()) {
                        Toast.makeText(getApplicationContext(), "권한이 필요합니다.", Toast.LENGTH_LONG).show();
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_ACCESS_FINE_LOCATION);
                    }
                }
            }
        });

        Button btnCancel = findViewById(R.id.cancelBtn);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cbCoarseLoc = findViewById(R.id.cbCoarse);
        cbFineLoc = findViewById(R.id.cbFine);
    }

    @Override
    protected void onResume() {
        cbCoarseLoc.setChecked(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        cbFineLoc.setChecked(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        if (cbCoarseLoc.isChecked() && cbFineLoc.isChecked()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            //overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_scale_in);
            finish();
        }
        super.onResume();
    }
}
