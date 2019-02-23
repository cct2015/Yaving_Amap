import 'dart:convert';

class Position {
  int left;
  int top;

  Position({this.left, this.top});

  Map<String, Object> toJson() {
    return {'left': left, 'top': top};
  }

  String toJsonString() => jsonEncode(toJson());
}
