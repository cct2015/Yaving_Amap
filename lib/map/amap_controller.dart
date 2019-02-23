import 'package:flutter/services.dart';
import 'dart:async';
import 'dart:convert';
import 'package:yaving_amap/yaving_amap.dart';
import 'package:yaving_amap/common/log.dart';

//标记被点击回调
typedef void MarkerClickedCallBack(Marker marker);

//可视区域变化回调
typedef void CameraChangedCallBack(Camera camera);

//地图控制器
class AmapController {
  final MethodChannel _methodChannel;
  MarkerClickedCallBack markerClickedCallBack;
  CameraChangedCallBack cameraChangedCallBack;

  AmapController.withId(int id)
      : _methodChannel = MethodChannel('com.yaving/map$id') {
    _methodChannel.setMethodCallHandler(_handleMethodCall);
  }

  void setMarkerClickedListener(MarkerClickedCallBack clicker) {
    this.markerClickedCallBack = clicker;
  }

  void setCameraChangedCallBack(CameraChangedCallBack changer) {
    this.cameraChangedCallBack = changer;
  }

  void dispose() {}

  Future<dynamic> _handleMethodCall(MethodCall call) async {
    switch (call.method) {
      case 'marker#onTap':
        final String markerId = call.arguments['marker'];
        final Map<dynamic, dynamic> position = call.arguments['position'];
        //获取传过来的参数
        LatLng latLng = LatLng(position['latitude'], position['longitude']);
        final Marker marker =
            Marker(id: markerId, options: MarkerOptions(position: latLng));
        if (marker != null) {
          if (this.markerClickedCallBack != null) {
            this.markerClickedCallBack(marker);
          }
        }
        break;
      case 'camera#changed':
        if (this.cameraChangedCallBack == null) return;
        final int zoom = (call.arguments['zoom'] as double).toInt();

        final LatLng target =
            LatLng(call.arguments['targetLat'], call.arguments['targetLong']);
        final LatLng southwest =
            LatLng(call.arguments['swLat'], call.arguments['swLong']);
        final LatLng northeast =
            LatLng(call.arguments['neLat'], call.arguments['neLong']);
        Camera camera = Camera(
            zoom: zoom,
            center: target,
            southwest: southwest,
            northeast: northeast);
        this.cameraChangedCallBack(camera);
        break;
      default:
        throw MissingPluginException();
    }
  }

//  获取地图的 范围
  Future<MapRect> mapRect() async {
    Map<dynamic, dynamic> map = await _methodChannel
        .invokeMethod('map#getMapRect') as Map<dynamic, dynamic>;
    return MapRect(width: map['width'], height: map['height']);
  }

//  显示我的位置
  Future showMyLocation(MyLocationOptions options) {
    L.log('showMyLocation dart端参数: options -> $options');
    return _methodChannel.invokeMethod(
      'map#showMyLocation',
      {'options': options.toJsonString()},
    );
  }

  /// 移动指定LatLng到中心
  Future changeLatLng(LatLng target) {
    L.log('changeLatLng dart端参数: target -> $target');
    return _methodChannel.invokeMethod(
      'map#changeLatLng',
      {'target': target.toJsonString()},
    );
  }

//  创建圆形
  Future<String> addCircle(CircleOptions options) async {
    final _optionsJson = options.toJsonString();
    L.log('方法addCircle的参数CircleOptions为：$_optionsJson');
    String id = await _methodChannel
        .invokeMethod("marker#addCircle", {'options': _optionsJson});
    return id;
  }

//  更新圆形
  Future<void> updateCircle(String id, CircleOptions options) {
    final _optionsJson = options.toJsonString();
    L.log('方法updateCircle的参数CircleOptions为：$_optionsJson');
    _methodChannel.invokeMethod(
        "marker#updateCircle", {'id': id, 'options': _optionsJson});
  }

//  创建Marker
  Future<void> addMarker(MarkerOptions options) {
    final _optionsJson = options.toJsonString();
    L.log('方法addMarker的参数MarkerOptions为：$_optionsJson');
    _methodChannel.invokeMethod('marker#addMarker', {'options': _optionsJson});
  }

//  创建Marker 列表
  Future<void> addMarkers(List<MarkerOptions> options,
      {bool moveToCenter = true, bool clear = true}) {
    final _optionsJson = jsonEncode(options.map((op) => op.toJson()).toList());
    L.log(
        '方法addMarkers的参数MarkerOptions为：$_optionsJson,moveToCenter=$moveToCenter,clear=$clear');
    _methodChannel.invokeMethod('marker#addMarkers', {
      'options': _optionsJson,
      'moveToCenter': moveToCenter,
      'clear': clear
    });
  }

//  删除某个marker
  Future<void> removeMarker(String id) {
    _methodChannel.invokeMethod('marker#removeMarker', {'id': id});
  }

//  删除所有的Marker
  Future clearAllMarker() {
    _methodChannel.invokeMethod('marker#clearAllMarker');
  }
}
