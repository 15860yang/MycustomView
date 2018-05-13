package com.example.mycustomview;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 杨豪 on 2018/5/12.
 */

public class MyProgressView extends View {

    private final static int CIRCLE = 1;
    private final static int FIVE_POINTS_STAR = 2;


    private int backColor = Color.GRAY;//背景色
    private int guageColor = Color.RED;//进度条颜色
    private int guageModel = CIRCLE;//进度模式
    private int lineColor = Color.GREEN;//进度线颜色

    private MyProgressListener listener;  //进度改变监听

    private Paint mPaint;  //画笔

    private int width;  //控件总宽度
    private int height; // 空间总高度

    private int guageRadius;  //进度圆中心
    private int guageX = 0;   //进度圆中心此时的x值
    private int guageMinX;    //进度圆中心x坐标最小值
    private int guageMaxX;     //进度圆中心x坐标最大值

    private int guageLineMinX; //进度线最小x值
    private int guageLineMaxX; //进度线最大x值


    public MyProgressView(Context context) {
        this(context,null);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.MyProgressView);
        for(int i = 0;i < array.getIndexCount(); i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.MyProgressView_myProgressBackColor:
                    backColor = array.getColor(attr,Color.GRAY);
                    break;
                case R.styleable.MyProgressView_guageColor:
                    guageColor = array.getColor(attr,Color.RED);
                    break;
                case R.styleable.MyProgressView_guageModel:
                    guageModel = array.getInt(attr,1);
                    break;
                case R.styleable.MyProgressView_lineColor:
                    lineColor = array.getColor(attr,Color.GREEN);
                    break;
            }
        }
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        //判断宽模式
        if (widthMeasureMode == MeasureSpec.EXACTLY){
            width = widthMeasureSize;
        }else {
            ViewGroup parenGroup = (ViewGroup) getParent();
            if(parenGroup != null){
                width = parenGroup.getMeasuredWidth();
            }
        }
        //判断高模式
        if(heightMeasureMode == MeasureSpec.EXACTLY){
            height = heightMeasureSize;
        }else {
            ViewGroup parenGroup = (ViewGroup) getParent();
            if(parenGroup != null){
                height = parenGroup.getMeasuredHeight()/2/2/2;
            }
        }

        switch (guageModel){
            case CIRCLE:
                guageRadius = (int) (((float)((height - getPaddingTop() - getPaddingBottom())/2))*0.25);
                break;
            case FIVE_POINTS_STAR:
                break;
                default:break;
        }
        guageMinX = getPaddingLeft() + guageRadius;
        guageLineMinX = getPaddingLeft();
        guageMaxX = width - getPaddingRight() - guageRadius;
        guageLineMaxX = guageMaxX + guageRadius;

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画最外边框
        Rect rect = new Rect();
        rect.set(getPaddingLeft(),getPaddingTop(),width - getPaddingRight(),height -getPaddingBottom());
        mPaint.setColor(backColor);
        canvas.drawRect(rect,mPaint);

        //检查进度
        if(guageX <= 0){
            guageX = guageMinX;
        }
        //画已经走过的进度线
        //测量进度线y坐标
        int y = (height - getPaddingTop() - getPaddingBottom())/2 + getPaddingTop();

        mPaint.setColor(lineColor);
        Rect rect1 = new Rect(guageLineMinX,y-height/2/2/2,guageX,y+height/2/2/2);
        canvas.drawRect(rect1,mPaint);

        //画未走的进度条
        rect1.set(guageX,y-height/2/2/2,guageLineMaxX,y+height/2/2/2);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect1,mPaint);

        mPaint.setColor(guageColor);
        switch (guageModel){
            case CIRCLE:
                canvas.drawCircle(guageX,y,guageRadius,mPaint);
                break;
            case FIVE_POINTS_STAR:
                break;
        }
    }

    boolean canMove = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) getX();
        int y = (int) getY();
        int downX;
        int downY;

        if((x < getPaddingLeft() || x > width - getPaddingRight()) &&
                y < getPaddingTop() || y > height - getPaddingBottom()){
            return false;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) getY();

                if(downX > (guageX - guageRadius) && downX < (guageX + guageRadius)){
                    canMove = true;
                    Log.d("--- ","设置canMove为true");
                    listener.moveStart(this);
                }

                Log.d("---down == ","guageX = "+guageX + ",guageRadius = "+guageRadius+",downX = "+downX);
                Log.d("---canMove == ",canMove+"");
                guageX+=10;

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if(canMove){
                    Log.d("---Move == ","正在移动，x = "+event.getX()+",guageX = "+guageX);
                    int nowX = (int) event.getX();
                    if(nowX > guageMaxX ){
                        guageX = guageMaxX;
                    }else if(nowX < guageMinX){
                        guageX = guageMinX;
                    }else {
                        guageX = nowX;
                    }
                    invalidate();
                    listener.moveing(this);
                }

                break;

            case MotionEvent.ACTION_UP:
                listener.moveEnd(this);
                Log.d("----","设置可点击为false" + canMove);
                canMove = false;
                break;
        }
        return true;
    }

    public float getProgress(){
        return (guageX - guageMinX)*1.0f/(guageMaxX - guageMinX);
    }

    public void setProgressChangeListener(MyProgressListener listener){
        this.listener = listener;
    }

    public interface MyProgressListener{
        void moveing(MyProgressView progress);
        void moveStart(MyProgressView progress);
        void moveEnd(MyProgressView progress);
    }
}
