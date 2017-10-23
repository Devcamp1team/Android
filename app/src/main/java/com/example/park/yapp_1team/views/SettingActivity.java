package com.example.park.yapp_1team.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.park.yapp_1team.R;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        statusBarChange();
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.txt_setting_acknowledgment: {
                startActivity(new Intent(getApplicationContext(), AcknowledgementActivity.class));
                break;
            }
            case R.id.btn_setting_evaluation: {

                break;
            }
            case R.id.img_setting_cancel: {
                finish();
                break;
            }
            case R.id.txt_setting_license: {
                startActivity(new Intent(getApplicationContext(), LicenseActivity.class));
            }
        }
    }
}
