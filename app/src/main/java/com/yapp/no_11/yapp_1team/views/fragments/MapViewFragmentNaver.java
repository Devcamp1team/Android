package com.yapp.no_11.yapp_1team.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.yapp.no_11.yapp_1team.R;
import com.yapp.no_11.yapp_1team.utils.nmap.NMapFragment;
import com.yapp.no_11.yapp_1team.utils.nmap.NMapPOIflagType;
import com.yapp.no_11.yapp_1team.utils.nmap.NMapViewerResourceProvider;

/**
 * Created by HunJin on 2017-12-19.
 */

public class MapViewFragmentNaver extends NMapFragment
        implements NMapView.OnMapStateChangeListener, NMapPOIdataOverlay.OnStateChangeListener {

    private double lat = 37.4979462;
    private double lng = 127.0254323;

    private double movieLat;
    private double movieLng;
    private String theaterName = "영화관";

    private NMapView mapView;
    private NMapController mapController;
    private NMapViewerResourceProvider mapViewerResourceProvider;
    private NMapOverlayManager mapOverlayManager;

    //    private MapView mapView;
    private ImageView imgCurrentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapView = (NMapView) v.findViewById(R.id.map_view);
        mapView.setClientId(getResources().getString(R.string.n_key));
        mapView.setClickable(true);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mapView = new NMapView(getContext());

        imgCurrentLocation = (ImageView) view.findViewById(R.id.img_current_location);
        imgCurrentLocation.bringToFront();

        event();
    }

    private void moveMapCenter() {
        NGeoPoint currentPoint = new NGeoPoint(lng, lat);
        mapController.setMapCenter(currentPoint);

        NMapPOIdata poiData = new NMapPOIdata(2, mapViewerResourceProvider);
        poiData.addPOIitem(lng, lat, "현재 위치", NMapPOIflagType.FROM, 0);
        poiData.addPOIitem(movieLng, movieLat, theaterName, NMapPOIflagType.TO, 0);
        poiData.endPOIdata();

        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);
        poiDataOverlay.setOnStateChangeListener(this);

    }

    private void event() {
        imgCurrentLocation.setOnClickListener(v -> moveMapCenter());
    }

    public void setMovieLat(double movieLat) {
        this.movieLat = movieLat;
    }

    public void setMovieLng(double movieLng) {
        this.movieLng = movieLng;
    }

    public void setTheaterName(String name) {
        this.theaterName = name;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.setBuiltInZoomControls(true, null);
        mapView.setOnMapStateChangeListener(this);
        mapController = mapView.getMapController();
        mapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());
        mapOverlayManager = new NMapOverlayManager(getActivity(), mapView, mapViewerResourceProvider);
        moveMapCenter();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if (nMapError == null) {
            moveMapCenter();
        } else {
            Log.e("map init error", nMapError.message);
        }
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
        if (nMapPOIitem != null) {
            Log.i("map focus change", "onFocusChanged: " + nMapPOIitem.toString());
        } else {
            Log.i("map focus change", "onFocusChanged: ");
        }

    }

    @Override
    public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
        Toast.makeText(getContext(), "onCalloutClick: " + nMapPOIitem.getTitle(), Toast.LENGTH_LONG).show();
    }
}
