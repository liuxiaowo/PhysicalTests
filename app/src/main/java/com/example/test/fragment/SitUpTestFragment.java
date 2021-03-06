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
import com.example.test.activity.SitUpActivity;
import com.example.test.view.Chronometer01;

import static android.content.Context.SENSOR_SERVICE;

/**
 * 仰卧起坐-自测
 */
public class SitUpTestFragment extends Fragment implements View.OnClickListener {
    //返回按钮
    private ImageButton back;
    //倒计时
    private Chronometer01 timer;
    //倒计时开始
    private ImageButton start;
    //传感器管理器
    private SensorManager sensorManager;
    //计数器
    private TextView countView;
    //数字
    private int count = 0;
    //方向传感器
    private MySensorEventListener mySensorEventListener = new MySensorEventListener();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sit_up_test, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //back btn
        back = (ImageButton) view.findViewById(R.id.back_sit_up_test);
        //timer
        timer = (Chronometer01) view.findViewById(R.id.sit_up_test_timer);
        start = (ImageButton) view.findViewById(R.id.sit_up_reset);
        back.setOnClickListener(this);
        start.setOnClickListener(this);
        timer.initTime(2 * 60);
        //count
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        countView = (TextView) view.findViewById(R.id.sit_up_test_count);

        timer.setOnTimeCompleteListener(new Chronometer01.OnTimeCompleteListener() {
            @Override
            public void onTimeComplete() {
                if (SitUpActivity.isWhoFragment == 1) {
                    Toast.makeText(getContext(), "自测结束,恭喜您做了" + count + "个仰卧起坐", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public static SitUpTestFragment newInstance() {
        SitUpTestFragment fragment = new SitUpTestFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            case R.id.back_sit_up_test:
                getActivity().finish();
                break;
            //倒计时重置按钮
            case R.id.sit_up_reset:
                timer.reStart();
                count = 0;
                countView.setText(count + "");
                break;
        }
    }


    /**
     * 方向传感器监听器
     */
    class MySensorEventListener implements SensorEventListener {

        boolean isBend = false, isStraight = false, isStraightAgain = false;

        @Override
        //可以得到传感器实时测量出来的变化值
        public void onSensorChanged(SensorEvent event) {
            //方向传感器
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                float z = event.values[SensorManager.DATA_Z];
                if ((z <= 0 && z >= -10) || (z >= 0 && z <= 10)) {
                    isStraight = true;
                    Log.e("directionSensor", "躺下");
                }
                //第二次转为45或-45时起作用
                if ((z >= -90 && z <= -80) || (z >= 80 && z <= 90)) {
                    isBend = true;
                    Log.e("directionSensor", "起来90度");
                }
                if (isBend && ((z <= 0 && z >= -10) || (z >= 0 && z <= 10))) {
                    isStraightAgain = true;
                    Log.e("directionSensor", "再次躺下");
                }
                //仰卧起坐加1
                if (isStraightAgain) {
                    isStraight = isBend = isStraightAgain = false;
                    count++;
                    countView.setText(count + "");
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {
            Sensor sensor_orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            sensorManager.registerListener(mySensorEventListener, sensor_orientation, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //注销所有传感器的监听
        if (mySensorEventListener != null && sensorManager != null)
            sensorManager.unregisterListener(mySensorEventListener);
    }
}
