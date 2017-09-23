package com.example.park.yapp_1team.items;

/**
 * Created by Park on 2017-08-20.
 */

public class MovieInfoListItem {

    private int id;
    private String theater;
    private double distance;
    private String title;
    private String time;
    private String seat;
    private String thumbnail;

    public MovieInfoListItem(String title, String time, String seat) {
        this.title = title;
        this.time = time;
        this.seat = seat;
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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
