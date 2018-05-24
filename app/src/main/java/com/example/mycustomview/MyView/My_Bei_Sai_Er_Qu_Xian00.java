package com.example.mycustomview.MyView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * Created by 杨豪 on 2018/5/17.
 */





public class My_Bei_Sai_Er_Qu_Xian00 extends View {
    public My_Bei_Sai_Er_Qu_Xian00(Context context) {
        super(context);
    }

    public My_Bei_Sai_Er_Qu_Xian00(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public My_Bei_Sai_Er_Qu_Xian00(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    Path path = new Path();
    Paint paint = new Paint();
    public void init(){
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);//设置画笔宽度
        paint.setColor(Color.RED);
    }


    /**
     *      利用相对坐标控制贝塞尔曲线
     *     public void rQuadTo(float dx1, float dy1, float dx2, float dy2)
     *
     *      比如上个方法
     *          path.moveTo(100,300);
     *          path.quadTo(400,400,500,300);
     *      用这个方法可以这样子
     *          path.moveTo(100,300);
     *          path.rquadTo(300,100,400,0);
     */
    private int mItemWaveLength = 400;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength/2;
        path.moveTo(-mItemWaveLength,originY);
        for (int i = -mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
            path.rQuadTo(halfWaveLen/2,-50,halfWaveLen,0);
            path.rQuadTo(halfWaveLen/2,50,halfWaveLen,0);
        }
        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();


        canvas.drawPath(path,paint);
    }


    private int dx;
    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
