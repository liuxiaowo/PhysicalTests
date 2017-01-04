package com.example.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.physicaltests.R;

/**
 *俯卧撑-练习
 */
public class PushUpExerciseFragment extends Fragment implements View.OnClickListener{
    private ImageButton back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_up_exercise, container, false);
        back = (ImageButton)view.findViewById(R.id.back_push_up_exercise);
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
}
