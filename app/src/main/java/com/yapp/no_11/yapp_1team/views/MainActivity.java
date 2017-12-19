package com.yapp.no_11.yapp_1team.views;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yapp.no_11.yapp_1team.R;
import com.yapp.no_11.yapp_1team.adapters.MainRecyclerViewAdapter;
import com.yapp.no_11.yapp_1team.adapters.MainSelectItemViewAdapter;
import com.yapp.no_11.yapp_1team.interfaces.CheckEvent;
import com.yapp.no_11.yapp_1team.interfaces.SelectItemClickListener;
import com.yapp.no_11.yapp_1team.items.LotteGsonModel;
import com.yapp.no_11.yapp_1team.items.MovieListItem;
import com.yapp.no_11.yapp_1team.network.MovieInfoCrawling;
import com.yapp.no_11.yapp_1team.sql.RealmRest;
import com.yapp.no_11.yapp_1team.utils.JSON;
import com.yapp.no_11.yapp_1team.utils.RecyclerViewItemDecoration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.yapp.no_11.yapp_1team.utils.URL.LOTTE_MOVIE_PICTURE_URL;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DIVISION_SIZE = 8;

    private RecyclerView movieListRecyclerView;
    private MainRecyclerViewAdapter movieListRecyclerViewAdapter;
    private MainSelectItemViewAdapter mainSelectItemViewAdapter;
    private RecyclerView selectMovieRecyclerView;
    private LinearLayout layoutShowMovieTIme;
    private ImageView imgMovieSearch;

    private List<MovieListItem> dataArray;
    private List<String> movieNames = new ArrayList<>();
    private List<String> movieImages = new ArrayList<>();

    private RealmRest realmRest;

    private CheckEvent checkEvent = new CheckEvent() {
        @Override
        public void check(int position, ImageView imgMovieThumbnail, View imgLine) {
            List<MovieListItem> items = movieListRecyclerViewAdapter.getDatas();
            if (items.get(position).getCheck() == 0) {
                movieListRecyclerViewAdapter.setCurrentOrder(movieListRecyclerViewAdapter.getCurrentOrder() + 1);
                items.get(position).setCheck(1);
                movieNames.add(items.get(position).getName());
                movieImages.add(items.get(position).getUrl());
            } else {
                items.get(position).setCheck(0);
                movieNames.remove(items.get(position).getName());
                movieImages.remove(items.get(position).getUrl());
            }

            movieListRecyclerViewAdapter.notifyItemChanged(position);
            mainSelectItemViewAdapter.notifyDataSetChanged();
        }
    };

    private SelectItemClickListener clickListener = new SelectItemClickListener() {
        @Override
        public void click(View view, int position) {
            String name = movieNames.get(position);
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

    }

    @Override
    protected void onDestroy() {
        new RealmRest().closeRealm();
        super.onDestroy();
    }

    private void event() {
        layoutShowMovieTIme.setOnClickListener(v -> intent());

        imgMovieSearch.setOnClickListener(v -> intent());
    }

    private void intent() {
        if (movieNames.size() > 0) {
            Intent it = new Intent(getApplicationContext(), SelectMovieInfoActivity.class);
            it.putExtra("names", (Serializable) movieNames);
            it.putExtra("images", (Serializable) movieImages);
            startActivity(it);
        } else {
            Toast.makeText(getApplicationContext(), "영화를 한 개 이상 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialize() {
        layoutShowMovieTIme = (LinearLayout) findViewById(R.id.layout_show_movie_time);
        imgMovieSearch = (ImageView) findViewById(R.id.img_search_movie);

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
        mainSelectItemViewAdapter.setClikListener(clickListener);
        selectMovieRecyclerView.setAdapter(mainSelectItemViewAdapter);

        realmRest = new RealmRest();

        dataArray = movieCrawling();
        for (Object aDataArray : dataArray) {
            movieListRecyclerViewAdapter.add((MovieListItem) aDataArray);
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

            for (String[] aMega : mega) {
                realmRest.insertMegaInfo(aMega);
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

            for (String[] aCgv : cgv) {
                realmRest.insertCGVInfo(aCgv);
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

}
