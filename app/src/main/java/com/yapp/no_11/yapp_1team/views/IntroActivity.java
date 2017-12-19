package com.yapp.no_11.yapp_1team.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.github.paolorotolo.appintro.AppIntro;
import com.yapp.no_11.yapp_1team.views.fragments.FirstIntroFragment;
import com.yapp.no_11.yapp_1team.views.fragments.FourthIntroFragment;
import com.yapp.no_11.yapp_1team.views.fragments.SecondIntroFragment;
import com.yapp.no_11.yapp_1team.views.fragments.ThirdIntroFragment;

import io.fabric.sdk.android.Fabric;


/**
 * Created by HunJin on 2017-09-23.
 */

public class IntroActivity extends AppIntro {

    private Fragment firstFragment;
    private Fragment secondFragment;
    private Fragment thirdFragment;
    private FourthIntroFragment fourthFragment;

    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());


        sp = getSharedPreferences("firstflag", Context.MODE_PRIVATE);

        /**
         * 사용자의 첫 방문인지 체크한다.
         */
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if (hasVisited) {
            setHasVisit();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        // Note here that we DO NOT use setContentView();
        firstFragment = new FirstIntroFragment();
        secondFragment = new SecondIntroFragment();
        thirdFragment = new ThirdIntroFragment();
        fourthFragment = new FourthIntroFragment();
        fourthFragment.setClick(() -> {
            setHasVisit();
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        });

        // Add your slide fragments here.
        // AppIntro will automatically generate the
        // dots indicator and buttons.

        addSlide(firstFragment);
        addSlide(secondFragment);
        addSlide(thirdFragment);
        addSlide(fourthFragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
//        addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setFadeAnimation();

        setBarColor(Color.parseColor("#00000000"));
        setSeparatorColor(Color.parseColor("#00000000"));
        setNavBarColor("#00000000");
        setIndicatorColor(Color.parseColor("#6a7482"), Color.parseColor("#BDBDBD"));


        // Hide Skip/Done button.
        setColorSkipButton(Color.GRAY);
        setColorDoneText(Color.GRAY);
        setNextArrowColor(Color.GRAY);


        showSkipButton(true);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        setHasVisit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        setHasVisit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        if (newFragment == fourthFragment) {
            setIndicatorColor(Color.parseColor("#00000000"), Color.parseColor("#00000000"));
            showSkipButton(false);
        } else {
            setIndicatorColor(Color.parseColor("#6a7482"), Color.parseColor("#BDBDBD"));

            showSkipButton(true);
        }
        // Do something when the slide changes.
    }

    private void setHasVisit() {
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("hasVisited", true);
        e.apply();
    }
}
