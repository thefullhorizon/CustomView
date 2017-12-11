package com.nanshanstudio.customview.moveevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by NANSHAN on 3/8/17.
 */

public class CButton extends Button {

    public CButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CButton_dispatchTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CButton_dispatchTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CButton_dispatchTouchEvent_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CButton_onTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CButton_onTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CButton_onTouchEvent_UP");
                break;
            default:
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

}
