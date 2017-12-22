package com.yapp.no_11.yapp_1team.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yapp.no_11.yapp_1team.R;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

/**
 * Created by HunJin on 2017-12-19.
 */

public class MapViewFragmentDaum extends Fragment {

    private static final int THEATER_TAG = 0;
    private static final int CURRENT_TAG = 1;

    private double lat = 37.4979462;
    private double lng = 127.0254323;

    private double movieLat;
    private double movieLng;
    private String theaterName = "영화관";

    private MapView mapView;
    private ImageView imgCurrentLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapLayout mapLayout = new MapLayout(getActivity());
        mapView = mapLayout.getMapView();

        mapView.setDaumMapApiKey(getResources().getString(R.string.daum_key));
        mapView.setOpenAPIKeyAuthenticationResultListener(openAPIKeyAuthenticationResultListener);
        mapView.setMapViewEventListener(mapViewEventListener);
        mapView.setMapType(MapView.MapType.Standard);

        ViewGroup container = (ViewGroup) view.findViewById(R.id.map_view);
        container.addView(mapLayout);

        imgCurrentLocation = (ImageView) view.findViewById(R.id.img_current_location);
        imgCurrentLocation.bringToFront();

        event();
    }


    private MapView.OpenAPIKeyAuthenticationResultListener openAPIKeyAuthenticationResultListener = (mapView, i, s) -> {

    };

    private MapView.MapViewEventListener mapViewEventListener = new MapView.MapViewEventListener() {
        @Override
        public void onMapViewInitialized(MapView mapView) {
            moveMapCenter();
        }

        @Override
        public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewZoomLevelChanged(MapView mapView, int i) {

        }

        @Override
        public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

        }
    };


    private void moveMapCenter() {

        MapPoint theaterPoint = MapPoint.mapPointWithGeoCoord(movieLat, movieLng);
        MapPoint currentPoint = MapPoint.mapPointWithGeoCoord(lat, lng);

        addTheaterMarker(theaterPoint);
        addCurrentMarker(currentPoint);

        showAll(theaterPoint, currentPoint);
    }

    private void addTheaterMarker(MapPoint theaterPoint) {
        MapPOIItem theaterMarker = new MapPOIItem();
        theaterMarker.setItemName(theaterName);
        theaterMarker.setTag(THEATER_TAG);

        theaterMarker.setMapPoint(theaterPoint);
        theaterMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        theaterMarker.setCustomImageResourceId(R.drawable.ic_adjust_black_24dp);
        theaterMarker.setCustomImageAutoscale(false);
        theaterMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(theaterMarker);
        mapView.selectPOIItem(theaterMarker, true);


    }

    private void addCurrentMarker(MapPoint currentPoint) {
        MapPOIItem currentMarker = new MapPOIItem();
        currentMarker.setItemName("현재 위치");
        currentMarker.setTag(CURRENT_TAG);

        currentMarker.setMapPoint(currentPoint);
        currentMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        currentMarker.setCustomImageResourceId(R.drawable.ic_place_black_36dp);
        currentMarker.setCustomImageAutoscale(false);
        currentMarker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(currentMarker);
        mapView.selectPOIItem(currentMarker, true);
        mapView.setMapCenterPoint(currentPoint, true);

    }

    private void showAll(MapPoint theaterPoint, MapPoint currentPoint) {
        int padding = 20;
        float minZoomLevel = 7;
        float maxZoomLevel = 10;
        MapPointBounds bounds = new MapPointBounds(theaterPoint, currentPoint);
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));

        double centerLat = (theaterPoint.getMapPointGeoCoord().latitude + currentPoint.getMapPointGeoCoord().latitude) / 2;
        double centerLng = (theaterPoint.getMapPointGeoCoord().longitude + currentPoint.getMapPointGeoCoord().longitude) / 2;

        MapPoint centerPoint = MapPoint.mapPointWithGeoCoord(centerLat, centerLng);

        mapView.setMapCenterPointAndZoomLevel(centerPoint, 6, false);
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
    }
}
