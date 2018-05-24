package com.example.mycustomview.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 杨豪 on 2018/5/24.
 */

public class useObjectAnimatorTOChangeMyView extends View {
    public useObjectAnimatorTOChangeMyView(Context context) {
        super(context);
    }

    public useObjectAnimatorTOChangeMyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public useObjectAnimatorTOChangeMyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int radius = 20;

    Paint mPaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(250,500,radius,mPaint);
    }


    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public int getRadius() {
        return 10;
    }
}
