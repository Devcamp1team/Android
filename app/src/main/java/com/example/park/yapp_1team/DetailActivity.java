package com.example.park.yapp_1team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    TextView textView1;
    String movieName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        textView1 = (TextView)findViewById(R.id.text1_detail);

        movieName = intent.getStringExtra("MOVIENAME");
        Log.e("movieName",""+movieName);
        textView1.setText(movieName);
    }
}
