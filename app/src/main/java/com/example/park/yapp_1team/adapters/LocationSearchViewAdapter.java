package com.example.park.yapp_1team.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.items.SearchListItem;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

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

    private Realm realm;
    private PlaceAutocompleteFragment autocompleteFragment;

    public LocationSearchViewAdapter(Context context, RealmResults<SearchListItem> realmResults, boolean automaticUpdate, boolean animate, PlaceAutocompleteFragment autocompleteFragment)
    {
        super(context, realmResults, automaticUpdate, animate);

        this.autocompleteFragment = autocompleteFragment;

        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();
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

        viewHolder.searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocompleteFragment.setText(viewHolder.searchText.getText());
            }
        });

    }

    public class ViewHolder extends RealmViewHolder {

        private TextView searchText;
        private FrameLayout removeImg;

        public ViewHolder(View itemView){
            super(itemView);
            this.searchText = (TextView) itemView.findViewById(R.id.search_location_item);
            this.removeImg = (FrameLayout) itemView.findViewById(R.id.search_location_remove);
        }

    }
}
