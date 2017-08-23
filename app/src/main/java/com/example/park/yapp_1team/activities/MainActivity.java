package com.example.park.yapp_1team.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.MainRecyclerViewAdapter;
import com.example.park.yapp_1team.interfaces.CheckEvent;
import com.example.park.yapp_1team.items.MovieInfoListItem;
import com.example.park.yapp_1team.items.movieListItem;
import com.example.park.yapp_1team.items.TheaterCodeItem;
import com.example.park.yapp_1team.network.MovieInfoCrawling;
import com.example.park.yapp_1team.network.MovieListCrawling;
import com.example.park.yapp_1team.network.TheaterInfoCrawling;
import com.example.park.yapp_1team.utils.CustomComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.park.yapp_1team.utils.Strings.CGV;
import static com.example.park.yapp_1team.utils.Strings.LOTTE;
import static com.example.park.yapp_1team.utils.URL.NAVER_SELECT;
import static com.example.park.yapp_1team.utils.URL.NAVER_URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieListRecyclerView;
    private MainRecyclerViewAdapter movieListRecyclerViewAdapter;
    private FloatingActionButton fabButton1, fabButton2;

    private List<MovieListItem> dataArray;
    private List<String> movieNames = new ArrayList<>();

    private CustomComparator customComparator = new CustomComparator();

    private CheckEvent checkEvent = new CheckEvent() {
        @Override
        public void check(int position, ImageView imageView1, ImageView imageView2) {
            Log.e("main", dataArray.get(position).getName() + " : " + dataArray.get(position).getCurrentOrder());
            if (dataArray.get(position).getCheck() == 0) {
                imageView1.setColorFilter(Color.parseColor("#99000000"));
                dataArray.get(position).setCheck(1);
                movieNames.add(dataArray.get(position).getName());
                imageView2.setVisibility(View.VISIBLE);
                dataArray.get(position).setCurrentOrder(movieListRecyclerViewAdapter.getCurrentOrder());
                movieListRecyclerViewAdapter.setCurrentOrder(movieListRecyclerViewAdapter.getCurrentOrder()+1);
                Log.e("main", "order : " + movieListRecyclerViewAdapter.getCurrentOrder());
            } else {
                imageView1.setColorFilter(Color.parseColor("#00000000"));
                dataArray.get(position).setCheck(0);
                movieNames.add(dataArray.get(position).getName());
                imageView2.setVisibility(View.INVISIBLE);
                dataArray.get(position).setCurrentOrder(dataArray.get(position).getOriginalOrder());
            }
            Log.e("main","before sort" + dataArray.size());
            Collections.sort(dataArray, customComparator);
            Log.e("main","after sort" + dataArray.size());
            movieListRecyclerViewAdapter.addList(dataArray);
            movieListRecyclerViewAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(this, SplashActivity.class));

        initialize();

        dataArray = movieCrawling();

        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext()) {
            movieListRecyclerViewAdapter.add((movieListItem) iterator.next());
        }

//        for (int i = 0; i < dataArray.size(); i++) {
//            movieListRecyclerViewAdapter.add(dataArray.get(i));
//        }

        movieListRecyclerView.setAdapter(movieListRecyclerViewAdapter);

        fabButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> string_array = (ArrayList) movieListRecyclerViewAdapter.get();

                Intent intent = new Intent(getApplicationContext(), RealActivity.class);
                intent.putStringArrayListExtra("STRING", string_array);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        movieListRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_start);
        fabButton1 = (FloatingActionButton) findViewById(R.id.image1_underbar_start);
        fabButton2 = (FloatingActionButton) findViewById(R.id.image2_underbar_start);

        movieListRecyclerView.setHasFixedSize(true);
        movieListRecyclerViewAdapter = new MainRecyclerViewAdapter(this, checkEvent);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        movieListRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private List<movieListItem> movieCrawling() {
        MovieInfoCrawling test = new MovieInfoCrawling(NAVER_URL, NAVER_SELECT);

        List<movieListItem> items = new ArrayList<>();
        try {
            items = (ArrayList<movieListItem>) test.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return items;
    }


    /**
     * 이게 왜 있는지 이해가 안감
     */
    private void crawling() {
        // cgv theater info crawling
        TheaterInfoCrawling theaterInfoCrawling = new TheaterInfoCrawling(CGV);
        ArrayList<TheaterCodeItem> cgv_theater = new ArrayList<>();

        try {
            cgv_theater = (ArrayList<TheaterCodeItem>) theaterInfoCrawling.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // cgv movie time crawling
        MovieListCrawling movieListCrawling = new MovieListCrawling(cgv_theater.get(1).getTheater(), cgv_theater.get(1).getArea());
        ArrayList<MovieInfoListItem> cgv_movie = new ArrayList<>();

        try {
            cgv_movie = (ArrayList<MovieInfoListItem>) movieListCrawling.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        // lotte theater info crawling
        theaterInfoCrawling = new TheaterInfoCrawling(LOTTE);
        ArrayList<TheaterCodeItem> lotte_theater = new ArrayList<>();

        try {
            lotte_theater = (ArrayList<TheaterCodeItem>) theaterInfoCrawling.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        // lotte movie time crawling
        movieListCrawling = new MovieListCrawling(lotte_theater.get(1).getTheater(), lotte_theater.get(1).getArea(), lotte_theater.get(1).getDetailarea());
        ArrayList<MovieInfoListItem> lotte_movie = new ArrayList<>();

        try {
            lotte_movie = (ArrayList<MovieInfoListItem>) movieListCrawling.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}
