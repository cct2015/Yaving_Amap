import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:yaving_amap/common/misc.dart';
import 'package:yaving_amap/map/amap_controller.dart';
import 'package:yaving_amap/map/model/amap_options.dart';

const _viewType = 'com.yaving/AMapView';

//地图创建成功回调
typedef void MapCreatedCallBack(AmapController controller);

class AMapView extends StatelessWidget {
  const AMapView(
      {Key key,
      this.onAmapViewCreated,
      this.hitTestBehavior = PlatformViewHitTestBehavior.opaque,
      this.layoutDirection,
      this.amapOptions = const AMapOptions()})
      : super(key: key);

//  回调
  final MapCreatedCallBack onAmapViewCreated;
  final PlatformViewHitTestBehavior hitTestBehavior;
  final TextDirection layoutDirection;
  final AMapOptions amapOptions;

  @override
  Widget build(BuildContext context) {
    devicePixelRatio = MediaQuery.of(context).devicePixelRatio;

    final gestureRecognizers = <Factory<OneSequenceGestureRecognizer>>[
      Factory<OneSequenceGestureRecognizer>(() => EagerGestureRecognizer()),
    ].toSet();

    final String params = jsonEncode(amapOptions.toJson());
    final messageCodec = StandardMessageCodec();
    if (defaultTargetPlatform == TargetPlatform.android) {
      return AndroidView(
        viewType: _viewType,
        hitTestBehavior: hitTestBehavior,
        gestureRecognizers: gestureRecognizers,
        onPlatformViewCreated: _onViewCreated,
        layoutDirection: layoutDirection,
        creationParams: params,
        creationParamsCodec: messageCodec,
      );
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      return UiKitView(
        viewType: _viewType,
        hitTestBehavior: hitTestBehavior,
        gestureRecognizers: gestureRecognizers,
        onPlatformViewCreated: _onViewCreated,
        layoutDirection: layoutDirection,
        creationParams: params,
        creationParamsCodec: messageCodec,
      );
    } else {
      return Text(
        '$defaultTargetPlatform is not yet supported by the maps plugin',
      );
    }
  }

  void _onViewCreated(int id) {
    final controller = AmapController.withId(id);
    debugPrint('controller创建成功' + controller.toString());
    if (onAmapViewCreated != null) {
      onAmapViewCreated(controller);
    }
  }

}
