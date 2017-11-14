package com.example.park.yapp_1team.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.park.yapp_1team.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.park.yapp_1team.utils.PermissionRequestCode.CGV_THEATER_CODE;
import static com.example.park.yapp_1team.utils.PermissionRequestCode.FILTER_INTENT_RESULT_CODE;
import static com.example.park.yapp_1team.utils.PermissionRequestCode.LOTTE_THEATER_CODE;
import static com.example.park.yapp_1team.utils.PermissionRequestCode.MEGA_THEATER_CODE;

public class FilterActivity extends BaseActivity {


    private static final int LAYOUT_COUNT = 3;

    private RelativeLayout[] layouts = new RelativeLayout[LAYOUT_COUNT];
    private CheckBox[] checkBoxes = new CheckBox[LAYOUT_COUNT];
    private LinearLayout layoutSystemContents;

    private int[] layoutId = {
            R.id.layout_filter_cgv,
            R.id.layout_filter_mega,
            R.id.layout_filter_lotte};

    private int[] checkBoxId = {
            R.id.cb_filter_cgv,
            R.id.cb_filter_mega,
            R.id.cb_filter_lotte
    };

    private List<String> checkArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        checkArr = new ArrayList<>();

        initialize();

        statusBarChange();

        Intent it = getIntent();

        String value = it.getExtras().getString("type");
        System.out.println(value);

        String[] s = value.split("\\|");
        makeView(s);

        event();
    }

    private void makeView(String[] s) {
        for (int i = 0; i < s.length; i++) {
            int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
            RelativeLayout layout = new RelativeLayout(this);
            layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, layouts[0].getLayoutParams().height));

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

            TextView txtType = new TextView(this);
            txtType.setText(s[i]);
            txtType.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            txtType.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

            CheckBox cbAuditorium = new CheckBox(this);
            cbAuditorium.setLayoutParams(layoutParams2);


            layout.addView(txtType);
            layout.addView(cbAuditorium);

            int finalI = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cbAuditorium.isChecked()) {
                        cbAuditorium.setChecked(false);
                        checkArr.remove(s[finalI]);
                    } else {
                        cbAuditorium.setChecked(true);
                        checkArr.add(s[finalI]);
                    }
                }
            });

            layoutSystemContents.addView(layout);

            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp);
            View view = new View(this);
            view.setBackgroundColor(Color.BLACK);
            view.setLayoutParams(layoutParams3);
            layoutSystemContents.addView(view);

        }
    }

    private void initialize() {
        for (int i = 0; i < layouts.length; i++) {
            layouts[i] = (RelativeLayout) findViewById(layoutId[i]);
            checkBoxes[i] = (CheckBox) findViewById(checkBoxId[i]);
        }
        layoutSystemContents = (LinearLayout) findViewById(R.id.layout_filter_system_contents);
    }

    private void event() {
        for (int i = 0; i < layouts.length; i++) {
            final int finalI = i;
            layouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBoxes[finalI].isChecked()) {
                        checkBoxes[finalI].setChecked(false);
                    } else {
                        checkBoxes[finalI].setChecked(true);
                    }
                }
            });
        }
    }


    public void onClick(View view) {
        int cgv = checkBoxes[0].isChecked() ? 1 : 0;
        int mega = checkBoxes[1].isChecked() ? 1 : 0;
        int lotte = checkBoxes[2].isChecked() ? 1 : 0;
        String brand = "";
        if (cgv == 1) {
            brand += CGV_THEATER_CODE + "|";
        }
        if (mega == 1) {
            brand += MEGA_THEATER_CODE + "|";
        }
        if (lotte == 1) {
            brand += LOTTE_THEATER_CODE + "|";
        }

        if (brand.length() > 0) {
            brand = brand.substring(0, brand.length() - 1);
        }

        String option = "";
        for (String s : checkArr) {
            option += s + "|";
        }
        if (option.length() > 0) {
            option = option.substring(0, option.length() - 1);
        }

        Bundle bundle = new Bundle();
        bundle.putString("brand", brand);
        bundle.putString("option", option);
        setResult(FILTER_INTENT_RESULT_CODE, new Intent().putExtra("data", bundle));
        finish();
    }

    public void reset(View v) {
        String brand = "";
        String option = "";
        Bundle bundle = new Bundle();
        bundle.putString("brand",brand);
        bundle.putString("option",option);
        setResult(FILTER_INTENT_RESULT_CODE, new Intent().putExtra("data", bundle));
        finish();
    }

    public void close(View v) {
        finish();
    }

}
