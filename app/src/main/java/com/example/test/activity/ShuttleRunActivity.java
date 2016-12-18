package com.example.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.physicaltests.R;

public class ShuttleRunActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back_shape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        //返回按钮
        back_shape = (ImageButton)findViewById(R.id.back_shape);
        back_shape.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_shape:
                finish();
                break;
        }
    }
}
