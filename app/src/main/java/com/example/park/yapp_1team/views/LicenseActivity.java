package com.example.park.yapp_1team.views;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.park.yapp_1team.R;

import java.io.IOException;
import java.io.InputStream;

public class LicenseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        statusBarChange();

        TextView txtLicense = (TextView) findViewById(R.id.txt_license);
        try {
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("license.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);

            txtLicense.setText(text);

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void click(View v) {
        finish();
    }
}
