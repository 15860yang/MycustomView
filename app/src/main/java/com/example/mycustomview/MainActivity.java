package com.example.mycustomview;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
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
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by 杨豪 on 2018/5/22.
 */

public class MainActivity extends AppCompatActivity{

    private Button bt;
    private ArgbEvaluator argbEvaluator;
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setColorChange();



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
}
