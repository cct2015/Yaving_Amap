package com.yaving.yavingamap.common;

import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.yaving.yavingamap.model.FixedMarkerOptions;
import com.yaving.yavingamap.model.MyLocationOptions;
import com.yaving.yavingamap.model.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.view.FlutterMain;

public class Convert {

    //    获取创建地图参数
    public static AMapOptions toAmapOptions(Map<String, Object> map) {
        AMapOptions options = new AMapOptions();
        options.mapType(toInt(map.get("mapType")));
        options.scaleControlsEnabled(toBoolean(map.get("scaleControlsEnbaled")));
        options.compassEnabled(toBoolean(map.get("compassEnabled")));
        options.scrollGesturesEnabled(toBoolean(map.get("scrollGestureEnabled")));
        options.zoomGesturesEnabled(toBoolean(map.get("zoomGesturesEnabled")));
        options.zoomControlsEnabled(toBoolean(map.get("zoomControlsEnabled")));
        options.rotateGesturesEnabled(toBoolean(map.get("rotateGesturesEnabled")));
        return options;
    }


    //    创建圆形面
    public static CircleOptions toCircleOptions(Map<String, Object> map) {
        CircleOptions options = new CircleOptions();
        //设置中心点
        final LatLng center = toLatLng(map.get("center"));
        options.center(center);
        //半径
        final double radius = toDouble(map.get("radius"));
        options.radius(radius);
//填充颜色
        final int fillcolor = toInt(map.get("fillcolor"));
        options.fillColor(fillcolor);
        //边框颜色
        final Object stockcolor = map.get("stockcolor");
        if (stockcolor != null) {
            options.strokeColor(toInt(stockcolor));
        }

//        边框形状
        //AMapPara.DOTTEDLINE_TYPE_DEFAULT:不绘制虚线（默认）
        //AMapPara.DOTTEDLINE_TYPE_SQUARE:方形；
        //AMapPara.DOTTEDLINE_TYPE_CIRCLE：圆形；
        final Object linetype = map.get("linetype");
        if (linetype != null) {
            options.setStrokeDottedLineType(toInt(linetype));
        }
        //设置边框
        final Object width = map.get("width");
        if (width != null) {
            options.strokeWidth(toFloat(width));
        }
        final Object index = map.get("index");
        if (index != null) {
            options.zIndex(toFloat(index));
        }
        return options;
    }


    //    固定位置的 marker参数
    public static FixedMarkerOptions toFixedMarkerOptions(Map<String, Object> map) {
        FixedMarkerOptions options = new FixedMarkerOptions();
        final Object icon = map.get("icon");
        if (icon != null) {
            options.setIcon(toBitmapDescriptor(icon));
        }
        final Object position = map.get("position");
        options.setPosition(toPosition(position));
        return options;
    }

    //    显示我的位置 参数
    public static MyLocationOptions toMyLocationOptions(Map<String, Object> map) {
        MyLocationOptions options = new MyLocationOptions();
        final Object icon = map.get("icon");
        if (icon != null) {
            options.setIcon(toBitmapDescriptor(icon));
        }
//        边框颜色
        final Object stockColor = map.get("stockColor");
        if (stockColor != null) {
            options.setStockColor(toInt(stockColor));
        }
        //边框宽度
        final Object sotckWidth = map.get("sotckWidth");
        if (sotckWidth != null) {
            options.setStockWidth(toInt(sotckWidth));
        }
//        精度圈填充颜色
        final Object fillColor = map.get("fillColor");
        if (fillColor != null) {
            options.setFillColor(toInt(fillColor));
        }
//        位置调整频次
        final Object interval = map.get("interval");
        if (interval != null) {
            options.setInterval(toInt(interval));
        }
//        定位展示模 showType
        final Object showType = map.get("showType");
        if (showType != null) {
            options.setShowType(toInt(showType));
        }
        //精度圈半径
        options.setRadius(toInt(map.get("radius")));

        final Object zoom = map.get("zoom");
        if (showType != null) {
            options.setZoom(toInt(zoom));
        }

        return options;
    }


    //    标记参数转为 Map
    public static Map<String, Object> markerOptionsToMap(MarkerOptions options) {
        Map<String, Object> map = new HashMap<>();
        map.put("icon", "");
        map.put("draggable", options.isDraggable());
        map.put("position", options.getPosition());
        return map;
    }

    //    获取创建Marker参数
    public static MarkerOptions toMarkerOptions(Map<String, Object> map) {
        MarkerOptions options = new MarkerOptions();
        final Object icon = map.get("icon");
        if (icon != null) {
            options.icon(toBitmapDescriptor(icon));
        }
        final Object draggable = map.get("draggable");
        if (draggable != null) {
            options.draggable(toBoolean(draggable));
        }
        final Object position = map.get("position");
        if (position != null) {
            options.position(toLatLng(position));
        }
        return options;
    }

    public static BitmapDescriptor toBitmapDescriptor(Object o) {
        return BitmapDescriptorFactory.fromAsset(FlutterMain.getLookupKeyForAsset(o.toString()));
    }

    public static Position toPosition(Object o) {
        final Map<?, ?> data = toMap(o);
        return new Position(toInt(data.get("left")), toInt(data.get("top")));
    }

    public static LatLng toLatLng(Object o) {
        final Map<?, ?> data = toMap(o);
        return new LatLng(toDouble(data.get("latitude")), toDouble(data.get("longitude")));
    }

    public static List<?> toList(Object o) {
        return (List<?>) o;
    }

    public static Map<?, ?> toMap(Object o) {
        return (Map<?, ?>) o;
    }

    public static boolean toBoolean(Object o) {
        return ((Boolean) o).booleanValue();
    }

    public static int toInt(Object o) {
        return ((Number) o).intValue();
    }


    public static double toDouble(Object o) {
        return ((Number) o).doubleValue();
    }

    public static float toFloat(Object o) {
        return ((Number) o).floatValue();
    }
}
