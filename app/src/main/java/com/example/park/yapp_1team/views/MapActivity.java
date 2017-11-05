package com.example.park.yapp_1team.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.views.fragments.MapViewFragment;

public class MapActivity extends BaseActivity {

    private static final String TAG = MapActivity.class.getSimpleName();
    private Toolbar mapToolbar;

    private ImageView imgThumbnail;
    private TextView txtItemMovieInfoTime;
    private TextView txtItemMovieInfoTitle;
    private TextView txtItemMovieInfoLocation;
    private TextView txtUseSeat;
    private TextView txtTotalSeat;
    private TextView txtTheaterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        statusBarChange();

        initialize();
    }



    private void initialize() {

        mapToolbar = (Toolbar) findViewById(R.id.toolbar_map);

//        setSupportActionBar(mapToolbar);

        mapToolbar.setContentInsetsAbsolute(0, 0);

        imgThumbnail = (ImageView) findViewById(R.id.img_item_movie_info_thumbnail);
        txtItemMovieInfoLocation = (TextView) findViewById(R.id.txt_item_movie_info_location);
        txtItemMovieInfoTime = (TextView) findViewById(R.id.txt_item_movie_info_start_time);
        txtItemMovieInfoTitle = (TextView) findViewById(R.id.txt_item_movie_info_title);
        txtTotalSeat = (TextView) findViewById(R.id.txt_item_movie_info_remainder_seat);
        txtUseSeat = (TextView) findViewById(R.id.txt_item_movie_info_using_seat);

        txtTheaterName = (TextView) findViewById(R.id.txt_theater_name);

        Intent it = getIntent();
        String title = it.getExtras().getString("title");
        String time = it.getExtras().getString("time");
        String location = it.getExtras().getString("location");
        String thumbnail = it.getExtras().getString("thumbnail");
        String totalSeat = it.getExtras().getString("totalSeat");
        String useSeat = it.getExtras().getString("useSeat");
        double lat = it.getExtras().getDouble("lat");
        double lng = it.getExtras().getDouble("lng");

        txtTheaterName.setText(location.substring(0, location.indexOf('·')));
        txtItemMovieInfoTitle.setText(title);
        txtItemMovieInfoTime.setText(time);
        txtItemMovieInfoLocation.setText(location);
        txtUseSeat.setText(useSeat);
        txtTotalSeat.setText("/" + totalSeat);
        Glide.with(this).load(thumbnail).into(imgThumbnail);

        MapViewFragment fragment = new MapViewFragment();
        fragment.setMovieLat(lat);
        fragment.setMovieLng(lng);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.map_view_container, fragment);
        transaction.commit();

    }

    public void click(View v) {
        finish();
    }
}
