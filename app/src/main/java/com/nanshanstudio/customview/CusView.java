package com.nanshanstudio.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NANSHAN on 12/12/17.
 */

public class CusView extends View {

    private int width;//设置高
    private int height;//设置高
    private Paint mPaint;
    //设置一个Bitmap
    private Bitmap bitmap;
    //创建该Bitmap的画布
    private Canvas bitmapCanvas;
    private Paint mPaintCirlcle;
    private Paint mPaintRect;




    public CusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();//Bitmap的画笔

        mPaintCirlcle = new Paint();
        mPaintCirlcle.setAntiAlias(true);
        mPaintCirlcle.setColor(Color.YELLOW);
        PorterDuffXfermode DSTmode  = new PorterDuffXfermode(PorterDuff.Mode.DST);
        mPaintCirlcle.setXfermode(DSTmode);

        mPaintRect = new Paint();
        mPaintRect.setAntiAlias(true);
        mPaintRect.setColor(Color.BLUE);
//        使用PorterDuff.Mode不能直接在View的画布上使用，也就是不能在自定义View的onDraw()方法中使用。必须在Bitmap的Canvas上使用
        PorterDuffXfermode XORmode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        mPaintRect.setXfermode(XORmode);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);//设置宽和高

        //自己创建一个Bitmap
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);//该画布为bitmap的
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置该View画布的背景
//        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

//        bitmapCanvas.drawCircle(width / 2, height / 2, width / 2, mPaintCirlcle);
        bitmapCanvas.drawRect(0, 0, width , height / 2, mPaintRect);
        bitmapCanvas.drawRect(width / 2 - width / 4, height / 4 - height / 8, width / 2 + width / 4, height / 4 + height / 8, mPaintRect);
    }


}
