package com.example.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.physicaltests.R;

/**
 * 秒表计时
 */
public class TimerFragment extends Fragment implements View.OnClickListener{
    //秒表计时
    private Chronometer timer;
    //开始、暂停、重置
    private Button start,stop,reset;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        timer = (Chronometer)view.findViewById(R.id.timer);
        start = (Button)view.findViewById(R.id.timer_btnStart);
        stop = (Button)view.findViewById(R.id.timer_btnStop);
        reset = (Button)view.findViewById(R.id.timer_btnReset);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        reset.setOnClickListener(this);
        return view;
    }

    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //计时开始
            case R.id.timer_btnStart:
                timer.start();
                break;
            //计时暂停
            case R.id.timer_btnStop:
                timer.stop();
                break;
            //计时重置
            case R.id.timer_btnReset:
                timer.setBase(SystemClock.elapsedRealtime());// 复位
                break;
        }
    }
}
