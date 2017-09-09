package com.example.park.yapp_1team.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.SelectMovieRecyclerViewAdapter;
import com.example.park.yapp_1team.interfaces.RcvClickListener;
import com.example.park.yapp_1team.items.SelectMovieInfoItem;

import java.util.ArrayList;
import java.util.List;

public class SelectMovieInfoActivity extends BaseActivity {

    private static final String TAG = SelectMovieInfoActivity.class.getSimpleName();

    private RecyclerView rcvSelectMovie;
    private FloatingActionButton fabSelectMovieInfoBack;
    private FloatingActionButton fabMovieFilter;
    private Toolbar selectToolbar;

    private SelectMovieRecyclerViewAdapter adapter;
    private int count = 0;

    private List<String> names;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_movie_info, menu);
        return true;
    }
}
