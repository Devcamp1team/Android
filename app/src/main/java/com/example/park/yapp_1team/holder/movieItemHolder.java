package com.example.park.yapp_1team.holder;

import com.example.park.yapp_1team.items.MovieListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Park on 2017-08-19.
 */

public class MovieItemHolder {

    private List<MovieListItem> list;

    public MovieItemHolder(){
        list = new ArrayList<>();
    }

    public void setList(List<MovieListItem> list)
    {
        this.list = list;
    }
}
