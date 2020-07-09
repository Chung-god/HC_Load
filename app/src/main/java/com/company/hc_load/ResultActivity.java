package com.company.hc_load;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    ImageView iv_image;
    Tmap tmapData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //TextView textView = (TextView)findViewById(R.id.parsetext);
        Intent intent = getIntent();
        String place = intent.getStringExtra("place");
        double lat = intent.getExtras().getDouble("lat");
        double lon = intent.getExtras().getDouble("lon");
        Task t = new Task(place);
        double x0 = lon;
        double y0 = lat;
        double x1 = Double.parseDouble(t.a.longitude);
        double y1 = Double.parseDouble(t.a.latitude);
        double x2 = Double.parseDouble(t.b.longitude);
        double y2 = Double.parseDouble(t.b.latitude);
        double x3 = Double.parseDouble(t.c.longitude);
        double y3 = Double.parseDouble(t.c.latitude);

        tmapData = new Tmap(x1, y1, x2, y2, x3, y3);
        iv_image = findViewById(R.id.iv_image);
        iv_image.setImageBitmap(tmapData.mapImage);


    }
}
