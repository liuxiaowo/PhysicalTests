package com.example.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.physicaltests.R;
import com.example.test.view.Chronometer01;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_up_test, container, false);
        back = (ImageButton)view.findViewById(R.id.back_push_up_test);
        timer = (Chronometer01)view.findViewById(R.id.push_up_test_timer);
        start = (ImageButton)view.findViewById(R.id.push_up_start);
        back.setOnClickListener(this);
        start.setOnClickListener(this);
        timer.initTime(2*60);
        timer.setOnTimeCompleteListener(new Chronometer01.OnTimeCompleteListener()
        {
            @Override
            public void onTimeComplete()
            {
                Toast.makeText(getContext(), "2分钟倒计时时间到!", Toast.LENGTH_SHORT).show();
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
                break;
        }
    }
}
