package com.example.park.yapp_1team.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.park.yapp_1team.utils.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2017-09-23.
 */

public class MegaboxTheaterCrawling extends AsyncTask<Object, Object, List<MegaboxInfo>> {

    private static final String TAG = MegaboxTheaterCrawling.class.getSimpleName();


    @Override
    protected List<MegaboxInfo> doInBackground(Object... params) {
        List<MegaboxInfo> megaUrlList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URL.MEGABOX_THEATER_URL).get();
            Elements elements = document.select(URL.MEGABOX_SELECT_THEATER);
            for (Element element : elements) {
                String name = element.html();
                String www = element.attr("onclick");
                www = www.substring(www.indexOf('\'')+1, www.lastIndexOf('\''));
//                Log.e(TAG, www);

                MegaboxInfo info = new MegaboxInfo();
                info.name = name;
                info.www = www;
                megaUrlList.add(info);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return megaUrlList;
    }
}
