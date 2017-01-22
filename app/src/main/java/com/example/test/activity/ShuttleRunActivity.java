package com.example.test.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.example.physicaltests.R;

public class ShuttleRunActivity extends AppCompatActivity implements View.OnClickListener{

    //返回按钮
    private ImageButton back;
    //计时
    private Chronometer timer;
    //按钮
    private Button start,end,reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle_run);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_shuttle_run:
                finish();
                break;
            case R.id.btnStart:
                timer.start();
                break;
            case R.id.btnStop:
                timer.stop();
                break;
            case R.id.btnReset:
                timer.setBase(SystemClock.elapsedRealtime());// 复位
                break;
        }
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
        //返回按钮
        back = (ImageButton)findViewById(R.id.back_shuttle_run);
        timer = (Chronometer)findViewById(R.id.shuttle_run_timer);
        start = (Button)findViewById(R.id.btnStart);
        end = (Button)findViewById(R.id.btnStop);
        reset = (Button)findViewById(R.id.btnReset);
        back.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        reset.setOnClickListener(this);
    }
}
