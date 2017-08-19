package com.example.park.yapp_1team;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Park on 2017-08-19.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 3000);// 3 초
    }
}
