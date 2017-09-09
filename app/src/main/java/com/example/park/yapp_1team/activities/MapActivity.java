package com.example.park.yapp_1team.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.activities.fragments.MapViewFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MapActivity extends BaseActivity {

    private static final String TAG = MapActivity.class.getSimpleName();
    private Toolbar mapToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        statusBarChange();
        getKeyHash();

        initialize();

    }

    private void initialize() {

        mapToolbar = (Toolbar) findViewById(R.id.toolbar_map);

//        setSupportActionBar(mapToolbar);

        mapToolbar.setContentInsetsAbsolute(0, 0);


        MapViewFragment fragment = new MapViewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.map_view_container, fragment);
        transaction.commit();

    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.park.yapp_1team", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
