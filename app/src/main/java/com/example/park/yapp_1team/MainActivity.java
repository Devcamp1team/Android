package com.example.park.yapp_1team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.park.yapp_1team.network.movie_info_crawling;

import java.util.concurrent.ExecutionException;

import static com.example.park.yapp_1team.utils.URL.NAVER_SELECT;
import static com.example.park.yapp_1team.utils.URL.NAVER_URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SplashActivity.class));

        movie_info_crawling test = new movie_info_crawling(NAVER_URL, NAVER_SELECT);

        try{
            test.execute().get();
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}
