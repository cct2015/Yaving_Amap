import 'package:flutter/services.dart';
import 'package:yaving_amap/common/log.dart';

class Permissions {
  static Permissions _instance;
  static const _permissionChannel = MethodChannel('com.yaving/permission');

  Permissions._();

  factory Permissions() {
    if (_instance == null) {
      _instance = Permissions._();
      return _instance;
    } else {
      return _instance;
    }
  }

  Future<bool> requestPermission() {
    return _permissionChannel.invokeMethod('requestPermission').then((result) {
      bool s = result as bool;
      L.log('result == $result');
      return s;
    });
  }
}
