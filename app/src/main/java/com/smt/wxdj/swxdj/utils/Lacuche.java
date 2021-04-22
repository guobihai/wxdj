package com.smt.wxdj.swxdj.utils;


import com.smt.wxdj.swxdj.bean.Trk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gbh on 17/3/29.
 * 缓存类
 */

public class Lacuche {
    private static Lacuche sLacuche;
    private Map<String, List<Trk>> mListHashMap;

    public Lacuche() {
        mListHashMap = new HashMap<>();
    }

    /**
     * 单例
     *
     * @return
     */
    public static Lacuche getLacuche() {
        if (null == sLacuche) {
            sLacuche = new Lacuche();
        }
        return sLacuche;
    }

    /**
     * 放入缓存
     *
     * @param key
     * @param trkList
     */
    public void putTrkData(String key, List<Trk> trkList) {
        mListHashMap.put(key, trkList);
    }


    /**
     * 获取拖车缓存
     *
     * @return
     */
    public String[] getTrkArray(String key) {
        List<Trk> trkList = mListHashMap.get(key);
        if (null != trkList && trkList.size() != 0) {
            String[] trkArray = new String[trkList.size()];
            for (int i = 0; i < trkList.size(); i++) {
                trkArray[i] = trkList.get(i).getTrk();
            }
            return trkArray;
        }
        return null;
    }


    /**
     * 获取拖车缓存
     *
     * @return
     */
    public List<Trk> getTrkList(String key) {
        List<Trk> trkList = mListHashMap.get(key);
        if (null == trkList) return null;
        if (trkList.size() == 0) return null;
        return trkList;
    }
}
