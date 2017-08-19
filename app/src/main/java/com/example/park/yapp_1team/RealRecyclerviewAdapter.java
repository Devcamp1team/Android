package com.example.park.yapp_1team;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TaeYoung on 2017-08-19.
 */

public class RealRecyclerviewAdapter extends RecyclerView.Adapter<RealRecyclerviewAdapter.ViewHolder>
{

    ArrayList<RealitemData> data_array = new ArrayList<>();


    public void add(RealitemData realitemData)
    {
        data_array.add(realitemData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView text1,text2;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView)itemView.findViewById(R.id.text1_recyclerview1);
            text2 = (TextView)itemView.findViewById(R.id.text2_recyclerview1);
        }
    }

    @Override
    public RealRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_realmain,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RealRecyclerviewAdapter.ViewHolder holder, int position) {
        RealitemData realitemData = data_array.get(position);
        holder.text1.setText(realitemData.text1);
        holder.text2.setText(realitemData.text2);


    }

    @Override
    public int getItemCount() {
        return data_array.size();
    }
}
