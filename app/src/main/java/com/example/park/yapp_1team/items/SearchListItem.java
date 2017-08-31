package com.example.park.yapp_1team.items;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Park on 2017-08-30.
 */

public class SearchListItem extends RealmObject {

    @PrimaryKey
    private String location;

    public SearchListItem(){}

    public SearchListItem(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }
}
