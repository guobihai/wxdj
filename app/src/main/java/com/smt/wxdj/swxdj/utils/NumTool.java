package com.smt.wxdj.swxdj.utils;

import android.text.TextUtils;

/**
 * Created by gbh on 16/9/5.
 */
public class NumTool {
    public static String praseNum(String wgt) {
        if (TextUtils.isEmpty(wgt)) return "0.0";
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        return df.format(Double.parseDouble(wgt));
    }
}
