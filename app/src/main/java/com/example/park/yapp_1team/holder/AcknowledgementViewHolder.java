package com.example.park.yapp_1team.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.park.yapp_1team.R;

/**
 * Created by HunJin on 2017-10-18.
 */

public class AcknowledgementViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgThumbnail;
    public TextView txtNickname;
    public TextView txtEmail;

    public AcknowledgementViewHolder(View itemView) {
        super(itemView);

        imgThumbnail = (ImageView)itemView.findViewById(R.id.img_acknowledge_thumbnail);
        txtEmail = (TextView)itemView.findViewById(R.id.txt_acknowledge_email);
        txtNickname = (TextView)itemView.findViewById(R.id.txt_acknowledge_nick_name);

    }
}
