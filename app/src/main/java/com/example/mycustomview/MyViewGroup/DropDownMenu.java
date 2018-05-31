package com.example.mycustomview.MyViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycustomview.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 杨豪 on 2018/5/26.
 */

public class DropDownMenu extends ViewGroup {

    private final static String TAG = "DropDownMenu";

    private List<String> titleList;//标题集合
    private ArrayList<ArrayList<String>> titleItemList; //每个下拉菜单的列表的集合
    private List<ViewGroup> dropViewList = new ArrayList<>();//下拉ViewGroup集合

    private LinearLayout tabMenuView;//顶部选项按钮布局
    private FrameLayout frameLayout;//底部三层布局

    private View container; //主内容区域
    private View shadeView;//底下的遮罩
    private FrameLayout dropViewFrameLayout;


    private boolean isFirstAddView = true;


    private Animation inAnimation;//进场动画
    private Animation outAnimation;//出场动画


    private Context context;

    private int selectButtonColor = Color.GREEN;

    private int width; // 主体的宽
    private int height; // 主体的高

    private boolean isDropMenuShow = false;
    private int nowShowingMenuPosition = -1;
    private View showingMenu = null;


    private SelectListener listener;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            int i = msg.arg1;//recyclerView的序号
            int i1 = msg.arg2;//子项序号
            Button button = (Button) (tabMenuView.getChildAt(i*2));
            button.setText(titleItemList.get(i).get(i1));
            cancelDropMenuShow();
            if(listener != null){
                listener.slect(i,i1);
            }
            return true;
        }
    });

    public DropDownMenu(Context context) {
        this(context,null);
    }
    public DropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }
    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();

    }
    /**
     * 初始化
     *      顶部按钮集合
     *      主内容View----frameLayout
     *
     *      遮罩shadeView
     *      弹出菜单集合dropViewFrameLayout
     */
    private void init(){

        inAnimation = creatInAnimator();
        outAnimation = creatOutAnimator();

        //遮罩初始化设置为不可见
        shadeView = new TextView(context);
        shadeView.setVisibility(INVISIBLE);


        tabMenuView = new LinearLayout(context);
        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        tabMenuView.setLayoutParams(params1);
        tabMenuView.setOrientation(LinearLayout.HORIZONTAL);

        //标题栏底下的下划线
        View underLine = new View(context);

        underLine.setBackgroundColor(Color.GRAY);

        //主内容layout
        frameLayout = new FrameLayout(context);

        //弹出菜单集合
        dropViewFrameLayout = new FrameLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(params);

        this.addView(tabMenuView,0);
        this.addView(underLine,1);
        this.addView(frameLayout,2);
    }
    /**
     * 设置主要数据
     *      设置并添加顶部按钮
     *      主layout添加子项
     *      弹出菜单集合添加子项
     * @param titleList
     * @param titleItemList
     * @param container
     */
    public void setMyDropMenuDate(final List<String> titleList, ArrayList<ArrayList<String>> titleItemList, View container) {
        this.titleList = titleList;
        this.titleItemList = titleItemList;
        this.container = container;
    }

    /**
     * 设置按钮背景色
     */
    public void setSelectButtonColor(int selectButtonColor) {
        this.selectButtonColor = selectButtonColor;
    }

    /**
     * 设置监听器
     * @param listener
     */
    public void setListener(SelectListener listener) {
        this.listener = listener;
    }

    //将数据在合适的时间绑定在ViewGroup之上
    private void setMyDropMenuDate(){

        if(!isFirstAddView){
            return;
        }
        isFirstAddView = false;

        Log.d(TAG, "setMyDropMenuDate: ");

        for(int i = 0;i < titleList.size();i++){
            addTab(titleList.get(i),i);
        }
        for(int i = 0;i < titleList.size();i++){
            dropViewFrameLayout.addView(creatRecyclerView(titleItemList.get(i),i));
        }


        //这里具体原因也不清楚
        frameLayout.removeAllViews();


        ViewGroup parent = (ViewGroup) container.getParent();
        if(parent != null){
            parent.removeView(container);
        }else {
            Log.d(TAG, "setMyDropMenuDate: container 的 父布局为空");
        }
//        LayoutParams layoutParams = container.getLayoutParams();
//        Log.d(TAG, "setMyDropMenuDate: layoutParms.width = " + layoutParams.width+",height = " + layoutParams.height);
        
        
        container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        //添加主布局
        frameLayout.addView(container,0);


        shadeView.setBackgroundColor(0x88888888);
        //添加遮罩
        frameLayout.addView(shadeView,1);

        int fHeight = frameLayout.getMeasuredHeight();
        Log.d(TAG, "setMyDropMenuDate: 现在测量到的主布局的高 frameLayout.getMeasuredHeight() = " +fHeight);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (fHeight*0.65));
        dropViewFrameLayout.setLayoutParams(params);

        //添加弹出布局
        frameLayout.addView(dropViewFrameLayout,2);


        shadeView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: shadeView is Clicked");
                cancelDropMenuShow();
            }
        });


    }

    //取消显示
    private void cancelDropMenuShow(){
        showingMenu.clearAnimation();
        showingMenu.setAnimation(outAnimation);
        outAnimation.start();
        nowShowingMenuPosition = -1;
        isDropMenuShow = false;
    }


    private void addTab(String title, int position) {
        Button button = creatButton(title,position);

        int tabWidth = tabMenuView.getMeasuredWidth();
        Log.d(TAG, "addTab: tabWidth = " + tabWidth);
        LayoutParams params = new LayoutParams(tabWidth/3,LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        tabMenuView.addView(button);

        if (position != titleList.size()) {
            View view = new View(context);
            view.setLayoutParams(new LayoutParams(dpTpPx(1),LayoutParams.WRAP_CONTENT));
            tabMenuView.addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureModel= MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        if(widthMeasureModel == MeasureSpec.EXACTLY){  //是固定d数值或者match_Parent
            width = widthMeasureSize;
        }else {
            ViewGroup parent = (ViewGroup) getParent();
            width = parent.getMeasuredWidth();
        }
        if(heightMeasureModel == MeasureSpec.EXACTLY){
            height = heightMeasureSize;
        }else {
            ViewGroup parent = (ViewGroup) getParent();
            height = parent.getMeasuredHeight();
        }

        setMyDropMenuDate();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int titleHeight = tabMenuView.getChildAt(0).getMeasuredHeight();
        getChildAt(0).layout(0,0,width,titleHeight);//设置标题栏位置
        getChildAt(1).layout(0,titleHeight,width,titleHeight+3);
        container.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,height - titleHeight - 3));
        container.invalidate();


        getChildAt(2).layout(0,titleHeight+3,frameLayout.getMeasuredWidth(),frameLayout.getMeasuredHeight());

    }




    private Button creatButton(String title, final int position){
        Button button = new Button(context);
        button.setText(title);
        button.setGravity(Gravity.CENTER);
        button.setBackgroundColor(selectButtonColor);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                shadeView.setVisibility(VISIBLE);
                Log.d(TAG, "正在展示："+isDropMenuShow+",展示第"+nowShowingMenuPosition+"个");
                if(!isDropMenuShow){   //如果没在显示
                    Log.d(TAG, "从没展示到展示第"+position+"个");

                    showingMenu = dropViewFrameLayout.getChildAt(position);
                    showingMenu.setVisibility(VISIBLE);
                    showingMenu.clearAnimation();
                    showingMenu.setAnimation(inAnimation);
                    inAnimation.start();


                    isDropMenuShow = true;
                    nowShowingMenuPosition = position;
                }else if(isDropMenuShow && nowShowingMenuPosition != position){ // 如果当前下拉菜单正在展示，且不为点击的按钮之下的menu
                    Log.d(TAG, "从展示第"+nowShowingMenuPosition+"个到展示第"+position+"个");

                    showingMenu.setVisibility(INVISIBLE);
                    showingMenu = dropViewFrameLayout.getChildAt(position);
                    showingMenu.setVisibility(VISIBLE);
                    showingMenu.clearAnimation();
                    showingMenu.setAnimation(inAnimation);
                    inAnimation.start();

                    isDropMenuShow = true;
                    nowShowingMenuPosition = position;
                }else if(isDropMenuShow && nowShowingMenuPosition == position){  //正在显示当前点击视图
                    cancelDropMenuShow();
                }
            }
        });

        return button;
    }

    private RecyclerView creatRecyclerView(List<String> itemList,int position){

        RecyclerView recyclerView = new RecyclerView(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(params);
        recyclerView.setBackgroundColor(Color.WHITE);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        RecycleViewAdapter adapter = new RecycleViewAdapter(context,itemList,position,handler);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(GONE);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));

        return recyclerView;
    }

    /**
     * 创建进场动画
     * @return
     */
    private Animation creatInAnimator(){
        AnimationSet set = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
       TranslateAnimation translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(context,R.anim.in_translate);
        set.addAnimation(translateAnimation);
        set.addAnimation(alphaAnimation);
        set.setDuration(200);
        return set;
    }

    /**
     * 创建进场动画
     * @return
     */
    private Animation creatOutAnimator(){
        AnimationSet set = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        TranslateAnimation translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.outtranslate);
        set.addAnimation(alphaAnimation);
        set.addAnimation(translateAnimation);
        set.setDuration(200);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                shadeView.setVisibility(INVISIBLE);
                showingMenu.setVisibility(INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return set;
    }



    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    public interface SelectListener{
        void slect(int mainPosition,int itemPosition);
    }
}

class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    private List<String> dateList;
    private Context context;

    private final Handler handler;
    private final int rPosition;

    public RecycleViewAdapter(Context context,List<String> dateList,int rPosition,Handler handler){
        this.context = context;
        this.dateList = dateList;
        this.rPosition = rPosition;
        this.handler = handler;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycleViewItem_Iv);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleviewitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(dateList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.arg1 = rPosition;
                message.arg2 = position;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
}

