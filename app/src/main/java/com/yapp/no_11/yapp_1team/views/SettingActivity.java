package com.yapp.no_11.yapp_1team.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yapp.no_11.yapp_1team.R;

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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id="+getPackageName()));
                startActivity(intent);
                break;
            }
            case R.id.img_setting_cancel: {
                finish();
                break;
            }
            case R.id.txt_setting_license: {
                startActivity(new Intent(getApplicationContext(), LicenseActivity.class));
                break;
            }
            case R.id.txt_intro_init: {
                SharedPreferences sp = getSharedPreferences("firstflag", Context.MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean("hasVisited", false);
                e.apply();
                Toast.makeText(this, "앱을 실행시키면 인트로가 보입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
