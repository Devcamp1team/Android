package com.example.park.yapp_1team.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.park.yapp_1team.holder.TheaterCodeHolder;
import com.example.park.yapp_1team.items.TheaterCodeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.park.yapp_1team.utils.JSON.CGV_THEATER_JSON;
import static com.example.park.yapp_1team.utils.JSON.LOTTE_THEATER_JSON;
import static com.example.park.yapp_1team.utils.Strings.CGV;
import static com.example.park.yapp_1team.utils.Strings.LOTTE;

/**
 * Created by Park on 2017-08-20.
 */

public class TheaterInfoCrawling extends AsyncTask {

    private String company;
    private String url;
    private List<TheaterCodeItem> items = new ArrayList<>();


    public TheaterInfoCrawling(String company)
    {
        this.company = company;
    }

    public TheaterInfoCrawling(String company, String url)
    {

    }

    @Override
    protected List<TheaterCodeItem> doInBackground(Object[] params) {

        if(company.equals(CGV))
        {
            cgv();
        }
        else if(company.equals(LOTTE))
        {
            lotte();
        }

        return items;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    private void cgv() {

        try {

            JSONArray jsonArr = new JSONArray(CGV_THEATER_JSON);

            for(int i = 0 ; i < jsonArr.length() ; ++i)
            {
                JSONObject tempObj = jsonArr.getJSONObject(i);
                JSONArray tempArr = tempObj.getJSONArray("AreaTheaterDetailList");

                for(int j = 0 ; j < tempArr.length() ; ++j)
                {
                    JSONObject objTemp = tempArr.getJSONObject(j);

                    String region = objTemp.get("RegionCode").toString();
                    String theater = objTemp.get("TheaterCode").toString();
                    String cinemaName = objTemp.get("TheaterName").toString();

                    TheaterCodeItem item = new TheaterCodeItem(CGV, cinemaName, region, theater);

                    Log.e("cgv", "region : " + region + " theater : " + theater);

                    items.add(item);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void lotte()
    {
        try {
            JSONObject jsonObj = new JSONObject(LOTTE_THEATER_JSON);

            JSONArray jsonArr = jsonObj.getJSONObject("Cinemas").getJSONArray("Items");

            for(int i = 0 ; i < jsonArr.length() ; ++i)
            {
                JSONObject jsonTemp = jsonArr.getJSONObject(i);

                String theater  = jsonTemp.get("CinemaID").toString();
                String detail = jsonTemp.get("DetailDivisionCode").toString();
                String area = jsonTemp.get("DivisionCode").toString();
                String name = jsonTemp.get("CinemaNameKR").toString();

                TheaterCodeItem item = new TheaterCodeItem(LOTTE, name, area, detail, theater);

                Log.e("lotte", "id : " + theater + " name : " + name + " division : " + area + " detail : " + detail);

                items.add(item);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
