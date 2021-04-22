package com.smt.wxdj.swxdj.bean;

import com.smtlibrary.utils.JsonUtils;

import java.io.Serializable;

/**
 * Created by gbh on 16/7/4.
 * 呗位，田位
 */
public class Bay extends User implements Serializable {
    private String Maj_Loc;//主区
    private String Sub_Loc;//分区
    private String Bay;//
    private String Bay_Type;//田位类型
    private String Stack;//场地
    private String Stk_Order;//次序状态
    private String Max_Cell;//最大排
    private String Max_Tier;//最大层
    private String Draw_X;//X轴
    private String Draw_Y;//Y轴
    private String Draw_Angle;//
    private String Cntr_Dir;//
    private String eqp_Size_Limit;//
    private String Cur_Size_Limit;//呗位放置箱子的类型
    private String Max_Plug;//
    private String Cntr_Count;//
    private String Count_Reef;//
    private String Enable_Ind;//
    private String Avail_Ind;//
    private String ENABLE_IND;//
    private String Lst_Upd_Seq;//
    private String Pick_Priority;//
    private String pick_Method;
    private String Max_Density;
    private String Tier_Level;

    private transient boolean isSelect;

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

    public String getBay() {
        return Bay;
    }

    public void setBay(String bay) {
        Bay = bay;
    }

    public String getBay_Type() {
        return Bay_Type;
    }

    public void setBay_Type(String bay_Type) {
        Bay_Type = bay_Type;
    }

    public String getStack() {
        return Stack;
    }

    public void setStack(String stack) {
        Stack = stack;
    }

    public String getStk_Order() {
        return Stk_Order;
    }

    public void setStk_Order(String stk_Order) {
        Stk_Order = stk_Order;
    }

    public String getMax_Cell() {
        return Max_Cell;
    }

    public void setMax_Cell(String max_Cell) {
        Max_Cell = max_Cell;
    }

    public String getMax_Tier() {
        return Max_Tier;
    }

    public void setMax_Tier(String max_Tier) {
        Max_Tier = max_Tier;
    }

    public String getDraw_X() {
        return Draw_X;
    }

    public void setDraw_X(String draw_X) {
        Draw_X = draw_X;
    }

    public String getDraw_Y() {
        return Draw_Y;
    }

    public void setDraw_Y(String draw_Y) {
        Draw_Y = draw_Y;
    }

    public String getDraw_Angle() {
        return Draw_Angle;
    }

    public void setDraw_Angle(String draw_Angle) {
        Draw_Angle = draw_Angle;
    }

    public String getCntr_Dir() {
        return Cntr_Dir;
    }

    public void setCntr_Dir(String cntr_Dir) {
        Cntr_Dir = cntr_Dir;
    }

    public String getEqp_Size_Limit() {
        return eqp_Size_Limit;
    }

    public void setEqp_Size_Limit(String eqp_Size_Limit) {
        this.eqp_Size_Limit = eqp_Size_Limit;
    }

    public String getCur_Size_Limit() {
        return Cur_Size_Limit;
    }

    public void setCur_Size_Limit(String cur_Size_Limit) {
        Cur_Size_Limit = cur_Size_Limit;
    }

    public String getMax_Plug() {
        return Max_Plug;
    }

    public void setMax_Plug(String max_Plug) {
        Max_Plug = max_Plug;
    }

    public String getCntr_Count() {
        return Cntr_Count;
    }

    public void setCntr_Count(String cntr_Count) {
        Cntr_Count = cntr_Count;
    }

    public String getCount_Reef() {
        return Count_Reef;
    }

    public void setCount_Reef(String count_Reef) {
        Count_Reef = count_Reef;
    }

    public String getEnable_Ind() {
        return Enable_Ind;
    }

    public void setEnable_Ind(String enable_Ind) {
        Enable_Ind = enable_Ind;
    }

    public String getAvail_Ind() {
        return Avail_Ind;
    }

    public void setAvail_Ind(String avail_Ind) {
        Avail_Ind = avail_Ind;
    }

    public String getENABLE_IND() {
        return ENABLE_IND;
    }

    public void setENABLE_IND(String ENABLE_IND) {
        this.ENABLE_IND = ENABLE_IND;
    }

    public String getLst_Upd_Seq() {
        return Lst_Upd_Seq;
    }

    public void setLst_Upd_Seq(String lst_Upd_Seq) {
        Lst_Upd_Seq = lst_Upd_Seq;
    }

    public String getPick_Priority() {
        return Pick_Priority;
    }

    public void setPick_Priority(String pick_Priority) {
        Pick_Priority = pick_Priority;
    }

    public String getPick_Method() {
        return pick_Method;
    }

    public void setPick_Method(String pick_Method) {
        this.pick_Method = pick_Method;
    }

    public String getMax_Density() {
        return Max_Density;
    }

    public void setMax_Density(String max_Density) {
        Max_Density = max_Density;
    }

    public String getTier_Level() {
        return Tier_Level;
    }

    public void setTier_Level(String tier_Level) {
        Tier_Level = tier_Level;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
