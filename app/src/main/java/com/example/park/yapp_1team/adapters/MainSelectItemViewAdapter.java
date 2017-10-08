package com.example.park.yapp_1team.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.holder.MainSelectItemHolder;
import com.example.park.yapp_1team.interfaces.SelectItemClikListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2017-10-08.
 */

public class MainSelectItemViewAdapter extends RecyclerView.Adapter<MainSelectItemHolder> {

    private List<String> selectMovieList = new ArrayList<>();
    private SelectItemClikListener clikListener;

    public SelectItemClikListener getClikListener() {
        return clikListener;
    }

    public void setClikListener(SelectItemClikListener clikListener) {
        this.clikListener = clikListener;
    }

    public List<String> getSelectMovieList() {
        return selectMovieList;
    }

    public void setSelectMovieList(List<String> selectMovieList) {
        this.selectMovieList = selectMovieList;
    }

    @Override
    public MainSelectItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_main_toolbar_select, parent, false);
        return new MainSelectItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final MainSelectItemHolder holder, final int position) {
        holder.textView.setText(selectMovieList.get(position).toString().substring(0, 1));
        holder.textView.setSolidColor("#FF4081");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikListener.click(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectMovieList.size();
    }
}
