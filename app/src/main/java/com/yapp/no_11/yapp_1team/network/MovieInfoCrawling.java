package com.yapp.no_11.yapp_1team.network;

import android.os.AsyncTask;

import com.yapp.no_11.yapp_1team.items.MovieListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Park on 2017-08-19.
 */

public class MovieInfoCrawling extends AsyncTask {

    private String url;

    private ArrayList<MovieListItem> items = new ArrayList<>();

    public MovieInfoCrawling(String url) {
        this.url = url;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {

            Document document = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .data("paramList", "{\"MethodName\":\"GetMovies\",\"channelType\":\"HO\",\"osType\":\"Chrome\",\"osVersion\":\"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Mobile Safari/537.36\",\"multiLanguageID\":\"KR\",\"division\":1,\"moviePlayYN\":\"Y\",\"orderType\":\"1\",\"blockSize\":100,\"pageNo\":1}")
                    .post();

            Elements elements = document.select("body");

            JSONObject jsonObj =  new JSONObject(elements.text());

            JSONArray jsonArr = jsonObj.getJSONObject("Movies").getJSONArray("Items");

            int order = 1000;

            for(int i = 0 ; i < jsonArr.length() ; ++i) {
                JSONObject jsonTemp = jsonArr.getJSONObject(i);

                String img = jsonTemp.get("PosterURL").toString();
                String name = jsonTemp.get("MovieNameKR").toString();

                if(!name.equals("AD"))
                {

                    MovieListItem item = new MovieListItem(order, name, img);

                    items.add(item);
                    order++;
                }

            }

        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }


}
