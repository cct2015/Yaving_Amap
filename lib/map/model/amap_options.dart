/// 普通地图
const MAP_TYPE_NORMAL = 1;

/// 卫星地图
const MAP_TYPE_SATELLITE = 2;

/// 黑夜地图
const MAP_TYPE_NIGHT = 3;

/// 导航模式
const MAP_TYPE_NAVI = 4;

/// 公交模式
const MAP_TYPE_BUS = 5;

//地图选择项
class AMapOptions {
  const AMapOptions(
      {this.mapType = MAP_TYPE_NORMAL,
      this.scaleControlsEnbaled = false,
      this.compassEnabled = false,
      this.scrollGestureEnabled = true,
      this.zoomGesturesEnabled = true,
      this.zoomControlsEnabled = true,
      this.rotateGesturesEnabled = true});

//  地图模式[Android]
  final int mapType;

//  比例尺是否可用[Android]
  final bool scaleControlsEnbaled;

//  指南针是否可用[Android]
  final bool compassEnabled;

//  拖动手势是否可用[Android]
  final bool scrollGestureEnabled;

//  缩放手势是否可用[Android]
  final bool zoomGesturesEnabled;

//  是否显示放大缩小 按钮
  final bool zoomControlsEnabled;

//  地图旋转手势是否可用[Android]
  final bool rotateGesturesEnabled;

//
  Map<String, Object> toJson() {
    return {
      'mapType': mapType,
      'scaleControlsEnbaled': scaleControlsEnbaled,
      'compassEnabled': compassEnabled,
      'scrollGestureEnabled': scrollGestureEnabled,
      'zoomGesturesEnabled': zoomGesturesEnabled,
      'zoomControlsEnabled': zoomControlsEnabled,
      'rotateGesturesEnabled': rotateGesturesEnabled
    };
  }

  AMapOptions copyWith(
      {int mapType,
      bool scaleControlsEnbaled,
      bool compassEnabled,
      bool scrollGestureEnabled,
      bool zoomGesturesEnabled,
      bool zoomControlsEnabled,
      bool rotateGesturesEnabled}) {
    return AMapOptions(
      mapType: mapType ?? this.mapType,
      scaleControlsEnbaled: scaleControlsEnbaled ?? this.scaleControlsEnbaled,
      compassEnabled: compassEnabled ?? this.compassEnabled,
      scrollGestureEnabled: scrollGestureEnabled ?? this.mapType,
      zoomGesturesEnabled: zoomGesturesEnabled ?? this.zoomGesturesEnabled,
      zoomControlsEnabled: zoomControlsEnabled ?? this.zoomControlsEnabled,
      rotateGesturesEnabled:
          rotateGesturesEnabled ?? this.rotateGesturesEnabled,
    );
  }
}
