package com.example;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2017/2/2 0002.
 */

public class PTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //百度地图sdk初始化
        SDKInitializer.initialize(getApplicationContext());
    }
}
