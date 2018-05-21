package com.example.mycustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 杨豪 on 2018/5/18.
 */

public class Paint之ColorMatrix extends View{

    public Paint之ColorMatrix(Context context) {
        super(context);
    }

    public Paint之ColorMatrix(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Paint之ColorMatrix(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     *  生成色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 0.5, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
     *
     *
     */

    private Paint mPaint = new Paint();
    private Bitmap bitmap;//位图

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setAntiAlias(true);//抗锯齿
//        mPaint.setARGB(255,200,100,100);
//
//        // 绘制原始位图
//        canvas.drawRect(0,0,500,600,mPaint);
//
//        canvas.translate(550,0);
//        // 生成色彩矩阵
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0,
//                0, 0, 1, 0, 0,
//                0, 0, 0, 1, 0,
//        });
//        //设置色彩矩阵，根据矩阵的信息，我们知道，设置之后的色彩是只取之前的蓝色色彩以及之前的透明度
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
//        canvas.drawRect(0,0,500,600,mPaint);

        bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_launcher);

        // 绘制原始位图
        canvas.drawBitmap(bitmap,null,new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);

        canvas.translate(0, 510);
        // 生成色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);


    }
}
