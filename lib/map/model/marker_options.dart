import 'package:yaving_amap/yaving_amap.dart';
import 'dart:convert';
import 'package:yaving_amap/common/misc.dart';
import 'package:meta/meta.dart';

//mark参数
class MarkerOptions {
//  Marker覆盖物图标[Android]
  String icon;

//  Marker是否可用拖拽
  bool draggable;

//  位置
  LatLng position;

//  覆盖物文字描述
  String snippet;

  MarkerOptions({@required this.position, this.icon, this.draggable = false});

  Map<String, Object> toJson() {
    return {
      'icon': toResolutionAware(icon),
      'draggable': draggable,
      'position': position
    };
  }

  String toJsonString() => jsonEncode(toJson());
}
