package com.yapp.no_11.yapp_1team.views;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yapp.no_11.yapp_1team.R;
import com.yapp.no_11.yapp_1team.adapters.SelectMovieRecyclerViewAdapter;
import com.yapp.no_11.yapp_1team.interfaces.RcvClickListener;
import com.yapp.no_11.yapp_1team.items.CGVRealmModel;
import com.yapp.no_11.yapp_1team.items.LotteRealmModel;
import com.yapp.no_11.yapp_1team.items.MegaboxRealmModel;
import com.yapp.no_11.yapp_1team.items.MovieInfoListItem;
import com.yapp.no_11.yapp_1team.items.SelectMovieInfoItem;
import com.yapp.no_11.yapp_1team.items.TheaterDisInfo;
import com.yapp.no_11.yapp_1team.network.MovieCrawling;
import com.yapp.no_11.yapp_1team.sql.RealmRest;
import com.yapp.no_11.yapp_1team.utils.Strings;

import junit.framework.Assert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import io.realm.RealmResults;

import static com.yapp.no_11.yapp_1team.utils.PermissionRequestCode.FILTER_INTENT_RESULT_CODE;
import static com.yapp.no_11.yapp_1team.utils.PermissionRequestCode.SETUP_REQUEST_CODE;

public class SelectMovieInfoActivityRx extends BaseActivity {

    private static final String TAG = SelectMovieInfoActivityRx.class.getSimpleName();
    private static final int MAX_THEATER = 5;

    private RxPermissions rxPermissions;
    private RxLocation rxLocation;

    private RecyclerView rcvSelectMovie;
    private FloatingActionButton fabMovieFilter;
    private Toolbar selectToolbar;

    private TextView txtCurrentLocation;
    private LinearLayout layoutShowLocation;
    private RelativeLayout layoutBlank;
    private AdView adView;

    private SelectMovieRecyclerViewAdapter adapter;

    private int count = 0;

    private List<String> names;
    private List<String> images;

    private RealmRest realmRest;

    private double lat, lng;
    private double originalLat, originalLng;
    private int setupHour, setupMin, totalTime;

    private List<MovieInfoListItem> originalList = new ArrayList<>();
    private List<MovieInfoListItem> currentList = new ArrayList<>();


    private int hour = -1, min = -1;
    private int originalHour;
    private int originalMin;

    private String placeName;
    private String originalName;

    private boolean isFirst = true;
    private boolean isTimeChange = false;


