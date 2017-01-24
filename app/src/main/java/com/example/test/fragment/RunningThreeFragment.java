package com.example.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.physicaltests.R;

/**
 *俯卧撑-自测
 */
public class RunningThreeFragment extends Fragment implements View.OnClickListener{
    //返回按钮
    private ImageButton back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_three, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //back btn
        back = (ImageButton)view.findViewById(R.id.back_running_three);
        back.setOnClickListener(this);
        return view;
    }

    public static RunningThreeFragment newInstance() {
        RunningThreeFragment fragment = new RunningThreeFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回按钮
            case R.id.back_running_three:
                getActivity().finish();
                break;
        }
    }
}