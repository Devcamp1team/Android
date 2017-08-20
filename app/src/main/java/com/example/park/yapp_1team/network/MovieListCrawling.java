package com.example.park.yapp_1team.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.park.yapp_1team.items.MovieInfoListItem;
import com.example.park.yapp_1team.items.TheaterCodeItem;
import com.example.park.yapp_1team.items.movieListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.park.yapp_1team.utils.Strings.CGV;
import static com.example.park.yapp_1team.utils.Strings.LOTTE;
import static com.example.park.yapp_1team.utils.URL.CGV_SELECT;
import static com.example.park.yapp_1team.utils.URL.CGV_THEATER_MOVIE_INFO;
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

    // CGV
    public MovieListCrawling(String cinemaID, String division)
    {
        this.url = CGV_THEATER_MOVIE_INFO;
        this.cinemaID = cinemaID;
        this.division = division;

        this.company = CGV;
    }


    @Override
    protected List<MovieInfoListItem> doInBackground(Object[] params) {

        if(company.equals(LOTTE))
        {
            lotte();
        }
        else if(company.equals(CGV))
        {
            cgv();
        }

        return items;
    }

    public void cgv()
    {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            Date today = new Date();

            Document document = Jsoup.connect(CGV_THEATER_MOVIE_INFO+"?areacode="+division+"&theatercode="+cinemaID+"&date="+sdf.format(today)).get();
            Elements elements = document.select(CGV_SELECT);

            for (Element element : elements) {

                String name = element.select("strong").text();

                Elements tempEle = element.select("div.info-timetable li");

                for(Element t : tempEle){

                    String start = t.select("a em").text();

                    String remain = t.select("span.txt-lightblue").text();

                    MovieInfoListItem item = new MovieInfoListItem(name, start, remain);

                    Log.e("log", "name : " + name + " start : " + start + " seat : " + remain);

                    items.add(item);
                }
            }


        } catch (IOException ie)
        {
            ie.printStackTrace();
        }
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

                MovieInfoListItem item = new MovieInfoListItem(movieName, startTime, bookingSeat);

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
