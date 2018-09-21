package utilpacket.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Koma on 2017/6/21.
 * Description：
 * version 1.0 Content：
 */

public class NoScrollViewPager extends ViewPager {

    private boolean isScroll = true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets scroll. true 可以滑动  false 不可以滑动
     *
     * @param isScroll the is scroll
     */
    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,false);
    }
}