package com.yapp.no_11.yapp_1team.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yapp.no_11.yapp_1team.R;

/**
 * Created by Park on 2017-09-24.
 */

public class ThirdIntroFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_onboard3, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.img_onboard_3);
        Glide.with(v).load(R.drawable.onboarding_3).into(imageView);
        return v;
    }
}
