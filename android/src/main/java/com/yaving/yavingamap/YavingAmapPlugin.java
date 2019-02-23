package com.yaving.yavingamap;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.yaving.yavingamap.channel.AMapBaseChannel;
import com.yaving.yavingamap.channel.PermissionChannel;
import com.yaving.yavingamap.map.AMapFactory;

import java.util.concurrent.atomic.AtomicInteger;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * YavingAmapPlugin
 */
public class YavingAmapPlugin implements Application.ActivityLifecycleCallbacks {

    public static final int CREATED = 1;
    public static final int STARTED = 2;
    public static final int RESUMED = 3;
    public static final int PAUSED = 4;
    public static final int STOPPED = 5;
    public static final int DESTROYED = 6;

    private final AtomicInteger state = new AtomicInteger(0);
    private final int registrarActivityHashCode;

    private static PermissionChannel permissionChannel;
    private static AMapBaseChannel aMapBaseChannel;

    private YavingAmapPlugin(Registrar registrar) {
        this.registrarActivityHashCode = registrar.activity().hashCode();
    }

    public static void registerWith(Registrar registrar) {

        aMapBaseChannel = new AMapBaseChannel(registrar);
        permissionChannel = new PermissionChannel(registrar);


        final YavingAmapPlugin plugin = new YavingAmapPlugin(registrar);
        registrar.activity().getApplication().registerActivityLifecycleCallbacks(plugin);
        registrar
                .platformViewRegistry()
                .registerViewFactory(
                        "com.yaving/AMapView", new AMapFactory(plugin.state, registrar));
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity.hashCode() != registrarActivityHashCode) {
            return;
        }
        state.set(CREATED);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity.hashCode() != registrarActivityHashCode) {
            return;
        }
        state.set(STARTED);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity.hashCode() != registrarActivityHashCode) {
            return;
        }
        state.set(RESUMED);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity.hashCode() != registrarActivityHashCode) {
            return;
        }
        state.set(PAUSED);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.hashCode() != registrarActivityHashCode) {
            return;
        }
        state.set(STOPPED);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity.hashCode() != registrarActivityHashCode) {
            return;
        }
        state.set(DESTROYED);
    }
}
