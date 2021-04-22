package com.smt.wxdj.swxdj.webservice;

import com.smt.wxdj.swxdj.bean.User;
import com.smtlibrary.utils.JsonUtils;

/**
 * Created by gbh on 16/6/30.
 */
public class BizObject extends User {
    private String LST_TX_USER;//用户名
    private String Maj_Loc;//主分区
    private String Sub_Loc;
    private String CurrTaskTYPE;//任务类型
    private String Stack;//场地
    private String Rown;//田位
    private String ToRow;//移动的田位
    private String Cell;//排
    private String CNTR;//箱号
    private String Cntr;//箱号
    private String Bay;
    private String Enable_Ind;
    private String Avail_Ind;
    private String Trk;//拖车


    private String MAJLOC;//主分区
    private String SUBLOC;

    private String START_TIME;
    private String END_TIME;
    private String ASSIGN_ROW;

    private String OLDCNTR;

    public String DataType;//类型
    private String Crane;//岸桥号


    private String TaskType;//类型    VSL 装卸  CY 装卸车
    private String param;


    //获取吊机列表的时候写死A
    public String Opr_Sta;

    public BizObject() {
//        if (LogUtils.LOG_DEBUG) {
//            this.Maj_Loc = "DGA";
//            this.Sub_Loc = "DHCT";
//            this.MAJLOC = "DGA";
//            this.SUBLOC = "DHCT";
//        } else {
            this.Maj_Loc = getSignonMAJLOC();
            this.Sub_Loc = getSignonSUBLOC();
            this.MAJLOC = getSignonMAJLOC();
            this.SUBLOC = getSignonSUBLOC();
//        }
        Opr_Sta = "A";//默认取吊机列表参数
    }

    public String getOpr_Sta() {
        return Opr_Sta;
    }

    public void setOpr_Sta(String opr_Sta) {
        Opr_Sta = opr_Sta;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setTaskType(String taskType) {
        TaskType = taskType;
    }

    public void setSub_Loc(String sub_Loc) {
        Sub_Loc = sub_Loc;
    }


    public void setMaj_Loc(String maj_Loc) {
        Maj_Loc = maj_Loc;
    }

    public String getCrane() {
        return Crane;
    }

    public void setCrane(String crane) {
        Crane = crane;
    }

    public void setCurrTaskTYPE(String currTaskTYPE) {
        CurrTaskTYPE = currTaskTYPE;
    }

    public String getStack() {
        return Stack;
    }

    public void setStack(String stack) {
        Stack = stack;
    }

    public void setRown(String rown) {
        Rown = rown;
    }

    public void setToRow(String toRow) {
        ToRow = toRow;
    }

    public void setCell(String cell) {
        Cell = cell;
    }

    public String getCNTR() {
        return CNTR;
    }

    public void setCNTR(String CNTR) {
        this.CNTR = CNTR;
    }

    public String getCntr() {
        return Cntr;
    }

    public void setCntr(String cntr) {
        Cntr = cntr;
    }

    public String getBay() {
        return Bay;
    }

    public void setBay(String bay) {
        Bay = bay;
    }

    public void setLST_TX_USER(String LST_TX_USER) {
        this.LST_TX_USER = LST_TX_USER;
    }

    public String getAvail_Ind() {
        return Avail_Ind;
    }

    public void setAvail_Ind(String avail_Ind) {
        Avail_Ind = avail_Ind;
    }

    public String getEnable_Ind() {
        return Enable_Ind;
    }

    public void setEnable_Ind(String enable_Ind) {
        Enable_Ind = enable_Ind;
    }

    public String getTrk() {
        return Trk;
    }

    public void setTrk(String trk) {
        Trk = trk;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getASSIGN_ROW() {
        return ASSIGN_ROW;
    }

    public void setASSIGN_ROW(String ASSIGN_ROW) {
        this.ASSIGN_ROW = ASSIGN_ROW;
    }

    public String getOLDCNTR() {
        return OLDCNTR;
    }

    public void setOLDCNTR(String OLDCNTR) {
        this.OLDCNTR = OLDCNTR;
    }

    public String getMAJLOC() {
        return MAJLOC;
    }

    public void setMAJLOC(String MAJLOC) {
        this.MAJLOC = MAJLOC;
    }

    public String getSUBLOC() {
        return SUBLOC;
    }

    public void setSUBLOC(String SUBLOC) {
        this.SUBLOC = SUBLOC;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
