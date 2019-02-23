package com.yaving.yavingamap.map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.VisibleRegion;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.yaving.yavingamap.YavingAmapPlugin;
import com.yaving.yavingamap.channel.PermissionChannel;
import com.yaving.yavingamap.common.Convert;
import com.yaving.yavingamap.common.JsonEx;
import com.yaving.yavingamap.model.FixedMarkerOptions;
import com.yaving.yavingamap.model.MyLocationOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformView;


public class AMapController implements Application.ActivityLifecycleCallbacks, PlatformView, AMap.OnMarkerClickListener, MarkerClickedListener, MethodChannel.MethodCallHandler, AMap.OnCameraChangeListener, AMap.OnMapLoadedListener {

    //    地图
    private TextureMapView mapView;

    private final PluginRegistry.Registrar registrar;
    private final AtomicInteger activityState;
    private final int registrarActivityHashCode;
    private final Context context;
    private boolean disposed = false;
    private MethodChannel mapChannel;
    private Circle myLocalCircle;


    private final Map<String, MarkerController> markerControllerMap;
    private final Map<String, CircleController> circleControllerMap;


    public AMapController(Context context, int id, AtomicInteger activityState, PluginRegistry.Registrar registrar, AMapOptions options) {
        this.context = context;
        this.activityState = activityState;
        this.registrarActivityHashCode = registrar.activity().hashCode();
        this.mapView = new TextureMapView(context, options);
//        标记点击事件
        this.mapView.getMap().setOnMarkerClickListener(this);
        this.mapView.getMap().setOnCameraChangeListener(this);
        this.mapView.getMap().setOnMapLoadedListener(this);

        System.out.println("创建地图成功" + this.mapView.toString());
        this.registrar = registrar;
        this.mapChannel = new MethodChannel(registrar.messenger(), "com.yaving/map" + id);
        this.mapChannel.setMethodCallHandler(this);
        this.markerControllerMap = new HashMap<>();
        this.circleControllerMap = new HashMap<>();
        this.init();
    }

    void init() {
        switch (activityState.get()) {
            case YavingAmapPlugin.STOPPED:
                mapView.onCreate(null);
                mapView.onResume();
                mapView.onPause();
                break;
            case YavingAmapPlugin.PAUSED:
                mapView.onCreate(null);
                mapView.onResume();
                mapView.onPause();
                break;
            case YavingAmapPlugin.RESUMED:
                mapView.onCreate(null);
                mapView.onResume();
                break;
            case YavingAmapPlugin.STARTED:
                mapView.onCreate(null);
                break;
            case YavingAmapPlugin.CREATED:
                mapView.onCreate(null);
                break;
            case YavingAmapPlugin.DESTROYED:
                // Nothing to do, the activity has been completely destroyed.
                break;
            default:
                throw new IllegalArgumentException(
                        "Cannot interpret " + activityState.get() + " as an activity state");
        }
        registrar.activity().getApplication().registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (disposed || activity.hashCode() != registrarActivityHashCode)
            return;
        mapView.onCreate(null);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (disposed || activity.hashCode() != registrarActivityHashCode)
            return;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (disposed || activity.hashCode() != registrarActivityHashCode)
            return;
        mapView.onResume();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (disposed || activity.hashCode() != registrarActivityHashCode)
            return;
        mapView.onPause();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (disposed || activity.hashCode() != registrarActivityHashCode)
            return;

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (disposed || activity.hashCode() != registrarActivityHashCode)
            return;
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (disposed || activity.hashCode() != registrarActivityHashCode)
            return;
        mapView.onDestroy();
    }

    @Override
    public View getView() {

        return this.mapView;
    }

    @Override
    public void dispose() {
        if (disposed) {
            return;
        }
        disposed = true;
        mapChannel.setMethodCallHandler(null);
        mapView.onDestroy();
        registrar.activity().getApplication().unregisterActivityLifecycleCallbacks(this);
    }

