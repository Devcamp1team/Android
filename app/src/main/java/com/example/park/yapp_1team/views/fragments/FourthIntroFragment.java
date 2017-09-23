package com.example.park.yapp_1team.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.interfaces.FragmentButtonClick;
import com.example.park.yapp_1team.views.MainActivity;

/**
 * Created by Park on 2017-09-24.
 */

public class FourthIntroFragment extends Fragment  {

    Button myButton;
    FragmentButtonClick click;

    public void setClick(FragmentButtonClick click) {
        this.click = click;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_onboard4, container, false);
        return myView;
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
