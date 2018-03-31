package com.nanshanstudio.viewstudy.cview.focus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.nanshanstudio.viewstudy.R;

/**
 * Created by NANSHAN on 3/4/17.
 */

public class CImageText extends View {

    private Bitmap mImage;
    private int mImageScale;
    private static int IMAGE_SCALE_FITXY = 0;
    private static int IMAGE_SCALE_CENTER = 1;

    private String mtext;
    private int mtextColor;
    private int mtextSize;

    private int mWidth;
    private int mHeight;

    private Rect mTextBound;
    private Rect mImageRect;
    private Paint mPaint;


    public CImageText(Context context) {
        this(context, null);
    }

    public CImageText(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }


    public CImageText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //从自定义的属性attrs.xml中去拿布局文件Layout.xml中的自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageText, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageText_text:
                    mtext = a.getString(attr);
                    break;
                case R.styleable.CustomImageText_textColor:
                    mtextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageText_textSize:
                    mtextSize = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomImageText_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageText_imageScaleType:
                    mImageScale = a.getIndex(attr);
                    break;
            }
        }
        a.recycle();
        mImageRect = new Rect();

        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mtextSize);
        mPaint.getTextBounds(mtext, 0, mtext.length(), mTextBound);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){// match_parent , accurate
            mWidth = specSize;
        }else{
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            if (specMode == MeasureSpec.AT_MOST){// wrap_content
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(desire, specSize);
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            mHeight = specSize;
        }else{
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            if (specMode == MeasureSpec.AT_MOST){
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mImageRect.left = getPaddingLeft();
        mImageRect.right = mWidth + getPaddingRight();
        mImageRect.top = getPaddingTop();
        mImageRect.bottom = mHeight - getPaddingBottom();


        mPaint.setColor(mtextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mImageScale == IMAGE_SCALE_FITXY){
            canvas.drawBitmap(mImage, null, mImageRect, mPaint);
        } else{
            //计算居中的矩形范围
            mImageRect.left = mWidth / 2 - mImage.getWidth() / 2;
            mImageRect.right = mWidth / 2 + mImage.getWidth() / 2;
            mImageRect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            mImageRect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, mImageRect, mPaint);
        }

        mImageRect.top -= mTextBound.height();

        /**
         * 字体处理
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
        if (mTextBound.width() > mWidth){
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mtext, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - mImage.getHeight()- getPaddingBottom(), mPaint);
        } else{
            //正常情况，将字体居中
            canvas.drawText(mtext, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }



    }
}
