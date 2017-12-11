package com.nanshanstudio.customview.moveevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by NANSHAN on 3/8/17.
 */

public class CLinearLayout extends LinearLayout {

    public CLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        //通过实验发现ViewGroup :0 MotionEvent :263
        int viewgroupX = (int)getX();
        int motionEventX = (int)event.getX();
        System.out.println("ViewGroup :" + viewgroupX + "MotionEvent :" + motionEventX);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CLinearLayout_dispatchTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CLinearLayout_dispatchTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CLinearLayout_dispatchTouchEvent_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CLinearLayout_onInterceptTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CLinearLayout_onInterceptTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CLinearLayout_onInterceptTouchEvent_UP");
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CLinearLayout_onTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CLinearLayout_onTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CLinearLayout_onTouchEvent_UP");
                break;
            default:
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }
}
