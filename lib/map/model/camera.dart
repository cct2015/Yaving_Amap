import 'package:yaving_amap/map/model/latlng.dart';

class Camera {
  final int zoom;
  final LatLng center;
  final LatLng southwest;
  final LatLng northeast;

  Camera({this.zoom, this.center, this.southwest, this.northeast});
}
