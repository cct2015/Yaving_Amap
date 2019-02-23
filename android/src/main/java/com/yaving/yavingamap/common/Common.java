package com.yaving.yavingamap.common;

import java.util.List;

public class Common {
    //    判断列表内 是否完全等于 某个值
    public static <T> boolean AllEqual(List<T> list, T t) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != t) {
                return false;
            }
        }
        return true;
    }
}
