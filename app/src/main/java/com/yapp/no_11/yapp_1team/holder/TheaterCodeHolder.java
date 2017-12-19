package com.yapp.no_11.yapp_1team.holder;

import com.yapp.no_11.yapp_1team.items.TheaterCodeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Park on 2017-08-20.
 */

public class TheaterCodeHolder {

    private List<TheaterCodeItem> list;

    public TheaterCodeHolder(){
        list = new ArrayList<>();
    }

    public void setList(List<TheaterCodeItem> list)
    {
        this.list = list;
    }
}
