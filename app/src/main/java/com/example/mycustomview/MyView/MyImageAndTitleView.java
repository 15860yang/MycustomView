package com.example.mycustomview.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.mycustomview.R;

/**
 * Created by 杨豪 on 2018/5/10.
 */

public class MyImageAndTitleView extends View {

    private Paint mPaint;
    private Rect mBackBound;

    private int mBackColor = Color.GREEN;

    private Bitmap image;
    private String imageTitle = "没有设置标题";
    private int imageTitleColor = Color.RED;
    private float imageTitleSize = 40;
    private Rect mTitleBound;

    private int width;
    private int height;



    public MyImageAndTitleView(Context context) {
        this(context,null);
    }

    public MyImageAndTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyImageAndTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyImageAndTitleView,
                defStyleAttr,0);
        for(int i = 0;i< typedArray.getIndexCount();i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.MyImageAndTitleView_image:
                    image = BitmapFactory.decodeResource(getResources(), typedArray.
                            getResourceId(attr, 0));
                    break;
                case R.styleable.MyImageAndTitleView_imageTitle:
                    imageTitle = typedArray.getString(attr);
                    break;
                case R.styleable.MyImageAndTitleView_imageTitleColor:
                    imageTitleColor = typedArray.getColor(attr,Color.GRAY);
                    break;
                case R.styleable.MyImageAndTitleView_imageTitleSize:
                    imageTitleSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension
                            (TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyImageAndTitleView_myBackColor:
                    mBackColor = typedArray.getColor(attr,Color.BLACK);
                    break;
                default:break;
            }
        }

        typedArray.recycle();
        mPaint = new Paint();
        mBackBound = new Rect();
        mTitleBound = new Rect();

        mPaint.setColor(imageTitleColor);
        mPaint.setTextSize(imageTitleSize);
        mPaint.getTextBounds(imageTitle,0,imageTitle.length(),mTitleBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureModel = MeasureSpec.getMode(heightMeasureSpec);


        if(widthMeasureModel == MeasureSpec.EXACTLY){ //如果是match_Parent
            width = widthMeasureSize;
        }else {
            //宽度应该字体和图片中宽的   再和widthMeasureSize比较  较小的
            int imageWidth = image.getWidth() + getPaddingLeft() + getPaddingRight();
            int titleWidth = mTitleBound.width() + getPaddingLeft() + getPaddingRight();
            if(widthMeasureModel == MeasureSpec.AT_MOST){
                int l = Math.max(imageWidth,titleWidth);
                width = Math.min(widthMeasureSize,l);
            }
        }

        if(heightMeasureModel == MeasureSpec.EXACTLY){  //如果是match_Parent
            height = heightMeasureSize;
        }else {

            //高度应该字体加图片的高   再和heightMeasureSize比较  较小的
            int imageAndTitleHeight = image.getHeight() + getPaddingBottom() + getPaddingTop()+
                    mTitleBound.height();
            if(heightMeasureModel == MeasureSpec.AT_MOST){ //wrap_Content
                height = Math.min(heightMeasureSize,imageAndTitleHeight);
            }
        }


        mBackBound.set(0,0,width,height);

        Log.d("------","width = " + width +",height = "+height);

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mBackColor);
        Log.d("--------","mBackBound 高 = " + mBackBound.height()+ " 宽 = " + mBackBound.width());
        canvas.drawRect(mBackBound,mPaint);

        //第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
        Rect imageRect = new Rect(0,0,image.getWidth(),image.getHeight());
        Rect imageLocation = new Rect((int) (getWidth()/2 - image.getWidth() * 1.0f / 2),getPaddingTop(),
                getWidth()/2 + image.getWidth()/2,getPaddingTop()+image.getHeight());
        Log.d("--------","imageRect 高 = " + imageRect.height()+ " 宽 = " + imageRect.width());
        Log.d("--------","mTitleBound 高 = " + mTitleBound.height()+ " 宽 = " + mTitleBound.width());
        canvas.drawBitmap(image,imageRect,imageLocation,mPaint);

        mPaint.setColor(imageTitleColor);
        mPaint.setTextSize(imageTitleSize);
        if(mTitleBound.width() > width){

            TextPaint paint = new TextPaint(mPaint);
            String finalStr = TextUtils.ellipsize(imageTitle, paint, (float) width - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            Log.d("------dffff--------",finalStr);
            canvas.drawText(finalStr, getPaddingLeft(), getHeight() - getPaddingBottom(), paint);
        }else {
            mPaint.getTextBounds(imageTitle,0,imageTitle.length(),mTitleBound);
            canvas.drawText(imageTitle, getWidth() / 2 - mTitleBound.width()  *1.0f / 2, getHeight() - getPaddingBottom(), mPaint);
        }
    }
}
