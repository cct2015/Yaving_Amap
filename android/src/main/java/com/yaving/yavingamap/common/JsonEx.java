package com.yaving.yavingamap.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonEx {
    private static Gson gson = new GsonBuilder().serializeNulls().create();

    //    Json转化为对象
    public static <T> T fromJson(String str, Class<T> clazz) {
        return gson.fromJson(str, clazz);
    }

    //    对象转为字符串
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
