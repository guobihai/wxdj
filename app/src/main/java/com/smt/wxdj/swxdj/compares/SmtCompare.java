package com.smt.wxdj.swxdj.compares;

import android.text.TextUtils;

import com.smt.wxdj.swxdj.bean.CntrEntity;

import java.util.Comparator;

/**
 * Created by gbh on 16/9/12.
 */
public class SmtCompare implements Comparator<CntrEntity> {
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
    public int compare(CntrEntity t0, CntrEntity t1) {
        switch (type) {
            case TIER:
                return t0.getTier().compareTo(t1.getTier());
            case TRUCK:
                return t0.getTrk().compareTo(t1.getTrk());
            case CNTR:
                return t0.getCntr().compareTo(t1.getCntr());
            case TOROW:
                if (TextUtils.isEmpty(t0.getRown())) return 0;
                return t0.getRown().compareTo(t1.getRown());
            case OPR:
                return t0.getOpr().compareTo(t1.getOpr());
            case TYPE:
                return t0.getEqp_Type().compareTo(t1.getEqp_Type());
            case TIME:
                if (TextUtils.isEmpty(t0.getWaitTime()) || TextUtils.isEmpty(t1.getWaitTime()))
                    return 0;
                return t0.getWaitTime().compareTo(t1.getWaitTime());
            case WEIGHT: {
                if (TextUtils.isEmpty(t0.getGrs_Ton())) return 0;
                if ((Double.parseDouble(t0.getGrs_Ton()) == Double.parseDouble(t1.getGrs_Ton())))
                    return 0;
                else if ((Double.parseDouble(t0.getGrs_Ton()) > Double.parseDouble(t1.getGrs_Ton())))
                    return 1;
                else
                    return -1;
            }
        }
        return t0.getCntr().compareTo(t1.getCntr());
    }
}
