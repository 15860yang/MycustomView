package com.example.mycustomview.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 杨豪 on 2018/5/22.
 */

public class QQ_message_drag_effect extends View {
    public QQ_message_drag_effect(Context context) {
        super(context);
    }

    public QQ_message_drag_effect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QQ_message_drag_effect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width;
    int height;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width = ((ViewGroup)getParent()).getMeasuredWidth(),height = ((ViewGroup)getParent()).getMeasuredHeight());
    }

    float radius = 50;
    PointF startPoint = new PointF(300,500);
    PointF nowFingerPoint = new PointF();
    boolean isMoveing = false;

    Paint paint = new Paint();
    Path path = new Path();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if((x < startPoint.x + radius && x > startPoint.x - radius) &&
                        (y < startPoint.y + radius && y > startPoint.y - radius)){
                    isMoveing = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isMoveing){
                    nowFingerPoint.y = y;
                    nowFingerPoint.x = x;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isMoveing = false;
                radius = 50;
                invalidate();
                break;
        }


        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startX = startPoint.x;
        float startY = startPoint.y;

        paint.setColor(Color.RED);
        canvas.drawCircle(startX,startY,radius,paint);


        if(isMoveing){

            float endX = nowFingerPoint.x;
            float endY = nowFingerPoint.y;
            float dx = endX - startX;
            float dy = endY - startY;

            float distance = (float) Math.sqrt(Math.pow(endY-startY, 2) + Math.pow(endX-startX, 2));
            radius = 50 - distance/15;
            radius = radius<20 ? 20 : radius;

            double a = Math.atan(dy / dx);
            float offsetX = (float) (radius * Math.sin(a));
            float offsetY = (float) (radius * Math.cos(a));

            // 根据角度算出四边形的四个点
            float x1 = startX - offsetX;
            float y1 = startY + offsetY;

            float x2 = endX - offsetX;
            float y2 = endY + offsetY;

            float x3 = endX + offsetX;
            float y3 = endY - offsetY;

            float x4 = startX + offsetX;
            float y4 = startY - offsetY;

            float anchorX = (startX + endX) / 2;
            float anchorY = (startY + endY) / 2;

            path.reset();

            path.reset();
            path.moveTo(x1, y1);
            path.quadTo(anchorX, anchorY, x2, y2);
            path.lineTo(x3, y3);
            path.quadTo(anchorX, anchorY, x4, y4);
            path.lineTo(x1, y1);


            canvas.drawPath(path,paint);

            canvas.drawCircle(endX,endY,radius,paint);
        }
    }
}
