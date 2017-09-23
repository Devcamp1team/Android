package com.example.park.yapp_1team.items;

import io.realm.RealmObject;

/**
 * Created by HunJin on 2017-09-23.
 */

public class LotteRealmModel extends RealmObject {
    private String name;
    private int lotte;
    private double lat;
    private double lng;
    private String divisionCode;
    private String detailDivisionCode;
    private String cinemaID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLotte() {
        return lotte;
    }

    public void setLotte(int lotte) {
        this.lotte = lotte;
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

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getDetailDivisionCode() {
        return detailDivisionCode;
    }

    public void setDetailDivisionCode(String detailDivisionCode) {
        this.detailDivisionCode = detailDivisionCode;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }
}
