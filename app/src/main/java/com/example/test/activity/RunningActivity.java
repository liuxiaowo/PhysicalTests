package com.example.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.physicaltests.R;

public class RunningActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        //返回按钮
        back = (ImageButton)findViewById(R.id.back_running);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_running:
                finish();
                break;
        }
    }
}
