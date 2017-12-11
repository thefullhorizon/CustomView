package com.nanshanstudio.customview.cview.focus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.nanshanstudio.customview.R;
import com.nanshanstudio.customview.cview.focus.CScrollView;
import com.nanshanstudio.customview.cview.focus.CVoiceView;

public class CVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);
        CVoiceView cVoiceView = (CVoiceView) findViewById(R.id.cVoiceView);
        final CScrollView mScrollView = (CScrollView) findViewById(R.id.mScrollView);
        cVoiceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    System.out.println("CVoiceView__onTouch__UP");
                    //允许ScrollView截断点击事件，ScrollView可滑动
                    mScrollView.requestDisallowInterceptTouchEvent(false);
                }else{
                    System.out.println("CVoiceView__onTouch__DOWN_MOVE");
                    //不允许ScrollView截断点击事件，点击事件由子View处理
                    mScrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Activity__onTouchEvent__DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Activity__onTouchEvent__MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Activity__onTouchEvent__UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
