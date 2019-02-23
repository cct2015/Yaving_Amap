import 'dart:io';
import 'dart:collection';
import 'package:flutter/material.dart';
import 'package:yaving_amap/yaving_amap.dart';

double devicePixelRatio = 1;

String toResolutionAware(String assetName) {
  final RegExp _extractRatioRegExp = RegExp(r'/?(\d+(\.\d*)?)x$');
  const double _naturalResolution = 1.0;

  double _parseScale(String key) {
    if (key == assetName) {
      return _naturalResolution;
    }

    final File assetPath = File(key);
    final Directory assetDir = assetPath.parent;

    final Match match = _extractRatioRegExp.firstMatch(assetDir.path);
    if (match != null && match.groupCount > 0)
      return double.parse(match.group(1));
    return _naturalResolution; // i.e. default to 1.0x
  }

  String _findNearest(SplayTreeMap<double, String> candidates, double value) {
    if (candidates.containsKey(value)) return candidates[value];
    final double lower = candidates.lastKeyBefore(value);
    final double upper = candidates.firstKeyAfter(value);
    if (lower == null) return candidates[upper];
    if (upper == null) return candidates[lower];
    if (value > (lower + upper) / 2)
      return candidates[upper];
    else
      return candidates[lower];
  }

  String _chooseVariant(
    String main,
    ImageConfiguration config,
    List<String> candidates,
  ) {
    if (config.devicePixelRatio == null ||
        candidates == null ||
        candidates.isEmpty) return main;
    final SplayTreeMap<double, String> mapping = SplayTreeMap<double, String>();
    for (String candidate in candidates)
      mapping[_parseScale(candidate)] = candidate;
    return _findNearest(mapping, config.devicePixelRatio);
  }

  final String chosenName = _chooseVariant(
      assetName,
      ImageConfiguration(devicePixelRatio: devicePixelRatio),
      YavingAmap.assetManifest == null
          ? null
          : YavingAmap.assetManifest[assetName]);
  debugPrint('设备devicePixelRatio: $devicePixelRatio, 选中的图片: $chosenName');
  return chosenName;
}
