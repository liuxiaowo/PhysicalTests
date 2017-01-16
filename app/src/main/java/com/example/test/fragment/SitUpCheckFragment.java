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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.physicaltests.R;
import com.example.test.activity.PushUpActivity;
import com.example.test.view.Chronometer01;

import static android.content.Context.SENSOR_SERVICE;

/**
 *俯卧撑-考核
 */
public class SitUpCheckFragment extends Fragment implements View.OnClickListener{
    //返回按钮
    private ImageButton back;
    //感应器管理器
    private SensorManager sensorManager;
    //方向传感器
    private MySensorEventListener mySensorEventListener = new MySensorEventListener();
    //俯卧撑计数
    private int count = 0;
    //俯卧撑数字显示View
    private TextView countView;
    //重置按钮
    private ImageButton reset;
    //倒计时
    private Chronometer01 timer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_up_check, container, false);
        back = (ImageButton)view.findViewById(R.id.back_push_up_check);
        countView = (TextView)view.findViewById(R.id.push_up_check_orientation_sensor);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        reset = (ImageButton)view.findViewById(R.id.push_up_check_reset);
        timer = (Chronometer01)view.findViewById(R.id.push_up_check_timer);
        timer.initTime(2*60);
        back.setOnClickListener(this);
        reset.setOnClickListener(this);
        timer.setOnTimeCompleteListener(new Chronometer01.OnTimeCompleteListener()
        {
            @Override
            public void onTimeComplete()
            {
                if(PushUpActivity.isWhoFragment==2) {
                    Toast.makeText(getContext(), "考核结束,恭喜您做了" + count + "个俯卧撑", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public static SitUpCheckFragment newInstance() {
        SitUpCheckFragment fragment = new SitUpCheckFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.back_push_up_check:
                getActivity().finish();
                break;
            //重置
            case R.id.push_up_check_reset:
                timer.reStart();
                count = 0;
                countView.setText(count+"");
                break;
        }
    }

    /**
     * 方向传感器监听器
     */
    class MySensorEventListener implements SensorEventListener {

        boolean isBend=false,isStraight=false,isStraightAgain=false;
        @Override
        //可以得到传感器实时测量出来的变化值
        public void onSensorChanged(SensorEvent event) {
            //方向传感器
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                float y = event.values[SensorManager.DATA_Y];
                if(y>=-100&&y<=-80){
                    isStraight = true;
                    Log.e("directionSensor","直臂");
                }
                if(y>=-10&&y<=10){
                    isBend = true;
                    Log.e("directionSensor","曲臂");
                }
                if(isBend&&y>=-100&&y<=-80){
                    isStraightAgain = true;
                    Log.e("directionSensor","再次直臂");
                }
                //俯卧撑加1
                if(isStraightAgain){
                    isStraight = isBend = isStraightAgain = false;
                    count++;
                    countView.setText(count+"");
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
        if(sensorManager!=null) {
            Sensor sensor_orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            sensorManager.registerListener(mySensorEventListener, sensor_orientation, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //注销所有传感器的监听
        if(mySensorEventListener!=null&&sensorManager!=null)
            sensorManager.unregisterListener(mySensorEventListener);
    }
}
