package com.example.park.yapp_1team.activities;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.LocationSearchViewAdapter;
import com.example.park.yapp_1team.items.SearchListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Park on 2017-08-27.
 */

public class LocationSetupActivity extends AppCompatActivity {

    private BottomSheetDialog bottomSheetDialog;
    private LinearLayout setTimeLayout;
    private RecyclerView searchRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_time_setup);

        initial();

        setTimeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }

        });
    }

    private void initial()
    {
        setTimeLayout = (LinearLayout) findViewById(R.id.setup_time);

        bottomSheetDialog = new BottomSheetDialog(LocationSetupActivity.this);

        bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.location_bottom, null));


        /*
         *  test
         */

        List<SearchListItem> items = new ArrayList<SearchListItem>();

        SearchListItem tmp1 = new SearchListItem("서울시 관악구 신림동");
        SearchListItem tmp2 = new SearchListItem("서울시 강북구 수유동");
        SearchListItem tmp3 = new SearchListItem("서울시 관악구 상도동");

        items.add(tmp1);
        items.add(tmp2);
        items.add(tmp3);

        searchRecView = (RecyclerView) findViewById(R.id.location_setup_recycle_view);
        searchRecView.setAdapter(new LocationSearchViewAdapter(items));
        searchRecView.setItemAnimator(new DefaultItemAnimator());
    }
}
