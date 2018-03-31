package com.nanshanstudio.viewstudy.cview.focus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.nanshanstudio.viewstudy.R;

/**
 * Created by NANSHAN on 3/6/17.
 */


public class CVoiceView extends View {

    private Bitmap bg;
    private int count;
    private int splitSize;//间距
    private int firstColor;
    private int secondColor;
    private int circleWidth;

    private int mCurrentCount;//当前的音量
    private Paint mPaint;
    private Rect mRect;

    public CVoiceView(Context context) {
        this(context, null);
    }

    public CVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CVoiceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //从自定义的属性attrs.xml中去拿布局文件Layout.xml中的自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CVoiceView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CVoiceView_firstColor:
                    firstColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CVoiceView_secondColor:
                    secondColor = a.getColor(attr, Color.GRAY);
                    break;

                case R.styleable.CVoiceView_count:
                    count = a.getInteger(attr, 20);
                    break;
                case R.styleable.CVoiceView_splitSize:
                    splitSize = a.getInteger(attr, 20);
                    break;
                case R.styleable.CVoiceView_bg:
                    bg = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));

                    break;
                case R.styleable.CVoiceView_circleWidth:
                    circleWidth = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
                    break;

            }

        }
        a.recycle();

        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(circleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断点形状为圆头
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - circleWidth / 2;//半径是到圆环厚度的中间

        drawOval(canvas, centre, radius);


        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - circleWidth / 2;
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + circleWidth;
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + circleWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         * Math.sqrt(2) * relRadius内切正方形的边长
         */
        if (bg.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - bg.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - bg.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + bg.getWidth());
            mRect.bottom = (int) (mRect.top + bg.getHeight());

        }
        // 绘图
        canvas.drawBitmap(bg, null, mRect, mPaint);

    }

    /**
     * 画块块去
     */
    private void drawOval(Canvas canvas, int centre, int radius) {
        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize = (360 * 1.0f - count * splitSize) / count;
        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        mPaint.setColor(firstColor);
        for (int i = 0; i < count; i++) {
            canvas.drawArc(oval, i * (itemSize + splitSize), itemSize, false, mPaint);
        }
        mPaint.setColor(secondColor);
        for (int i = 0; i < mCurrentCount; i++)
            canvas.drawArc(oval, i * (itemSize + splitSize) - 180, itemSize, false, mPaint);
    }

    public void up() {
        mCurrentCount++;
        postInvalidate();
    }

    public void down() {
        mCurrentCount--;
        postInvalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CVoiceView_dispatchTouchEvent_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("CVoiceView_dispatchTouchEvent_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("CVoiceView_dispatchTouchEvent_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("CVoiceView_onTouchEvent_DOWN");
                xDown = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                System.out.println("CVoiceView_onTouchEvent_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("CVoiceView_onTouchEvent_UP");
                xUp = (int) event.getY();
                if (xUp > xDown) {
                    down();
                } else {
                    up();
                }
                break;
        }
        return true;
    }



}