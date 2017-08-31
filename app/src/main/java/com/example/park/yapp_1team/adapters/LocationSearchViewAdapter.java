package com.example.park.yapp_1team.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.items.SearchListItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by Park on 2017-08-30.
 */

public class LocationSearchViewAdapter
        extends RealmBasedRecyclerViewAdapter<SearchListItem, LocationSearchViewAdapter.ViewHolder> {

    private List<SearchListItem> locationList;
    private Realm realm;

    public LocationSearchViewAdapter(Context context, RealmResults<SearchListItem> realmResults, boolean automaticUpdate, boolean animate)
    {
        super(context, realmResults, automaticUpdate, animate);

        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();
        RealmResults<SearchListItem> searchList = getUserList();
    }

    private RealmResults<SearchListItem> getUserList(){
        return realm.where(SearchListItem.class).findAll();
    }


    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.itemview_location_search, viewGroup, false);
        ViewHolder vh = new ViewHolder((LinearLayout) v);

        return vh;
    }

    @Override
    public void onBindRealmViewHolder(final ViewHolder viewHolder, int position) {
        final SearchListItem searchItem = realmResults.get(position);
        viewHolder.searchText.setText(searchItem.getLocation());

        viewHolder.removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();

                String location = viewHolder.searchText.getText().toString();

                RealmResults<SearchListItem> tmpItem = realm.where(SearchListItem.class).equalTo("location", location).findAll();
                tmpItem.deleteAllFromRealm();

                realm.commitTransaction();
            }
        });

    }

    public class ViewHolder extends RealmViewHolder {

        private ImageView searchImg;
        private TextView searchText;
        private FrameLayout removeImg;

        public ViewHolder(View itemView){
            super(itemView);
            this.searchImg = (ImageView) itemView.findViewById(R.id.search_location_icon);
            this.searchText = (TextView) itemView.findViewById(R.id.search_location_item);
            this.removeImg = (FrameLayout) itemView.findViewById(R.id.search_location_remove);
        }

    }
}
