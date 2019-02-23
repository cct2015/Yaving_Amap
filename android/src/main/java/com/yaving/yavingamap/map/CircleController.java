package com.yaving.yavingamap.map;

import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;

public class CircleController {
    private final Circle circle;

    public CircleController(Circle circle) {
        this.circle = circle;
    }

    //    更新圆形信息
    public void updateCircle(CircleOptions options) {
        this.circle.setCenter(options.getCenter());
        this.circle.setFillColor(options.getFillColor());
        this.circle.setRadius(options.getRadius());
        this.circle.setStrokeColor(options.getStrokeColor());
        this.circle.setStrokeDottedLineType(options.getStrokeDottedLineType());
        this.circle.setStrokeWidth(options.getStrokeWidth());
        this.circle.setZIndex(options.getZIndex());
    }
}
