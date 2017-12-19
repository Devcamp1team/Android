package com.yapp.no_11.yapp_1team.items;

/**
 * Created by HunJin on 2017-09-02.
 */

public class SelectMovieInfoItem {
    private String startTime;
    private String endTIme;
    private String imgThumbnail;
    private String title;
    private String leftSeat;
    private String useSeat;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTIme() {
        return endTIme;
    }

    public void setEndTIme(String endTIme) {
        this.endTIme = endTIme;
    }

    public String getImgThumbnail() {
        return imgThumbnail;
    }

    public void setImgThumbnail(String imgThumbnail) {
        this.imgThumbnail = imgThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLeftSeat() {
        return leftSeat;
    }

    public void setLeftSeat(String leftSeat) {
        this.leftSeat = leftSeat;
    }

    public String getUseSeat() {
        return useSeat;
    }

    public void setUseSeat(String useSeat) {
        this.useSeat = useSeat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
