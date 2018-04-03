package com.icode.jiling.na517demo_mvvm.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jiling on 2018/4/3.
 */

public class RecViewPager extends ViewPager {

    private int size;

    private float x1,x2,y1,y2;

    public RecViewPager(Context context) {
        super(context);
    }

    public RecViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public  void setPagerSize(int pagerSize){
        size = pagerSize;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            x2 = event.getX();
            y2 = event.getY();
            if(x1 - x2 > 10 && getCurrentItem() == 0) {//左滑
                return true;
            }
            if(x2 - x1 > 10 && getCurrentItem() == size-1) {//右滑
                return true;
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
