package com.example.park.yapp_1team.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.SelectMovieRecyclerViewAdapter;
import com.example.park.yapp_1team.interfaces.RcvClickListener;
import com.example.park.yapp_1team.items.CGVDistance;
import com.example.park.yapp_1team.items.CGVRealmModel;
import com.example.park.yapp_1team.items.SelectMovieInfoItem;
import com.example.park.yapp_1team.sql.RealmRest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.RealmResults;

import static com.example.park.yapp_1team.utils.PermissionRequestCode.LOCATION_PERMISSION_CODE;

public class SelectMovieInfoActivity extends BaseActivity {

    private static final String TAG = SelectMovieInfoActivity.class.getSimpleName();

    private RecyclerView rcvSelectMovie;
    private FloatingActionButton fabSelectMovieInfoBack;
    private FloatingActionButton fabMovieFilter;
    private Toolbar selectToolbar;

    private SelectMovieRecyclerViewAdapter adapter;
    private int count = 0;

    private List<String> names;

    private RealmRest realmRest;

    private double lat, lng;
    private Location mCurrentLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    private RcvClickListener clickListener = new RcvClickListener() {
        @Override
        public void itemClick(int position) {
            // TODO: 2017-09-08 item click event
            startActivity(new Intent(getApplicationContext(), MapActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movie_info);
        statusBarChange();
        // TODO: 2017-09-02 intent로 count 받아오기
        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("names");

        count = names.size();

        Log.e(TAG, String.valueOf(count));

        initialize();
        addItem();
        event();

        permissionCheck();
    }

    private void event() {
        fabSelectMovieInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationSetupActivity.class);
                startActivity(intent);
            }
        });

        fabMovieFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initialize() {
        selectToolbar = (Toolbar) findViewById(R.id.toolbar_select_info);
        setSupportActionBar(selectToolbar);
        selectToolbar.setContentInsetsAbsolute(0, 0);

        rcvSelectMovie = (RecyclerView) findViewById(R.id.rcv_select_movie_info);
        fabSelectMovieInfoBack = (FloatingActionButton) findViewById(R.id.btn_select_movie_info_back);
        fabMovieFilter = (FloatingActionButton) findViewById(R.id.btn_select_movie_info_filter);

        if (count > 1) {
            adapter = new SelectMovieRecyclerViewAdapter(this, true);
        } else {
            adapter = new SelectMovieRecyclerViewAdapter(this, false);
        }

        adapter.setRcvClickListener(clickListener);

        rcvSelectMovie.setHasFixedSize(true);
        rcvSelectMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvSelectMovie.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcvSelectMovie.setAdapter(adapter);

        realmRest = new RealmRest();

    }

    private void addItem() {
        List<SelectMovieInfoItem> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            SelectMovieInfoItem item = new SelectMovieInfoItem();
            item.setTitle(names.get(0));
            item.setStartTime("9:00");
            item.setEndTIme(" - 13:00");
            item.setLocation("CGV 청담·2.20km·프리미엄");
            item.setUseSeat("13");
            item.setLeftSeat("/200");

            list.add(item);
            adapter.setListItems(list);
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getLocation() {
        if (!checkGPS()) {
            Toast.makeText(this, "GPS를 확인하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        OnCompleteListener<Location> onCompleteListener = new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    mCurrentLocation = task.getResult();
                    lat = mCurrentLocation.getLatitude();
                    lng = mCurrentLocation.getLongitude();

                    findCGV(lat, lng);

                } else {
                    try {
                        Log.e(TAG, "CurrentLocation exception");
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, onCompleteListener);
    }

    private void findCGV(double lat, double lng) {

        RealmResults<CGVRealmModel> results = realmRest.getCGVInfo();
        Location currentLocation = new Location("currentPosition");
        currentLocation.setLatitude(lat);
        currentLocation.setLongitude(lng);

        Log.e(TAG, "findCGV result size : " + results.size());

        List<CGVDistance> distances = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            Location cgvLocation = new Location("cgv");
            cgvLocation.setLatitude(results.get(i).getLat());
            cgvLocation.setLongitude(results.get(i).getLng());

            double dis = currentLocation.distanceTo(cgvLocation);
            CGVDistance cgvDistance = new CGVDistance();
            cgvDistance.setCgvRealmModel(results.get(i));
            cgvDistance.setDistance(dis);

            distances.add(cgvDistance);
        }

        Comparator<CGVDistance> comparator = new Comparator<CGVDistance>() {
            @Override
            public int compare(CGVDistance o1, CGVDistance o2) {
                if (o1.getDistance() > o2.getDistance()) {
                    return 1;
                } else if (o1.getDistance() < o2.getDistance()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        Collections.sort(distances, comparator);

        // 가까운 영화관 3개로부터 정보 get
        for(int i=0;i<3;i++) {
            getMovieInfo(distances.get(i).getCgvRealmModel());
        }
    }

    private void getMovieInfo(CGVRealmModel realmModel) {
        // TODO: 2017-09-22 크롤링을 통해 영화 이름 비교 + 시간과 정보 가져오기
        // TODO: 2017-09-22 주소 : http://www.cgv.co.kr/common/showtimes/iframeTheater.aspx?areacode=01&theatercode=0060&date=20170922
        // areacode와 theatercode, date를 변경해가며 검색 (date는 기본 오늘)
        // TODO: 2017-09-22 가져온 값을 하나의 리스트에 저장하고 시간 순으로 정렬
    }

    private boolean checkGPS() {
        LocationManager manager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS 상태 변경");
            builder.setMessage("GPS 상태 변경 하러 갈래요?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            it.addCategory(Intent.CATEGORY_DEFAULT);
                            startActivity(it);
                        }
                    });
            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
            return false;
        } else {
            return true;
        }
    }

    private void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
            // TODO: 2017-08-11 already has permission granted, go to next step.
            getLocation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_movie_info, menu);
        return true;
    }
}
