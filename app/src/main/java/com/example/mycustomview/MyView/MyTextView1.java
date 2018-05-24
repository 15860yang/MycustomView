package com.example.mycustomview.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.mycustomview.R;

/**
 * Created by 杨豪 on 2018/4/27.
 */

public class MyTextView1 extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;


    /**
     * 绘制时控制文本的的绘制范围，一个矩形，刚好能装下设置的文字的大小，就是说一个矩形刚好装下一个文字
     */
    private Rect mBound;

    private Paint mPaint;


    public MyTextView1(Context context) {

        this(context,null);

        Log.d("---------","这是第一个构造函数");
    }

    public MyTextView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        Log.d("---------","这是第二个构造函数");
    }

    public MyTextView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        Log.d("---------","这是第三个构造函数");
        //获取应用中设置的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView1,defStyleAttr,0);
        int n = typedArray.getIndexCount();
        for (int i = 0;i < n;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.MyTextView1_titleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.MyTextView1_titleTextColor:
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyTextView1_titleTextSize:
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,
                                    getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setColor(mTitleTextColor);
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            //测量文字所占的width
            //这是画笔的字体的大小
            mPaint.setTextSize(mTitleTextSize);
            //获取得到的文字边界，放进mBound这个矩形内，所以文字所占的width就是mBound的width
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textWidth = mBound.width();
            //view的width = 左边内边距 + 文字Width + 右边内边距
            width = (int)(getPaddingLeft() + textWidth + getPaddingRight());
        }

        if( heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            //测量文字所占的height
            //这是画笔的字体的大小
            mPaint.setTextSize(mTitleTextSize);
            //获取得到的文字边界，放进mBound这个矩形内，所以文字所占的width就是mBound的width
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textHeight = mBound.height();
            //view的height = 左边内边距 + 文字height + 右边内边距
            height = (int)(getPaddingTop() + textHeight + getPaddingBottom());
        }
        //设置大小
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        //左  上  右   下
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,getWidth()/2 - mBound.width()/2,getHeight()/2 + mBound.height()/ 2,mPaint);
    }
}
