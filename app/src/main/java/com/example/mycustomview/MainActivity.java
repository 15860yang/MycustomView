package com.example.mycustomview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mycustomview.MyView.MyPropertyAnimatorCircleView;
import com.example.mycustomview.MyView.useObjectAnimatorTOChangeMyView;
import com.example.mycustomview.MyViewGroup.DropDownMenu;

import java.util.ArrayList;

/**
 * Created by 杨豪 on 2018/5/22.
 */

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private Button bt;
    private ArgbEvaluator argbEvaluator;
    private ImageView iv;

    private Button propertyBt;//属性动画按钮

    DropDownMenu menu;
    ArrayList<String> title;
    ArrayList<ArrayList<String>> arrayLists;
    TextView view;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setColorChange();
//        setPropertyAnimator();
//        setPropertyView();
//        useObjectAnimatorTOChangeMyView();
//        useKeyFrameTOChangeMyView();
//        useAnimatorSetTochangeView();
//        setLinearAnimation();
//        setLinearAnimationMore();
        setMyDropMenuView();

//        TestTranslate();

    }

    private void TestTranslate() {
        Button b = findViewById(R.id.main_translateBt);
        final Button button = findViewById(R.id.main_Bt1);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.outtranslate);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.startAnimation(animation);
            }
        });
    }

    private void setMyDropMenuView() {
        menu = findViewById(R.id.main_myDropMenuView);
        title = new ArrayList<>();
        title.add("班级");
        title.add("性别");
        title.add("年龄");
        arrayLists = new ArrayList<>();
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("一年级");
        arrayList1.add("二年级");
        arrayList1.add("三年级");
        arrayList1.add("四年级");
        arrayList1.add("五年级");
        arrayList1.add("六年级");
        arrayList1.add("七年级");
        arrayList1.add("八年级");
        arrayList1.add("九年级");
        arrayList1.add("十年级");
        arrayList1.add("十一年级");
        arrayList1.add("十二年级");
        arrayLists.add(arrayList1);
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("男");
        arrayList2.add("女");
        arrayList2.add("保密");
        arrayLists.add(arrayList2);
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList3.add("<= 6");
        arrayList3.add("6 - 12");
        arrayList3.add("12 - 18");
        arrayList3.add("18 - 22");
        arrayList3.add("22 - 30");
        arrayLists.add(arrayList3);

        view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setGravity(Gravity.BOTTOM);
        view.setTextColor(Color.BLACK);
        view.setTextSize(50);
        view.setText("主页面");

        menu.setMyDropMenuDate(title,arrayLists,view);

        menu.setListener(new DropDownMenu.SelectListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void slect(int mainPosition, int itemPosition) {
                String s = "slect: 你选择了第"+ mainPosition+"栏，第"+itemPosition+"项";
                Log.d(TAG, s);
                view.setText(s);
            }
        });
    }


    /**
     * 布局动画进阶
     */
    static int dis = 0;
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setLinearAnimationMore() {
        final LinearLayout layout = findViewById(R.id.main_Ll);
        Button add = findViewById(R.id.main_ll_Add);
        final Button remove = findViewById(R.id.main_ll_Remove);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = new Button(getBaseContext());
                button.setText("新加button"+ dis++);
                button.setAlpha(0);
                layout.addView(button,2);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt = layout.getChildCount();
                if(cnt > 2){
                    layout.removeViewAt(2);
                }
            }
        });

        LayoutTransition transition = new LayoutTransition();

        //设置新子项进场动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "translationX", -1000f, 0f);
        ObjectAnimator animator1 = ObjectAnimator.ofArgb(null,"backgroundColor",Color.BLUE,Color.GREEN);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(null,"alpha",0,1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(animator1).with(animator2);
        transition.setAnimator(LayoutTransition.APPEARING,animatorSet);

        //设置新子项出场动画
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(null, "translationX",  0f,-1000f);
        ObjectAnimator animator4 = ObjectAnimator.ofArgb(null,"backgroundColor",Color.GREEN,Color.BLUE);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(null,"alpha",1,0);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.play(animator3).with(animator4).with(animator5);
        transition.setAnimator(LayoutTransition.DISAPPEARING, animatorSet1);

        //设置动画时长
        transition.setDuration(3000);
        layout.setLayoutTransition(transition);
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
