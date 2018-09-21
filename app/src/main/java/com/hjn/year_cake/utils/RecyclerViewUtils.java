package com.hjn.year_cake.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by ${templefck} on 2018/9/17.
 */
public class RecyclerViewUtils {
    private static boolean move = false;

    public static void scrollToTop(RecyclerView recyclerView, final int p){

        final LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int                       fir     = manager.findFirstVisibleItemPosition();
        int                       end     = manager.findLastVisibleItemPosition();
        if (p <= fir) {
            recyclerView.scrollToPosition(p);
        } else if (p <= end) {
            int top = recyclerView.getChildAt(p - fir).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(p);    //先让当前view滚动到列表内
            move = true;
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (move) {
                    move = false;
                    int n = p - manager.findFirstVisibleItemPosition();
                    if (n >= 0 && n < recyclerView.getChildCount()) {
                        recyclerView.scrollBy(0, recyclerView.getChildAt(n).getTop()); //滚动到顶部
                    }
                }
            }
        });
    }
}
