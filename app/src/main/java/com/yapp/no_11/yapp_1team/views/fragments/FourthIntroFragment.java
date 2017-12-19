package com.yapp.no_11.yapp_1team.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yapp.no_11.yapp_1team.R;
import com.yapp.no_11.yapp_1team.interfaces.FragmentButtonClick;

/**
 * Created by Park on 2017-09-24.
 */

public class FourthIntroFragment extends Fragment {

    Button myButton;
    FragmentButtonClick click;

    public void setClick(FragmentButtonClick click) {
        this.click = click;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_onboard4, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.img_onboard_4);
        Glide.with(v).load(R.drawable.onboarding_4).into(imageView);
        return v;
    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myButton = (Button) view.findViewById(R.id.onboard_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.click();
            }
        });
    }
}