    //    创建Marker
    public Marker addMarker(MarkerOptions options) {
        final Marker marker = this.mapView.getMap().addMarker(options);
        this.markerAnimation(marker);
        markerControllerMap.put(marker.getId(), new MarkerController(marker, this));
        return marker;
    }

    //    动画效果
    private void markerAnimation(Marker marker) {
        Animation markerAnimation = new ScaleAnimation(0, 1, 0, 1); //初始化生长效果动画
        markerAnimation.setDuration(200);  //设置动画时间 单位毫秒
        marker.setAnimation(markerAnimation);
        marker.startAnimation();
    }

    //    创建圆形
    public Circle addCircle(CircleOptions options) {
        Circle circle = this.mapView.getMap().addCircle(options);
        return circle;
    }

    //    显示我的位置
    public void showMyLocation(MyLocationOptions options) {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        定位模式和频次
        myLocationStyle.myLocationType(options.getShowType());
        myLocationStyle.interval(options.getInterval());
        //判断是否有自定义图标
        if (options.getIcon() != null)
            myLocationStyle.myLocationIcon(options.getIcon());
//        精度圈填充颜色
        //设置为透明
        myLocationStyle.radiusFillColor(0x00000000);
        /*
        if (options.getFillColor() > 0) {
            myLocationStyle.radiusFillColor(options.getFillColor());
        }*/
//        边框颜色
        //改为透明
        myLocationStyle.strokeColor(0x0000000);
        /*
        if (options.getStockColor() > 0) {
            myLocationStyle.strokeColor(options.getStockColor());
        }*/
//        边框宽度
        //边框为0
        myLocationStyle.strokeWidth(0);
        /*
        if (options.getStockWidth() > 0) {
            myLocationStyle.strokeWidth(options.getStockWidth());
        }*/
        if (myLocalCircle == null) {
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.strokeWidth(options.getStockWidth());
            circleOptions.strokeColor(options.getStockColor());
            circleOptions.fillColor(options.getFillColor());
            circleOptions.radius(options.getRadius());
            this.myLocalCircle = this.addCircle(circleOptions);
        }

        this.mapView.getMap().setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        this.mapView.getMap().setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        this.mapView.getMap().moveCamera(CameraUpdateFactory.zoomTo(options.getZoom()));

        this.mapView.getMap().setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.i("AMapController", "我的位置变化Location=" + JsonEx.toJson(location));
                myLocalCircle.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });
    }

    //    删除所有的Marker
    public void clearAllMarker() {
        for (String key : markerControllerMap.keySet()) {
            MarkerController controller = markerControllerMap.get(key);
            controller.remove();
        }
        markerControllerMap.clear();
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case "map#getMapRect": {
                int width = this.mapView.getWidth();
                int height = this.mapView.getHeight();
                Map<String, Object> map = new HashMap<>();
                map.put("width", width);
                map.put("height", height);
                result.success(map);
                break;
            }
            case "map#showMyLocation": {
                //展示我的位置
                Map<String, Object> map = new HashMap<String, Object>();
                map = JsonEx.fromJson(methodCall.argument("options").toString(), map.getClass());
                final MyLocationBuilder locationBuilder = new MyLocationBuilder(this, Convert.toMyLocationOptions(map));
                locationBuilder.builder();
                result.success(true);
                break;
            }
            case "map#changeLatLng": {  //改变地图中心店
                Map<String, Object> map = new HashMap<String, Object>();
                map = JsonEx.fromJson(methodCall.argument("target").toString(), map.getClass());
                LatLng latLng = Convert.toLatLng(map);
                this.mapView.getMap().animateCamera(CameraUpdateFactory.changeLatLng(latLng));
                break;
            }
            case "marker#addMarker": {
                Map<String, Object> map = new HashMap<String, Object>();
                map = JsonEx.fromJson(methodCall.argument("options").toString(), map.getClass());
                final MarkerBuilder markerBuilder = new MarkerBuilder(this, Convert.toMarkerOptions(map));
                final String id = markerBuilder.build();
                result.success(id);
                break;
            }
            case "marker#addMarkers": {
                String options = methodCall.argument("options").toString();
                boolean moveToCenter = methodCall.<Boolean>argument("moveToCenter");
                boolean clear = methodCall.<Boolean>argument("clear");
                Log.i("AMapController", "批量创建Markers参数为=" + options);

                List<Map<String, Object>> listMap = new ArrayList<>();
                listMap = JsonEx.fromJson(options, listMap.getClass());
                ArrayList<MarkerOptions> markerOptions = new ArrayList<>();
                for (Map map : listMap) {
                    markerOptions.add(Convert.toMarkerOptions(map));
                }
                //判断是否需要清空
                if (clear) {
                    this.clearAllMarker();
                }
                ArrayList<Marker> markers = this.mapView.getMap().addMarkers(markerOptions, moveToCenter);
                for (Marker marker : markers) {
                    this.markerAnimation(marker);
                    this.markerControllerMap.put(marker.getId(), new MarkerController(marker, this));
                }
                result.success(true);
                break;
            }
            case "marker#removeMarker": {
                String id = methodCall.argument("id");
                //获取控制器
                MarkerController controller = markerControllerMap.get(id);
                if (controller != null) {
                    controller.remove();
                }
                result.success(true);
                break;
            }
            case "marker#clearAllMarker": {
                this.clearAllMarker();
                break;
            }
            case "marker#addCircle": {
                //添加圆形面
                Map<String, Object> map = new HashMap<String, Object>();
                map = JsonEx.fromJson(methodCall.argument("options").toString(), map.getClass());
                final CircleBuilder circleBuilder = new CircleBuilder(this, Convert.toCircleOptions(map));
                String id = circleBuilder.build();
                Log.i("AMapController", "创建圆形区域id=" + id);
                this.circleControllerMap.put(id, circleBuilder.getController());
                result.success(id);
                break;
            }
            case "marker#updateCircle": {
                //更新圆形面
                Map<String, Object> map = new HashMap<String, Object>();
                map = JsonEx.fromJson(methodCall.argument("options").toString(), map.getClass());
                String id = methodCall.argument("id").toString();
                CircleOptions options = Convert.toCircleOptions(map);
                CircleController controller = circleControllerMap.get(id);
                controller.updateCircle(options);
                result.success(true);
                break;
            }
            default:
                result.notImplemented();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final MarkerController controller = markerControllerMap.get(marker.getId());
        return (controller != null && controller.onClicked());
    }

    @Override
    public void onMarkerClicked(Marker marker) {
        final Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("marker", marker.getId());
        arguments.put("position", JsonEx.toJson(marker.getPosition()));
        mapChannel.invokeMethod("marker#onTap", arguments);
    }

    //    地图被显示
    @Override
    public void onMapLoaded() {
        Log.i("AMapController", "地图加载完成");
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        final float zoom = cameraPosition.zoom; //缩放比例
        final LatLng target = cameraPosition.target;//目标位置
        VisibleRegion visibleRegion = this.mapView.getMap().getProjection().getVisibleRegion();
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;   //由可视区域的四个顶点形成的经纬度范围
        LatLng southwest = latLngBounds.southwest;      //西南角坐标
        LatLng northeast = latLngBounds.northeast;      //东北角坐标

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("zoom", zoom);
        arguments.put("targetLat", target.latitude);
        arguments.put("targetLong", target.longitude);
        arguments.put("swLat", southwest.latitude);
        arguments.put("swLong", southwest.longitude);
        arguments.put("neLat", northeast.latitude);
        arguments.put("neLong", northeast.longitude);

        mapChannel.invokeMethod("camera#changed", arguments);
    }
}
