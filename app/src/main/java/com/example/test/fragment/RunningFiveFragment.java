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
 *跑步-5000米
 */
public class RunningFiveFragment extends Fragment implements View.OnClickListener{
    //返回按钮
    private ImageButton back;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_five, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //back btn
        back = (ImageButton)view.findViewById(R.id.back_running_five);
        back.setOnClickListener(this);
        return view;
    }

    public static RunningFiveFragment newInstance() {
        RunningFiveFragment fragment = new RunningFiveFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回按钮
            case R.id.back_running_five:
                getActivity().finish();
                break;
        }
    }

}
