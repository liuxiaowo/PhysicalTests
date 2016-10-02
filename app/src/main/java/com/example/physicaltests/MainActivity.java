package com.example.physicaltests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        //底部导航栏
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.Tab);
        //设置颜色（选中/未选中/背景颜色）
        bottomNavigationBar
                .setActiveColor(R.color.btn_color)
                .setInActiveColor("#EBE9ED")
                .setBarBackgroundColor("#FFFFFF");
        //设置导航栏背景模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        //背景颜色
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //静态工厂构造方法
        bottomNavigationBar .addItem(new BottomNavigationItem(R.mipmap.test,"测试"))
                .addItem(new BottomNavigationItem(R.mipmap.check, "考核"))
                .addItem(new BottomNavigationItem(R.mipmap.timer,"计时"))
                .addItem(new BottomNavigationItem(R.mipmap.standard,"标准"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });

    }
}