    private RcvClickListener clickListener = new RcvClickListener() {
        @Override
        public void itemClick(int position, List<SelectMovieInfoItem> listItems) {
            // TODO: 2017-09-08 item click event
            Intent it = new Intent(getApplicationContext(), MapActivity.class);

            it.putExtra("title", listItems.get(position).getTitle());
            it.putExtra("time", listItems.get(position).getStartTime());
            it.putExtra("totalSeat", listItems.get(position).getLeftSeat());
            it.putExtra("useSeat", listItems.get(position).getUseSeat());
            it.putExtra("location", listItems.get(position).getLocation());
            it.putExtra("thumbnail", listItems.get(position).getImgThumbnail());
            it.putExtra("lat", listItems.get(position).getLat());
            it.putExtra("lng", listItems.get(position).getLng());
            it.putExtra("currentLat", lat);
            it.putExtra("currentLng", lng);

            startActivity(it);
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
        images = intent.getStringArrayListExtra("images");

        count = names.size();

        rxPermissions = new RxPermissions(this);
        rxLocation = new RxLocation(this);

        initialize();
        event();

        permissionCheck();
    }

    private void setTime() {
        Calendar rightNow = Calendar.getInstance();
        originalHour = rightNow.get(Calendar.HOUR_OF_DAY);
        originalMin = rightNow.get(Calendar.MINUTE);
    }

    private void event() {

        fabMovieFilter.setOnClickListener(v -> {

            Set<String> set = new HashSet<>();
            for (MovieInfoListItem item : originalList) {
                set.add(item.getTypeTheater());
            }
            StringBuilder value = new StringBuilder();
            for (String s : set) {
                value.append(s).append("|");
            }

            value = new StringBuilder(value.substring(0, value.length() - 1));
            Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
            intent.putExtra("type", value.toString());
            startActivityForResult(intent, FILTER_INTENT_RESULT_CODE);
        });

    }

    private void initialize() {
        selectToolbar = (Toolbar) findViewById(R.id.toolbar_select_info);
        setSupportActionBar(selectToolbar);
        selectToolbar.setContentInsetsAbsolute(0, 0);

        txtCurrentLocation = (TextView) findViewById(R.id.txt_select_location_title);
        layoutShowLocation = (LinearLayout) findViewById(R.id.layout_show_location);
        layoutBlank = (RelativeLayout) findViewById(R.id.layout_blank_movie_info);

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        setTime();

        rcvSelectMovie = (RecyclerView) findViewById(R.id.rcv_select_movie_info);
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

        layoutShowLocation.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LocationSetupActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            if (isTimeChange) {
                intent.putExtra("hour", hour);
                intent.putExtra("min", min);
            } else {
                intent.putExtra("hour", originalHour);
                intent.putExtra("min", originalMin);
            }
            intent.putExtra("isTimeChange", isTimeChange);

            startActivityForResult(intent, SETUP_REQUEST_CODE);

        });

    }

    private void addItem(List<MovieInfoListItem> totalListItems) {
        List<SelectMovieInfoItem> list = new ArrayList<>();

        adapter.clear();

        for (int i = 0; i < totalListItems.size(); i++) {
            SelectMovieInfoItem infoItem = new SelectMovieInfoItem();
            for (int j = 0; j < names.size(); j++) {
                if (totalListItems.get(i).getTitle().equals(names.get(j))) {
                    infoItem.setImgThumbnail(images.get(j));
                    break;
                }
            }

            infoItem.setTitle(totalListItems.get(i).getTitle());
            infoItem.setLeftSeat("/" + totalListItems.get(i).getTotalSeat());
            infoItem.setUseSeat(totalListItems.get(i).getRemindSeat());
            infoItem.setStartTime(totalListItems.get(i).getTime());
            infoItem.setLat(totalListItems.get(i).getLat());
            infoItem.setLng(totalListItems.get(i).getLng());
            int d = (int) totalListItems.get(i).getDistance() / 1000;
            int f = (int) totalListItems.get(i).getDistance() % 1000 / 100;

            infoItem.setLocation(totalListItems.get(i).getTheater() + " · " + d + "." + f + "km" + " · " + totalListItems.get(i).getTypeTheater());
            infoItem.setEndTIme("");
            list.add(infoItem);
        }
        adapter.setListItems(list);
        if (list.size() > 0) {
            rcvSelectMovie.setVisibility(View.VISIBLE);
            layoutBlank.setVisibility(View.GONE);
        } else {
            rcvSelectMovie.setVisibility(View.GONE);
            layoutBlank.setVisibility(View.VISIBLE);
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getLocation() {
        if (!checkGPS()) {
            Toast.makeText(this, "GPS를 확인하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        rxLocation.location().updates(locationRequest)
                .flatMap(location -> rxLocation.geocoding().fromLocation(location).toObservable())
                .subscribe(address -> {
                    originalLat = address.getLatitude();
                    originalLng = address.getLongitude();
                    originalName = address.getSubLocality();

                    lat = originalLat;
                    lng = originalLng;
                    placeName = originalName;
                    txtCurrentLocation.setText(placeName);
                    findTheater(lat, lng);
                });
    }


    private void findTheater(double lat, double lng) {

        List<TheaterDisInfo> disInfo = new ArrayList<>();

        Location currentLocation = new Location("currentPosition");
        currentLocation.setLatitude(lat);
        currentLocation.setLongitude(lng);

        RealmResults<CGVRealmModel> cgvResults = realmRest.getCGVInfo();
        for (int i = 0; i < cgvResults.size(); i++) {
            Location cgvLocation = new Location("cgv");
            double cgvLat = cgvResults.get(i).getLat();
            double cgvLng = cgvResults.get(i).getLng();
            cgvLocation.setLatitude(cgvLat);
            cgvLocation.setLongitude(cgvLng);
            double dis = currentLocation.distanceTo(cgvLocation);

            TheaterDisInfo theaterDisInfo = new TheaterDisInfo();
            theaterDisInfo.name = cgvResults.get(i).getName();
            theaterDisInfo.lat = cgvLat;
            theaterDisInfo.lng = cgvLng;
            theaterDisInfo.distance = dis;
            theaterDisInfo.code = Strings.THEATER_CODE_CGV;

            disInfo.add(theaterDisInfo);
        }

        RealmResults<MegaboxRealmModel> megaResult = realmRest.getMegaInfo();
        for (int i = 0; i < megaResult.size(); i++) {
            Location megaLocation = new Location("megabox");
            double megaLat = megaResult.get(i).getLat();
            double megaLng = megaResult.get(i).getLng();
            megaLocation.setLatitude(megaLat);
            megaLocation.setLongitude(megaLng);
            double dis = currentLocation.distanceTo(megaLocation);

            TheaterDisInfo theaterDisInfo = new TheaterDisInfo();
            theaterDisInfo.name = megaResult.get(i).getName();
            theaterDisInfo.lat = megaLat;
            theaterDisInfo.lng = megaLng;
            theaterDisInfo.distance = dis;
            theaterDisInfo.code = Strings.THEATER_CODE_MEGA;

            disInfo.add(theaterDisInfo);
        }

        RealmResults<LotteRealmModel> lotteResult = realmRest.getLotteInfo();
        for (int i = 0; i < lotteResult.size(); i++) {
            Location lotteLocation = new Location("Lotte");
            double lotteLat = lotteResult.get(i).getLat();
            double lotteLng = lotteResult.get(i).getLng();
            lotteLocation.setLatitude(lotteLat);
            lotteLocation.setLongitude(lotteLng);
            double dis = currentLocation.distanceTo(lotteLocation);


            TheaterDisInfo theaterDisInfo = new TheaterDisInfo();

            theaterDisInfo.name = lotteResult.get(i).getName();

            theaterDisInfo.lat = lotteLat;
            theaterDisInfo.lng = lotteLng;
            theaterDisInfo.distance = dis;
            theaterDisInfo.code = Strings.THEATER_CODE_LOTTE;

            disInfo.add(theaterDisInfo);
        }

        Comparator<TheaterDisInfo> comparator = (o1, o2) -> {
            if (o1.distance > o2.distance) {
                return 1;
            } else if (o1.distance < o2.distance) {
                return -1;
            } else {
                return 0;
            }
        };

        Collections.sort(disInfo, comparator);


        getMovieInfo(disInfo);
    }


    // CGV : http://www.cgv.co.kr/theaters/special/show-times.aspx?regioncode=103&theatercode=0040
    // Megabox : http://www.megabox.co.kr/?menuId=theater-detail&region=10&cinema=1372#menu3
    // lotte cinema : http://www.lottecinema.co.kr/LCHS/Contents/Cinema/Cinema-Detail.aspx?divisionCode=1&detailDivisionCode=1&cinemaID=1013
    private void getMovieInfo(List<TheaterDisInfo> disInfo) {

        List<MovieInfoListItem> totalListItems = new ArrayList<>();

        for (int i = 0; i < MAX_THEATER; i++) {
            switch (disInfo.get(i).code) {
                case 1: {
                    // cgv
                    RealmResults<CGVRealmModel> results = realmRest.getCGVInfo(disInfo.get(i).name);
                    for (int j = 0; j < results.size(); j++) {

                        MovieCrawling movieCrawling = new MovieCrawling(results.get(j).getTheaterCode(), results.get(j).getAreaCode(), (ArrayList) names);
                        try {
                            List<MovieInfoListItem> list = (List<MovieInfoListItem>) movieCrawling.execute().get();

                            for (int k = 0; k < list.size(); k++) {
                                MovieInfoListItem item = list.get(k);
                                item.setId(1);
                                item.setDistance(disInfo.get(i).distance);
                                item.setTheater(disInfo.get(i).name);
                                item.setLat(disInfo.get(i).lat);
                                item.setLng(disInfo.get(i).lng);
                                totalListItems.add(item);
                            }

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }

                    }

                    break;
                }
                case 2: {
                    // megabox

                    RealmResults<MegaboxRealmModel> results = realmRest.getMegaInfo(disInfo.get(i).name);
                    for (int j = 0; j < results.size(); j++) {
                        String url = results.get(j).getWww();

                        String cinema = url.substring(url.lastIndexOf("=") + 1, url.length());

                        MovieCrawling movieCrawling = new MovieCrawling(cinema, (ArrayList) names);
                        try {
                            List<MovieInfoListItem> listItems = (List<MovieInfoListItem>) movieCrawling.execute().get();

                            for (int k = 0; k < listItems.size(); k++) {
                                MovieInfoListItem item = listItems.get(k);
                                item.setId(2);
                                item.setDistance(disInfo.get(i).distance);
                                item.setLat(disInfo.get(i).lat);
                                item.setLng(disInfo.get(i).lng);
                                item.setTheater("메가박스 " + disInfo.get(i).name);
                                totalListItems.add(item);
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }

                    }

                    break;
                }
                case 3: {
                    // lotte
                    RealmResults<LotteRealmModel> results = realmRest.getLotteInfo(disInfo.get(i).name.trim());
                    for (int j = 0; j < results.size(); j++) {
                        MovieCrawling movieCrawling = new MovieCrawling(results.get(j).getCinemaID(), results.get(j).getDivisionCode(), results.get(j).getDetailDivisionCode(), (ArrayList) names);
                        try {
                            List<MovieInfoListItem> listItems = (List<MovieInfoListItem>) movieCrawling.execute().get();
                            for (int k = 0; k < listItems.size(); k++) {
                                MovieInfoListItem item = listItems.get(k);
                                item.setId(3);

                                item.setTheater("롯데 " + disInfo.get(i).name);
                                item.setDistance(disInfo.get(i).distance);
                                item.setLat(disInfo.get(i).lat);
                                item.setLng(disInfo.get(i).lng);

                                totalListItems.add(item);
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }

        Comparator<MovieInfoListItem> comparator = (o1, o2) -> {
            if (!o1.getTime().equals("") && !o2.getTime().equals("")) {

                // 시작 시간 : 단위 분
                int o1Time = convertTime(o1.getTime());
                int o2Time = convertTime(o2.getTime());

                // 가는 시간 : 단위 분 (km 당 10분으로 계산)
                int o1DisTime = (int) (o1.getDistance() / 100);
                int o2DisTime = (int) (o2.getDistance() / 100);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String current = simpleDateFormat.format(new Date());

                // 현재 시간
                int currentTime = convertTime(current);

                // 이동 시간 포함하여 남은 시간
                int o1AvailableTime = o1Time - (currentTime + o1DisTime);
                int o2AvailableTime = o2Time - (currentTime + o2DisTime);

                if (o1AvailableTime < o2AvailableTime) {
                    return -1;
                } else if (o1AvailableTime > o2AvailableTime) {
                    return 1;
                } else {
                    return 0;
                }
            } else if (!o1.getTime().equals("")) {
                return -1;
            } else if (!o2.getTime().equals("")) {
                return 1;
            } else {
                return 0;
            }
        };

        Collections.sort(totalListItems, comparator);

        currentList = totalListItems;

//        addItem(currentList);

        if (isFirst) {
            originalList = totalListItems;
            isFirst = false;
            addItem(currentList);
        } else {
            if (isTimeChange) {

                hour = setupHour;
                min = setupMin;

                totalTime = setupHour * 60 + setupMin;

                List<MovieInfoListItem> items = new ArrayList<>();

                for (MovieInfoListItem item : currentList) {
                    String[] tmpTime = item.getTime().split(":");
                    int tmpTotalTime = Integer.parseInt(tmpTime[0]) * 60 + Integer.parseInt(tmpTime[1]);

                    if (tmpTotalTime >= totalTime) {
                        items.add(item);
                    }
                }
                addItem(items);
            } else {
                hour = originalHour;
                min = originalMin;
                addItem(currentList);
            }
        }

//        for (int i = 0; i < totalListItems.size(); i++) {
//            Log.e(TAG, "total name : " + totalListItems.get(i).getTheater() + " " +
//                    totalListItems.get(i).getId() + " " +
//                    "title : " + totalListItems.get(i).getTitle() + " " +
//                    "time : " + totalListItems.get(i).getTime() + " " +
//                    "auditorium : " + totalListItems.get(i).getAuditorium() + " " +
//                    "type : " + totalListItems.get(i).getTypeTheater() + " " +
//                    "total seat : " + totalListItems.get(i).getTotalSeat() + " " +
//                    "remind seat : " + totalListItems.get(i).getRemindSeat() + " " +
//                    "distance : " + totalListItems.get(i).getDistance());
//        }
    }

    private int convertTime(String time) {
        String si = time.substring(0, time.indexOf(':'));
        String bun = time.substring(time.indexOf(':') + 1);

        return Integer.parseInt(si) * 60 + Integer.parseInt(bun);
    }

    private boolean checkGPS() {
        LocationManager manager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS 상태 변경");
            builder.setMessage("GPS 상태 변경 하러 갈래요?");
            builder.setPositiveButton("Yes",
                    (dialog, which) -> {
                        Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        it.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivity(it);
                    });
            builder.setNegativeButton("No",
                    (dialog, which) ->
                            Toast.makeText(this, "이용에 제약이 있을 수 있습니다.", Toast.LENGTH_SHORT).show());
            builder.show();
            return false;
        } else {
            return true;
        }
    }

    private void permissionCheck() {

        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        getLocation();
                    } else {
                        Toast.makeText(this, "권한을 거부하셨습니다. 이용에 제한이 생길 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_movie_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_1: {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
            }
            case R.id.menu_item_2: {
                lat = originalLat;
                lng = originalLng;
                txtCurrentLocation.setText(originalName);
                isTimeChange = false;
                isFirst = true;
                setTime();
                addItem(originalList);
                break;
            }
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        if (requestCode == FILTER_INTENT_RESULT_CODE) {

            Bundle bundle = data.getExtras().getBundle("data");
            String brandValue = bundle.getString("brand");
            String optionValue = bundle.getString("option");

            if (bundle == null) {
                return;
            }

            Assert.assertNotNull("filter bundle is null", bundle);
            Assert.assertNotNull("filter brand is null", brandValue);
            Assert.assertNotNull("filter option is null", optionValue);

            String[] dump = new String[0];
            if (brandValue.length() > 0) {
                dump = brandValue.split("\\|");
            }

            String[] option = new String[0];
            if (optionValue.length() > 0) {
                option = optionValue.split("\\|");
            }

            int[] brand = new int[dump.length];
            for (int i = 0; i < dump.length; i++) {
                brand[i] = Integer.parseInt(dump[i]);
            }

            boolean isBrandFilter = false;
            boolean isRoomFilter = false;

            if (brand.length > 0) {
                isBrandFilter = true;
            }
            if (option.length > 0) {
                isRoomFilter = true;
            }


            List<MovieInfoListItem> items = new ArrayList<>();

            for (MovieInfoListItem item : currentList) {
                if (isBrandFilter && isRoomFilter) {
                    for (int aBrand : brand) {
                        if (item.getTheaterCode() == aBrand) {
                            for (String anOption : option) {
                                if (item.getTypeTheater().equals(anOption)) {
                                    items.add(item);
                                }
                            }
                        }
                    }
                } else if (isBrandFilter) {
                    for (int aBrand : brand) {
                        if (aBrand == item.getTheaterCode()) {
                            items.add(item);
                        }
                    }
                } else if (isRoomFilter) {
                    for (String anOption : option) {
                        if (anOption.equals(item.getTypeTheater())) {
                            items.add(item);
                        }
                    }
                } else {
                    items = originalList;
                }
            }

            addItem(items);

        } else if (requestCode == SETUP_REQUEST_CODE) {
            // TODO: 날짜 문제 해결

            Bundle bundle = data.getExtras().getBundle("setupdata");

            if (bundle == null) {
                return;
            }

            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");

            placeName = bundle.getString("name");
            setupHour = bundle.getInt("hour");
            setupMin = bundle.getInt("min");

            isTimeChange = bundle.getBoolean("isTimeChange");

            hour = setupHour;
            min = setupMin;


            isFirst = false;

            findTheater(lat, lng);

            if (!placeName.equals("")) {
                txtCurrentLocation.setText(placeName);
            }
        }
    }
}
