package com.example.park.yapp_1team.holder;

import com.example.park.yapp_1team.items.MovieInfoListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Park on 2017-08-20.
 */

public class MovieListHolder {

    private List<MovieInfoListItem> list;

    public MovieListHolder(){
        list = new ArrayList<>();
    }

    public void setList(List<MovieInfoListItem> list)
    {
        this.list = list;
    }
}
