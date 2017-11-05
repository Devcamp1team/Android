package com.example.park.yapp_1team.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.MainRecyclerViewAdapter;
import com.example.park.yapp_1team.adapters.MainSelectItemViewAdapter;
import com.example.park.yapp_1team.interfaces.CheckEvent;
import com.example.park.yapp_1team.interfaces.SelectItemClikListener;
import com.example.park.yapp_1team.items.LotteGsonModel;
import com.example.park.yapp_1team.items.MegaboxRealmModel;
import com.example.park.yapp_1team.items.MovieListItem;
import com.example.park.yapp_1team.network.MegaboxInfo;
import com.example.park.yapp_1team.network.MovieInfoCrawling;
import com.example.park.yapp_1team.sql.RealmRest;
import com.example.park.yapp_1team.utils.CustomComparator;
import com.example.park.yapp_1team.utils.JSON;
import com.example.park.yapp_1team.utils.RecyclerViewItemDecoration;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.realm.RealmResults;

import static com.example.park.yapp_1team.utils.URL.LOTTE_MOVIE_PICTURE_URL;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DIVISION_SIZE = 8;

    private RecyclerView movieListRecyclerView;
    private MainRecyclerViewAdapter movieListRecyclerViewAdapter;
    private MainSelectItemViewAdapter mainSelectItemViewAdapter;
    private RecyclerView selectMovieRecyclerView;
    private LinearLayout layoutShowMovieTIme;

    private List<MovieListItem> dataArray;
    private List<String> movieNames = new ArrayList<>();
    private List<String> movieImages = new ArrayList<>();

    private RealmRest realmRest;

    private CustomComparator customComparator = new CustomComparator();

    private CheckEvent checkEvent = new CheckEvent() {
        @Override
        public void check(int position, ImageView imgMovieThumbnail, View imgLine) {
            List<MovieListItem> items = movieListRecyclerViewAdapter.getDatas();
            if (items.get(position).getCheck() == 0) {
                movieListRecyclerViewAdapter.setCurrentOrder(movieListRecyclerViewAdapter.getCurrentOrder() + 1);
                items.get(position).setCheck(1);
                movieNames.add(items.get(position).getName());
                movieImages.add(items.get(position).getUrl());
//                items.get(position).setCurrentOrder(movieListRecyclerViewAdapter.getCurrentOrder());
            } else {
                items.get(position).setCheck(0);
                movieNames.remove(items.get(position).getName());
                movieImages.remove(items.get(position).getUrl());
//                items.get(position).setCurrentOrder(items.get(position).getOriginalOrder());
            }

            movieListRecyclerViewAdapter.notifyItemChanged(position);
            mainSelectItemViewAdapter.notifyDataSetChanged();

//            Collections.sort(items, customComparator);
//            movieListRecyclerViewAdapter.addList(items);
//          movieListRecyclerViewAdapter.notifyDataSetChanged();
        }
    };

    private SelectItemClikListener clikListener = new SelectItemClikListener() {
        @Override
        public void click(View view, int position) {
            String name = movieNames.get(position).toString();
            movieNames.remove(name);
            List<MovieListItem> items = movieListRecyclerViewAdapter.getDatas();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getName().equals(name)) {
                    movieImages.remove(items.get(i).getUrl());
                    items.get(i).setCheck(0);
                    movieListRecyclerViewAdapter.notifyItemChanged(i);
                    break;
                }
            }
            mainSelectItemViewAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        SharedPreferences sp = getSharedPreferences("firstflag", Context.MODE_PRIVATE);

        /**
         * 사용자의 첫 방문인지 체크한다.
         */
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if (!hasVisited) {

            startActivity(new Intent(this, IntroActivity.class));
            finish();

            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit();

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusBarChange();

        initialize();
        event();
        if (realmRest.getCGVInfo() == null || realmRest.getCGVInfo().size() == 0) {
            saveCGVAsset();
        }

        if (realmRest.getLotteInfo() == null || realmRest.getLotteInfo().size() == 0) {
            saveLotteInfo();
        }

        if (realmRest.getMegaInfo() == null || realmRest.getMegaInfo().size() == 0) {
            saveMegaAsset();
        }

        RealmResults<MegaboxRealmModel> r = realmRest.getMegaInfo();
    }

    @Override
    protected void onDestroy() {
        new RealmRest().closeRealm();
        super.onDestroy();
    }

    private void event() {
        layoutShowMovieTIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieNames.size() > 0) {
                    Intent it = new Intent(getApplicationContext(), SelectMovieInfoActivity.class);
                    it.putExtra("names", (Serializable) movieNames);
                    it.putExtra("images", (Serializable) movieImages);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "영화를 한 개 이상 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialize() {
        layoutShowMovieTIme = (LinearLayout) findViewById(R.id.layout_show_movie_time);

        movieListRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_start);
        movieListRecyclerView.setHasFixedSize(true);
        movieListRecyclerViewAdapter = new MainRecyclerViewAdapter(this, checkEvent);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        RecyclerViewItemDecoration itemDecoration = new RecyclerViewItemDecoration(DIVISION_SIZE);
        movieListRecyclerView.setLayoutManager(gridLayoutManager);
        movieListRecyclerView.addItemDecoration(itemDecoration);

        selectMovieRecyclerView = (RecyclerView) findViewById(R.id.rcv_select_main_movie_name);
        selectMovieRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        selectMovieRecyclerView.setLayoutManager(linearLayoutManager);
        mainSelectItemViewAdapter = new MainSelectItemViewAdapter();
        mainSelectItemViewAdapter.setClikListener(clikListener);
        selectMovieRecyclerView.setAdapter(mainSelectItemViewAdapter);

        realmRest = new RealmRest();

        dataArray = movieCrawling();
        Iterator iterator = dataArray.iterator();
        while (iterator.hasNext()) {
            movieListRecyclerViewAdapter.add((MovieListItem) iterator.next());
        }
        movieListRecyclerView.setAdapter(movieListRecyclerViewAdapter);

        mainSelectItemViewAdapter.setSelectMovieList(movieNames);
    }

    private List<MovieListItem> movieCrawling() {
        MovieInfoCrawling test = new MovieInfoCrawling(LOTTE_MOVIE_PICTURE_URL);

        List<MovieListItem> items = new ArrayList<>();
        try {
            items = (ArrayList<MovieListItem>) test.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return items;
    }


    private List<MegaboxInfo> list = new ArrayList<>();


    private void saveLotteInfo() {
        String info = JSON.LOTTE_THEATER_JSON;

        Gson gson = new Gson();
        LotteGsonModel model = gson.fromJson(info, LotteGsonModel.class);
        for (int i = 0; i < model.getCinemases().getItems().length; i++) {
            realmRest.insertLotteInfo(model.getCinemases().getItems()[i]);
        }
    }

    private void saveMegaAsset() {
        try {
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("MegaLatLng.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            String[] megaList = text.split("\n");
            String[][] mega = new String[megaList.length][];
            for (int i = 0; i < mega.length; i++) {
                mega[i] = megaList[i].split(",");
            }

            for (int i = 0; i < mega.length; i++) {
                realmRest.insertMegaInfo(mega[i]);
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void saveCGVAsset() {
        try {
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("CGV.txt");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            String[] cgvList = text.split("\n");
            String[][] cgv = new String[cgvList.length][];
            for (int i = 0; i < cgv.length; i++) {
                cgv[i] = cgvList[i].split(",");
            }

            for (int i = 0; i < cgv.length; i++) {
                realmRest.insertCGVInfo(cgv[i]);
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

}
