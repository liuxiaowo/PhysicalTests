package com.example.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.physicaltests.R;
import com.example.view.GaugeChart01View;
import com.example.view.RulerView;

public class ShapeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    private RulerView rulerView;
    private TextView height;
    //圆
    private GaugeChart01View chart = null;
    //进度/状态
    private TextView  process = null;
    private SeekBar seekBar = null;
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
        rulerView = (RulerView) findViewById(R.id.ruler_view);
        height = (TextView)findViewById(R.id.shape_height);
        chart = (GaugeChart01View)findViewById(R.id.chart_view);
        process = (TextView)findViewById(R.id.tv_process);
        seekBar = (SeekBar) this.findViewById(R.id.seekBar1);

        //标尺带有文字
        rulerView.isWithText();
        //自动对齐
        rulerView.isAutoAlign();
        //刻度间隔
        rulerView.setIndicatePadding(Integer.valueOf(10));
        //初值设置(position-12)
        rulerView.smoothScrollTo(138);

        seekBar.setMax(180);

        back.setOnClickListener(this);
        rulerView.setOnScaleListener(new RulerView.OnScaleListener() {
            @Override
            public void onScaleChanged(int scale) {
                height.setText("您的身高为:"+scale+"cm");
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                process.setText(Integer.toString(progress));
                chart.setAngle(progress);
                chart.chartRender();
                chart.invalidate();
            }
        });
    }
}
