package com.yaving.yavingamap.map;

import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;

public class CircleBuilder {
    private final AMapController controller;
    private final CircleOptions options;
    private Circle circle;
    private CircleController circleController;

    public CircleBuilder(AMapController controller, CircleOptions options) {
        this.controller = controller;
        this.options = options;
    }

    //    创建
    String build() {
        this.circle = this.controller.addCircle(this.options);
        this.circleController = new CircleController(this.circle);
        return this.circle.getId();
    }

    CircleController getController() {
        return this.circleController;
    }

}
