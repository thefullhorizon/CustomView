package com.nanshanstudio.viewstudy.cview.focus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.nanshanstudio.viewstudy.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by NANSHAN on 3/2/17.
 */

public class CTextView extends View {


    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;


    private Rect mBound;
    private Paint mPaint;


    public CTextView(Context context) {
        this(context, null);
    }

    public CTextView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }


    public CTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //从自定义的属性attrs.xml中去拿布局文件Layout.xml中的自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    //第二个参数给出了一个默认值（通过TypeValue将sp转dx）
                    mTitleTextSize = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

            }

        }
        a.recycle();


        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        // mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

        this.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v){
                mTitleText = randomText();
                postInvalidate();
            }

        });

    }

    private String randomText(){
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set){
            sb.append("" + i);
        }
        return sb.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int width = 0;
        int height = 0;

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode){
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                width = getPaddingLeft() + getPaddingRight() + mBound.width();
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode){
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                height = getPaddingTop() + getPaddingBottom() + mBound.height();
                break;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        //在控件的重点去绘制文本
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

}
