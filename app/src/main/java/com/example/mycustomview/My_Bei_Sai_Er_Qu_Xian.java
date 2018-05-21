package com.example.mycustomview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by 杨豪 on 2018/5/17.
 */

/**
 * 贝塞尔曲线实现划线
 */

public class My_Bei_Sai_Er_Qu_Xian extends View {
    public My_Bei_Sai_Er_Qu_Xian(Context context) {
        super(context);
    }

    public My_Bei_Sai_Er_Qu_Xian(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public My_Bei_Sai_Er_Qu_Xian(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    Path path = new Path();
    Paint paint = new Paint();
    public void init(){
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);//设置画笔宽度
        paint.setColor(Color.RED);
    }

    float mPreX;
    float mPreY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX())/2;
                float endY = (mPreY + event.getY())/2;

                path.quadTo(mPreX,mPreY,endX,endY);
                mPreY = event.getY();
                mPreX = event.getX();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        path.moveTo(100,300);//起始点，如果没有指定，则默认为0,0
//        path.quadTo(200,200,300,300);//贝塞尔曲线函数
//        path.quadTo(400,400,500,300);

        canvas.drawPath(path,paint);
    }
}
