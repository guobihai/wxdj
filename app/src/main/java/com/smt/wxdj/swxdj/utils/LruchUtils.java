package com.smt.wxdj.swxdj.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xlj on 2017/5/23.
 * 缓存类
 */

public class LruchUtils {
    public static Map<String, String> mSystemMaps = new HashMap<>();

    /**
     * add maps
     *
     * @param maps
     */
    public static void setSystemMaps(Map<String, String> maps) {
        mSystemMaps.putAll(maps);
    }


    public static void clear() {
        if (mSystemMaps == null) return;
        mSystemMaps.clear();
    }


    /**
     * 判断参数是否开启
     *
     * @param key
     * @return
     */
    public static boolean isSwitch(String key) {
        if (mSystemMaps == null) return true;
        String temp = mSystemMaps.get(key);
        if (TextUtils.isEmpty(temp)) return true;
        if (temp.equals("NO")) return false;
        return true;
    }

    /**
     * 判断参数是否开启
     *
     * @param key
     * @return
     */
    public static String getValues(String key) {
        return mSystemMaps.get(key);
    }

    /**
     * 系统参数缓存是否存在
     * @return
     */
    public static boolean isExist() {
        if(mSystemMaps == null) return false;
        return mSystemMaps.size() == 0 ? false : true;
    }

}
