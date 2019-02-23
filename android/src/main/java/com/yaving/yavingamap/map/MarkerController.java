package com.yaving.yavingamap.map;

import com.amap.api.maps.model.Marker;

public class MarkerController {
    private final Marker marker;
    private final MarkerClickedListener listener;

    public MarkerController(Marker marker, MarkerClickedListener listener) {
        this.marker = marker;
        this.listener = listener;
    }

    public void remove() {
        this.marker.remove();
    }

    public boolean onClicked() {
        if (listener != null) {
            this.listener.onMarkerClicked(marker);
        }
        return true;
    }

}
