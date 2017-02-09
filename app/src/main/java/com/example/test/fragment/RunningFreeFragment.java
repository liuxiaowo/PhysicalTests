package com.example.test.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;
import com.baidu.trace.OnTrackListener;
import com.baidu.trace.Trace;
import com.example.physicaltests.R;
import com.example.test.data.RealtimeTrackData;
import com.example.test.service.GsonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *跑步-自由跑
 */
public class RunningFreeFragment extends Fragment implements View.OnClickListener{
    //返回按钮,开始按钮
    private ImageButton back,start;

    int gatherInterval = 2;  //位置采集周期 (s)
    int packInterval = 2;  //打包周期 (s)
    String entityName = null;  // entity标识
    long serviceId = 133586;// 鹰眼服务ID
    int traceType = 2;  //轨迹服务类型
    private static OnStartTraceListener startTraceListener = null;  //开启轨迹服务监听器
    private static OnStopTraceListener stopTraceListener = null;  //停止轨迹服务监听器

    private static MapView mapView = null;
    private static BaiduMap baiduMap = null;
    private static OnEntityListener entityListener = null;
    private RefreshThread refreshThread = null;  //刷新地图线程以获取实时点
    private static MapStatusUpdate msUpdate = null;
    private static BitmapDescriptor realtimeBitmap;  //图标
    private static OverlayOptions overlay;  //覆盖物
    private static List<com.baidu.mapapi.model.LatLng> pointList = new ArrayList<>();  //定位点的集合
    private static PolylineOptions polyline = null;  //路线覆盖物


    private Trace trace;  // 实例化轨迹服务
    private LBSTraceClient client;  // 实例化轨迹服务客户端
    //开始、暂停按钮切换
    private int isStarted = 1;
    //计时
    private Chronometer timer;
    //速度,距离
    private TextView speed,distanceView;
    /**
     * Track监听器
     */
    protected static OnTrackListener trackListener = null;
    //开始时间
    private int startTime = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running_free, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //back btn
        back = (ImageButton)view.findViewById(R.id.back_running_free);
        mapView = (MapView)view.findViewById(R.id.run_free_mapView);
        start = (ImageButton)view.findViewById(R.id.run_free_start);
        timer = (Chronometer)view.findViewById(R.id.run_free_timer);
        speed = (TextView) view.findViewById(R.id.run_free_speed);
        distanceView = (TextView) view.findViewById(R.id.run_free_distance);
        baiduMap = mapView.getMap();
        mapView.showZoomControls(false);
        entityName = getImei(getActivity());  //手机Imei值的获取，用来充当实体名
        client = new LBSTraceClient(getActivity());  //实例化轨迹服务客户端
        trace = new Trace(getActivity(), serviceId, entityName, traceType);  //实例化轨迹服务
        client.setInterval(gatherInterval, packInterval);  //设置位置采集和打包周期
        // 设置定位模式
        client. setLocationMode(LocationMode.High_Accuracy);

        initOnEntityListener();
        initOnStartTraceListener();

        client.startTrace(trace, startTraceListener);  // 开启轨迹服务
        //计时开始
        timer.start();
        //开始时间
        startTime = (int)(System.currentTimeMillis() / 1000);

