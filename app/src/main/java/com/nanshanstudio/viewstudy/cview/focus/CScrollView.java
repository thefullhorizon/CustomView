package com.nanshanstudio.viewstudy.cview.focus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by NANSHAN on 3/10/17.
 */

public class CScrollView extends ScrollView {

    public CScrollView(Context context) {
        this(context,null);
    }

    public CScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CScrollView_dispatchTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CScrollView_dispatchTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CScrollView_dispatchTouchEvent_UP");
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
                System.out.println("CScrollView_onInterceptTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CScrollView_onInterceptTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CScrollView_onInterceptTouchEvent_UP");
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
                System.out.println("CScrollView_onTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CScrollView_onTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CScrollView_onTouchEvent_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
//        return true;
    }


}
