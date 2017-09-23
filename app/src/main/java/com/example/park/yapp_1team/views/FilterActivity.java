package com.example.park.yapp_1team.views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.park.yapp_1team.R;

public class FilterActivity extends AppCompatActivity {


    TextView textView2relative3,textView3relative3,textView4relative3;
    TextView textView2relative4,textView3relative4,textView4relative4,textView5relative4;
    TextView textView2relative5,textView3relative5,textView4relative5,textView5relative5;
    TextView textView2relative6,textView3relative6,textView4relative6,textView5relative6,textView6relative6,textView7relative6,textView8relative6;
    static int checked[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        textView2relative3 = (TextView)findViewById(R.id.text2_relative3_filter);
        textView3relative3 = (TextView)findViewById(R.id.text3_relative3_filter);
        textView4relative3 = (TextView)findViewById(R.id.text4_relative3_filter);

        textView2relative4 = (TextView)findViewById(R.id.text2_relative4_filter);
        textView3relative4 = (TextView)findViewById(R.id.text3_relative4_filter);
        textView4relative4 = (TextView)findViewById(R.id.text4_relative4_filter);
        textView5relative4 = (TextView)findViewById(R.id.text5_relative4_filter);

        textView2relative5 = (TextView)findViewById(R.id.text2_relative5_filter);
        textView3relative5 = (TextView)findViewById(R.id.text3_relative5_filter);
        textView4relative5 = (TextView)findViewById(R.id.text4_relative5_filter);
        textView5relative5 = (TextView)findViewById(R.id.text5_relative5_filter);

        textView2relative6 = (TextView)findViewById(R.id.text2_relative6_filter);
        textView3relative6 = (TextView)findViewById(R.id.text3_relative6_filter);
        textView4relative6 = (TextView)findViewById(R.id.text4_relative6_filter);
        textView5relative6 = (TextView)findViewById(R.id.text5_relative6_filter);
        textView6relative6 = (TextView)findViewById(R.id.text6_relative6_filter);
        textView7relative6 = (TextView)findViewById(R.id.text7_relative6_filter);
        textView8relative6 = (TextView)findViewById(R.id.text8_relative6_filter);


        checked = new int[20];
        for(int i=0;i<20;i++)
        {
            checked[i]=0;
        }

        final String white = "#ffffff";
        final String gray = "#6a7482";
        final String pink = "#ff007e";


        textView2relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[0] == 0)
                {
                    textView2relative3.setBackgroundColor(Color.parseColor(pink));
                    textView2relative3.setTextColor(Color.parseColor(white));
                    checked[0] ++;
                }
                else
                {
                    textView2relative3.setBackgroundColor(Color.parseColor(white));
                    textView2relative3.setTextColor(Color.parseColor(gray));
                    checked[0] --;
                }
            }
        });
        textView3relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[1] == 0)
                {
                    textView3relative3.setBackgroundColor(Color.parseColor(pink));
                    textView3relative3.setTextColor(Color.parseColor(white));
                    checked[1] ++;
                }
                else
                {
                    textView3relative3.setBackgroundColor(Color.parseColor(white));
                    textView3relative3.setTextColor(Color.parseColor(gray));
                    checked[1] --;
                }
            }
        });
        textView4relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[2] == 0)
                {
                    textView4relative3.setBackgroundColor(Color.parseColor(pink));
                    textView4relative3.setTextColor(Color.parseColor(white));
                    checked[2] ++;
                }
                else
                {
                    textView4relative3.setBackgroundColor(Color.parseColor(white));
                    textView4relative3.setTextColor(Color.parseColor(gray));
                    checked[2] --;
                }
            }
        });

        textView2relative4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[3] == 0)
                {
                    textView2relative4.setBackgroundColor(Color.parseColor(pink));
                    textView2relative4.setTextColor(Color.parseColor(white));
                    checked[3] ++;
                }
                else
                {
                    textView2relative4.setBackgroundColor(Color.parseColor(white));
                    textView2relative4.setTextColor(Color.parseColor(gray));
                    checked[3] --;
                }
            }
        });
        textView3relative4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[4] == 0)
                {
                    textView3relative4.setBackgroundColor(Color.parseColor(pink));
                    textView3relative4.setTextColor(Color.parseColor(white));
                    checked[4] ++;
                }
                else
                {
                    textView3relative4.setBackgroundColor(Color.parseColor(white));
                    textView3relative4.setTextColor(Color.parseColor(gray));
                    checked[4] --;
                }
            }
        });
        textView4relative4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[5] == 0)
                {
                    textView4relative4.setBackgroundColor(Color.parseColor(pink));
                    textView4relative4.setTextColor(Color.parseColor(white));
                    checked[5] ++;
                }
                else
                {
                    textView4relative4.setBackgroundColor(Color.parseColor(white));
                    textView4relative4.setTextColor(Color.parseColor(gray));
                    checked[5] --;
                }
            }
        });
        textView5relative4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[6] == 0)
                {
                    textView5relative4.setBackgroundColor(Color.parseColor(pink));
                    textView5relative4.setTextColor(Color.parseColor(white));
                    checked[6] ++;
                }
                else
                {
                    textView5relative4.setBackgroundColor(Color.parseColor(white));
                    textView5relative4.setTextColor(Color.parseColor(gray));
                    checked[6] --;
                }
            }
        });

        textView2relative5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[7] == 0)
                {
                    textView2relative5.setBackgroundColor(Color.parseColor(pink));
                    textView2relative5.setTextColor(Color.parseColor(white));
                    checked[7] ++;
                }
                else
                {
                    textView2relative5.setBackgroundColor(Color.parseColor(white));
                    textView2relative5.setTextColor(Color.parseColor(gray));
                    checked[7] --;
                }
            }
        });
        textView3relative5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[8] == 0)
                {
                    textView3relative5.setBackgroundColor(Color.parseColor(pink));
                    textView3relative5.setTextColor(Color.parseColor(white));
                    checked[8] ++;
                }
                else
                {
                    textView3relative5.setBackgroundColor(Color.parseColor(white));
                    textView3relative5.setTextColor(Color.parseColor(gray));
                    checked[8] --;
                }
            }
        });
        textView4relative5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[9] == 0)
                {
                    textView4relative5.setBackgroundColor(Color.parseColor(pink));
                    textView4relative5.setTextColor(Color.parseColor(white));
                    checked[9] ++;
                }
                else
                {
                    textView4relative5.setBackgroundColor(Color.parseColor(white));
                    textView4relative5.setTextColor(Color.parseColor(gray));
                    checked[9] --;
                }
            }
        });
        textView5relative5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[10] == 0)
                {
                    textView5relative5.setBackgroundColor(Color.parseColor(pink));
                    textView5relative5.setTextColor(Color.parseColor(white));
                    checked[10] ++;
                }
                else
                {
                    textView5relative5.setBackgroundColor(Color.parseColor(white));
                    textView5relative5.setTextColor(Color.parseColor(gray));
                    checked[10] --;
                }
            }
        });

        textView2relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[11] == 0)
                {
                    textView2relative6.setBackgroundColor(Color.parseColor(pink));
                    textView2relative6.setTextColor(Color.parseColor(white));
                    checked[11] ++;
                    textView3relative6.setBackgroundColor(Color.parseColor(white));
                    textView3relative6.setTextColor(Color.parseColor(gray));
                    textView4relative6.setBackgroundColor(Color.parseColor(white));
                    textView4relative6.setTextColor(Color.parseColor(gray));
                    textView5relative6.setBackgroundColor(Color.parseColor(white));
                    textView5relative6.setTextColor(Color.parseColor(gray));
                    textView6relative6.setBackgroundColor(Color.parseColor(white));
                    textView6relative6.setTextColor(Color.parseColor(gray));
                    textView7relative6.setBackgroundColor(Color.parseColor(white));
                    textView7relative6.setTextColor(Color.parseColor(gray));
                    textView8relative6.setBackgroundColor(Color.parseColor(white));
                    textView8relative6.setTextColor(Color.parseColor(gray));

                }
                else
                {
                    textView2relative6.setBackgroundColor(Color.parseColor(white));
                    textView2relative6.setTextColor(Color.parseColor(gray));
                    checked[11] --;
                }
            }
        });
        textView3relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[12] == 0)
                {
                    textView3relative6.setBackgroundColor(Color.parseColor(pink));
                    textView3relative6.setTextColor(Color.parseColor(white));
                    checked[12] ++;
                    textView2relative6.setBackgroundColor(Color.parseColor(white));
                    textView2relative6.setTextColor(Color.parseColor(gray));
                    textView4relative6.setBackgroundColor(Color.parseColor(white));
                    textView4relative6.setTextColor(Color.parseColor(gray));
                    textView5relative6.setBackgroundColor(Color.parseColor(white));
                    textView5relative6.setTextColor(Color.parseColor(gray));
                    textView6relative6.setBackgroundColor(Color.parseColor(white));
                    textView6relative6.setTextColor(Color.parseColor(gray));
                    textView7relative6.setBackgroundColor(Color.parseColor(white));
                    textView7relative6.setTextColor(Color.parseColor(gray));
                    textView8relative6.setBackgroundColor(Color.parseColor(white));
                    textView8relative6.setTextColor(Color.parseColor(gray));

                }
                else
                {
                    textView3relative6.setBackgroundColor(Color.parseColor(white));
                    textView3relative6.setTextColor(Color.parseColor(gray));
                    checked[12] --;
                }
            }
        });
        textView4relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[13] == 0)
                {
                    textView4relative6.setBackgroundColor(Color.parseColor(pink));
                    textView4relative6.setTextColor(Color.parseColor(white));
                    checked[13] ++;
                    textView3relative6.setBackgroundColor(Color.parseColor(white));
                    textView3relative6.setTextColor(Color.parseColor(gray));
                    textView2relative6.setBackgroundColor(Color.parseColor(white));
                    textView2relative6.setTextColor(Color.parseColor(gray));
                    textView5relative6.setBackgroundColor(Color.parseColor(white));
                    textView5relative6.setTextColor(Color.parseColor(gray));
                    textView6relative6.setBackgroundColor(Color.parseColor(white));
                    textView6relative6.setTextColor(Color.parseColor(gray));
                    textView7relative6.setBackgroundColor(Color.parseColor(white));
                    textView7relative6.setTextColor(Color.parseColor(gray));
                    textView8relative6.setBackgroundColor(Color.parseColor(white));
                    textView8relative6.setTextColor(Color.parseColor(gray));

                }
                else
                {
                    textView4relative6.setBackgroundColor(Color.parseColor(white));
                    textView4relative6.setTextColor(Color.parseColor(gray));
                    checked[13] --;
                }
            }
        });
        textView5relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[14] == 0)
                {
                    textView5relative6.setBackgroundColor(Color.parseColor(pink));
                    textView5relative6.setTextColor(Color.parseColor(white));
                    checked[14] ++;
                    textView3relative6.setBackgroundColor(Color.parseColor(white));
                    textView3relative6.setTextColor(Color.parseColor(gray));
                    textView4relative6.setBackgroundColor(Color.parseColor(white));
                    textView4relative6.setTextColor(Color.parseColor(gray));
                    textView2relative6.setBackgroundColor(Color.parseColor(white));
                    textView2relative6.setTextColor(Color.parseColor(gray));
                    textView6relative6.setBackgroundColor(Color.parseColor(white));
                    textView6relative6.setTextColor(Color.parseColor(gray));
                    textView7relative6.setBackgroundColor(Color.parseColor(white));
                    textView7relative6.setTextColor(Color.parseColor(gray));
                    textView8relative6.setBackgroundColor(Color.parseColor(white));
                    textView8relative6.setTextColor(Color.parseColor(gray));

                }
                else
                {
                    textView5relative6.setBackgroundColor(Color.parseColor(white));
                    textView5relative6.setTextColor(Color.parseColor(gray));
                    checked[14] --;
                }
            }
        });
        textView6relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[15] == 0)
                {
                    textView6relative6.setBackgroundColor(Color.parseColor(pink));
                    textView6relative6.setTextColor(Color.parseColor(white));
                    checked[15] ++;
                    textView3relative6.setBackgroundColor(Color.parseColor(white));
                    textView3relative6.setTextColor(Color.parseColor(gray));
                    textView4relative6.setBackgroundColor(Color.parseColor(white));
                    textView4relative6.setTextColor(Color.parseColor(gray));
                    textView5relative6.setBackgroundColor(Color.parseColor(white));
                    textView5relative6.setTextColor(Color.parseColor(gray));
                    textView2relative6.setBackgroundColor(Color.parseColor(white));
                    textView2relative6.setTextColor(Color.parseColor(gray));
                    textView7relative6.setBackgroundColor(Color.parseColor(white));
                    textView7relative6.setTextColor(Color.parseColor(gray));
                    textView8relative6.setBackgroundColor(Color.parseColor(white));
                    textView8relative6.setTextColor(Color.parseColor(gray));

                }
                else
                {
                    textView6relative6.setBackgroundColor(Color.parseColor(white));
                    textView6relative6.setTextColor(Color.parseColor(gray));
                    checked[15] --;
                }
            }
        });
        textView7relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[16] == 0)
                {
                    textView7relative6.setBackgroundColor(Color.parseColor(pink));
                    textView7relative6.setTextColor(Color.parseColor(white));
                    checked[16] ++;
                    textView3relative6.setBackgroundColor(Color.parseColor(white));
                    textView3relative6.setTextColor(Color.parseColor(gray));
                    textView4relative6.setBackgroundColor(Color.parseColor(white));
                    textView4relative6.setTextColor(Color.parseColor(gray));
                    textView5relative6.setBackgroundColor(Color.parseColor(white));
                    textView5relative6.setTextColor(Color.parseColor(gray));
                    textView6relative6.setBackgroundColor(Color.parseColor(white));
                    textView6relative6.setTextColor(Color.parseColor(gray));
                    textView2relative6.setBackgroundColor(Color.parseColor(white));
                    textView2relative6.setTextColor(Color.parseColor(gray));
                    textView8relative6.setBackgroundColor(Color.parseColor(white));
                    textView8relative6.setTextColor(Color.parseColor(gray));

                }
                else
                {
                    textView7relative6.setBackgroundColor(Color.parseColor(white));
                    textView7relative6.setTextColor(Color.parseColor(gray));
                    checked[16] --;
                }
            }
        });
        textView8relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked[17] == 0)
                {
                    textView8relative6.setBackgroundColor(Color.parseColor(pink));
                    textView8relative6.setTextColor(Color.parseColor(white));
                    checked[17] ++;
                    textView3relative6.setBackgroundColor(Color.parseColor(white));
                    textView3relative6.setTextColor(Color.parseColor(gray));
                    textView4relative6.setBackgroundColor(Color.parseColor(white));
                    textView4relative6.setTextColor(Color.parseColor(gray));
                    textView5relative6.setBackgroundColor(Color.parseColor(white));
                    textView5relative6.setTextColor(Color.parseColor(gray));
                    textView6relative6.setBackgroundColor(Color.parseColor(white));
                    textView6relative6.setTextColor(Color.parseColor(gray));
                    textView7relative6.setBackgroundColor(Color.parseColor(white));
                    textView7relative6.setTextColor(Color.parseColor(gray));
                    textView2relative6.setBackgroundColor(Color.parseColor(white));
                    textView2relative6.setTextColor(Color.parseColor(gray));

                }
                else
                {
                    textView8relative6.setBackgroundColor(Color.parseColor(white));
                    textView8relative6.setTextColor(Color.parseColor(gray));
                    checked[17] --;
                }
            }
        });


    }



}
