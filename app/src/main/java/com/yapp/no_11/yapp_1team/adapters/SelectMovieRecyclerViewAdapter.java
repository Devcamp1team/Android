package com.yapp.no_11.yapp_1team.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yapp.no_11.yapp_1team.R;
import com.yapp.no_11.yapp_1team.holder.SelectMovieHolder;
import com.yapp.no_11.yapp_1team.interfaces.RcvClickListener;
import com.yapp.no_11.yapp_1team.items.SelectMovieInfoItem;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by HunJin on 2017-09-02.
 */

public class SelectMovieRecyclerViewAdapter extends RecyclerView.Adapter<SelectMovieHolder> {

    private Context context;
    private final int HEAD = 0;
    private final int DEFAULT = 1;

    private List<SelectMovieInfoItem> listItems = new ArrayList<>();

    private boolean isMulti = true;

    private static final String TAG = SelectMovieRecyclerViewAdapter.class.getSimpleName();

    private RcvClickListener rcvClickListener;

    public void setRcvClickListener(RcvClickListener rcvClickListener) {
        this.rcvClickListener = rcvClickListener;
    }

    public List<SelectMovieInfoItem> getListItems() {
        return listItems;
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
            v = LayoutInflater.from(context).inflate(R.layout.item_rcv_movie_full_thumbnail, parent, false);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.item_rcv_movie_info, parent, false);
        }

        return new SelectMovieHolder(v);
    }


    @Override
    public void onBindViewHolder(SelectMovieHolder holder, final int position) {
        if (getItemViewType(position) == HEAD) {

            RequestOptions requestOptions = new RequestOptions();
            Glide.with(context)
                    .load(listItems.get(position).getImgThumbnail())
                    .apply(requestOptions.centerCrop().transform(new BlurTransformation(50)))
                    .into(holder.imgFullThumbnail);
            Glide.with(context)
                    .load(listItems.get(position).getImgThumbnail())
                    .into(holder.imgSmallThumbnail);
            holder.txtFullTitle.setText(listItems.get(position).getTitle());
        } else {
            if (isMulti) {
                holder.imgThumbnail.setVisibility(View.VISIBLE);
                Glide.with(context).load(listItems.get(position).getImgThumbnail()).into(holder.imgThumbnail);
                holder.txtMovieTitle.setText(listItems.get(position).getTitle());
            } else {
                holder.imgThumbnail.setVisibility(View.GONE);
                holder.txtMovieTitle.setVisibility(View.GONE);
            }

            holder.txtStartTime.setText(listItems.get(position).getStartTime());
            holder.txtEndTime.setText(listItems.get(position).getEndTIme());
            holder.txtUseSeat.setText(listItems.get(position).getUseSeat());
            holder.txtLeftSeat.setText(listItems.get(position).getLeftSeat());
            holder.txtLocation.setText(listItems.get(position).getLocation());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rcvClickListener.itemClick(position, listItems);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!isMulti && position == HEAD) {
            return HEAD;
        } else {
            return DEFAULT;
        }
    }
}
