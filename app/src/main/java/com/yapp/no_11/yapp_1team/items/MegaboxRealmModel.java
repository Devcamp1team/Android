package com.yapp.no_11.yapp_1team.items;

import io.realm.RealmObject;

/**
 * Created by HunJin on 2017-09-23.
 */

public class MegaboxRealmModel extends RealmObject {
    private String name;
    private int mega;
    private double lat;
    private double lng;
    private String www;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMega() {
        return mega;
    }

    public void setMega(int mega) {
        this.mega = mega;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }
}
