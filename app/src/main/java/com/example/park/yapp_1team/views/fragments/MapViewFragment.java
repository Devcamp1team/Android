package com.example.park.yapp_1team.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.park.yapp_1team.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import static com.example.park.yapp_1team.utils.PermissionRequestCode.LOCATION_PERMISSION_CODE;

/**
 * Created by HunJin on 2017-09-09.
 */

public class MapViewFragment extends Fragment implements NMapView.OnMapStateChangeListener {

    private static final String TAG = MapViewFragment.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mCurrentLocation;

    private double lat = 37.4979462;
    private double lng = 127.0254323;

    private NMapContext context;
    private NMapController controller;


    private NMapView mapView;

    private ImageView imgCurrentLocation;

    private double movieLat;
    private double movieLng;

    public double getMovieLat() {
        return movieLat;
    }

    public void setMovieLat(double movieLat) {
        this.movieLat = movieLat;
    }

    public double getMovieLng() {
        return movieLng;
    }

    public void setMovieLng(double movieLng) {
        this.movieLng = movieLng;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = new NMapContext(super.getActivity());
        context.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_view, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (NMapView) getView().findViewById(R.id.map_view);
        mapView.setClientId(getResources().getString(R.string.n_key));// 클라이언트 아이디 설정

        context.setupMapView(mapView);

        imgCurrentLocation = (ImageView) super.getView().findViewById(R.id.img_current_location);
        imgCurrentLocation.bringToFront();

        event();

    }

    private void permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
            } else {
                // TODO: 2017-08-11 already has permission granted, go to next step.
                getLocation();
            }
        } else {
            startLocationService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(getContext(), "지도 체크를 할건데 권한 체크 안하면 뭐... 제약이 있을 수 있어", Toast.LENGTH_SHORT).show();
                    moveMapCenter();
                }
                break;
            }
        }
    }

    private void moveMapCenter() {
        NGeoPoint nGeoPoint = new NGeoPoint();
        nGeoPoint.set(lng, lat);
        controller.setMapCenter(nGeoPoint);

//        NMapOverlayItem item = new NMapOverlayItem(nGeoPoint, "현재위치", "", getResources().getDrawable(R.drawable.ic_add_location_pink_24dp));

        NMapResourceProvider nMapResourceProvider = new NMapResourceProvider(getContext()) {
            @Override
            protected int findResourceIdForMarker(int i, boolean b) {
                return 0;
            }

            @Override
            protected Drawable getDrawableForMarker(int i, boolean b, NMapOverlayItem nMapOverlayItem) {
                if (i == 1) {
                    return getResources().getDrawable(R.drawable.ic_add_location_pink_24dp);
                } else {
                    return getResources().getDrawable(R.drawable.ic_place_black_24dp);
                }

            }

            @Override
            public Drawable getCalloutBackground(NMapOverlayItem nMapOverlayItem) {
                return null;
            }

            @Override
            public String getCalloutRightButtonText(NMapOverlayItem nMapOverlayItem) {
                return null;
            }

            @Override
            public Drawable[] getCalloutRightButton(NMapOverlayItem nMapOverlayItem) {
                return new Drawable[0];
            }

            @Override
            public Drawable[] getCalloutRightAccessory(NMapOverlayItem nMapOverlayItem) {
                return new Drawable[0];
            }

            @Override
            public int[] getCalloutTextColors(NMapOverlayItem nMapOverlayItem) {
                return new int[0];
            }

            @Override
            public Drawable[] getLocationDot() {
                return new Drawable[0];
            }

            @Override
            public Drawable getDirectionArrow() {
                return null;
            }

            @Override
            public int getParentLayoutIdForOverlappedListView() {
                return 0;
            }

            @Override
            public int getOverlappedListViewId() {
                return 0;
            }

            @Override
            public int getLayoutIdForOverlappedListView() {
                return 0;
            }

            @Override
            public void setOverlappedListViewLayout(ListView listView, int i, int i1, int i2) {

            }

            @Override
            public int getListItemLayoutIdForOverlappedListView() {
                return 0;
            }

            @Override
            public int getListItemTextViewId() {
                return 0;
            }

            @Override
            public int getListItemTailTextViewId() {
                return 0;
            }

            @Override
            public int getListItemImageViewId() {
                return 0;
            }

            @Override
            public int getListItemDividerId() {
                return 0;
            }

            @Override
            public void setOverlappedItemResource(NMapPOIitem nMapPOIitem, ImageView imageView) {

            }
        };

        NMapOverlayManager mapOverlayManager = new NMapOverlayManager(getContext(), mapView, nMapResourceProvider);


        int markerId = 1;

// set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, nMapResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(lng, lat, "현재", markerId, 0);
        poiData.addPOIitem(movieLng, movieLat, "영화관", 2, 1);
        poiData.endPOIdata();

        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);

        poiDataOverlay.setOnStateChangeListener(new NMapPOIdataOverlay.OnStateChangeListener() {
            @Override
            public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                return;
            }

            @Override
            public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                return;
            }
        });

    }

    @SuppressWarnings("MissingPermission")
    private void getLocation() {
        if (!checkGPS()) {
            Toast.makeText(getContext(), "GPS를 확인하세요.", Toast.LENGTH_SHORT).show();
            moveMapCenter();
            return;
        }
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        OnCompleteListener<Location> onCompleteListener = new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    mCurrentLocation = task.getResult();
                    lat = mCurrentLocation.getLatitude();
                    lng = mCurrentLocation.getLongitude();
                    moveMapCenter();
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(getActivity(), onCompleteListener);
    }

    private boolean checkGPS() {
        LocationManager manager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("GPS 상태 변경");
            builder.setMessage("GPS 상태 변경 하러 갈래요?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            it.addCategory(Intent.CATEGORY_DEFAULT);
                            startActivity(it);
                        }
                    });
            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
            return false;
        } else {
            return true;
        }
    }

    private void event() {
        imgCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        context.onStart();
        mapView.setClickable(true);
        mapView.setEnabled(true);
        mapView.setFocusable(true);
        mapView.setFocusableInTouchMode(true);
        mapView.requestFocus();
        mapView.setOnMapStateChangeListener(this);

        controller = mapView.getMapController();
        permissionCheck();
    }

    @Override
    public void onResume() {
        super.onResume();
        context.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        context.onPause();
    }

    @Override
    public void onStop() {
        context.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        context.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if (nMapError == null) { // success
            mapView.setBuiltInZoomControls(true, null);
            permissionCheck();
            controller.setZoomLevel(11);
            mapView.setBuiltInAppControl(true);
        } else {
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


    boolean isFind = false;

    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            if (!isFind) {
                lat = latitude;
                lng = longitude;
                moveMapCenter();
            }
            isFind = true;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 0;
        float minDistance = 0;


        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                Toast.makeText(getContext(), "Last Known Location : " + "Latitude : " + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Toast.makeText(getContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

}
