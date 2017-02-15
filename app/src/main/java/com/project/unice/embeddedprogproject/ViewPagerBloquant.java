package com.project.unice.embeddedprogproject;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class ViewPagerBloquant extends ViewPager {

    public ViewPagerBloquant(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        return false;
    }
}
