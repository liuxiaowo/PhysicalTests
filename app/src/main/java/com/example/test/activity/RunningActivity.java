package com.example.test.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.physicaltests.R;
import com.example.test.fragment.RunningFiveFragment;
import com.example.test.fragment.RunningFreeFragment;
import com.example.test.fragment.RunningThreeFragment;

import java.util.ArrayList;

public class RunningActivity extends AppCompatActivity implements View.OnClickListener {
    //选项
    private ImageButton add;
    //3000跑按钮
    private ImageButton three_run;
    //5000跑按钮
    private ImageButton five_run;
    //自由跑按钮
    private ImageButton free_run;
    //周围几个按钮显示与否
    private boolean isVisible = false;
    //页面切换
    private ArrayList<Fragment> fragments;

    public static int isWhoFragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        initView();
    }

    private void initView() {
        //顶部栏背景颜色
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }

        add = (ImageButton) findViewById(R.id.add_running);
        three_run = (ImageButton) findViewById(R.id.three_thousand_running);
        five_run = (ImageButton) findViewById(R.id.five_thousand_running);
        free_run = (ImageButton) findViewById(R.id.free_running);
        add.setOnClickListener(this);
        three_run.setOnClickListener(this);
        five_run.setOnClickListener(this);
        free_run.setOnClickListener(this);

        //所有fragment
        fragments = getFragments();
        //默认fragment
        setDefaultFragment();
    }


    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.running_framelayout, RunningThreeFragment.newInstance());
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(RunningThreeFragment.newInstance());
        fragments.add(RunningFiveFragment.newInstance());
        fragments.add(RunningFreeFragment.newInstance());
        return fragments;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //选项按钮
            case R.id.add_running:
                if (isVisible) {
                    three_run.setVisibility(View.GONE);
                    five_run.setVisibility(View.GONE);
                    free_run.setVisibility(View.GONE);
                    isVisible = false;
                } else {
                    three_run.setVisibility(View.VISIBLE);
                    five_run.setVisibility(View.VISIBLE);
                    free_run.setVisibility(View.VISIBLE);
                    isVisible = true;
                }
                break;
            //测试
            case R.id.three_thousand_running:
                if (isVisible) {
                    if (fragments != null) {
                        //添加测试
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(0);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.running_framelayout, fragment);
                        } else {
                            ft.add(R.id.running_framelayout, fragment);
                        }
                        //移除其他
                        ft.remove(fragments.get(1));
                        ft.remove(fragments.get(2));
                        ft.commitAllowingStateLoss();
                        isWhoFragment = 1;
                    }
                }
                break;
            //考核
            case R.id.five_thousand_running:
                if (isVisible) {
                    if (fragments != null) {
                        //添加考核
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(1);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.running_framelayout, fragment);
                        } else {
                            ft.add(R.id.running_framelayout, fragment);
                        }
                        //移除其他
                        ft.remove(fragments.get(0));
                        ft.remove(fragments.get(2));
                        ft.commitAllowingStateLoss();
                        isWhoFragment = 2;
                    }
                }
                break;
            //练习
            case R.id.free_running:
                if (isVisible) {
                    if (fragments != null) {
                        //添加练习
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(2);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.running_framelayout, fragment);
                        } else {
                            ft.add(R.id.running_framelayout, fragment);
                        }
                        //移除其他
                        ft.remove(fragments.get(1));
                        ft.remove(fragments.get(0));
                        ft.commitAllowingStateLoss();
                        isWhoFragment = 3;
                    }
                }
                break;
        }
    }
}
