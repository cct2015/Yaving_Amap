package com.yaving.yavingamap.map;

import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yaving.yavingamap.model.FixedMarkerOptions;
import com.yaving.yavingamap.model.Position;

public class MarkerBuilder {
    private final AMapController controller;
    private final MarkerOptions options;
    private Marker marker;


    MarkerBuilder(AMapController controller, MarkerOptions options) {
        this.controller = controller;
        this.options = options;
    }


    String build() {
        this.marker = controller.addMarker(this.options);
        return marker.getId();
    }
}
