package com.nanshanstudio.viewstudy.cview.focus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NANSHAN on 3/6/17.
 */

public class CClock extends View {

    private Paint paint;
    private int clockThemeColor = Color.BLACK;
    private int clockThemeBackgroundColor = Color.GRAY;

    public CClock(Context context) {
        this(context, null);
    }

    public CClock(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CClock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));

    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawColor(clockThemeBackgroundColor);

        int circleX = getWidth()/2;
        int circleY = getHeight()/2;
        int radius = 200;
        paint.setColor(clockThemeColor);
        paint.setStrokeWidth(2);
        canvas.drawCircle(circleX, circleY, radius, paint);

        int gap = 30;
        Path path = new Path();
        path.addArc(new RectF(circleX - radius +gap,circleY - radius+gap,
                circleX + radius - gap,circleY + radius - gap), -125, 80);

        paint.setTextSize(26);
        paint.setStrokeWidth(1);
        paint.setColor(Color.RED);
        canvas.drawTextOnPath("HORIZON CLOCK", path, 0, 0, paint);


        paint.setStrokeWidth(2);//设置大刻度画笔对象
        paint.setColor(clockThemeColor);

        Paint tmpPaint = new Paint(paint); //设置小刻度画笔对象
        tmpPaint.setStrokeWidth(1);
        tmpPaint.setTextSize(22);
        int count = 60;//刻度总数
        for(int i=0 ; i < count ; i++){
            if(i%5 == 0){
                int clockValue = (i/5 ==0)? 12 : i/5;
                canvas.drawLine(circleX, circleY - radius, circleX, circleY - radius - 24f, paint);
                canvas.drawText(String.valueOf(clockValue), circleX - 5 , circleY - radius - 32, tmpPaint);
            }else{
                canvas.drawLine(circleX, circleY - radius, circleX, circleY - radius - 12f, tmpPaint);
            }
            canvas.rotate(360/count,circleX,circleY);
        }

        //绘制指针
        tmpPaint.setColor(Color.GRAY);
        tmpPaint.setStrokeWidth(4);
        canvas.drawCircle(circleX,circleY, 20, tmpPaint);

        int poinLength = 150;
        canvas.drawLine(circleX , circleY + 35, circleX ,circleY - poinLength, paint);

        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(circleX,circleY, 15, tmpPaint);



    }
}
