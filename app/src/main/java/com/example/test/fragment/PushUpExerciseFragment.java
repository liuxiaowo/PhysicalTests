package com.example.test.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.physicaltests.R;

import static android.content.Context.SENSOR_SERVICE;

/**
 *俯卧撑-练习
 */
public class PushUpExerciseFragment extends Fragment implements View.OnClickListener{
    //返回按钮
    private ImageButton back;
    //方向感应器值
    private TextView level;
    //感应器管理器
    private SensorManager sensorManager;
    //方向传感器
    private MySensorEventListener mySensorEventListener = new MySensorEventListener();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_up_exercise, container, false);
        back = (ImageButton)view.findViewById(R.id.back_push_up_exercise);
        level = (TextView)view.findViewById(R.id.push_up_ex_orientation_sensor);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        back.setOnClickListener(this);
        return view;
    }

    public static PushUpExerciseFragment newInstance() {
        PushUpExerciseFragment fragment = new PushUpExerciseFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_push_up_exercise:
                getActivity().finish();
                break;
        }
    }

    /**
     * 方向传感器监听器
     */
   class MySensorEventListener implements SensorEventListener {
        @Override
        //可以得到传感器实时测量出来的变化值
        public void onSensorChanged(SensorEvent event) {
            //方向传感器
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                //x表示手机指向的方位，0表示北,90表示东，180表示南，270表示西
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                //tv_orientation是界面上的一个TextView标签，不再赘述
                level.setText(x + "," + y + "," + z);
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
