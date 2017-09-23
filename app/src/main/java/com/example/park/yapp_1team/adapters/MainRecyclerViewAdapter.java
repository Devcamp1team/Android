package com.example.park.yapp_1team.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.interfaces.CheckEvent;
import com.example.park.yapp_1team.items.MovieListItem;
import com.example.park.yapp_1team.views.MainActivity;

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
        ImageView imgLine;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_item_main_movie_thumbnail);
            imgLine = (ImageView) itemView.findViewById(R.id.img_item_main_movie_line);
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

        holder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEvent.check(position, holder.imgThumbnail, holder.imgLine);

            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
