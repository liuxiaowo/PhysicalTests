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
import com.example.test.fragment.PushUpCheckFragment;
import com.example.test.fragment.PushUpExerciseFragment;
import com.example.test.fragment.PushUpTestFragment;

import java.util.ArrayList;

public class PushUpActivity extends AppCompatActivity implements View.OnClickListener {

    //选项
    private ImageButton add;
    //测试按钮
    private ImageButton test;
    //考核按钮
    private ImageButton check;
    //练习按钮
    private ImageButton exercise;
    //周围几个按钮显示与否
    private boolean isVisible = false;
    //页面切换
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);
        initView();
    }

    private void initView(){
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

        add = (ImageButton) findViewById(R.id.add_push_up);
        test = (ImageButton) findViewById(R.id.test_push_up);
        check = (ImageButton) findViewById(R.id.check_push_up);
        exercise = (ImageButton) findViewById(R.id.exercise_push_up);
        add.setOnClickListener(this);
        test.setOnClickListener(this);
        check.setOnClickListener(this);
        exercise.setOnClickListener(this);

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
        transaction.replace(R.id.push_up_framelayout, PushUpTestFragment.newInstance());
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(PushUpTestFragment.newInstance());
        fragments.add(PushUpCheckFragment.newInstance());
        fragments.add(PushUpExerciseFragment.newInstance());
        return fragments;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //选项按钮
            case R.id.add_push_up:
                if (isVisible) {
                    test.setVisibility(View.GONE);
                    check.setVisibility(View.GONE);
                    exercise.setVisibility(View.GONE);
                    isVisible = false;
                } else {
                    test.setVisibility(View.VISIBLE);
                    check.setVisibility(View.VISIBLE);
                    exercise.setVisibility(View.VISIBLE);
                    isVisible = true;
                }
                break;
            //测试
            case R.id.test_push_up:
                if (isVisible) {
                    if (fragments != null) {
                        //添加测试
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(0);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.push_up_framelayout, fragment);
                        } else {
                            ft.add(R.id.push_up_framelayout, fragment);
                        }
                        //移除其他
                        ft.remove(fragments.get(1));
                        ft.remove(fragments.get(2));
                        ft.commitAllowingStateLoss();
                    }
                }
                break;
            case R.id.check_push_up:
                if (isVisible) {
                    if (fragments != null) {
                        //添加考核
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(1);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.push_up_framelayout, fragment);
                        } else {
                            ft.add(R.id.push_up_framelayout, fragment);
                        }
                        //移除其他
                        ft.remove(fragments.get(0));
                        ft.remove(fragments.get(2));
                        ft.commitAllowingStateLoss();
                    }
                }
                break;
            case R.id.exercise_push_up:
                if (isVisible) {
                    if (fragments != null) {
                        //添加测试
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments.get(2);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.push_up_framelayout, fragment);
                        } else {
                            ft.add(R.id.push_up_framelayout, fragment);
                        }
                        //移除其他
                        ft.remove(fragments.get(1));
                        ft.remove(fragments.get(0));
                        ft.commitAllowingStateLoss();
                    }
                }
                break;
        }
    }
}
