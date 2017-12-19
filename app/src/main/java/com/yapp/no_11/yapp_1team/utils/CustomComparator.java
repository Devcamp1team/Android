package com.yapp.no_11.yapp_1team.utils;

import com.yapp.no_11.yapp_1team.items.MovieListItem;

import java.util.Comparator;

/**
 * Created by HunJin on 2017-08-23.
 */

public class CustomComparator implements Comparator<MovieListItem> {

    @Override
    public int compare(MovieListItem o1, MovieListItem o2) {
        if (o1.getCurrentOrder() < o2.getCurrentOrder()) {
            return -1;
        } else if (o1.getCurrentOrder() > o2.getCurrentOrder()) {
            return 1;
        } else {
            return 0;
        }
    }
}
