package com.yaving.yavingamap.model;

public class CameraVisibleParams {
    private int zoom;
    private MapLatLng center;
    //    南西坐标
    private MapLatLng southwest;
    //    北东坐标
    private MapLatLng northeast;

    public CameraVisibleParams() {

    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public MapLatLng getCenter() {
        return center;
    }

    public void setCenter(MapLatLng center) {
        this.center = center;
    }

    public MapLatLng getSouthwest() {
        return southwest;
    }

    public void setSouthwest(MapLatLng southwest) {
        this.southwest = southwest;
    }

    public MapLatLng getNortheast() {
        return northeast;
    }

    public void setNortheast(MapLatLng northeast) {
        this.northeast = northeast;
    }
}
