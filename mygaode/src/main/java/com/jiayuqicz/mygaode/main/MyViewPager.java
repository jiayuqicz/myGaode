package com.jiayuqicz.mygaode.main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wzq on 2017/8/22.
 */

public class MyViewPager extends ViewPager {


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //当前页面为地图页面的时候，关闭滑动，防止误触
        if(this.getCurrentItem() == 0)
            return false;

        return super.onInterceptTouchEvent(ev);
    }


}
