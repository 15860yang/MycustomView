package com.example.mycustomview.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 杨豪 on 2018/5/19.
 */

public class Canvee_Test1 extends View {
    public Canvee_Test1(Context context) {
        super(context);
    }

    public Canvee_Test1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Canvee_Test1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawColor(Color.BLUE);
        canvas.clipRect(50,50,600,600);
        int i = canvas.save();
        canvas.drawRect(100,100,400,400,paint);
        int i1 = canvas.save();
        paint.setColor(Color.GREEN);
        canvas.drawRect(150,150,350,350,paint);
        canvas.restore();
    }
}
