package com.example.park.yapp_1team.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.park.yapp_1team.items.movieListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Park on 2017-08-19.
 */

public class movie_info_crawling extends AsyncTask {

    private String url;
    private String select;

    private List<movieListItem> items = new ArrayList<>();

    public movie_info_crawling(String url, String select)
    {
        this.url = url;
        this.select = select;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try{

            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(select);

            Log.e("naver", elements.toString());

            for (Element element : elements) {

                String img = element.attr("src");
                String name = element.attr("alt");

                movieListItem item = new movieListItem(name, img);

                items.add(item);

                Log.e("item", "img = " + img + " name = " + name);
            }

            return items;

        } catch (IOException ie)
        {
            ie.printStackTrace();
        }

        return null;
    }


}
