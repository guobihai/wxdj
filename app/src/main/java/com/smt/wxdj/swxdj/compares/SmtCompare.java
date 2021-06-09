package com.smt.wxdj.swxdj.compares;

import android.text.TextUtils;

import com.smt.wxdj.swxdj.bean.CntrEntity;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardTaskInfo;

import java.util.Comparator;

/**
 * Created by gbh on 16/9/12.
 */
public class SmtCompare implements Comparator<YardTaskInfo> {
    String type;

    public static final String TIER = "TIER";
    public static final String TRUCK = "TRUCK";
    public static final String CNTR = "CNTR";
    public static final String TOROW = "TOROW";
    public static final String OPR = "OPR";
    public static final String TYPE = "TYPE";
    public static final String TIME = "TIME";
    public static final String WEIGHT = "WEIGHT";


    public SmtCompare(String type) {
        this.type = type;
    }

    @Override
    public int compare(YardTaskInfo t0, YardTaskInfo t1) {
        switch (type) {
            case TIER:
                return t0.getIntTier() > t1.getIntTier() ? 0 : -1;
            case TRUCK:
                return t0.getTrkNo().compareTo(t1.getTrkNo());
            case CNTR:
                return t0.getCntr().compareTo(t1.getCntr());
            case TOROW:
//                if (TextUtils.isEmpty(t0.getRown())) return 0;
                return t0.getLocation().compareTo(t1.getLocation());
            case OPR:
                return t0.getOptr().compareTo(t1.getOptr());
            case TYPE:
                return t0.getSizeClass().compareTo(t1.getSizeClass());
            case TIME:
                return t0.getWaitMinute() > (t1.getWaitMinute()) ? 0 : -1;
            case WEIGHT: {
                if (TextUtils.isEmpty(t0.getGrs_Ton())) return 0;
                if ((Double.parseDouble(String.valueOf(t0.getGrsWgt())) == Double.parseDouble(String.valueOf(t1.getGrsWgt()))))
                    return 0;
                else if ((Double.parseDouble(String.valueOf(t0.getGrsWgt())) > Double.parseDouble(String.valueOf(t1.getGrsWgt()))))
                    return 1;
                else
                    return -1;
            }
        }
        return t0.getCntr().compareTo(t1.getCntr());
    }
}
