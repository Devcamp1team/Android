package com.example.park.yapp_1team.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.park.yapp_1team.views.fragments.FirstIntroFragment;
import com.example.park.yapp_1team.views.fragments.FourthIntroFragment;
import com.example.park.yapp_1team.views.fragments.SecondIntroFragment;
import com.example.park.yapp_1team.views.fragments.ThirdIntroFragment;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by HunJin on 2017-09-23.
 */

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment firstFragment = new FirstIntroFragment();
        Fragment secondFragment = new SecondIntroFragment();
        Fragment thirdFragment = new ThirdIntroFragment();
        Fragment fourthFragment = new FourthIntroFragment();
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.

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
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
