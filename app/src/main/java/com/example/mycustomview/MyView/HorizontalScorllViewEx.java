package com.example.mycustomview.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by 杨豪 on 2018/5/6.
 */

public class HorizontalScorllViewEx extends ViewGroup {

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    //记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;



    //记录上次滑动的坐标（拦截方法中）
    private int mLastXIntercept = 0;
    private int mLastyIntercept = 0;

    //滑动和速度检测
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScorllViewEx(Context context) {
        super(context);
    }

    public HorizontalScorllViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScorllViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(){
        if(mScroller == null){
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }


    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //false 表示不拦截
                intercepted = false;
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    //终止动画，确认拦截点击事件
                    //这里我猜一下应该是正在滑动，然后手指点击了一下，于是就停止滑动，将手指点击的这次事件认为是终止滑动的事件
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastyIntercept;
                if(Math.abs(deltaX) > Math.abs(deltaY)){
                    //x方向大于y方向的位移，就代表是x方向的滑动,此时拦截
                    intercepted = true;
                }else {
                    //y方向的滑动，不拦截
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起事件，貌似没啥暖用
                intercepted = false;
                break;
            default:break;
        }

        //更新上次滑动的坐标
        mLastX = x;
        mLastyIntercept = x;
        mLastY = y;
        mLastyIntercept = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //给速度测量器加入事件，他就会测量这个事件的速度
        mVelocityTracker.addMovement(event);
        //拿到事件此时的坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //处理事件
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastY;
                int deltaY = y - mLastY;
                //scrollBy(int dx, int dy)主要用于滑屏操作,两个参数分别代表滑屏前与滑屏后的坐标之差
                //在这里手指按着滑动了多少，就是多少
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                //设置速度单位，1000表示一秒内的位移
                mVelocityTracker.computeCurrentVelocity(1000);
                //得到x方向的速度
                float xVelocity = mVelocityTracker.getXVelocity();
                if(Math.abs(xVelocity) >= 50){
                    //如果速度大于50，那么就让这次滑动直接向左或者向右滑动一个子View
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    //如果速度小于50，那么就根据此时的位置来确定滑动到上一个还是下一个子View的位置
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                //接下来判断是否滑到了最右边，如果是的话，那就滑不过去了
                mChildIndex = Math.max(0,Math.min(mChildIndex,mChildrenSize -1 ));
                //计算滑动的距离
                int dx = mChildIndex * mChildWidth - scrollX;
                //有我们自己实现的一个弹性滑动的方法，从当前x出往右滑动dx的距离
                smoothScrollBy(dx,0);
                //该有的速度测量完毕，清空数据
                mVelocityTracker.clear();
                break;
            default:break;
        }
        //滑动完成，表示已经处理该事件，所以返回真表示吸收
        return true;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpaceModel = MeasureSpec.getMode(heightMeasureSpec);


        if(childCount == 0){
            setMeasuredDimension(0,0);
        }else if(widthSpaceModel == MeasureSpec.AT_MOST && heightMeasureSpec == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth,measureHeight);
        } else if(heightSpaceModel == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize,measureHeight);
        } else if(widthSpaceModel == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measureWidth,heightSpaceSize);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        for(int i = 0;i<childCount;i++){
            final View childView = getChildAt(i);
            if(childView.getVisibility() != View.GONE){
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft,0,childLeft+childWidth,childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }
    /**
     * 弹性滑动，就是一个时间段一个时间段时间段接着去重绘，达到滑动的效果
     */

    private void smoothScrollBy(int dx,int dy){
        //保存滑动数据，开始x，y终止x，y，时间
        mScroller.startScroll(getScrollX(),0,dx,0,500);
        //开始滑动，一个类似循环的方法，
        invalidate();
    }

    //为配合上面的弹性滑动实现的方法
    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    //view不在显示状态时调用该方法
    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
