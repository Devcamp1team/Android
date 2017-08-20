package com.example.park.yapp_1team;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.park.yapp_1team.network.movie_info_crawling;
import com.example.park.yapp_1team.items.movieListItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.park.yapp_1team.utils.URL.NAVER_SELECT;
import static com.example.park.yapp_1team.utils.URL.NAVER_URL;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    MainRecyclerviewAdapter mainRecyclerviewAdapter;
    ImageView image1,image2;
    TextView textview1;
    static int s = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SplashActivity.class));

        ArrayList<movieListItem> data_array = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_start);
        image1 = (ImageView)findViewById(R.id.image1_underbar_start);
        image2 = (ImageView)findViewById(R.id.image2_underbar_start);
        textview1 = (TextView)findViewById(R.id.text1_underbar_start);

        movie_info_crawling test = new movie_info_crawling(NAVER_URL, NAVER_SELECT);

        try{
            data_array = (ArrayList<movieListItem>)test.execute().get();
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        }




        mainRecyclerviewAdapter = new MainRecyclerviewAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        for(int i=0;i<data_array.size();i++) {
            mainRecyclerviewAdapter.add(data_array.get(i));
        }


        recyclerView.setAdapter(mainRecyclerviewAdapter);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> string_array = new ArrayList<>();
                string_array = mainRecyclerviewAdapter.get();

                Intent intent = new Intent(getApplicationContext(),RealActivity.class);
                intent.putStringArrayListExtra("STRING",string_array);
                startActivity(intent);
            }
        });

    }


    public class MainRecyclerviewAdapter extends RecyclerView.Adapter<MainRecyclerviewAdapter.ViewHolder>
    {

        ArrayList<movieListItem> datas = new ArrayList<>();
        ArrayList<String> movieName = new ArrayList<>();

        public void add(movieListItem movieListItem)
        {
            datas.add(movieListItem);
        }

        public ArrayList<String> get()
        {
            return movieName;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
                ImageView imageView, imageView2;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.image1_recyclerview2);
                imageView2 =(ImageView)itemView.findViewById(R.id.image2_recyclerview2);
            }
        }

        @Override
        public MainRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_main,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MainRecyclerviewAdapter.ViewHolder holder, int position) {
            final movieListItem movieListItem = datas.get(position);


            Glide.with(getApplicationContext()).load(movieListItem.getURL()).into(holder.imageView);

            try {
                if(movieListItem.getCheck()==1)
                {
                    holder.imageView.setColorFilter(Color.parseColor("#99000000"));
                    holder.imageView2.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.imageView.setColorFilter(Color.parseColor("#00000000"));
                    holder.imageView2.setVisibility(View.INVISIBLE);
                }

            }
            catch (Exception e)
            {
                Log.e("aaaaaaaaaa",""+e);
            }
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if(movieListItem.getCheck()== 0) {                                  //check 안되어있으면

                       holder.imageView.setColorFilter(Color.parseColor("#99000000"));
                       movieListItem.setCheck(1);
                       movieName.add(movieListItem.getName());
                       Log.e("aaaaaaaa",""+movieName);

                       holder.imageView2.setVisibility(View.VISIBLE);
                       //textview1.setText(movieName.size());
                    }
                    else if(movieListItem.getCheck()== 1)
                    {
                        holder.imageView.setColorFilter(Color.parseColor("#00000000"));
                        movieListItem.setCheck(0);
                        movieName.remove(movieListItem.getName());
                        Log.e("aaaaaaaa",""+movieName);

                        holder.imageView2.setVisibility(View.INVISIBLE);
                     //   textview1.setText(movieName.size());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }






}
