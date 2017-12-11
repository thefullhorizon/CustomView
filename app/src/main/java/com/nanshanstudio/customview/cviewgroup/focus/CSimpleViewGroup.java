package com.nanshanstudio.customview.cviewgroup.focus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by NANSHAN on 3/5/17.
 */

/**
 *
 * onMeasure()
 * 首先需要知道子View大小，然后才能确定自定义的GroupView如何设置才能容纳它们。
 * 根据子View的大小和ViewGroup需要实现的效果，确定最终ViewGroup的大小。
 * onLayout()
 * ViewGroup和子View的大小确定后，接着就是如何去摆放子View,你可以按照自己特定的规则去摆放。
 * 然后将子View对号入座放入已知的分割单元。
 *
 * 这个模拟的是LinearLayout的横向布局
 *
 */
public class CSimpleViewGroup extends ViewGroup {

    public CSimpleViewGroup(Context context) {
        super(context);
    }

    public CSimpleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CSimpleViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //触发对子View的measure
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //根据子view的大小确定viewGroup的大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (getChildCount() == 0){
            // 如果当前ViewGroup没有子View，就没有存在的意义，无需占空间
            setMeasuredDimension(0, 0);
        }else {
            // WrapContent
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                // 宽度为所有子view宽度相加，高度取子view最大高度
                int width = getTotalWidth();
                int height = getMaxHeight();
                setMeasuredDimension(width, height);
            } else if (widthMode == MeasureSpec.AT_MOST) {
                // 宽度为所有子View宽度相加，高度为测量高度
                setMeasuredDimension(getTotalWidth(), heightSize);
            } else if (heightMode == MeasureSpec.AT_MOST) {
                // 宽度为测量宽度，高度为子view最大高度
                setMeasuredDimension(widthSize, getMaxHeight());
            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int currentWidth = l;
        for (int i = 0;i<childCount;i++){
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            int width = childView.getMeasuredWidth();
            childView.layout(currentWidth, t, currentWidth + width, t + height);
            currentWidth += width;
        }
    }

    private int getTotalWidth(){
        int totalWidth = 0;
        for (int i=0;i<getChildCount();i++){
            totalWidth += getChildAt(i).getMeasuredWidth();
        }
        return totalWidth;
    }

    private int getMaxHeight() {
        int maxHeight = 0;
        View childView;
        for (int i=0;i<getChildCount();i++){
            childView = getChildAt(i);
            if (childView.getMeasuredHeight() > maxHeight)
                maxHeight = childView.getMeasuredHeight();
        }
        return maxHeight;
    }



}
