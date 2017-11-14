package com.example.park.yapp_1team.items;

/**
 * Created by Park on 2017-08-20.
 */

public class MovieInfoListItem {

    private int id;
    private int theaterCode;
    private String theater;
    private double distance;
    private String title;
    private String time;
    private String remindSeat;
    private String auditorium;
    private String typeTheater;
    private String totalSeat;
    private String thumbnail;
    private double lat;
    private double lng;

    public MovieInfoListItem(int theaterCode, String title, String time, String remindSeat) {
        this.theaterCode = theaterCode;
        this.title = title;
        this.time = time;
        this.remindSeat = remindSeat;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public void setTypeTheater(String typeTheater) {
        this.typeTheater = typeTheater;
    }

    public void setTotalSeat(String totalSeat) {
        this.totalSeat = totalSeat;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public String getTypeTheater() {
        return typeTheater;
    }

    public String getTotalSeat() {
        return totalSeat;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemindSeat() {
        return remindSeat;
    }

    public void setRemindSeat(String remindSeat) {
        this.remindSeat = remindSeat;
    }

    public int getTheaterCode() {
        return theaterCode;
    }

    public void setTheaterCode(int theaterCode) {
        this.theaterCode = theaterCode;
    }
}
