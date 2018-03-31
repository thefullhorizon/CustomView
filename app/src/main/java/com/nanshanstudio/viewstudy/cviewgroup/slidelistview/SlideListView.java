package com.nanshanstudio.viewstudy.cviewgroup.slidelistview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.nanshanstudio.viewstudy.R;

/**
 * Created by cdxsc on 2016/9/1.
 */
public class SlideListView extends ListView {


    private static final String TAG = "lzy";
    private int maxLength;
    private int mStartX = 0;
    private LinearLayout itemLayout;
    private int pos;
    private Rect mTouchFrame;
    private int xDown, xMove, yDown, yMove, mTouchSlop;
    private Scroller mScroller;
    private Button textView;
    private ImageView imageView;
    private boolean isFirst = true;
    private OnSlideListener onSlideListener;

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.onSlideListener = onSlideListener;
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //滑动到最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //滑动的最大距离
        maxLength = ((int) (180 * context.getResources().getDisplayMetrics().density + 0.5f));
        //初始化Scroller
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
//                System.out.print("ACTION_DOWN : itemLayout.getScrollX(): " + itemLayout.getScrollX());
                xDown = x;
                yDown = y;
                //判断当前点击点是否在哪一个Item中（position）
                int mFirstPosition = getFirstVisiblePosition();
                Rect frame = mTouchFrame;
                if (frame == null) {
                    mTouchFrame = new Rect();
                    frame = mTouchFrame;
                }
                int count = getChildCount();
                for (int i = count - 1; i >= 0; i--) {//人的手触摸操作一般在手机屏幕偏下操作,所以这里从下往上判断，对程序进行优化
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(frame);
                        if (frame.contains(x, y)) {
                            //因为getChildCount()只会得到展现在屏幕中的子View个数，并不是总个数
                            pos = mFirstPosition + i;
                            break;
                        }
                    }
                }
                //通过position得到itemLayout
                itemLayout = (LinearLayout) getChildAt(pos - mFirstPosition);
                textView = (Button) itemLayout.findViewById(R.id.bt_item);
                imageView = (ImageView) itemLayout.findViewById(R.id.iv_item);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSlideListener != null) {
                            onSlideListener.onDelete(pos);
                            //恢复原状
                            mScroller.startScroll(maxLength, 0, -maxLength, 0);
                            invalidate();
                        }
                    }
                });
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                //竖直方向小于最小滑动距离，水平方向大于
                if (Math.abs(dy) < mTouchSlop * 2 && Math.abs(dx) > mTouchSlop) {
                    int scrollX = itemLayout.getScrollX();
                    int newScrollX = mStartX - x;
                    //对ItemLayout滑动的左右进行限制
                    if (newScrollX < 0 && scrollX <= 0) {
                        newScrollX = 0;
                    } else if (newScrollX > 0 && scrollX >= maxLength) {
                        newScrollX = 0;
                    }
                    //当划过二分之一时，imageView做一个缩放动画
                    if (scrollX > maxLength / 2) {
                        if (isFirst) {
                            ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.8f, 1.2f, 0.8f);
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.8f, 1.2f, 0.8f);
                            AnimatorSet animSet = new AnimatorSet();
                            animSet.play(animatorX).with(animatorY);
                            animSet.setDuration(800);
                            animSet.start();
                            isFirst = false;
                        }
                    }
                    itemLayout.scrollBy(newScrollX, 0);
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                int scrollX = itemLayout.getScrollX();
//                System.out.println("ACTION_UP : itemLayout.getScrollX(): " + itemLayout.getScrollX());
                if (scrollX > maxLength / 2) {
                    //划过一半松手，显示全部
                    mScroller.startScroll(scrollX, 0, maxLength - scrollX, 0);
                    invalidate();
                } else {
                    //恢复原状
                    mScroller.startScroll(scrollX, 0, -scrollX, 0);
                    invalidate();
                }
                isFirst = true;
            }
            break;
        }
        mStartX = x;
        return super.onTouchEvent(ev);
    }

    //view 里onDraw方法会调用
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            itemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            System.out.println("mScroller.getCurrX(): " + mScroller.getCurrX());
            postInvalidate();
        }
    }

    public interface OnSlideListener {
        void onDelete(int pos);
    }
}
