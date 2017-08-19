package com.example.park.yapp_1team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class RealActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RealRecyclerviewAdapter realRecyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_main);
        realRecyclerviewAdapter = new RealRecyclerviewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        realRecyclerviewAdapter.add(new RealitemData("aaaa","bbb"));
        realRecyclerviewAdapter.add(new RealitemData("cccc","eee"));
        realRecyclerviewAdapter.add(new RealitemData("gggg","fff"));
        realRecyclerviewAdapter.add(new RealitemData("hhhh","iii"));
        realRecyclerviewAdapter.add(new RealitemData("aaaa","bbb"));
        realRecyclerviewAdapter.add(new RealitemData("cccc","eee"));
        realRecyclerviewAdapter.add(new RealitemData("gggg","fff"));
        realRecyclerviewAdapter.add(new RealitemData("hhhh","iii"));
        realRecyclerviewAdapter.add(new RealitemData("aaaa","bbb"));
        realRecyclerviewAdapter.add(new RealitemData("cccc","eee"));
        realRecyclerviewAdapter.add(new RealitemData("gggg","fff"));
        realRecyclerviewAdapter.add(new RealitemData("hhhh","iii"));

        recyclerView.setAdapter(realRecyclerviewAdapter);

    }

}
