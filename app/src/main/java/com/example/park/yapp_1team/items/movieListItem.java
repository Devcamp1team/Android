package com.example.park.yapp_1team.items;

/**
 * Created by Park on 2017-08-19.
 */

public class MovieListItem {

    private String name;
    private String url;
    private int check;

    public MovieListItem(String name, String url, int check)
    {
        this.name = name;
        this.url = url;
        this.check = check;
    }
    public MovieListItem(String name, String url)
    {
        this.name = name;
        this.url = url;

    }

    public MovieListItem()
    {

    }
    public String getName()
    {
        return name;
    }

    public String getURL()
    {
        return url;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setURL(String url)
    {
        this.url = url;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
