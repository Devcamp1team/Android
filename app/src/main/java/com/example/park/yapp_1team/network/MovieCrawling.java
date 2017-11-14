package com.example.park.yapp_1team.network;

import android.os.AsyncTask;

import com.example.park.yapp_1team.items.MovieInfoListItem;
import com.example.park.yapp_1team.items.TheaterCodeItem;

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

import static com.example.park.yapp_1team.utils.PermissionRequestCode.CGV_THEATER_CODE;
import static com.example.park.yapp_1team.utils.PermissionRequestCode.LOTTE_THEATER_CODE;
import static com.example.park.yapp_1team.utils.PermissionRequestCode.MEGA_THEATER_CODE;
import static com.example.park.yapp_1team.utils.Strings.CGV;
import static com.example.park.yapp_1team.utils.Strings.LOTTE;
import static com.example.park.yapp_1team.utils.Strings.MEGA;
import static com.example.park.yapp_1team.utils.URL.CGV_SELECT;
import static com.example.park.yapp_1team.utils.URL.CGV_THEATER_MOVIE_INFO;
import static com.example.park.yapp_1team.utils.URL.LOTTE_THEATER_MOVIE_INFO;

/**
 * Created by HunJin on 2017-09-23.
 */

public class MovieCrawling extends AsyncTask {
    List<MovieInfoListItem> items = new ArrayList<>();
    List<MovieInfoListItem> realItems = new ArrayList<>();


    private String url;
    private String company;
    private String cinemaID;
    private String division;
    private String detail;
    private ArrayList<String> movieList;

    ArrayList<TheaterCodeItem> array;

    // LOTTE
    public MovieCrawling(String cinemaID, String division, String detail, ArrayList movieList) {
        this.movieList = movieList;
        this.url = LOTTE_THEATER_MOVIE_INFO;
        this.cinemaID = cinemaID;
        this.division = division;
        this.detail = detail;

        this.company = LOTTE;
    }

    // CGV
    public MovieCrawling(String cinemaID, String division, ArrayList movieList) {
        this.movieList = movieList;
        this.url = CGV_THEATER_MOVIE_INFO;
        this.cinemaID = cinemaID;
        this.division = division;

        this.company = CGV;
    }

    // MEGABOX
    public MovieCrawling(String cinema, ArrayList movieList) {
        this.url = "http://www.megabox.co.kr/pages/theater/Theater_Schedule.jsp";
        this.movieList = movieList;
        this.company = MEGA;
        this.cinemaID = cinema;
    }


    @Override
    protected List<MovieInfoListItem> doInBackground(Object[] params) {

        if (company.equals(LOTTE)) {
            lotte();
        } else if (company.equals(CGV)) {
            cgv();
        } else if (company.equals(MEGA)) {
            megabox();
        }


        return realItems;
    }


    // http://www.cgv.co.kr/common/showtimes/iframeTheater.aspx?areacode=01&theatercode=0060&date=20170922
    public void cgv() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            Date today = new Date();

            Document document = Jsoup.connect(CGV_THEATER_MOVIE_INFO + "?areacode=" + division + "&theatercode=" + cinemaID + "&date=" + sdf.format(today)).get();
            Elements elements = document.select(CGV_SELECT);

//            Log.e("url", CGV_THEATER_MOVIE_INFO + "?areacode=" + division + "&theatercode=" + cinemaID + "&date=" + sdf.format(today));

            for (Element element : elements) {

                String name = element.select("strong").text();

                Elements typeHallEle = element.select("div.type-hall");

                String typeTheater = "";
                String auditoriumTheater = "";
                String total = "";

                for (Element type : typeHallEle) {
                    Elements hallEle = type.select("div.info-hall li");
                    typeTheater = hallEle.get(0).text();
                    auditoriumTheater = hallEle.get(1).text();
                    total = hallEle.get(2).text();
                    total = total.substring(2, total.length() - 1);

                    Elements tempEle = type.select("div.info-timetable li");

                    for (Element t : tempEle) {

                        String start = t.select("a em").text();

                        String remain = t.select("span.txt-lightblue").text();

                        if (remain.length() > 4) {
                            remain = remain.substring(4, remain.length() - 1);
                        }

                        MovieInfoListItem item = new MovieInfoListItem(CGV_THEATER_CODE, name, start, remain);
                        item.setAuditorium(auditoriumTheater);
                        item.setTotalSeat(total);
                        item.setTypeTheater(typeTheater);

                        if (!name.equals("") && !start.equals("") && !remain.equals("")) {
//                            Log.e("log CGV", "name : " + name + " start : " + start + " seat : " + remain + " total : " + total + " auditorium : " + auditoriumTheater + " type : " + typeTheater);
                            items.add(item);
                        }
                    }
                }
            }

            for (int i = 0; i < items.size(); i++) {
                for (int j = 0; j < movieList.size(); j++) {
                    if (items.get(i).getTitle().equals(movieList.get(j))) {
                        realItems.add(items.get(i));
                    }
                }
            }


        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    // http://www.lottecinema.co.kr/LCHS/Contents/Cinema/Cinema-Detail.aspx?divisionCode=1&detailDivisionCode=2&cinemaID=3030
    public void lotte() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date today = new Date();

//            Log.e("paramlist", "{\"MethodName\":\"GetPlaySequence\",\"channelType\":\"HO\",\"osType\":\"Chrome\",\"osVersion\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36\",\"playDate\":\"" + sdf.format(today) + "\",\"cinemaID\":\"" + Integer.parseInt(division) + "|" + Integer.parseInt(detail)
//                    + "|" + Integer.parseInt(cinemaID) + "\",\"representationMovieCode\":\"\"}");

