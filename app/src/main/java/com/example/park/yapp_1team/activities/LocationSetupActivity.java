package com.example.park.yapp_1team.activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.LocationSearchViewAdapter;
import com.example.park.yapp_1team.items.SearchListItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static android.util.Pair.create;

/**
 * Created by Park on 2017-08-27.
 */

public class LocationSetupActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{

    private BottomSheetDialog bottomSheetDialog;
    private LinearLayout setTimeLayout;
    private LinearLayout curTimeLayout;
    private Realm realm;
    private NumberPicker bottomDate;
    private NumberPicker bottomTime;
    private Button bottomButton;
    private ImageView setupTime;
    private ImageView curTime;
    private int defaultValue;

    private String returnTime;
    private Place returnPlace;


    // GoogleAPI
    private GoogleApiClient mGoogleApiClient;


    // 초기 설정되어있는 위치, 시간 값 받아오기
//    public LocationSetupActivity(String inputTime)
//    {
//        this.returnTime = inputTime;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_time_setup);

        initial();
    }

    private void initial() {


        /*
         *  Google API
         */

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)        // 만나서 질문
                .setCountry("KR")
                .build();

        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint("서울특별시 마포구 망원동");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.e("get info", "Place : " + place.getName());

                insertUserData(place.getName().toString());

                returnPlace = place;
            }

            @Override
            public void onError(Status status) {
                Log.e("suggestion system error", "Error occur"+status);
            }
        });

        /*
         * BottomSheet
         */

        setTimeLayout = (LinearLayout) findViewById(R.id.setup_time);
        curTimeLayout = (LinearLayout) findViewById(R.id.current_time);

        bottomSheetDialog = new BottomSheetDialog(LocationSetupActivity.this);

        final View bottomView = getLayoutInflater().inflate(R.layout.location_bottom, null);
        bottomSheetDialog.setContentView(bottomView);

        bottomDate = (NumberPicker) bottomView.findViewById(R.id.number_date);
        bottomTime = (NumberPicker) bottomView.findViewById(R.id.number_time);
        bottomButton = (Button) bottomView.findViewById(R.id.bottom_button);
        setupTime = (ImageView) findViewById(R.id.check_time2);
        curTime = (ImageView) findViewById(R.id.check_time1);

        final Pair<String[], String[]> pairStr = setDate();

        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupTime.setBackground(getResources().getDrawable(R.drawable.check_test));
                curTime.setBackgroundColor(Color.WHITE);

                returnTime = pairStr.first[bottomDate.getValue()].toString()+" "+pairStr.second[bottomTime.getValue()].toString();

                Log.e("Time setup", returnTime);

                bottomSheetDialog.hide();
            }
        });

        setTimeLayout.setOnClickListener(new LinearLayout.OnClickListener() {

            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }

        });
        curTimeLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                curTime.setBackground(getResources().getDrawable(R.drawable.check_test));
                setupTime.setBackgroundColor(Color.WHITE);
            }
        });

        /*
         *  Realm
         */

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();
        RealmResults<SearchListItem> searchList = getUserList();
        LocationSearchViewAdapter searchAdapter = new LocationSearchViewAdapter(this, searchList,true,false);
        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.location_setup_recycle_view);
        realmRecyclerView.setAdapter(searchAdapter);
    }

    private Pair<String[], String[]> setDate()
    {

        /*
         * Date
         */

        final String[] dateStr = getDatesFromCalendar();

        bottomDate.setMinValue(0);
        bottomDate.setMaxValue(dateStr.length - 1);
        bottomDate.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return dateStr[value];
            }
        });
        bottomDate.setDisplayedValues(dateStr);
        bottomDate.setWrapSelectorWheel(false);

        /*
         *  Time
         */


        final String[] timeStr = getTimesFromCalendar();

        bottomTime.setMinValue(0);
        bottomTime.setMaxValue(timeStr.length - 1);
        bottomTime.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return timeStr[value];
            }
        });
        bottomTime.setDisplayedValues(timeStr);
        bottomTime.setWrapSelectorWheel(false);

        bottomTime.setValue(defaultValue);


        return create(dateStr, timeStr);

    }

    private String[] getTimesFromCalendar()
    {
        Calendar calendar = Calendar.getInstance();

        List<String> times = new ArrayList<String>();

        if(calendar.get(Calendar.MINUTE) < 30)
            calendar.set(Calendar.MINUTE, 0);
        else
            calendar.set(Calendar.MINUTE, 30);

        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMin = calendar.get(Calendar.MINUTE);

        defaultValue = curHour * 2 + 1;

        if(!(curMin < 30))
            curHour++;

        String timeStr = "";

        for(int time = 0 ; time <= 23 ; ++time)
        {
            timeStr = "";

            if(time < 12)
            {
                timeStr += "오전 ";

                if(time == 0) timeStr += "12:";
                else timeStr += time + ":";
            }
            else
            {
                timeStr += "오후 ";

                if(time != 12) timeStr += (time-12) + ":";
                else timeStr += time + ":";
            }

            times.add(timeStr + "00 부터");
            times.add(timeStr + "30 부터");
        }

        return times.toArray(new String[times.size() - 1]);
    }

    private String[] getDatesFromCalendar()
    {
        Calendar calendar = Calendar.getInstance();

        List<String> dates = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("MM월 dd일 (E)");
        dates.add(dateFormat.format(calendar.getTime()));

        for(int i = 0 ; i < 30 ; ++i)
        {
            calendar.add(Calendar.DATE, 1);
            dates.add(dateFormat.format(calendar.getTime()));
        }

        return dates.toArray(new String[dates.size() - 1]);
    }

    private RealmResults<SearchListItem> getUserList(){
        return realm.where(SearchListItem.class).findAll();
    }

    private void insertUserData(String location){
        realm.beginTransaction();

        RealmResults<SearchListItem> tmpItem = realm.where(SearchListItem.class).equalTo("location", location).findAll();
        RealmResults<SearchListItem> tmpdelete = realm.where(SearchListItem.class).findAll();

        if(tmpdelete.size() > 3)
        {
            Log.e("delete", "in");
            tmpdelete.last().deleteFromRealm();
        }

        if(tmpItem.size() == 0) {
            realm.createObject(SearchListItem.class, location);
        }

        realm.commitTransaction();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e("Connection Fail", connectionResult.toString());
    }


    // return Time
    public String getTime()
    {
        return returnTime;
    }
    // return Place
    public Object getLocation()
    {
        return returnPlace;
    }
}
