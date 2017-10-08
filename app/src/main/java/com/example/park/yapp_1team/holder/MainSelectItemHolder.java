package com.example.park.yapp_1team.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.utils.CircularTextView;

/**
 * Created by HunJin on 2017-10-08.
 */

public class MainSelectItemHolder extends RecyclerView.ViewHolder {

    public CircularTextView textView;

    public MainSelectItemHolder(View itemView) {
        super(itemView);
        textView = (CircularTextView) itemView.findViewById(R.id.txt_item_main_movie_select);
    }
}