            Document document = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .data("paramList", "" +
                            "{" +
                            "\"MethodName\":\"GetPlaySequence\"," +
                            "\"channelType\":\"HO\"," +
                            "\"osType\":\"Chrome\"," +
                            "\"osVersion\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36\"," +
                            "\"playDate\":\"" + sdf.format(today) + "\"," +
                            "\"cinemaID\":\"" + Integer.parseInt(division) + "|" + Integer.parseInt(detail) + "|" + Integer.parseInt(cinemaID) + "\"," +
                            "\"representationMovieCode\":\"\"}")
                    .post();

            Elements elements = document.select("body");

            JSONObject jsonObj = new JSONObject(elements.text());

//            Log.e("elements", elements.text());

            JSONArray jsonArr = jsonObj.getJSONObject("PlaySeqsHeader").getJSONArray("Items");

            HashMap<String, String> movie = new HashMap<>();
            HashMap<String, String> film = new HashMap<>();

            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject jsonTemp = jsonArr.getJSONObject(i);

                String moviecode = jsonTemp.get("MovieCode").toString();
                String moviename = jsonTemp.get("MovieNameKR").toString();
                String filmcode = jsonTemp.get("FilmCode").toString();
                String filmname = jsonTemp.get("FilmNameKR").toString();

                if (!movie.containsKey(moviecode)) {
                    movie.put(moviecode, moviename);
                }
                if (!film.containsKey(filmcode)) {
                    film.put(filmcode, filmname);
                }
            }

            jsonArr = jsonObj.getJSONObject("PlaySeqs").getJSONArray("Items");

            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject jsonTemp = jsonArr.getJSONObject(i);

                String movieCode = jsonTemp.get("MovieCode").toString();
                String movieName = "Missing";
                String startTime = jsonTemp.get("StartTime").toString();
                String endTime = jsonTemp.get("EndTime").toString();
                String totalSeat = jsonTemp.get("TotalSeatCount").toString();
                String bookingSeat = jsonTemp.get("BookingSeatCount").toString();
                String screenNum = jsonTemp.get("ScreenNameKR").toString();
                String filmName = "Missing";
                String filmCode = jsonTemp.get("FilmCode").toString();

                if (movie.containsKey(movieCode)) {
                    movieName = movie.get(movieCode);
                }

                if (film.containsKey(filmCode)) {
                    filmName = film.get(filmCode).toString();
                }

                MovieInfoListItem item = new MovieInfoListItem(LOTTE_THEATER_CODE, movieName, startTime, bookingSeat);

//                Log.e("lotte data", "movie code : " + movieCode + " movie name : " + movieName + " start time : " + startTime +
//                        " end time : " + endTime + " total seat : " + totalSeat + " booking seat : " + bookingSeat + " screen number : " + screenNum + " film name : " + filmName);

                item.setTypeTheater(filmName);
                item.setAuditorium(screenNum);
                item.setTotalSeat(totalSeat);

                items.add(item);
            }

            for (int i = 0; i < items.size(); i++) {
                for (int j = 0; j < movieList.size(); j++) {
                    if (items.get(i).getTitle().equals(movieList.get(j))) {
                        realItems.add(items.get(i));
                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // http://www.megabox.co.kr/pages/theater/Theater_Schedule.jsp
    public void megabox() {
        try {

//            Log.e("cra", "mega");

//            String s = url.substring(url.lastIndexOf('&'), url.lastIndexOf('#'));

//            Log.e("mega", s);

//            Log.e("megabox", url + " cinema id : " + cinemaID);

            Document document = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .data("cinema", cinemaID)
                    .post();


//            Elements elements = document.select(URL.aa);
//
//            for(Element element : elements) {
//                Log.e("doc",element.toString());
//            }

            // Elements elements = document.select(URL.MEGABOX_SELECT_THEATER_INFO);

            Elements elements = document.select("tr.lineheight_80");


            //og.e("size",elements.size()+"");

            String tmp = "";

            for (Element t : elements) {
                // Log.e("element",element.toString());


                String name = t.select("strong a").text();

                if (name.equals(""))
                    name = tmp;
                else
                    tmp = name;

                String theaterType = t.select("small").text();
                String auditorium = t.select("th.room div").text();

                Elements tmpElements = t.select("div.cinema_time");

                for (Element ele : tmpElements) {

                    String start = ele.select("span.time").text();

                    String seat = ele.select("span.seat").text();

                    if (seat.contains("/")) {

                        String remain = seat.substring(0, seat.indexOf("/"));
                        String total = seat.substring(seat.indexOf("/") + 1, seat.length());


                        MovieInfoListItem item = new MovieInfoListItem(MEGA_THEATER_CODE, name, start, remain);
                        item.setTotalSeat(total);
                        item.setAuditorium(auditorium);
                        item.setTypeTheater(theaterType);

//                        Log.e("mega log", "name : " + name + " start : " + start + " seat : " + remain + " total : " + total + " auditorium : " + auditorium + " type : " + theaterType);

                        items.add(item);
                    }
                }
            }

            for (int i = 0; i < items.size(); i++) {
                for (int j = 0; j < movieList.size(); j++) {
                    if (items.get(i).getTitle().equals(movieList.get(j))) {
                        realItems.add(items.get(i));
                    }
                }
            }


        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
