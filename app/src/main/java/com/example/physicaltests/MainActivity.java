package com.example.physicaltests;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.fragments.CheckFragment;
import com.example.fragments.StandardFragment;
import com.example.fragments.TestFragment;
import com.example.fragments.TimerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  BottomNavigationBar.OnTabSelectedListener{

    private ArrayList<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        //底部导航栏
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        //设置颜色（选中/未选中/背景颜色）
        bottomNavigationBar
                .setActiveColor(R.color.btn_color)
                .setInActiveColor("#E2E0E9")
                .setBarBackgroundColor("#FFFFFF");
        //设置导航栏背景模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        //背景颜色
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //静态工厂构造方法
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.test, "测试").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.check, "考核").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.mipmap.timer, "计时").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.standard, "标准").setActiveColorResource(R.color.brown))
                .setFirstSelectedPosition(0)
                .initialise();
        //所有fragment
        fragments = getFragments();
        //默认fragment
        setDefaultFragment();
        //导航栏选择监听
        bottomNavigationBar.setTabSelectedListener(this);

    }

    /** * 设置默认的 */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame, TestFragment.newInstance());
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TestFragment.newInstance());
        fragments.add(CheckFragment.newInstance());
        fragments.add(TimerFragment.newInstance());
        fragments.add(StandardFragment.newInstance());
        return fragments;
    }


    //bottomNavigationBar.setTabSelectedListener
    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.layFrame, fragment);
                } else {
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
