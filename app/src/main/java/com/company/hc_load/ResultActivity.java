package com.company.hc_load;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        TextView textView = (TextView)findViewById(R.id.parsetext);
        Object[] resultText = {};
        String place = "Nampo";
        Task t = new Task(place, 1);
        textView.setText(resultText.toString());
    }
}
