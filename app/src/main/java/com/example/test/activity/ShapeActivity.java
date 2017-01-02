package com.example.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.physicaltests.R;
import com.example.view.RulerView;

public class ShapeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        //初始化页面
        initView();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_shape:
                finish();
                break;
        }
    }

    /**
     * 初始化页面
     */
    private void initView() {
        //返回按钮
        back = (ImageButton) findViewById(R.id.back_shape);

        final RulerView rulerView = (RulerView) findViewById(R.id.ruler_view);

        back.setOnClickListener(this);
        //标尺带有文字
        rulerView.isWithText();
        //自动对齐
        rulerView.isAutoAlign();
        //刻度间隔
        rulerView.setIndicatePadding(Integer.valueOf(10));
        //初值设置
        rulerView.smoothScrollTo(160);


        rulerView.setOnScaleListener(new RulerView.OnScaleListener() {
            @Override
            public void onScaleChanged(int scale) {
            }
        });
    }
}
