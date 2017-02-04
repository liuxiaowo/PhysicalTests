package com.example.test.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.physicaltests.R;
import com.example.test.listener.BaiduLocationListener;
import com.example.test.listener.NotifyLister;
import com.example.test.utils.BaiduMapUtil;

import java.util.ArrayList;

/**
 * 跑步-3000米
 */
public class RunningThreeFragment extends Fragment implements View.OnClickListener {
    //返回按钮
    private ImageButton back, start;
    //百度定位
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new BaiduLocationListener();
    private NotifyLister mNotifyer = null;
    //百度地图
    private MapView mMapView = null;
    //判断是开始还是暂停
    private int isStarted = 0;
    //计数显示View,距离,速度
    private TextView countView,tvDistance,tvSpeed;
    //计步count
    private int runCount = 0;
    private Handler handler = new Handler();
    // 放线的坐标
    private ArrayList<LatLng> pointList = new ArrayList<LatLng>();
    // 更新统计界面可的数据
    private Runnable updateDataRunnable;
    // 管理地图
    private BaiduMap baiduMap;
    //倒计时
    private Chronometer meter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getActivity());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_three, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //back btn
        back = (ImageButton) view.findViewById(R.id.back_running_three);
        start = (ImageButton) view.findViewById(R.id.start_running_three);
        countView = (TextView) view.findViewById(R.id.run_count);
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        // 找到计时控件
        meter = (Chronometer) view.findViewById(R.id.chronometer1);
        // 找显示公里，显示速度的控件
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        tvSpeed = (TextView) view.findViewById(R.id.tv_recorder_speed);
        back.setOnClickListener(this);
        start.setOnClickListener(this);
        //划线
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latlng) {
                // 清除地图上的图
                /**
                 * baiduMap.clear(); Log.i("定位", latlng.longitude + "," +
                 * latlng); MarkerOptions options = new MarkerOptions();
                 * options.position(latlng);
                 * options.icon(BitmapDescriptorFactory
                 * .fromResource(R.drawable.icon_marka));
                 * baiduMap.addOverlay(options);
                 */
                // 用户在跑步，跑的时候，得坐标，放到list
                // 模拟得到坐标
                pointList.add(latlng);
                // 画线
                if (pointList.size() >= 2) {
                    PolylineOptions polylineOptions = new PolylineOptions();
                    // 设置线的坐标
                    polylineOptions.points(pointList);
                    baiduMap.clear();
                    baiduMap.addOverlay(polylineOptions);
                }
            }

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        return view;
    }

    public static RunningThreeFragment newInstance() {
        RunningThreeFragment fragment = new RunningThreeFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            case R.id.back_running_three:
                getActivity().finish();
                break;
            //跑步开始/暂停
            case R.id.start_running_three:
                try {
                    //开始状态
                    if (isStarted % 2 == 0) {
                        start.setBackgroundResource(R.drawable.run_stop);
                        // 更新count
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                runCount++;
                                countView.setText(runCount+"");
                                showReocorder();
                            }
                        }, 1000);
                    } else { //暂停状态
                        start.setBackgroundResource(R.drawable.run_start);
                        pointList.clear();
                        baiduMap.clear();
                        //前面执行handler.post(runnable)
                        //必须移出
                        handler.removeCallbacks(updateDataRunnable);
                    }
                    isStarted++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 定位提醒
     */
    private void SetNotifyLocation() {
        //位置提醒相关代码
        mNotifyer = new NotifyLister();
        mNotifyer.SetNotifyLocation(42.03249652949337, 113.3129895882556, 3000, "gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
        mLocationClient.registerNotify(mNotifyer);
        //取消位置提醒
        mLocationClient.removeNotifyEvent(mNotifyer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    // 2.3 写实现类
    class BaiduBdlocationListener implements BDLocationListener {

        // 百度地图框架定位成功后，回调onReceiveLocation
        @Override
        public void onReceiveLocation(BDLocation bDLocation) {
            // 得经度
            double longitude = bDLocation.getLongitude();
            // 得纬度
            double latitdue = bDLocation.getLatitude();

            Log.i("定位", "经度=" + longitude + ",纬度=" + latitdue);
            // 在原生模拟器上定位不成功，得到的是4.9E-324
            // 模拟一个位置
            if (latitdue == 4.9E-324) {
                // 没有得到坐标
                latitdue = 39.917263;
                longitude = 116.392151;
            }

            // 移动地图
            // LatLng：放的是坐标
            LatLng currentLocation = new LatLng(latitdue, longitude);
            // 17:地图显示的级别(4-17)
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                    .newLatLngZoom(currentLocation, 15);
            // 以动画的方式移动
            baiduMap.animateMapStatus(mapStatusUpdate);

            // 显示图片
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            // 创建一个能在地图上显示的图
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon);
            markerOptions.icon(bitmapDescriptor);
            baiduMap.addOverlay(markerOptions);
        }

    }

    public void showReocorder() {
        // 开始计时
        meter.start();
        // 从那个时候开始
        meter.setBase(SystemClock.elapsedRealtime());
        tvDistance.setText("0.00");
        tvSpeed.setText("0.00");

        updateDataRunnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Log.i("sportFragment", "run");
                    if (pointList.size() >= 2) {

                        // 更新公里
                        double distance = 0;
                        // list[0] 112,39
                        // list[1] 113,39
                        // list[2] 114,39
                        for (int i = 0; i < pointList.size() - 1; i++) {
                            double lon1 = pointList.get(i).longitude;
                            double lat1 = pointList.get(i).latitude;
                            double lon2 = pointList.get(i + 1).longitude;
                            double lat2 = pointList.get(i + 1).latitude;

                            // BaiduMapUtil.GetDistance 计算出的结果不是公里，是米
                            distance = distance
                                    + BaiduMapUtil.GetDistance(lon1, lat1,
                                    lon2, lat2);
                        }
                        // 把米转成公里
                        distance = distance / 1000;
                        String strDistance = String.valueOf(distance);
                        // 1.068766
                        if (strDistance.contains(".")) {
                            int pointIndex = strDistance.indexOf(".");
                            strDistance = strDistance.substring(0,
                                    pointIndex + 3);
                        }
                        tvDistance.setText(strDistance);
                        // 得时间
                        // 01:32
                        // [0] 01
                        // [1] 32
                        String showTime = meter.getText().toString();
                        double minute = 0, second = 0, hour = 0;
                        String strMinute = showTime.split(":")[0];
                        minute = Double.parseDouble(strMinute);

                        second = Double.parseDouble(showTime.split(":")[1]);

                        hour = minute * 60 + second / 60 / 60;
                        // 更新速度
                        double speed = distance / hour;
                        String strSpeed = String.valueOf(speed);
                        if (strSpeed.contains(".")) {
                            int pointIndex = strSpeed.indexOf(".");
                            strSpeed = strSpeed.substring(0, pointIndex + 3);
                            tvSpeed.setText(strSpeed);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 再次调用run
                    handler.postDelayed(this, 2000);
                }
            }
        };
        // 让updateDataRunnable运行
        handler.post(updateDataRunnable);
    }
}

