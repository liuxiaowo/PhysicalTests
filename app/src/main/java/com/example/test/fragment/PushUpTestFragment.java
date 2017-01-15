package com.example.test.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physicaltests.R;
import com.example.test.view.Chronometer01;

import static android.content.Context.SENSOR_SERVICE;

/**
 *俯卧撑-自测
 */
public class PushUpTestFragment extends Fragment implements View.OnClickListener{
    //返回按钮
    private ImageButton back;
    //倒计时
    private Chronometer01 timer;
    //倒计时开始
    private ImageButton start;
    //传感器管理器
    private SensorManager sensorManager;
    //传感器
    private Sensor sensor;
    //计数器
    private TextView countView;
    //数字
    private int count = 0;

    private boolean isCompleted = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_up_test, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //back btn
        back = (ImageButton)view.findViewById(R.id.back_push_up_test);
        //timer
        timer = (Chronometer01)view.findViewById(R.id.push_up_test_timer);
        start = (ImageButton)view.findViewById(R.id.push_up_start);
        back.setOnClickListener(this);
        start.setOnClickListener(this);
        timer.initTime(2*60);
        timer.reStart();
        //count
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        countView = (TextView)view.findViewById(R.id.push_up_test_count);

        timer.setOnTimeCompleteListener(new Chronometer01.OnTimeCompleteListener()
        {
            @Override
            public void onTimeComplete()
            {
                isCompleted = true;
            }
        });
        return view;
    }

    public static PushUpTestFragment newInstance() {
        PushUpTestFragment fragment = new PushUpTestFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回按钮
            case R.id.back_push_up_test:
                getActivity().finish();
                break;
            //倒计时重置按钮
            case R.id.push_up_start:
                timer.reStart();
                count = 0;
                countView.setText(count+"");
                isCompleted = false;
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {// 注册监听器
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
            sensorManager.registerListener(mProximityListener , sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(mProximityListener );
        }
    }

    private SensorEventListener mProximityListener  = new SensorEventListener() {
        boolean isHigh=false,isLow=false,isHighAgain=false;
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }

        @Override
        public void onSensorChanged(SensorEvent arg0) {
            float[] val=arg0.values;
            if(val[0]>=sensor.getMaximumRange()){
                isHigh=true;
                Log.e("rangeSensor", "高度到达");
            }

            if(isHigh&&val[0]<=3){
                isLow=true;
                Log.e("rangeSensor", "低度到达");
            }

            if(isLow&&val[0]>=sensor.getMaximumRange()){
                isHighAgain=true;
                Log.e("rangeSensor", "高度再次到达");
            }

            if(isHighAgain){
                count++;
                countView.setText(count+"");
                isHigh=isLow=isHighAgain=false;
                Log.e("rangeSensor", "俯卧撑加1");
            }
        }

    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //自测fragment显示同时自测结束后的提示框（避免在其他fragment弹出该土司）
            if(isCompleted){
                Toast.makeText(getContext(), "自测结束,恭喜您做了"+count+"个俯卧撑", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
