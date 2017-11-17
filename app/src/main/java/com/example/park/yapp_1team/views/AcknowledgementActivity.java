package com.example.park.yapp_1team.views;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.park.yapp_1team.R;
import com.example.park.yapp_1team.adapters.AcknowledgementAdapter;
import com.example.park.yapp_1team.items.AcknowledgementItem;

import java.util.ArrayList;
import java.util.List;

public class AcknowledgementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledgement);
        statusBarChange();
        initialize();
    }

    private void initialize() {
        RecyclerView rcvMakerInfo = (RecyclerView) findViewById(R.id.rcv_acknowledge_maker_info);
        AcknowledgementAdapter adapter = new AcknowledgementAdapter(this);
        rcvMakerInfo.setHasFixedSize(true);
        rcvMakerInfo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvMakerInfo.setAdapter(adapter);

        List<AcknowledgementItem> items = new ArrayList<>();

        AcknowledgementItem acknowledgementItemMinji = new AcknowledgementItem(0, "고라니", "kinm1020@gmail.com", R.drawable.gorani);
        AcknowledgementItem acknowledgementItemTaeyoung = new AcknowledgementItem(1, "고래", "kazun1429@gmail.com", R.drawable.gorae);
        AcknowledgementItem acknowledgementItemYoungjun = new AcknowledgementItem(2, "고니", "lee99200473@gmail.com", R.drawable.gonea);
        AcknowledgementItem acknowledgementItemYuri = new AcknowledgementItem(3, "고양이", "2460yuri@gmail.com", R.drawable.goyang);
        AcknowledgementItem acknowledgementItemYungjun = new AcknowledgementItem(4, "고릴라", "dudwns4971@gmail.com", R.drawable.gorilla);
        AcknowledgementItem acknowledgementItemHunjin = new AcknowledgementItem(5, "고슴도치", "ysg01129@gmail.com", R.drawable.gosum);

        items.add(acknowledgementItemMinji);
        items.add(acknowledgementItemYuri);
        items.add(acknowledgementItemTaeyoung);
        items.add(acknowledgementItemYoungjun);
        items.add(acknowledgementItemYungjun);
        items.add(acknowledgementItemHunjin);

        adapter.setList(items);
        adapter.notifyDataSetChanged();
    }

    public void click(View v) {
        finish();
    }
}
