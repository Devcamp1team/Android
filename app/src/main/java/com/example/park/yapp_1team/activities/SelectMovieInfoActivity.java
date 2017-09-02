package com.example.park.yapp_1team.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.SelectMovieRecyclerViewAdapter;
import com.example.park.yapp_1team.items.SelectMovieInfoItem;

import java.util.ArrayList;
import java.util.List;

public class SelectMovieInfoActivity extends AppCompatActivity {

    private RecyclerView rcvSelectMovie;
    private SelectMovieRecyclerViewAdapter adapter;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movie_info);

        // TODO: 2017-09-02 intent로 count 받아오기

        initialize();

        addItem();
    }

    private void initialize() {
        rcvSelectMovie = (RecyclerView) findViewById(R.id.rcv_select_movie_info);

        if (count > 1) {
            adapter = new SelectMovieRecyclerViewAdapter(this);
        } else {
            adapter = new SelectMovieRecyclerViewAdapter(this, false);
        }
        rcvSelectMovie.setHasFixedSize(true);
        rcvSelectMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvSelectMovie.setAdapter(adapter);
    }

    private void addItem() {
        List<SelectMovieInfoItem> list = new ArrayList<>();
        adapter.setListItems(list);
    }
}
