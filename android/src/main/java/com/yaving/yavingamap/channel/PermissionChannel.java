package com.yaving.yavingamap.channel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.yaving.yavingamap.common.Common;
import com.yaving.yavingamap.common.Convert;
import com.yaving.yavingamap.common.JsonEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class PermissionChannel implements MethodChannel.MethodCallHandler {
    private MethodChannel channel;
    private PluginRegistry.Registrar registrar;
    private final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};
    private int StatusCode = 0;
    private MethodChannel.Result channelResult;

    public PermissionChannel(PluginRegistry.Registrar registrar) {
        this.registrar = registrar;
        channel = new MethodChannel(registrar.messenger(), "com.yaving/permission");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(MethodCall methodCall, final MethodChannel.Result result) {
        switch (methodCall.method) {
            case "requestPermission": {
                channelResult = result;
                if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
                    StatusCode = methodCall.hashCode();
                    List<String> needPermissions = shouldPermission();
                    if (needPermissions.size() <= 0) {
                        channelResult.success(true);
                    } else {
                        ActivityCompat.requestPermissions(this.registrar.activity(), needPermissions.toArray(new String[needPermissions.size()]), StatusCode);
                        this.registrar.addRequestPermissionsResultListener(new PluginRegistry.RequestPermissionsResultListener() {
                            @Override
                            public boolean onRequestPermissionsResult(int code, String[] permission, int[] grantResults) {

                                Log.i("PermissionChannel", "授权返回 grantResult==" + JsonEx.toJson(grantResults));

                                boolean allow = true;
                                for (int grantResult : grantResults) {
                                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                                        allow = false;
                                        break;
                                    }
                                }

                                if (code == StatusCode) {
                                    channelResult.success(allow);
                                }
                                return true;
                            }
                        });
                    }
                } else {
                    channelResult.success(true);
                }
                break;
            }
            default:
                result.notImplemented();
        }
    }


    //权限判断和申请
    private List<String> shouldPermission() {
//需要申请的权限
        List<String> mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this.registrar.activity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        return mPermissionList;
    }
}
