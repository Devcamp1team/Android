package com.example.park.yapp_1team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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
        ArrayList<String> string_array = new ArrayList<>();

        Intent intent = getIntent();
        string_array = intent.getStringArrayListExtra("STRING");

        for(int i=0;i<string_array.size();i++)
        {
            String a = string_array.get(i);
            realRecyclerviewAdapter.add(new RealitemData(a,"aaa"));

        }

        recyclerView.setAdapter(realRecyclerviewAdapter);

    }

}
