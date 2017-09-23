package com.example.park.yapp_1team.sql;

import android.util.Log;

import com.example.park.yapp_1team.items.CGVRealmModel;
import com.example.park.yapp_1team.items.LotteGsonModel;
import com.example.park.yapp_1team.items.LotteRealmModel;
import com.example.park.yapp_1team.items.MegaboxRealmModel;
import com.example.park.yapp_1team.items.SearchListItem;
import com.example.park.yapp_1team.utils.Strings;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by HunJin on 2017-09-09.
 */

public class RealmRest {

    private static final String TAG = RealmRest.class.getSimpleName();

    private Realm realm;

    public RealmRest() {
        initRealm();
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
    }

    public void insertMegaInfo(String[] info) {
        realm.beginTransaction();
        MegaboxRealmModel megaboxRealmModel = realm.createObject(MegaboxRealmModel.class);
        megaboxRealmModel.setName(info[0]);
        megaboxRealmModel.setMega(Strings.THEATER_CODE_MEGA);
        megaboxRealmModel.setLat(Double.parseDouble(info[1]));
        megaboxRealmModel.setLng(Double.parseDouble(info[2]));
        megaboxRealmModel.setWww(info[3]);
        realm.commitTransaction();
    }


    public void insertCGVInfo(String[] info) {
        realm.beginTransaction();
        CGVRealmModel cgvRealmModel = realm.createObject(CGVRealmModel.class);
        cgvRealmModel.setName(info[0]);
        cgvRealmModel.setCgv(Strings.THEATER_CODE_CGV);
        cgvRealmModel.setLat(Double.parseDouble(info[1]));
        cgvRealmModel.setLng(Double.parseDouble(info[2]));
        cgvRealmModel.setAreaCode(info[3]);
        cgvRealmModel.setTheaterCode(info[4]);
        cgvRealmModel.setRegionCode(info[5]);
        realm.commitTransaction();
    }

    public void insertLotteInfo(LotteGsonModel.Item item) {
        realm.beginTransaction();
        LotteRealmModel realmModel = realm.createObject(LotteRealmModel.class);
        realmModel.setName(item.getName());
        realmModel.setLotte(Strings.THEATER_CODE_LOTTE);
        realmModel.setLat(Double.parseDouble(item.getLat()));
        realmModel.setLng(Double.parseDouble(item.getLng()));
        realmModel.setCinemaID(item.getId());
        realmModel.setDivisionCode(item.getDivCode());
        realmModel.setDetailDivisionCode(item.getDetailDivCode());
        realm.commitTransaction();
    }

    public RealmResults<MegaboxRealmModel> getMegaInfo() {
        return realm.where(MegaboxRealmModel.class).findAll();
    }


    public RealmResults<LotteRealmModel> getLotteInfo() {
        return realm.where(LotteRealmModel.class).findAll();
    }

    public RealmResults<CGVRealmModel> getCGVInfo() {
        return realm.where(CGVRealmModel.class).findAll();
    }

    public RealmResults<CGVRealmModel> getCGVInfo(String name) {
        return realm.where(CGVRealmModel.class).contains("name", name).findAll();
    }

    public RealmResults<LotteRealmModel> getLotteInfo(String name) {
        return realm.where(LotteRealmModel.class).contains("name",name).findAll();
    }

    public RealmResults<MegaboxRealmModel> getMegaInfo(String name) {
        return realm.where(MegaboxRealmModel.class).contains("name",name).findAll();
    }

    public RealmResults<SearchListItem> getUserList() {
        return realm.where(SearchListItem.class).findAll();
    }

    public RealmResults<SearchListItem> getUserList(String location) {
        return realm.where(SearchListItem.class).equalTo("location", location).findAll();
    }

    public void insertUserData(String location) {
        realm.beginTransaction();

        RealmResults<SearchListItem> tmpItem = getUserList(location);
        RealmResults<SearchListItem> tmpdelete = getUserList();

        if (tmpdelete.size() > 2) {
            Log.e("delete", "in");
            tmpdelete.first().deleteFromRealm();
        }

        if (tmpItem.size() == 0) {
            realm.createObject(SearchListItem.class, location);
        }

        realm.commitTransaction();
    }

    public void closeRealm() {
        realm.close();
    }


}
