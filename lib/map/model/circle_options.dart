import 'package:yaving_amap/yaving_amap.dart';
import 'package:flutter/material.dart';
import 'dart:convert';

//不绘制虚线默认
const DOTTEDLINE_TYPE_DEFAULT = -1;
//方形
const DOTTEDLINE_TYPE_SQUARE = 0;
//圆形
const DOTTEDLINE_TYPE_CIRCLE = 1;

class CircleOptions {
//  中心点位置
  LatLng center;

//半径
  double radius;

  //填充颜色
  Color fillcolor;

//边框颜色
  Color stockcolor;

//  边框形状
  int linetype;

//  边框宽度
  double width;

//排序
  double index;

  CircleOptions(
      {@required this.center,
      @required this.radius,
      @required this.fillcolor,
      this.stockcolor = const Color(0xFF000000),
      this.linetype = DOTTEDLINE_TYPE_DEFAULT,
      this.width = 1,
      this.index = 100});

//  生成map
  Map<String, Object> toJson() {
    return {
      "center": this.center,
      "radius": this.radius,
      "fillcolor": this.fillcolor.value,
      "stockcolor": this.stockcolor.value,
      "linetype": this.linetype,
      "width": this.width,
      "index": this.index
    };
  }

  String toJsonString() => jsonEncode(toJson());
}
