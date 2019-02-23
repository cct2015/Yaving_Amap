package com.yaving.yavingamap.map;

import android.content.Context;

import com.amap.api.maps.AMapOptions;

import java.util.concurrent.atomic.AtomicInteger;

import io.flutter.plugin.common.PluginRegistry;

public class AMapBuilder {
    AMapController build(int id, Context context, AtomicInteger state, PluginRegistry.Registrar registrar, AMapOptions options) {
        final AMapController controller = new AMapController(context, id, state, registrar, options);
        return controller;
    }

}
