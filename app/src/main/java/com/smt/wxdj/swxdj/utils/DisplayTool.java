package com.smt.wxdj.swxdj.utils;

import android.content.Context;

/**
 * Created by gbh on 16/6/27.
 */
public class DisplayTool {
    public static float getDisplayDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
