package com.example.mycustomview.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 杨豪 on 2018/5/24.
 */

public class MyPropertyAnimatorCircleView extends View {
    public MyPropertyAnimatorCircleView(Context context) {
        super(context);
    }

    public MyPropertyAnimatorCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPropertyAnimatorCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int radius = 20;
    Point point = new Point(100,100);
    Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        canvas.drawCircle(point.x,point.y,radius,paint);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
