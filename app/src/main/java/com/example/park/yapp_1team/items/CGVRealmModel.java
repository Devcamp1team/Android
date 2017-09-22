package com.example.park.yapp_1team.items;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by HunJin on 2017-09-22.
 */

public class CGVRealmModel extends RealmObject {

    @PrimaryKey
    private String name;

    private double lat;
    private double lng;
    private String areaCode;
    private String theaterCode;
    private String regionCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTheaterCode() {
        return theaterCode;
    }

    public void setTheaterCode(String theaterCode) {
        this.theaterCode = theaterCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
