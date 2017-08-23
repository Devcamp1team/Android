package com.example.park.yapp_1team.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.items.RealItemData;

import java.util.ArrayList;

/**
 * Created by TaeYoung on 2017-08-19.
 */

public class RealRecyclerviewAdapter extends RecyclerView.Adapter<RealRecyclerviewAdapter.ViewHolder> {

    ArrayList<RealItemData> data_array = new ArrayList<>();


    public void add(RealItemData realItemData) {
        data_array.add(realItemData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1_recyclerview1);
            text2 = (TextView) itemView.findViewById(R.id.text2_recyclerview1);
        }
    }

    @Override
    public RealRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_realmain, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RealRecyclerviewAdapter.ViewHolder holder, int position) {
        RealItemData realItemData = data_array.get(position);
        holder.text1.setText(realItemData.getText1());
        holder.text2.setText(realItemData.getText2());

    }

    @Override
    public int getItemCount() {
        return data_array.size();
    }
}
