package com.nanshanstudio.viewstudy.cviewgroup.focus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.nanshanstudio.viewstudy.R;
import com.nanshanstudio.viewstudy.utils.Utils;

import java.util.ArrayList;

/**
 * Created by NANSHAN on 3/5/17.
 * 模拟微信的添加图片的控件
 */

public class CAddImageView extends ViewGroup {

    public static final int MAX_PHOTO_NUMBER = 9;

    private int[] constImageIds = {
            R.drawable.switch_foreground, R.drawable.switch_foreground, R.drawable.switch_foreground,
            R.drawable.switch_foreground, R.drawable.switch_foreground, R.drawable.switch_foreground,
            R.drawable.switch_foreground, R.drawable.switch_foreground, R.drawable.switch_foreground};

    int hSpace = Utils.dpToPx(getResources(),10);
    int vSpace = Utils.dpToPx(getResources(),10);

    int childWidth = 0;
    int childHeight = 0;

    ArrayList<String> mImageResArrayList = new ArrayList<String>(9);
    private View addPhotoView;


    public CAddImageView(Context context) {
        this(context,null);
    }

    public CAddImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CAddImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.AddImageView, 0, 0);
        hSpace = t.getDimensionPixelSize(
                R.styleable.AddImageView_ninephoto_hspace, hSpace);
        vSpace = t.getDimensionPixelSize(
                R.styleable.AddImageView_ninephoto_vspace, vSpace);
        t.recycle();

        addPhotoView = new View(context);
        addView(addPhotoView);
        mImageResArrayList.add("k");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);

        childWidth = (rw - 2 * hSpace) / 3;
        childHeight = childWidth;
        //先设置子View的宽高
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            //如果自定义的Viewgroup在XML文件中有子View的话，使用measureChild来调用子View的onMeasure()
            //measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            lParams.left = (i % 3) * (childWidth + hSpace);
            lParams.top = (i / 3) * (childWidth + vSpace);
        }
        //再根据子View设置ViewGroup宽高
        int vw = rw;
        int vh = rh;
        if (childCount < 3) {
            vw = childCount * (childWidth + hSpace);
        }
        vh = ((childCount + 3) / 3) * (childWidth + vSpace);
        setMeasuredDimension(vw, vh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //子控件的布局
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            child.layout(lParams.left, lParams.top, lParams.left + childWidth,
                    lParams.top + childHeight);
            //业务逻辑的处理
            if (i == mImageResArrayList.size() - 1 && mImageResArrayList.size() != MAX_PHOTO_NUMBER) {
                child.setBackgroundResource(R.drawable.switch_background);
                child.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        addPhotoBtnClick();
                    }
                });
            }else {
                child.setBackgroundResource(constImageIds[i]);
                child.setOnClickListener(null);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void addPhotoBtnClick() {
        final CharSequence[] items = { "Take Photo", "Photo from gallery" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                addPhoto();
            }

        });
        builder.show();
    }

    public void addPhoto() {
        if (mImageResArrayList.size() < MAX_PHOTO_NUMBER) {
            View newChild = new View(getContext());
            addView(newChild);
            mImageResArrayList.add("k");
            requestLayout();//call onMeasure(),onLayout()
            invalidate();//call onDraw()
        }
    }

    /**
     * LayoutParams存储了子View在加入ViewGroup中时的一些参数信息，在继承ViewGroup类时，
     * 一般也需要新建一个新的LayoutParams类，
     */
     public static class LayoutParams extends ViewGroup.LayoutParams {

        public int left = 0;
        public int top = 0;

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams arg0) {
            super(arg0);
        }
    }

    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new CAddImageView.LayoutParams(getContext(), attrs);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(
            android.view.ViewGroup.LayoutParams p) {

        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof CAddImageView.LayoutParams;
    }



}
