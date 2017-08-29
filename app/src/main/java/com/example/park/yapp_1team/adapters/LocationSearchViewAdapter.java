package com.example.park.yapp_1team.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.items.SearchListItem;

import java.util.List;

/**
 * Created by Park on 2017-08-30.
 */

public class LocationSearchViewAdapter extends RecyclerView.Adapter<LocationSearchViewAdapter.ViewHolder>{

    private List<SearchListItem> locationList;

    public LocationSearchViewAdapter(List<SearchListItem> items){

        this.locationList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemview_location_search,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        SearchListItem item = locationList.get(position);
        viewHolder.textTitle.setText(item.getlocation());

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void delete(int position) {
        try {
            locationList.remove(position);
            notifyItemRemoved(position);
        } catch(IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textTitle;
        public ImageView removeButton;

        public ViewHolder(View itemView){
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.search_location_item);
            removeButton = (ImageView) itemView.findViewById(R.id.search_location_remove);
        }

    }
}
