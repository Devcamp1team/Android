package com.yapp.no_11.yapp_1team.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yapp.no_11.yapp_1team.R;
import com.yapp.no_11.yapp_1team.interfaces.CheckEvent;
import com.yapp.no_11.yapp_1team.items.MovieListItem;
import com.yapp.no_11.yapp_1team.utils.CircularTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2017-08-22.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MainRecyclerViewAdapter.class.getSimpleName();

    private List<MovieListItem> datas = new ArrayList<>();
    private List<String> movieName = new ArrayList<>();

    private int currentOrder = 0;

    private Context mContext;

    private CheckEvent checkEvent;

    public int getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }

    public MainRecyclerViewAdapter(Context context, CheckEvent checkEvent) {
        this.mContext = context;
        this.checkEvent = checkEvent;
    }

    public List<MovieListItem> getDatas() {
        return datas;
    }

    public void addList(List<MovieListItem> items) {
        datas = items;
    }

    public void add(MovieListItem MovieListItem) {
        datas.add(MovieListItem);
    }

    public List<String> get() {
        return movieName;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        RelativeLayout imgLine;
        CircularTextView txtRanking;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_item_main_movie_thumbnail);
            imgLine = (RelativeLayout) itemView.findViewById(R.id.layout_item_main_movie_line);
            txtRanking = (CircularTextView) itemView.findViewById(R.id.txt_item_main_movie_ranking);
        }
    }

    public void clear() {
        datas.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) holder.itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int deviceWidth = displayMetrics.widthPixels;
        deviceWidth = deviceWidth / 2;
        int deviceHeight = (int) (deviceWidth * 1.5);

        holder.itemView.getLayoutParams().height = deviceHeight;

        holder.itemView.requestLayout();

        final MovieListItem MovieListItem = datas.get(position);

        if (MovieListItem.getURL().isEmpty()) {
            Glide.with(mContext).load(R.drawable.ic_panorama_black_24dp).into(holder.imgThumbnail);
        } else {
            Glide.with(mContext).load(MovieListItem.getURL()).into(holder.imgThumbnail);
        }

        // maybe save status
        if (MovieListItem.getCheck() == 1) {
            holder.imgLine.setVisibility(View.VISIBLE);
        } else {
            holder.imgLine.setVisibility(View.INVISIBLE);
        }

        holder.imgThumbnail.setOnClickListener(v -> checkEvent.check(position, holder.imgThumbnail, holder.imgLine));

        holder.txtRanking.setText((position + 1) + "");
        holder.txtRanking.setSolidColor("#FF4081");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
