package com.example.mycustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 杨豪 on 2018/5/19.
 */

public class Canvee_Test2 extends View{
    public Canvee_Test2(Context context) {
        super(context);
    }

    public Canvee_Test2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Canvee_Test2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    Paint paint = new Paint();
    int width = 400;
    int height = 400;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        int layerId0 = canvas.saveLayer(0, 0, 800, 800, paint);

        paint.setColor(Color.RED);
        canvas.drawRect(100,100,500,500,paint);

        int layerId1 = canvas.saveLayer(150,150,450,450,paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(150,150,300,300,paint);

        int layerId2 = canvas.saveLayer(200,200,450,450,paint);
        paint.setColor(Color.GREEN);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawRect(250,250,350,350,paint);


        canvas.restore();
//        canvas.restoreToCount(layerId2);
        paint.setColor(Color.YELLOW);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas.drawRect(200,200,450,450,paint);

    }

    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }
}
