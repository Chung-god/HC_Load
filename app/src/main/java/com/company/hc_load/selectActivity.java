package com.company.hc_load;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class selectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // id 지정
        Button okBtn = findViewById(R.id.okBtn);
        RadioGroup local = findViewById(R.id.local);

        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Button Clicked.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(selectActivity.this, ResultActivity.class);
                RadioButton pnu = findViewById(R.id.pnu);
                RadioButton nampo = findViewById(R.id.nampo);
                RadioButton seomyeon = findViewById(R.id.seomyeon);
                if(pnu.isChecked()) {
                    intent.putExtra("place", "PNU");
                }
                if(nampo.isChecked()) {
                    intent.putExtra("place", "Nampo");
                }
                if(seomyeon.isChecked()) {
                    intent.putExtra("place", "Seomyeon");
                }
                startActivity(intent);
            }
        });
        // RadioButton 체크 토스트
        local.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pnu:
                        Toast.makeText(getApplicationContext(), "부산대학교가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nampo:
                        Toast.makeText(getApplicationContext(), "남포동이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.seomyeon:
                        Toast.makeText(getApplicationContext(), "서면이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}