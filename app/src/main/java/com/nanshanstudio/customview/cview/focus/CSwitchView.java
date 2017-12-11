package com.nanshanstudio.customview.cview.focus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nanshanstudio.customview.R;

/**
 * Created by NANSHAN on 3/2/17.
 * 自定义控件重新学习
 *
 * UI模块
 * 在onMeasure()中 设置上控件的宽高
 * 如果有子View怎在onLayout()中设置子View的宽高
 * 使用canvas画自定义的控件
 *
 * 手势与动画模块
 * 主要是在onTouchvent中国去解析用户的手势，要熟悉getX(),getRawX()的含义
 *
 *  加入动画的方式有两种：
 *  1.ScrollTo() , ScrollBy()，Scroller
 *  2.3.0之后加入的Property Animation
 *
 */

public class CSwitchView extends View {

    private Bitmap switchBackgroupBitmap;
    private Bitmap switchForegroupBitmap;
    private Paint paint;

    private boolean isSwitchState = true;
    private boolean isTouchState = false;
    private float currentPosition;
    private int maxPosition;

    private OnSwitchStateUpdateListener onSwitchStateUpdateListener;

    public interface OnSwitchStateUpdateListener {
        void onStateUpdate(boolean state);
    }


    public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener
                                                       onSwitchStateUpdateListener) {
        this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
    }

    public CSwitchView(Context context){
        this(context, null);
    }

    public CSwitchView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }


    public CSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomSwitch, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch (attr){

                case R.styleable.CustomSwitch_switch_background:
                    setBackgroundPic(a.getResourceId(attr,-1));

                    break;
                case R.styleable.CustomSwitch_switch_foreground:
                    setForegroundPic(a.getResourceId(attr,-1));
                    break;
                case R.styleable.CustomSwitch_switch_state:
                    isSwitchState = a.getBoolean(attr,false);
                   break;

            }

        }
        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackgroupBitmap.getWidth(), switchBackgroupBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(switchBackgroupBitmap, 0, 0, paint);
        if (isTouchState) {
            float movePosition = currentPosition - switchForegroupBitmap.getWidth() / 2.0f;
            maxPosition = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
            //Prescribe a limit to range of view can scroll
            if (movePosition < 0) {
                movePosition = 0;
            } else if (movePosition > maxPosition) {
                movePosition = maxPosition;
            }
            canvas.drawBitmap(switchForegroupBitmap, movePosition, 0, paint);
        }else {
            if (isSwitchState) {
                maxPosition = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
                canvas.drawBitmap(switchForegroupBitmap, maxPosition, 0, paint);
            } else {
                canvas.drawBitmap(switchForegroupBitmap, 0, 0, paint);
            }
        }

    }

    public void setBackgroundPic(int switchBackground) {
        switchBackgroupBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
    }

    public void setForegroundPic(int switchForeground) {
        switchForegroupBitmap = BitmapFactory.decodeResource(getResources(), switchForeground);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                isTouchState = true;
                currentPosition = event.getX();
                    break;
            case MotionEvent.ACTION_MOVE:
                currentPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchState = false;
                currentPosition = event.getX();

                float centerPosition = switchBackgroupBitmap.getWidth() / 2.0f;
                boolean currentState = currentPosition > centerPosition;
                if (currentState != isSwitchState && onSwitchStateUpdateListener != null) {
                    onSwitchStateUpdateListener.onStateUpdate(currentState);
                }
                isSwitchState = currentState;

                break;

        }
        invalidate();
        return true;
    }


}
