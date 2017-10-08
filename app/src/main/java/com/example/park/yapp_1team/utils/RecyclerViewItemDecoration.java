package com.example.park.yapp_1team.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by HunJin on 2017-10-08.
 */

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private int size;

    public RecyclerViewItemDecoration(int size) {
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, view.getResources().getDisplayMetrics());
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.set(0, 0, value, 0);
        } else {
            outRect.set(value, 0, 0, 0);
        }
    }
}
