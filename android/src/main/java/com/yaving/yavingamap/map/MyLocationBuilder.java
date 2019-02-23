package com.yaving.yavingamap.map;

import com.yaving.yavingamap.model.MyLocationOptions;

public class MyLocationBuilder {
    private AMapController controller;
    private MyLocationOptions locationOptions;

    public MyLocationBuilder(AMapController controller, MyLocationOptions options) {
        this.controller = controller;
        this.locationOptions = options;
    }

    public void builder() {
        this.controller.showMyLocation(this.locationOptions);
    }
}
