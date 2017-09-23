package com.example.park.yapp_1team.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.items.SelectMovieInfoItem;
import com.example.park.yapp_1team.views.fragments.MapViewFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MapActivity extends BaseActivity {

    private static final String TAG = MapActivity.class.getSimpleName();
    private Toolbar mapToolbar;

    private List<SelectMovieInfoItem> list;

    private ImageView imgThumbnail;
    private TextView txtItemMovieInfoTime;
    private TextView txtItemMovieInfoTitle;
    private TextView txtItemMovieInfoSeat;
    private TextView txtItemMovieInfoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        statusBarChange();
        getKeyHash();

        initialize();
    }

    private void initialize() {

        mapToolbar = (Toolbar) findViewById(R.id.toolbar_map);

//        setSupportActionBar(mapToolbar);

        mapToolbar.setContentInsetsAbsolute(0, 0);

        imgThumbnail = (ImageView)findViewById(R.id.img_item_movie_info_thumbnail);
        txtItemMovieInfoLocation = (TextView)findViewById(R.id.txt_item_movie_info_location);
        txtItemMovieInfoTime = (TextView)findViewById(R.id.txt_item_movie_info_start_time);
        txtItemMovieInfoTitle = (TextView)findViewById(R.id.txt_item_movie_info_title);

        Intent it = getIntent();
        String title = it.getExtras().getString("title");
        String time = it.getExtras().getString("time");
        String location = it.getExtras().getString("location");
        String thumbnail = it.getExtras().getString("thumbnail");
        double lat = it.getExtras().getDouble("lat");
        double lng = it.getExtras().getDouble("lng");

        txtItemMovieInfoTitle.setText(title);
        txtItemMovieInfoTime.setText(time);
        txtItemMovieInfoLocation.setText(location);
        Glide.with(this).load(thumbnail).into(imgThumbnail);

        Log.e(TAG,"location : " + lat + " : " + lng);

        MapViewFragment fragment = new MapViewFragment();
        fragment.setMovieLat(lat);
        fragment.setMovieLng(lng);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.map_view_container, fragment);
        transaction.commit();

    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.park.yapp_1team", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
