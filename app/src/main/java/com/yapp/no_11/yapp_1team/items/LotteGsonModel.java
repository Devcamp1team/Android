package com.yapp.no_11.yapp_1team.items;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HunJin on 2017-09-23.
 */

public class LotteGsonModel {

    private
    @SerializedName("Cinemas")
    Cinemas cinemases;

    public Cinemas getCinemases() {
        return cinemases;
    }

    public void setCinemases(Cinemas cinemases) {
        this.cinemases = cinemases;
    }

    public class Cinemas {

        private
        @SerializedName("Items")
        Item[] items;

        public Item[] getItems() {
            return items;
        }

        public void setItems(Item[] items) {
            this.items = items;
        }
    }

    public class Item {
        private
        @SerializedName("DivisionCode")
        String divCode;
        private
        @SerializedName("DetailDivisionCode")
        String detailDivCode;
        private
        @SerializedName("CinemaID")
        String id;
        private
        @SerializedName("CinemaNameKR")
        String name;
        private
        @SerializedName("SortSequence")
        String sort;
        private
        @SerializedName("Latitude")
        String lat;
        private
        @SerializedName("Longitude")
        String lng;

        public String getDivCode() {
            return divCode;
        }

        public void setDivCode(String divCode) {
            this.divCode = divCode;
        }

        public String getDetailDivCode() {
            return detailDivCode;
        }

        public void setDetailDivCode(String detailDivCode) {
            this.detailDivCode = detailDivCode;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }

}
