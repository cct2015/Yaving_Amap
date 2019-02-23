import 'dart:convert';

class LatLng {
  final double latitude;
  final double longitude;

  const LatLng(this.latitude, this.longitude);

  Map<String, Object> toJson() {
    return {'latitude': latitude, 'longitude': longitude};
  }

  String toJsonString() => jsonEncode(toJson());
}
