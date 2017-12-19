package com.yapp.no_11.yapp_1team.interfaces;

import com.yapp.no_11.yapp_1team.items.SelectMovieInfoItem;

import java.util.List;

/**
 * Created by HunJin on 2017-09-08.
 */

public interface RcvClickListener {
    void itemClick(int position, List<SelectMovieInfoItem> listItems);
}
