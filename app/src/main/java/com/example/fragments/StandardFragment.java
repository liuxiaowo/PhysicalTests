package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.physicaltests.R;

/**
 *考核标准
 */
public class StandardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard, container, false);
        return view;
    }

    public static StandardFragment newInstance() {
        StandardFragment fragment = new StandardFragment();
        return fragment;
    }

}
