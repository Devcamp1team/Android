package com.example.park.yapp_1team.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.park.yapp_1team.items.MovieListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Park on 2017-08-19.
 */

public class MovieInfoCrawling extends AsyncTask {

    private String url;
    private String select;

    private ArrayList<MovieListItem> items = new ArrayList<>();

    public MovieInfoCrawling(String url, String select) {
        this.url = url;
        this.select = select;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {

            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(select);

            int order = 1000;
            for (Element element : elements) {
                String img = element.attr("src");
                String name = element.attr("alt");

                MovieListItem item = new MovieListItem(name, img);

                items.add(item);
                order++;

                Log.e("item", "img = " + img + " name = " + name);
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }

        return items;
    }


}
