package com.jiayuqicz.mygaode.main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wzq on 2017/8/22.
 */

public class MyViewPager extends ViewPager {


    private boolean scroll = true;


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return scroll && super.onInterceptTouchEvent(ev);
    }

    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

}
