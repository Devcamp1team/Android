package com.example.park.yapp_1team.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.park.yapp_1team.items.MovieInfoListItem;
import com.example.park.yapp_1team.items.TheaterCodeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.park.yapp_1team.utils.Strings.LOTTE;
import static com.example.park.yapp_1team.utils.URL.LOTTE_THEATER_MOVIE_INFO;

/**
 * Created by Park on 2017-08-20.
 */

public class MovieListCrawling extends AsyncTask {

    List<MovieInfoListItem> items = new ArrayList<>();

    private String url;
    private String company;
    private String cinemaID;
    private String division;
    private String detail;

    ArrayList<TheaterCodeItem> array;

    // LOTTE
    public MovieListCrawling(String cinemaID, String division, String detail)
    {
        this.url = LOTTE_THEATER_MOVIE_INFO;
        this.cinemaID = cinemaID;
        this.division = division;
        this.detail = detail;

        this.company = LOTTE;
    }


    @Override
    protected List<MovieInfoListItem> doInBackground(Object[] params) {

        if(company.equals(LOTTE))
        {
            lotte();
        }

        return items;
    }

    public void lotte()
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date today = new Date();

            Log.e("paramlist", "{\"MethodName\":\"GetPlaySequence\",\"channelType\":\"HO\",\"osType\":\"Chrome\",\"osVersion\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36\",\"playDate\":\""+sdf.format(today)+"\",\"cinemaID\":\""+Integer.parseInt(division)+"|"+Integer.parseInt(detail)
                    +"|"+Integer.parseInt(cinemaID)+"\",\"representationMovieCode\":\"\"}");

            Document document = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .data("paramList", "{\"MethodName\":\"GetPlaySequence\",\"channelType\":\"HO\",\"osType\":\"Chrome\",\"osVersion\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36\",\"playDate\":\""+sdf.format(today)+"\",\"cinemaID\":\""+Integer.parseInt(division)+"|"+Integer.parseInt(detail)
                            +"|"+Integer.parseInt(cinemaID)+"\",\"representationMovieCode\":\"\"}")
                    .post();

            Elements elements = document.select("body");

            Log.e("json", elements.text().toString());

            JSONObject jsonObj =  new JSONObject(elements.text());

            JSONArray jsonArr = jsonObj.getJSONObject("PlaySeqsHeader").getJSONArray("Items");

            HashMap<String, String> movie = new HashMap<>();

            for(int i = 0 ; i < jsonArr.length() ; ++i)
            {
                JSONObject jsonTemp = jsonArr.getJSONObject(i);

                String moviecode = jsonTemp.get("MovieCode").toString();
                String moviename = jsonTemp.get("MovieNameKR").toString();

                if(!movie.containsKey(moviecode))
                {
                    movie.put(moviecode, moviename);
                }
            }

            jsonArr = jsonObj.getJSONObject("PlaySeqs").getJSONArray("Items");

            for(int i = 0 ; i < jsonArr.length() ; ++i) {
                JSONObject jsonTemp = jsonArr.getJSONObject(i);

                MovieInfoListItem item = new MovieInfoListItem();

                String movieCode = jsonTemp.get("MovieCode").toString();
                String movieName = "Missing";
                String startTime = jsonTemp.get("StartTime").toString();
                String endTime = jsonTemp.get("EndTime").toString();
                String totalSeat = jsonTemp.get("TotalSeatCount").toString();
                String bookingSeat = jsonTemp.get("BookingSeatCount").toString();
                String screenNum = jsonTemp.get("ScreenNameKR").toString();

                if (movie.containsKey(movieCode)) {
                    movieName = movie.get(movieCode);
                }

                item.setSeat(bookingSeat);
                item.setTime(startTime);
                item.setTitle(movieName);

                Log.e("parsing data","movie code : " + movieCode + " movie name : " + movieName + " start time : " + startTime +
                        "end time : " + endTime + " total seat : " + totalSeat + " booking seat : " + bookingSeat + "screen number : " + screenNum);

                items.add(item);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
