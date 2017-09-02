package com.example.park.yapp_1team.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.park.yapp_1team.R;

/**
 * Created by HunJin on 2017-09-02.
 */

public class SelectMovieHolder extends RecyclerView.ViewHolder {

    public ImageView imgThumbnail;
    public TextView txtStartTime;
    public TextView txtEndTime;
    public TextView txtMovieTitle;
    public TextView txtLeftSeat;
    public TextView txtUseSeat;
    public TextView txtLocation;

    public SelectMovieHolder(View itemView) {
        super(itemView);
        imgThumbnail = (ImageView) itemView.findViewById(R.id.img_item_movie_info_thumbnail);
        txtStartTime = (TextView) itemView.findViewById(R.id.txt_item_movie_info_start_time);
        txtEndTime = (TextView) itemView.findViewById(R.id.txt_item_movie_info_end_time);
        txtMovieTitle = (TextView) itemView.findViewById(R.id.txt_item_movie_info_title);
        txtLeftSeat = (TextView) itemView.findViewById(R.id.txt_item_movie_info_remainder_seat);
        txtUseSeat = (TextView) itemView.findViewById(R.id.txt_item_movie_info_using_seat);
        txtLocation = (TextView) itemView.findViewById(R.id.txt_item_movie_info_location);
    }
}
