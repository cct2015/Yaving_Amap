package com.yaving.yavingamap.model;

import com.amap.api.maps.model.BitmapDescriptor;

public class FixedMarkerOptions {
    private BitmapDescriptor icon;
    private Position position;

    public FixedMarkerOptions() {

    }

    public BitmapDescriptor getIcon() {
        return icon;
    }

    public void setIcon(BitmapDescriptor icon) {
        this.icon = icon;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
