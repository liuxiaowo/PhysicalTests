package com.example.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.physicaltests.R;

public class ShuttleRunActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle_run);
        //返回按钮
        back = (ImageButton)findViewById(R.id.back_shuttle_run);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_shuttle_run:
                finish();
                break;
        }
    }
}