        back.setOnClickListener(this);
        start.setOnClickListener(this);
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
            case R.id.run_free_start:
                //开始状态
                if (isStarted % 2 == 0) {
                    start.setBackgroundResource(R.drawable.run_stop);
                    pointList.clear();
                    baiduMap.clear();
                    client.startTrace(trace, startTraceListener);  // 开启轨迹服务
                    //计时开始
                    timer.start();
                }else{
                    start.setBackgroundResource(R.drawable.run_start);
                    client.stopTrace(trace,stopTraceListener); //结束轨迹服务
                    //计时暂停
                    timer.stop();
                }
                isStarted++;
                break;
        }
    }


    /**
     * 初始化设置实体状态监听器
     */
    private void initOnEntityListener(){

        //实体状态监听器
        entityListener = new OnEntityListener(){

            @Override
            public void onRequestFailedCallback(String arg0) {
                Looper.prepare();
                Toast.makeText(
                        getActivity(),
                        "entity请求失败的回调接口信息："+arg0,
                        Toast.LENGTH_SHORT)
                        .show();
                Looper.loop();
            }

            @Override
            public void onQueryEntityListCallback(String arg0) {
                /**
                 * 查询实体集合回调函数，此时调用实时轨迹方法
                 */
                showRealtimeTrack(arg0);
                //实时查询距离
                queryDistance(1,null,arg0);
            }

        };
    }



    /** 追踪开始或结束 */
    private void initOnStartTraceListener() {

        // 实例化开启轨迹服务回调接口
        startTraceListener = new OnStartTraceListener() {
            // 开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTraceCallback(int arg0, String arg1) {
                Log.i("TAG", "onTraceCallback=" + arg1);
                if(arg0 == 0 || arg0 == 10006){
                    startRefreshThread(true);
                }
            }

            // 轨迹服务推送接口（用于接收服务端推送消息，arg0 : 消息类型，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTracePushCallback(byte arg0, String arg1) {
                Log.i("TAG", "onTracePushCallback=" + arg1);
            }
        };

        //轨迹结束
        stopTraceListener = new OnStopTraceListener() {
            @Override
            public void onStopTraceSuccess() {
                //停止刷新
                startRefreshThread(false);
            }

            @Override
            public void onStopTraceFailed(int i, String s) {
                Log.i("TAG", "onStopTraceFailed=" + s);
            }
        };

        trackListener = new OnTrackListener() {

            // 请求失败回调接口
            @Override
            public void onRequestFailedCallback(String arg0) {
                // TODO Auto-generated method stub
                Log.i("TAG", "track请求失败回调接口消息 :"+ arg0);
            }

            @Override
            public void onQueryDistanceCallback(String arg0) {
                Log.i("TAG", "queryDistance回调消息 : "+ arg0);
                // TODO Auto-generated method stub
                try {
                    JSONObject dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && dataJson.getInt("status") == 0) {
                        double distance = (dataJson.getDouble("distance")/1000);
                        DecimalFormat df = new DecimalFormat("#.00");
                        distanceView.setText(df.format(distance));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.i("TAG", "queryDistance回调消息 : "+ arg0);
                }
            }

            @Override
            public Map<String, String> onTrackAttrCallback() {
                // TODO Auto-generated method stub
                System.out.println("onTrackAttrCallback");
                return null;
            }

        };

    }


    /**
     * 轨迹刷新线程
     * @author BLYang
     */
    private class RefreshThread extends Thread{

        protected boolean refresh = true;

        public void run(){

            while(refresh){
                queryRealtimeTrack();
                try{
                    Thread.sleep(packInterval * 1000);
                }catch(InterruptedException e){
                    System.out.println("线程休眠失败");
                }
            }

        }
    }

    /**
     * 查询实时线路
     */
    private void queryRealtimeTrack(){

        String entityName = this.entityName;
        String columnKey = "";
        int returnType = 0;
        int activeTime = 0;
        int pageSize = 10;
        int pageIndex = 1;

        this.client.queryEntityList(
                serviceId,
                entityName,
                columnKey,
                returnType,
                activeTime,
                pageSize,
                pageIndex,
                entityListener
        );

    }


    /**
     * 展示实时线路图
     * @param realtimeTrack
     */
    protected void showRealtimeTrack(String realtimeTrack){

        if(refreshThread == null || !refreshThread.refresh){
            return;
        }

        //数据以JSON形式存取
        RealtimeTrackData realtimeTrackData = GsonService.parseJson(realtimeTrack, RealtimeTrackData.class);

        if(realtimeTrackData != null && realtimeTrackData.getStatus() ==0){
            //速度
            speed.setText(realtimeTrackData.getEntities().get(0).getRealtime_point().getSpeed()+"");
            LatLng latLng = realtimeTrackData.getRealtimePoint();

            if(latLng != null){
                pointList.add(latLng);
                drawRealtimePoint(latLng);
            }
            else{
                Toast.makeText(getActivity(), "当前无轨迹点", Toast.LENGTH_LONG).show();
            }

        }

    }

    // 查询里程
    private void queryDistance(int processed, String processOption,String realtimeTrack) {
        //数据以JSON形式存取
        RealtimeTrackData realtimeTrackData = GsonService.parseJson(realtimeTrack, RealtimeTrackData.class);

        // entity标识
        String entityName = realtimeTrackData.getEntities().get(0).getEntity_name();

        // 是否返回纠偏后轨迹（0 : 否，1 : 是）
        int isProcessed = processed;

        // 里程补充
        String supplementMode = "run";
        String endTime = realtimeTrackData.getEntities().get(0).getModify_time();

        client.queryDistance(serviceId, entityName, isProcessed, processOption,
                supplementMode, startTime, Integer.decode(endTime), trackListener);
    }

    /**
     * 画出实时线路点
     * @param point
     */
    private void drawRealtimePoint(LatLng point){
        baiduMap.clear();
        MapStatus mapStatus = new MapStatus.Builder().target(point).zoom(18).build();
        msUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.location);
        overlay = new MarkerOptions().position(point)
                .icon(realtimeBitmap).zIndex(9).draggable(true);

        if(pointList.size() >= 2){
            polyline = new PolylineOptions().width(10).color(Color.RED).points(pointList);
        }

        addMarker();

    }


    private void addMarker(){

        if(msUpdate != null){
            baiduMap.setMapStatus(msUpdate);
        }

        if(polyline != null){
            baiduMap.addOverlay(polyline);
        }

        if(overlay != null){
            baiduMap.addOverlay(overlay);
        }


    }


    /**
     * 启动刷新线程
     * @param isStart
     */
    private void startRefreshThread(boolean isStart){

        if(refreshThread == null){
            refreshThread = new RefreshThread();
        }

        refreshThread.refresh = isStart;

        if(isStart){
            if(!refreshThread.isAlive()){
                refreshThread.start();
            }
        }
        else{
            refreshThread = null;
        }


    }


    /**
     * 获取手机的Imei码，作为实体对象的标记值
     * @param context
     * @return
     */

    private String getImei(Context context){
        String mImei = "NULL";
        try {
            mImei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            System.out.println("获取IMEI码失败");
            mImei = "NULL";
        }
        return mImei;
    }

}
