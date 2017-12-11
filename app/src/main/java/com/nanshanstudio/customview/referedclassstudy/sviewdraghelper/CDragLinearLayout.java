package com.nanshanstudio.customview.referedclassstudy.sviewdraghelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by NANSHAN on 3/8/17.
 *
 * 参考：http://blog.csdn.net/lmj623565791/article/details/46858663
 * 主题：ViewDragHelper的使用
 * 总结：本类主要是为演示ViewDragHelper的使用，其作用是帮助我们处理拖动子View的操作
 *      重点了解ViewDragHelper.Callback()的回调函数，以及这些回调函数的调用逻辑顺序
 *      需要指出的是当子View可以消耗事件的时候需要复写getViewHorizontalDragRange(),getViewVerticalDragRange()两个函数来指定可以滑动的区域
 *
 * 参考：http://blog.csdn.net/lmj623565791/article/details/47396187
 * 主题：讲解了基于ViewDragHelper的自定义ViewGroup DrawerLayout的实现过程
 * 具体的实现步骤总结:
 *      在布局文件中布局ContentView和DrawerView
 *      继承ViewGroup实现具有策划功能的自定义ViewGroup类
 *      这个类中实例一个ViewDragHelper对象，并在其ViewDragHelper.Callback()中做相应处理
 *      在事件传递先关函数onInterceptTouchEvent(),onTouchEvent()将相应的处理交由ViewDragHelper对象处理
 *
 *
 *
 */

public class CDragLinearLayout extends LinearLayout {

    private ViewDragHelper mDragger;
    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public CDragLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView || child == mAutoBackView;//决定当前View是否要被捕获
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //对移动的边界进行控制
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth();
                //Math.max(left, leftBound)为左边的最小值;Math.min(Math.max(), rightBound)表示到达右面的最大值
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                Log.i("DragLayout", getWidth()+" -- "+leftBound +" -- " + rightBound + " -- " + left+ " -- " +newLeft);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == mAutoBackView){
                    //因为其内部使用的是mScroller.startScroll，所以别忘了需要invalidate()以及结合computeScroll方法一起。
                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();}
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                super.onEdgeDragStarted(edgeFlags, pointerId);
                //captureChildView可绕过tryCaptureView
                mDragger.captureChildView(mEdgeTrackerView, pointerId);
            }

            //如果子View是可点击的也就是可以消费事件的话，记得重写以下这两个方法，以下两个函数定义了View可以移动的范围
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

        });
        //设置左边可以边界检查
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        /**
         * 如果消耗事件，那么就会先走onInterceptTouchEvent方法，判断是否可以捕获，
         * 而在判断的过程中会去判断另外两个回调的方法：getViewHorizontalDragRange
         * 和getViewVerticalDragRange，只有这两个方法返回大于0的值才能正常的捕获。
         */
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);//处理事件会用到ViewDragHelper.Callback中的回调
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
        mDragView.setClickable(true);
        mAutoBackView.setClickable(true);
        mEdgeTrackerView.setClickable(true);
    }

    @Override
    public void computeScroll() {
        if(mDragger.continueSettling(true)) {
            invalidate();
        }
    }

}
