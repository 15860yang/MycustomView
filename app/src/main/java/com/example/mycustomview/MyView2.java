package com.example.mycustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 杨豪 on 2018/5/5.
 */

public class MyView2 extends View {

    private int backColor = Color.GRAY;
    private int circleColor = Color.RED;
    private float circleRadius = 30f;

    private Paint mPaint = new Paint();

    public MyView2(Context context) {
        this(context,null);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取布局文件属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyView2);
        int n = typedArray.getIndexCount();
        for(int i = 0;i < n ;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.MyView2_backColor:
                    backColor = typedArray.getColor(attr,Color.GRAY);
                    break;
                case R.styleable.MyView2_circleColor:
                    circleColor = typedArray.getColor(attr,Color.RED);
                    break;
                case R.styleable.MyView2_circleRadius:
                    circleRadius = typedArray.getFloat(attr,20.0f);
                    break;
            }
        }
        typedArray.recycle();
    }


    private int width;
    private int height;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到长宽模式以及数值
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthModel == MeasureSpec.AT_MOST){
            width = 200;
        }else {
            width = widthSize;
        }
        if(heightModel == MeasureSpec.AT_MOST){

            height = 200;
        }else {
            height = heightSize;
        }

        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(backColor);
        //左上右下
        Rect rect = new Rect(0,0,width,height);
        canvas.drawRect(rect,mPaint);

        mPaint.setColor(circleColor);
        canvas.drawCircle((width+paddingRight+paddingLeft)/2,(height+paddingBottom+paddingTop)/2,circleRadius,mPaint);
    }
}
