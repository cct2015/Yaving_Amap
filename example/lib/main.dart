import 'dart:async';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:yaving_amap/yaving_amap.dart';

void main() {
  YavingAmap.init('a63620ddfb77701e03e4f0a338b2d59e');

  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  AmapController _controller;

  String circleid;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {}

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: Stack(
            children: <Widget>[
              AMapView(
                onAmapViewCreated: (controller) {
                  setState(() {
                    _controller = controller;
                  });
                  _updateMap();
                },
                amapOptions: AMapOptions(
                    mapType: MAP_TYPE_NAVI, zoomControlsEnabled: false),
              ),
              Container(
                child: Center(
                  child: IgnorePointer(
                    ignoring: true,
                    child: Container(
                      height: 164,
                      color: Color(0x3300ffdd),
                      child: Column(
                        children: <Widget>[
                          Container(
                            height: 32,
                            padding: EdgeInsets.all(7),
                            child: Text('获取中...'),
                            decoration: BoxDecoration(
                              color: Color(0xFFFFFFFF),
                              borderRadius: BorderRadius.all(
                                Radius.circular(15.0),
                              ),
                            ),
                          ),
                          Image.asset(
                            'images/center_marker_triangle.png',
                            height: 10,
                            fit: BoxFit.fitHeight,
                          ),
                          Image.asset(
                            'images/icon_me_location.png',
                            height: 40,
                            fit: BoxFit.fitHeight,
                          )
                        ],
                      ),
                    ),
                  ),
                ),
              ),
              Container(
                child: Column(
                  children: <Widget>[
                    Row(
                      children: <Widget>[
                        RaisedButton(
                          child: Text('添加Marker'),
                          onPressed: () {
                            _addMarker();
                          },
                        ),
                        RaisedButton(
                          child: Text('添加Circle'),
                          onPressed: () async {
                            circleid = await _controller.addCircle(
                                CircleOptions(
                                    center: _nextLatLng(),
                                    radius: 30000,
                                    fillcolor: Color(0x11000000)));
                          },
                        ),
                        RaisedButton(
                          child: Text('更新Circle'),
                          onPressed: () {
                            if (circleid != null) {
                              _controller.updateCircle(
                                  circleid,
                                  CircleOptions(
                                      center: _nextLatLng(),
                                      radius: 20000,
                                      fillcolor: Color(0x11ff0000),
                                      stockcolor: Color(0x33000000)));
                            }
                          },
                        ),
                      ],
                    ),
                    Row(
                      children: <Widget>[
                        RaisedButton(
                          child: Text('移动位置'),
                          onPressed: () {
                            _controller.changeLatLng(_nextLatLng());
                          },
                        ),
                        RaisedButton(
                          child: Text('添加固定Marker'),
                          onPressed: () async {},
                        ),
                        RaisedButton(
                          child: Text('定位蓝点'),
                          onPressed: () {
                            _controller.showMyLocation(MyLocationOptions(
                                icon: 'images/map_user_location.png',
                                zoom: 17,
                                fillColor: Color(0x3324BFFE),
                                stockColor: Color(0x99000000)));
                          },
                        )
                      ],
                    ),
                    Row(
                      children: <Widget>[
                        RaisedButton(
                          child: Text('批量添加Marker'),
                          onPressed: () {
                            List<MarkerOptions> list = List<MarkerOptions>();
                            list.add(MarkerOptions(
                                icon: 'images/park_1.png',
                                position: _nextLatLng()));
                            list.add(MarkerOptions(
                                icon: 'images/park_1.png',
                                position: _nextLatLng()));
                            list.add(MarkerOptions(
                                icon: 'images/park_1.png',
                                position: _nextLatLng()));
                            list.add(MarkerOptions(
                                icon: 'images/park_1.png',
                                position: _nextLatLng()));
                            list.add(MarkerOptions(
                                icon: 'images/park_1.png',
                                position: _nextLatLng()));
                            _controller.addMarkers(list);
                          },
                        ),
                        RaisedButton(
                          child: Text('清空Marker'),
                          onPressed: () {
                            _controller.clearAllMarker();
                          },
                        ),
                        RaisedButton(
                          child: Text('定位蓝点'),
                          onPressed: () {
                            _controller.showMyLocation(MyLocationOptions(
                                icon: 'images/map_user_location.png',
                                zoom: 17,
                                fillColor: Color(0x3324BFFE),
                                stockColor: Color(0x99000000)));
                          },
                        )
                      ],
                    )
                  ],
                ),
              )
            ],
          )),
    );
  }

  void _addMarker() async {
    if (await Permissions().requestPermission()) {
      _controller.addMarker(
          MarkerOptions(icon: 'images/park_1.png', position: _nextLatLng()));
    } else {
      debugPrint('权限不足');
    }
  }

  void _updateMap() async {
    if (await Permissions().requestPermission()) {
      _controller.setMarkerClickedListener((marker) {
        debugPrint('标记被点击$marker');
      });

      _controller.setCameraChangedCallBack((camera) {
        debugPrint('可视区域已经改变:$camera');
      });
    } else {
      debugPrint('权限不足');
    }
  }

  LatLng _nextLatLng() {
    final _random = Random();
    double nextLat = (301818 + _random.nextInt(303289 - 301818)) / 10000;
    double nextLng = (1200093 + _random.nextInt(1203691 - 1200093)) / 10000;
    return LatLng(nextLat, nextLng);
  }

  @override
  void dispose() {
    if (_controller != null) {
      _controller.dispose();
    }
    super.dispose();
  }
}
