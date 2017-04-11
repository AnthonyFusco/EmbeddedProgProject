package com.project.unice.embeddedprogproject.fragments;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Mark the end of the Fragment view.
 */
public class ViewPagerBlocker extends ViewPager {

    public ViewPagerBlocker(Context context, AttributeSet attrs) {
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
