package com.example.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physicaltests.R;
import com.example.view.GaugeChart01View;
import com.example.view.RulerView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ShapeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    private RulerView rulerView;
    private TextView heightView;
    //圆
    private GaugeChart01View chart = null;
    //进度/状态
    private TextView  process = null;
    private SeekBar seekBar = null;
    //当前身高
    private double height = 1.6;
    //当前体重
    private double weight = 50.0;
    //当前体质指数
    private double KBI;
    //体型测试结果
    private String result;
    //结果按钮
    private ImageButton result_btn;
    //保留1位小数
    private DecimalFormat df  = new DecimalFormat("#.0");

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
            case R.id.result_shape:
                KBI =  div(weight,height*height,2);
                if(KBI>=18.5&&KBI<=23.9){
                    result = "体型合格";
                }else if(KBI>=24.0&&KBI<=27.9){
                    result = "超重";
                }else if(KBI>=28.0){
                    result = "肥胖";
                }else if(KBI<18.5&&KBI>=12.0){
                    result = "偏瘦";
                }else{
                    result = "消瘦";
                }
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
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
        heightView = (TextView)findViewById(R.id.shape_height);
        chart = (GaugeChart01View)findViewById(R.id.chart_view);
        process = (TextView)findViewById(R.id.tv_process);
        seekBar = (SeekBar) this.findViewById(R.id.seekBar1);
        //结果按钮
        result_btn = (ImageButton) findViewById(R.id.result_shape);

        //标尺带有文字
        rulerView.isWithText();
        //自动对齐
        rulerView.isAutoAlign();
        //刻度间隔
        rulerView.setIndicatePadding(Integer.valueOf(10));
        //初值设置(position-12)
        rulerView.smoothScrollTo(138);
        //最大角度
        seekBar.setMax(180);
        //默认值
        chart.setAngle(75);
        process.setText("您的体重为:"+Integer.toString(50)+"kg");
        chart.chartRender();
        chart.invalidate();
        seekBar.setProgress(75);


        back.setOnClickListener(this);
        result_btn.setOnClickListener(this);
        rulerView.setOnScaleListener(new RulerView.OnScaleListener() {
            @Override
            public void onScaleChanged(int scale) {
                heightView.setText("您的身高为:"+div(scale,100,2)+"m");
                height = div(scale,100,2);
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
                //1.5 = 180/120
                double progress_s = progress/1.5;
                process.setText("您的体重为:"+df.format(progress_s)+"kg");
                weight = progress_s;
                chart.setAngle(progress);
                chart.chartRender();
                chart.invalidate();
            }
        });
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
