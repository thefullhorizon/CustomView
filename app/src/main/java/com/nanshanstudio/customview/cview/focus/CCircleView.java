package com.nanshanstudio.customview.cview.focus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.nanshanstudio.customview.R;

/**
 * Created by NANSHAN on 3/3/17.
 */

public class CCircleView extends View {

    private int mbackground;
    private int mforeground;
    private int mCircleWidth;
    private int mSpeed;

    private Paint mPaint;
    private int mProgress;
    private boolean isNext = false;


    public CCircleView(Context context) {
        this(context, null);
    }

    public CCircleView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }


    public CCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCircle, defStyle, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomCircle_circle_background:
                    mbackground = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomCircle_circle_foreground:
                    mforeground = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomCircle_circle_speed:
                    mSpeed = a.getInt(attr,10);
                    break;
                case R.styleable.CustomCircle_circle_width:
                    //将16转成px值
                    mCircleWidth = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics()));
                    break;

            }

        }
        a.recycle();
        mPaint = new Paint();

        new Thread(){
            public void run(){
                while (true){
                    mProgress++;
                    if (mProgress == 360){
                        mProgress = 0;
                        if (!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    postInvalidate();
                    try{
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2;
        int radius = centre - mCircleWidth / 2;

        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE); //设置空心
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
        if (!isNext){
            mPaint.setColor(mbackground);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mforeground);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        } else{
            mPaint.setColor(mforeground);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mbackground);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
    }

}
