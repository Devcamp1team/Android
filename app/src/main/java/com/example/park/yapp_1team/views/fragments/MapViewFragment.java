package com.example.park.yapp_1team.views.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.park.yapp_1team.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
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

    private NMapResourceProvider nMapResourceProvider;
    private NMapOverlayManager overlayManager;

    private NMapView mapView;

    private ImageView imgCurrentLocation;

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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
            // TODO: 2017-08-11 already has permission granted, go to next step.
            getLocation();
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
    }

    @SuppressWarnings("MissingPermission")
    private void getLocation() {
        if (!checkGPS()) {
            Toast.makeText(getContext(),"GPS를 확인하세요.",Toast.LENGTH_SHORT).show();
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
                        Log.e(TAG, "CurrentLocation exception");
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
            Log.e("Naver", "onMapInitHandler: error=" + nMapError.toString());
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
}