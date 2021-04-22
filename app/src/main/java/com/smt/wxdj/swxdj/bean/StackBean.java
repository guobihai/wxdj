package com.smt.wxdj.swxdj.bean;

import com.smtlibrary.utils.JsonUtils;

/**
 * Created by gbh on 16/7/4.
 * 场地模型
 */
public class StackBean extends User {
    private String Stack;
    private String Maj_Loc;
    private String Sub_Loc;
    private String Works;
    private String Side;
    private String Opr_Sta;
    private String OPR_START_ROWN;
    private String OPR_END_ROWN;
    private String STATUS;
    private String ASSIGN_ROW;
    private String START_TIME;
    private String END_TIME;
    private boolean isSelect;

    public StackBean() {
    }

    public StackBean(String stack, boolean isSelect) {
        Stack = stack;
        this.isSelect = isSelect;
    }

    public String getStack() {
        return Stack;
    }

    public void setStack(String stack) {
        Stack = stack;
    }


    public String getMaj_Loc() {
        return Maj_Loc;
    }

    public void setMaj_Loc(String maj_Loc) {
        Maj_Loc = maj_Loc;
    }

    public String getSub_Loc() {
        return Sub_Loc;
    }

    public void setSub_Loc(String sub_Loc) {
        Sub_Loc = sub_Loc;
    }

    public String getWorks() {
        return Works;
    }

    public void setWorks(String works) {
        Works = works;
    }

    public String getSide() {
        return Side;
    }

    public void setSide(String side) {
        Side = side;
    }

    public String getOpr_Sta() {
        return Opr_Sta;
    }

    public void setOpr_Sta(String opr_Sta) {
        Opr_Sta = opr_Sta;
    }

    public String getOPR_START_ROWN() {
        return OPR_START_ROWN;
    }

    public void setOPR_START_ROWN(String OPR_START_ROWN) {
        this.OPR_START_ROWN = OPR_START_ROWN;
    }

    public String getOPR_END_ROWN() {
        return OPR_END_ROWN;
    }

    public void setOPR_END_ROWN(String OPR_END_ROWN) {
        this.OPR_END_ROWN = OPR_END_ROWN;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getASSIGN_ROW() {
        return ASSIGN_ROW;
    }

    public void setASSIGN_ROW(String ASSIGN_ROW) {
        this.ASSIGN_ROW = ASSIGN_ROW;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
