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
public class RunningFreeFragment extends Fragment implements View.OnClickListener{
    //返回按钮
    private ImageButton back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_free, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //back btn
        back = (ImageButton)view.findViewById(R.id.back_running_free);
        back.setOnClickListener(this);
        return view;
    }

    public static RunningFreeFragment newInstance() {
        RunningFreeFragment fragment = new RunningFreeFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回按钮
            case R.id.back_running_free:
                getActivity().finish();
                break;
        }
    }
}
