package com.example.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.physicaltests.R;

/**
 *俯卧撑-练习
 */
public class PushUpExerciseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_up_exercise, container, false);
        return view;
    }

    public static PushUpExerciseFragment newInstance() {
        PushUpExerciseFragment fragment = new PushUpExerciseFragment();
        return fragment;
    }
}
