import 'dart:convert';
import 'package:flutter/material.dart';

//只定位一次。
const int LOCATION_TYPE_SHOW = 0;
//定位一次，且将视角移动到地图中心点。
const int LOCATION_TYPE_LOCATE = 1;
//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
const int LOCATION_TYPE_FOLLOW = 2;
//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
const int LOCATION_TYPE_MAP_ROTATE = 3;
//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
const int LOCATION_TYPE_LOCATION_ROTATE = 4;
//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
const int LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER = 5;
//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
const int LOCATION_TYPE_FOLLOW_NO_CENTER = 6;
//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
const int LOCATION_TYPE_MAP_ROTATE_NO_CENTER = 7;

//我的位置信息参数
class MyLocationOptions {
  //    定位蓝点的 自定义图标
  String icon;

  //    边框颜色
  Color stockColor;

  //    边框宽度
  int stockWidth;

  //经度圈的填充颜色
  Color fillColor;

  //调整定位频次 单位毫秒
  int interval;

//  精度圈半径
  int radius;

  //蓝点定位模式
  int showType;

//放大比例
  int zoom;

  MyLocationOptions(
      {this.icon,
      this.stockColor = const Color(0x99000000),
      this.stockWidth = 1,
      this.fillColor = const Color(0x3300ff00),
      this.interval,
      this.radius = 300,
      this.showType = LOCATION_TYPE_LOCATE,
      this.zoom = 16});

  Map<String, Object> toJson() {
    return {
      'icon': this.icon,
      'stockColor': this.stockColor.value,
      'stockWidth': this.stockWidth,
      'fillColor': this.fillColor.value,
      'interval': this.interval,
      'radius': this.radius,
      'zoom': this.zoom,
      'showType': this.showType
    };
  }

  String toJsonString() => jsonEncode(toJson());
}
