package com.yapp.no_11.yapp_1team.items;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Park on 2017-08-30.
 */

public class SearchListItem extends RealmObject {

    @PrimaryKey
    private String location;

    private double lat;
    private double lng;

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

    public SearchListItem(){}

    public SearchListItem(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }
}
