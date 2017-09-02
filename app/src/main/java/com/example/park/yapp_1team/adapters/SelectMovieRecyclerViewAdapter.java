package com.example.park.yapp_1team.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.holder.SelectMovieHolder;
import com.example.park.yapp_1team.items.SelectMovieInfoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2017-09-02.
 */

public class SelectMovieRecyclerViewAdapter extends RecyclerView.Adapter<SelectMovieHolder> {

    private Context context;
    private final int HEAD = 0;
    private List<SelectMovieInfoItem> listItems = new ArrayList<>();
    private boolean isMulti = true;

    public SelectMovieRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public SelectMovieRecyclerViewAdapter(Context context, boolean isMulti) {
        this.context = context;
        this.isMulti = isMulti;
    }

    public void setListItems(List<SelectMovieInfoItem> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    @Override
    public SelectMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == HEAD && isMulti == false) {
            v = LayoutInflater.from(context).inflate(R.layout.itemview_main, parent, false);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.item_rcv_movie_info, parent, false);
        }
        return new SelectMovieHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectMovieHolder holder, int position) {
        if (isMulti) {
            holder.imgThumbnail.setVisibility(View.VISIBLE);
            Glide.with(context).load(listItems.get(position).getImgThumbnail()).into(holder.imgThumbnail);
        } else {
            holder.imgThumbnail.setVisibility(View.GONE);
        }
        holder.txtMovieTitle.setText(listItems.get(position).getTitle());
        holder.txtStartTime.setText(listItems.get(position).getStartTime());
        holder.txtEndTime.setText(" ~ " + listItems.get(position).getEndTIme());
        holder.txtUseSeat.setText(listItems.get(position).getUseSeat());
        holder.txtLeftSeat.setText(listItems.get(position).getLeftSeat());
        holder.txtLocation.setText(listItems.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
