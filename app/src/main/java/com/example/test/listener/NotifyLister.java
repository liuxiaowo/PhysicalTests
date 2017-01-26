package com.example.test.listener;

import android.os.Vibrator;

import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;

/**
 * Created by Administrator on 2017/1/26 0026.
 * 注册位置提醒监听事件后，可以通过SetNotifyLocation 来修改位置提醒设置，修改后立刻生效。
 * BDNotifyListner实现
 */

public class NotifyLister extends BDNotifyListener {
    Vibrator mVibrator01 = null;
    public void onNotify(BDLocation mlocation, float distance){
        mVibrator01.vibrate(1000);//振动提醒已到设定位置附近
    }
}
