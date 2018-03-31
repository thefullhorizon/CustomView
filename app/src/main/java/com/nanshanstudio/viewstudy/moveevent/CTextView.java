package com.nanshanstudio.viewstudy.moveevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by NANSHAN on 3/10/17.
 */

public class CTextView extends TextView {

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CTextView_dispatchTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CTextView_dispatchTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CTextView_dispatchTouchEvent_UP");
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
                System.out.println("CTextView_onTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CTextView_onTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CTextView_onTouchEvent_UP");
                break;
            default:
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }
}
