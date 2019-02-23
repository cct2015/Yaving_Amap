import 'package:yaving_amap/yaving_amap.dart';
import 'dart:convert';
import 'package:flutter/material.dart';

class FixedMarkerOptions {
  //  Marker覆盖物图标[Android]
  String icon;

//  位置 相对于可视地图
  Position position;

  FixedMarkerOptions({@required this.position, this.icon});

  Map<String, Object> toJson() {
    return {'icon': toResolutionAware(icon), 'position': position};
  }

  String toJsonString() => jsonEncode(toJson());
}
