package com.example.park.yapp_1team.items;

/**
 * Created by Park on 2017-08-20.
 */

public class TheaterCodeItem {

    private String company;
    private String area;
    private String theater;
    private String detailarea;
    private String name;

    public TheaterCodeItem(String company, String name, String area, String theater)
    {
        this.company = company;
        this.name = name;
        this.area = area;
        this.theater = theater;
    }

    public TheaterCodeItem(String company, String name, String area, String detail, String theater)
    {
        this.company = company;
        this.name = name;
        this.area = area;
        this.detailarea = detail;
        this.theater = theater;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public void setTheater(String theater)
    {
        this.theater = theater;
    }

    public void setDetailarea(String detail)
    {
        this.detailarea = detail;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getArea()
    {
        return area;
    }

    public String getTheater()
    {
        return theater;
    }

    public String getDetailarea()
    {
        return detailarea;
    }

    public String getName()
    {
        return name;
    }

}
