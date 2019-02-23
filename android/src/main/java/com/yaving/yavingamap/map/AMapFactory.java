package com.yaving.yavingamap.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps.AMapOptions;
import com.yaving.yavingamap.common.Convert;
import com.yaving.yavingamap.common.JsonEx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class AMapFactory extends PlatformViewFactory {
    private final AtomicInteger mActivityState;
    private final PluginRegistry.Registrar mPluginRegistrar;

    public AMapFactory(AtomicInteger state, PluginRegistry.Registrar registrar) {
        super(StandardMessageCodec.INSTANCE);
        mActivityState = state;
        mPluginRegistrar = registrar;
    }

    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = JsonEx.fromJson(args.toString(), map.getClass());
        AMapOptions options = Convert.toAmapOptions(map);
        Log.i("AMapFactory", "AMapOption的内容为" + JsonEx.toJson(options));
        final AMapBuilder builder = new AMapBuilder();
        return builder.build(id, context, mActivityState, mPluginRegistrar, options);
    }
}
