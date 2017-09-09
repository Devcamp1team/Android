package com.example.park.yapp_1team.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.park.yapp_1team.sql.RealmRest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmResults;

import static android.util.Pair.create;

/**
 * Created by Park on 2017-08-27.
 */

public class LocationSetupActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private BottomSheetDialog bottomSheetDialog;
    private LinearLayout setTimeLayout;
    private LinearLayout curTimeLayout;
    private NumberPicker bottomDate;
    private NumberPicker bottomTime;
    private Button bottomButton;
    private ImageView setupTime;
    private ImageView curTime;
    private ImageView backBtn;
    private int defaultValue;

    private Toolbar locationToolbar;
    private PlaceAutocompleteFragment autocompleteFragment;

    private String returnTime;
    private Place returnPlace;

    private RealmRest realmRest;

    // GoogleAPI
//    private GoogleApiClient mGoogleApiClient;


    // 초기 설정되어있는 위치, 시간 값 받아오기
//    public LocationSetupActivity(String inputTime)
//    {
//        this.returnTime = inputTime;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_time_setup);

        initial();
        event();
    }

    private void initial() {

        locationToolbar = (Toolbar) findViewById(R.id.toolbar_location_setup);
        locationToolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(locationToolbar);

        realmRest = new RealmRest();

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("KR")
                .build();

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint("서울특별시 마포구 망원동");

        backBtn = (ImageView) findViewById(R.id.img_location_setup_back);

        /*
         * BottomSheet
         */

        setTimeLayout = (LinearLayout) findViewById(R.id.setup_time);
        curTimeLayout = (LinearLayout) findViewById(R.id.current_time);

        bottomSheetDialog = new BottomSheetDialog(LocationSetupActivity.this);

        final View bottomView = getLayoutInflater().inflate(R.layout.layout_location_bottom, null);
        bottomSheetDialog.setContentView(bottomView);

        bottomDate = (NumberPicker) bottomView.findViewById(R.id.number_date);
        bottomTime = (NumberPicker) bottomView.findViewById(R.id.number_time);
        bottomButton = (Button) bottomView.findViewById(R.id.bottom_button);
        setupTime = (ImageView) findViewById(R.id.check_time2);
        curTime = (ImageView) findViewById(R.id.check_time1);


        /*
         *  Realm
         */

        RealmResults<SearchListItem> searchList = realmRest.getUserList();

        LocationSearchViewAdapter searchAdapter = new LocationSearchViewAdapter(this, searchList, true, false, autocompleteFragment);

        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.location_setup_recycle_view);

        realmRecyclerView.setAdapter(searchAdapter);
    }

    private void event() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Pair<String[], String[]> pairStr = setDate();
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupTime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.check_test));
                curTime.setBackgroundColor(Color.WHITE);

                returnTime = pairStr.first[bottomDate.getValue()] + " " + pairStr.second[bottomTime.getValue()].toString();

                Log.e("Time setup", returnTime);

                bottomSheetDialog.hide();
            }
        });

        curTimeLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                curTime.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.check_test));
                setupTime.setBackgroundColor(Color.WHITE);
            }
        });
        setTimeLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.e("get info", "Place : " + place.getName());

                realmRest.insertUserData(place.getName().toString());

                returnPlace = place;
            }

            @Override
            public void onError(Status status) {
                Log.e("suggestion system error", "Error occur" + status);
            }
        });
    }

    private Pair<String[], String[]> setDate() {

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


    // get time string[]
    private String[] getTimesFromCalendar() {
        Calendar calendar = Calendar.getInstance();

        List<String> times = new ArrayList<>();

        if (calendar.get(Calendar.MINUTE) < 30)
            calendar.set(Calendar.MINUTE, 0);
        else
            calendar.set(Calendar.MINUTE, 30);

        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMin = calendar.get(Calendar.MINUTE);

        defaultValue = curHour * 2 + 1;

        if (!(curMin < 30))
            curHour++;

        String timeStr;

        for (int time = 0; time <= 23; ++time) {
            timeStr = "";

            if (time < 12) {
                timeStr += "오전 ";

                if (time == 0) timeStr += "12:";
                else timeStr += time + ":";
            } else {
                timeStr += "오후 ";

                if (time != 12) timeStr += (time - 12) + ":";
                else timeStr += time + ":";
            }

            times.add(timeStr + "00 부터");
            times.add(timeStr + "30 부터");
        }

        return times.toArray(new String[times.size() - 1]);
    }

    // get dates string[]
    private String[] getDatesFromCalendar() {
        Calendar calendar = Calendar.getInstance();

        List<String> dates = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM월 dd일 (E)");
        dates.add(dateFormat.format(calendar.getTime()));

        for (int i = 0; i < 30; ++i) {
            calendar.add(Calendar.DATE, 1);
            dates.add(dateFormat.format(calendar.getTime()));
        }

        return dates.toArray(new String[dates.size() - 1]);
    }


    // Realm get all UserData


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e("Connection Fail", connectionResult.toString());
    }


    // return Time
    public String getTime() {
        return returnTime;
    }

    // return Place
    public Object getLocation() {
        return returnPlace;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmRest.closeRealm();
    }
}
