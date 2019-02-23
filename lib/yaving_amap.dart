library yaving_amap;

import 'dart:async';
import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

export 'package:yaving_amap/map/amap_view.dart';
export 'package:yaving_amap/map/model/amap_options.dart';
export 'package:yaving_amap/map/model/marker_options.dart';
export 'package:yaving_amap/map/model/latlng.dart';
export 'package:yaving_amap/map/amap_controller.dart';
export 'package:yaving_amap/map/model/marker.dart';
export 'package:yaving_amap/common/permissions.dart';
export 'package:yaving_amap/map/model/camera.dart';
export 'package:yaving_amap/map/model/circle_options.dart';
export 'package:yaving_amap/map/model/map_rect.dart';
export 'package:yaving_amap/map/model/position.dart';
export 'package:yaving_amap/common/misc.dart';
export 'package:yaving_amap/map/model/fixed_marker_options.dart';
export 'package:yaving_amap/map/model/my_location_options.dart';

class YavingAmap {
  static const MethodChannel _channel =
      const MethodChannel('com.yaving/yaving_amap_base');

  static Map<String, List<String>> assetManifest;

//  初始化
  static Future init(String key) async {
    _channel.invokeMethod('setKey', {'key': key});

    assetManifest = await rootBundle
        .loadStructuredData<Map<String, List<String>>>('AssetManifest.json',
            (String jsonData) {
      if (jsonData == null) {
        return SynchronousFuture<Map<String, List<String>>>(null);
      }

      final Map<String, dynamic> parsedJson = json.decode(jsonData);
      final Iterable<String> keys = parsedJson.keys;
      final Map parseMainifest = Map<String, List<String>>.fromIterables(keys,
          keys.map<List<String>>((key) => List<String>.from(parsedJson[key])));
      return SynchronousFuture<Map<String, List<String>>>(parseMainifest);
    });
  }
}
