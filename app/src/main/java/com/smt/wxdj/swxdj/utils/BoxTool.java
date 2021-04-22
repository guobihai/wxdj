package com.smt.wxdj.swxdj.utils;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by gbh on 16/6/29.
 * 箱子工具类
 */
public class BoxTool {
    public static final String SWAPFLAG = "1";//交换箱标志
    public static final String E = "E";//空箱
    public static final String F = "F";//重箱
    public static final String EQP_STA = "R";//出口或转口集装箱
    public static final String CATEGORY = "DT,FT,TS";//内外贸

    /**
     * 字典映射
     */
    public static final Map<String, String> keyReference = new Hashtable<String, String>();

    static {
//        keyReference.put("GR", "卸箱");
//        keyReference.put("PK", "提箱");
//        keyReference.put("IP", "内提");
//        keyReference.put("IG", "内放");
//        keyReference.put("RM", "倒箱");
//        keyReference.put("PJ", "倒箱");
//        keyReference.put("UP", "取消");
//        keyReference.put("GT", "整理");
        keyReference.put("GR", MyApplication.getContext().getString(R.string.unloading_box));
        keyReference.put("PK", MyApplication.getContext().getString(R.string.suitcase));
        keyReference.put("IP", MyApplication.getContext().getString(R.string.internal));
        keyReference.put("IG", MyApplication.getContext().getString(R.string.internal_discharge));
        keyReference.put("RM", MyApplication.getContext().getString(R.string.inverted_box));
        keyReference.put("PJ", MyApplication.getContext().getString(R.string.inverted_box));
        keyReference.put("UP", MyApplication.getContext().getString(android.R.string.cancel));
        keyReference.put("GT", MyApplication.getContext().getString(R.string.arrangement));
    }

    /**
     * 放箱子
     */
    public static final String CTRL_PUTBOX = "GR";

    /**
     * 内放
     */
    public static final String CTRL_PUTBOXIG = "IG";
    /**
     * 提箱子
     */
    public static final String CTRL_GETBOX = "PK";
    /**
     * 内提
     */
    public static final String CTRL_GETBOXIP = "IP";

    /**
     * 取消提箱子
     */
    public static final String CTRL_UPBOX = "UP";
    public static final String CTRL_UIBOX = "UI";


    /**
     * 卸船
     */
    public static final String CTRL_DC = "DC";


    /**
     * 计划状态
     */
    public static final String STATE_PL = "PL";
    /**
     * 倒箱
     */
    public static final String STATE_PJ = "PJ";
    /**
     *
     */
    public static final String STATE_PW = "PW";
    /**
     * 在拖车
     */
    public static final String STATE_WD = "WD";
    /**
     * 完成
     */
    public static final String STATE_CP = "CP";
    /**
     * 取消
     */
    public static final String STATE_VD = "VD";

    /**
     * TRY_TYPE
     */
    public static final String STATE_MRE = "MRE";


}
