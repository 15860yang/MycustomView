package com.example.mycustomview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mycustomview.MyView.MyPropertyAnimatorCircleView;
import com.example.mycustomview.MyView.useObjectAnimatorTOChangeMyView;

/**
 * Created by 杨豪 on 2018/5/22.
 */

public class MainActivity extends AppCompatActivity{

    private Button bt;
    private ArgbEvaluator argbEvaluator;
    private ImageView iv;

    private Button propertyBt;//属性动画按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setColorChange();
        setPropertyAnimator();
        setPropertyView();
        useObjectAnimatorTOChangeMyView();
        useKeyFrameTOChangeMyView();
        useAnimatorSetTochangeView();

        setLinearAnimation();




    }

    /**
     * 为布局设置动画
     */
    private void setLinearAnimation() {

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.my_linear_anim);

        LayoutAnimationController controller = new LayoutAnimationController(animation,1);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);

        GridLayout linearLayout = findViewById(R.id.main_layout);
        linearLayout.setLayoutAnimation(controller);
    }


    private void useAnimatorSetTochangeView() {
        Button button = findViewById(R.id.main_useAnimatorSetBt);
        ImageView imageView = findViewById(R.id.main_useAnimatorSetIv);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView,"translationY",0,800,400,600);
        animator.setDuration(1000);
        ObjectAnimator animator2 = ObjectAnimator.ofArgb(button,"backgroundColor",Color.RED,Color.GREEN,Color.BLUE);
        animator2.setDuration(2000);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView,"translationX",0,300,150,350);
        animator1.setDuration(2000);
        final AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(animator).after(1000);
//        animatorSet.play(animator2).after(animator);
//        animatorSet.play(animator1).with(animator2);
//
//        animatorSet.play(animator1).with(animator2).after(animator).after(1000);

//        AnimatorSet.Builder builder = animatorSet.play(animator1);
//        builder.with(animator2);
//        builder.after(animator);
//        builder.after(1000);


        animator2.setStartDelay(1000);
        animatorSet.play(animator).with(animator2);

        animatorSet.setStartDelay(1000);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet.start();
            }
        });
    }
    /**
     * 使用关键帧去构建动画
     *
     */
    private void useKeyFrameTOChangeMyView() {
        Button button = findViewById(R.id.main_useKeyFrameViewBt);
        ImageView imageView = findViewById(R.id.main_useKeyFrameViewIv);
        Keyframe keyframe = Keyframe.ofFloat(0,0);
        Keyframe keyframe0 = Keyframe.ofFloat(0.3f,-200);
        Keyframe keyframe1 = Keyframe.ofFloat(0.5f,200);
        Keyframe keyframe2 = Keyframe.ofFloat(1f,100);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("translationY",keyframe,keyframe0
        ,keyframe1,keyframe2);

        Keyframe keyframe3 = Keyframe.ofFloat(0,0);
        Keyframe keyframe4 = Keyframe.ofFloat(0.3f,-200);
        Keyframe keyframe5 = Keyframe.ofFloat(0.5f,200);
        Keyframe keyframe6 = Keyframe.ofFloat(1f,100);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofKeyframe("translationX",keyframe3,keyframe4
                ,keyframe5,keyframe6);

        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView,holder,holder1);
        animator.setDuration(3000);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
            }
        });
    }

    /**
     * object的基本使用
     *
     */
    private void useObjectAnimatorTOChangeMyView() {
        useObjectAnimatorTOChangeMyView view = findViewById(R.id.main_useObjectAnimatorView);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view,"radius",0,100,200,300);

        objectAnimator.setDuration(3000);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator.start();
            }
        });

    }

    private void setPropertyView() {
        final MyPropertyAnimatorCircleView circleView = findViewById(R.id.main_Circle);
        final ValueAnimator animator = ValueAnimator.ofObject(new MyEvaluator(),0,100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleView.setRadius((Integer) animation.getAnimatedValue());
                circleView.invalidate();
            }
        });
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
            }
        });
    }
    //给按钮设置属性动画
    void setPropertyAnimator(){
        propertyBt = findViewById(R.id.main_PropertyBt);
        final ValueAnimator animator = ValueAnimator.ofInt(0,400,100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int i = (Integer) animation.getAnimatedValue();
                propertyBt.layout(i,i,i+propertyBt.getWidth(),i+propertyBt.getHeight());
            }
        });
        animator.setDuration(3000);
        animator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                float i;
                if(input > 0.5){
                    i = (float) (0.01 + (1 - 0.01) * ((input - 0.5) / (1 - 0.01)));
                }else {
                    i = (float) (0 + (0.01 - 0) * ((input - 0) / (0.01)));
                }
                return i;
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                int startInt = startValue;
                return (int)(startInt + (fraction) * (endValue - startInt));

            }
        });

        propertyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
            }
        });
    }

    /**
     * 按钮颜色渐变效果以及图片补间动画
     */
    void setColorChange(){
        final int c2 = ContextCompat.getColor(this, R.color.colorPrimary);
        final int c1 =  ContextCompat.getColor(this, R.color.colorAccent);
        argbEvaluator = new ArgbEvaluator();
        bt = findViewById(R.id.main_Bt);
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                for (int i = 0;i<100;i++){
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int c  = (int) argbEvaluator.evaluate((float) i/100, c1, c2);
                    Message message = Message.obtain();
                    message.what = 1;

                    message.arg1 = c;
                    handler.sendMessage(message);
                }
            }
        });
        iv = findViewById(R.id.main_Iv);
        final AnimationSet animator = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.animation);
        animator.setDuration(1000);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.start();
                iv.startAnimation(animator);
            }
        });
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d("-------",msg.what+"");
            bt.setBackgroundColor(msg.arg1);
            return true;
        }
    });

    private class MyEvaluator implements TypeEvaluator<Integer> {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            return startValue + (int)((endValue - startValue)*fraction);
        }
    }
}
