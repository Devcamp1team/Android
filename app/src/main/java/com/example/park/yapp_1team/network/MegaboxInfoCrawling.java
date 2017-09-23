package com.example.park.yapp_1team.network;

import android.os.AsyncTask;

import com.example.park.yapp_1team.utils.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by HunJin on 2017-09-23.
 */

public class MegaboxInfoCrawling extends AsyncTask<Object, Object, String> {

    private static final String TAG = MegaboxInfoCrawling.class.getSimpleName();

    private String url;

    public MegaboxInfoCrawling(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {

            Document document = Jsoup.connect(url).get();

            Elements elements = document.select(URL.MEGABOX_SELECT_THEATER_INFO);

            String latlng = null;
//            Log.e(TAG,elements.get(0).toString());
            for (Element element : elements) {
//                Log.e(TAG, element.toString());
                String s = element.toString();
                s = s.substring(s.indexOf("var"), s.indexOf(';')).trim();
                s = s.substring(s.indexOf('(') + 1, s.indexOf(')'));

                latlng = s;
            }

            return latlng;
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return null;
    }
}
