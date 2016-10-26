package com.betterda.xsnano.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用数据
 * Created by lyf on 2016/7/1.
 */
public class ConstantsData {

    private  static   Map<String,String> hashMap ;

    /**
     * 返回店铺类型
     * @param key
     * @return
     */
    public static   String getMapString(String key) {
        if (null == hashMap) {
            hashMap = new HashMap<>();

        }

        return hashMap.get(key);
    }

    public static Map<String, String> getHashMap() {
        if (null == hashMap) {
            hashMap = new HashMap<>();

        }
        return hashMap;
    }
}
