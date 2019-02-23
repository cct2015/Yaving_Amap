package com.yaving.yavingamap.channel;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class AMapBaseChannel implements MethodChannel.MethodCallHandler {
    private MethodChannel channel;
    private PluginRegistry.Registrar registrar;

    public AMapBaseChannel(PluginRegistry.Registrar registrar) {
        this.registrar = registrar;
        channel = new MethodChannel(registrar.messenger(), "com.yaving/yaving_amap_base");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case "setKey": {
                result.success("android端需要在Manifest里配置key");
                break;
            }
            default:
                result.notImplemented();
        }
    }
}
