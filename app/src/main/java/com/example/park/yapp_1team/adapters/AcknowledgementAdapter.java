package com.example.park.yapp_1team.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.holder.AcknowledgementViewHolder;
import com.example.park.yapp_1team.items.AcknowledgementItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2017-10-18.
 */

public class AcknowledgementAdapter extends RecyclerView.Adapter<AcknowledgementViewHolder> {

    private Context context;
    private List<AcknowledgementItem> list = new ArrayList<>();

    public AcknowledgementAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AcknowledgementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acknowledge_maker_info, parent, false);
        return new AcknowledgementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AcknowledgementViewHolder holder, int position) {
        AcknowledgementItem item = list.get(position);
        holder.txtNickname.setText(item.getNickname());
        holder.txtEmail.setText(item.getEmail());
//        holder.imgThumbnail.setImageResource(item.getThumbnail());
        Glide.with(context).load(item.getThumbnail()).into(holder.imgThumbnail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<AcknowledgementItem> list) {
        this.list = list;
    }
}
