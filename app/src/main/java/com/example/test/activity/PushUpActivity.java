package com.example.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.physicaltests.R;

public class PushUpActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);
        //返回按钮
        back = (ImageButton)findViewById(R.id.back_push_up);
        add =(ImageButton)findViewById(R.id.add_push_up);
        test = (ImageButton)findViewById(R.id.test_push_up);
        check = (ImageButton)findViewById(R.id.check_push_up);
        exercise = (ImageButton)findViewById(R.id.exercise_push_up);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //返回按钮
            case R.id.back_push_up:
                finish();
                break;
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
        }
    }
}
