package com.example.physicaltests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.adapter.GuideViewPagerAdapter;
import com.example.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private Button startBtn;
    // 引导页图片资源
    private static final int[] pics = { R.layout.guide_one,
            R.layout.guide_two, R.layout.guide_three,R.layout.guide_four};

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Log.e("xiawo","1");

        views = new ArrayList<View>();
        Log.e("xiawo","2");

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            Log.e("xiawo","3");
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            if (i == pics.length - 1) {
                Log.e("xiawo","4");
                startBtn = (Button) view.findViewById(R.id.btn_enter);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }
            views.add(view);
            Log.e("xiawo","5");
        }
        Log.e("xiawo","6");
        vp = (ViewPager) findViewById(R.id.vp_guide);
        Log.e("xiawo","7");
        adapter = new GuideViewPagerAdapter(views);
        Log.e("xiawo","8");
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new PageChangeListener());
        Log.e("xiawo","9");

        initDots();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("xiawo","12");
        // 如果切换到后台，就设置下次不进入功能引导页
        SharedPreferencesUtil.putBoolean(GuideActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
        finish();
        Log.e("xiawo","13");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initDots() {
        Log.e("xiawo","10");
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        Log.e("xiawo","21");
        dots = new ImageView[pics.length];
        Log.e("xiawo","22");

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            Log.e("xiawo","23");
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
            Log.e("xiawo","24");
        }
        Log.e("xiawo","25");
        currentIndex = 0;
        Log.e("xiawo","26");
        dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态

        Log.e("xiawo","11");

    }

    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        Log.e("xiawo","16");
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        Log.e("xiawo","13");
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }
        Log.e("xiawo","14");
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
        Log.e("xiawo","15");
    }


    private void enterMainActivity() {
        Intent intent = new Intent(GuideActivity.this,
                WelcomeActivity.class);
        startActivity(intent);
        SharedPreferencesUtil.putBoolean(GuideActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
        finish();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }

    }
}
