package com.nanshanstudio.customview.moveevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.nanshanstudio.customview.R;

/**
 * Created by NANSHAN on 3/8/17.
 */

public class MoveEventStudy  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moveevent);

        CButton btn = (CButton) findViewById(R.id.btn);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("CButton__onTouch__DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("CButton__onTouch__MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("CButton__onTouch__UP");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CButton__onClickListener clicked!");
            }
        });

        CTextView tv = (CTextView) findViewById(R.id.tv);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("CTextView__onTouch__DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("CTextView__onTouch__MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("CTextView__onTouch__UP");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CTextView__onClickListener clicked!");
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Activity__dispatchTouchEvent__DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Activity__dispatchTouchEvent__MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Activity__dispatchTouchEvent__UP");
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
