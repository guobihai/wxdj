package com.smt.wxdj.swxdj.viewmodel.nbean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.utils.BoxTool;

public class YardTaskInfo extends BoxDetalBean {
    static final String CX = "CX";//倒箱子
    /**
     * activity : PK
     * cntr1 : CFSF03270006
     * cntrType1 : 40GP
     * gateId : 00000000-0000-0000-0000-000000000000
     * id : 39fc14ff-c382-07a7-93fc-9f11281abff5
     * location : 49 - 1
     * svc : CTC
     * svcDesc : 倒箱
     * trkNo : TK10010
     * trkType :
     * waitMinute : 1832.1623686333332
     * workTime : 2021-04-24T15:40:48.395257
     * yardBayId : 39fb7959-7d4c-8ec1-6b6c-e8f1bbd3f8b7
     * yardBlockId : 00000000-0000-0000-0000-000000000000
     */


    private String activity;
    private String cntr;
    private String cntrId;
    private String cntrType1;
    private String gateId;
    private String id;
    private String location;
    private String svc;
    private String svcDesc;
    private String trkNo;
    private String trkType;
    private double waitMinute;
    private String workTime;
    private String yardBayId;
    private String yardBlockId;
    private String fromYardBlockId;
    private String toYardBlockId;
    private String toYardBayId;
    private String fromYardBayId;
    private String typeClass;
    private String sizeClass;
    private int toCell;
    private int fromCell;
    private int fromTier;
    private String workLine;//工作线
    //    工作线：workLine
//    残损：cntrCond
//    持箱人：optr
//    货名：cargoName
//    总重：grsWgt
    private String cntrCond;
    private String optr;
    private String cargoName;
    private Object grsWgt;

    /**
     * roadId : 3fa85f64-5717-4562-b3fc-2c963f66afa6
     * cell : 0
     * tier : 0
     * waitMinute : 0
     * cntr2 : string
     * cntr2Id : 3fa85f64-5717-4562-b3fc-2c963f66afa6
     * sizeClass2 : string
     * typeClass2 : string
     * feInd : string
     * status : string
     * activityDesc : string
     * statusDesc : string
     */

    private String roadId;
    private int cell;
    private int tier;
    private String cntr2;
    private String cntr2Id;
    private String sizeClass2;
    private String typeClass2;
    private String feInd;
    private String feIndDesc;
    private String status;
    private String activityDesc;
    private String statusDesc;

    public YardCntrInfo toYardCntrInfo(String yardSiteId) {
        YardCntrInfo bean = new YardCntrInfo();
        bean.setActivity(activity);
        bean.setCntr(cntr);
//        bean.setCell(location);
        bean.setTrkWorkId(id);
        bean.setTrk(trkNo);
        bean.setYardSiteId(yardSiteId);
        bean.setTrk_Type(trkType);
        bean.setYardBlockId(yardBlockId);
        bean.setId(cntrId);
        bean.setSizeClass(sizeClass);
        bean.setTypeClass(typeClass);
        bean.setFeInd(feInd);
        bean.setStatus(status);
        if (activity.equals(CX)) {//倒箱子
            bean.setYardBlockId(fromYardBlockId);
            bean.setYardBayId(fromYardBayId);
            bean.setFromYardBayId(fromYardBayId);
            bean.setToYardBayId(toYardBayId);
            bean.setFromYardBlockId(fromYardBlockId);
            bean.setToYardBlockId(toYardBlockId);
        } else {
            bean.setYardBayId(yardBayId);
        }
        return bean;
    }


    public YardTaskInfo(String cell, int cellX, int CellY, boolean isPutBox) {
        this.defaultCell = cell;
        this.setCell(String.valueOf(cellX));
        this.setCell(String.valueOf(cellX));
        this.setTier(String.valueOf(CellY));
        this.setTier(String.valueOf(CellY));
        setBoxDt(isPutBox ? BoxTool.CTRL_PUTBOX : BoxTool.CTRL_GETBOX);
    }

    public YardTaskInfo(String cell, String boxDt, boolean isHashBox) {
        this.defaultCell = cell;
        this.isHashBox = isHashBox;
        this.boxDt = boxDt;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCntr() {
        return cntr;
    }

    public void setCntr1(String cntr) {
        this.cntr = cntr;
    }

    public String getCntrType1() {
        return cntrType1;
    }

    public void setCntrType1(String cntrType1) {
        this.cntrType1 = cntrType1;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSvc() {
        return svc;
    }

    public void setSvc(String svc) {
        this.svc = svc;
    }

    public String getSvcDesc() {
        return svcDesc;
    }

    public void setSvcDesc(String svcDesc) {
        this.svcDesc = svcDesc;
    }

    public String getTrkNo() {
        return trkNo == null ? "" : trkNo;
    }

    public void setTrkNo(String trkNo) {
        this.trkNo = trkNo;
    }

    public String getTrkType() {
        return trkType;
    }

    public void setTrkType(String trkType) {
        this.trkType = trkType;
    }

    public double getWaitMinute() {
        return waitMinute;
    }

    public void setWaitMinute(double waitMinute) {
        this.waitMinute = waitMinute;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getYardBayId() {
        return yardBayId;
    }

    public void setYardBayId(String yardBayId) {
        this.yardBayId = yardBayId;
    }

    public String getYardBlockId() {
        return yardBlockId;
    }

    public void setYardBlockId(String yardBlockId) {
        this.yardBlockId = yardBlockId;
    }

    public String getCntr1Id() {
        return cntrId;
    }

    public void setCntr1Id(String cntr1Id) {
        this.cntrId = cntr1Id;
    }

    public String getToYardBayId() {
        return toYardBayId;
    }

    public void setToYardBayId(String toYardBayId) {
        this.toYardBayId = toYardBayId;
    }

    public String getFromYardBayId() {
        return fromYardBayId;
    }

    public void setFromYardBayId(String fromYardBayId) {
        this.fromYardBayId = fromYardBayId;
    }

    public String getTypeClass() {
        return typeClass == null ? "" : typeClass;
    }

    public String getSizeClass() {
        return sizeClass == null ? "" : sizeClass;
    }

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }

    public int getIntCell() {
        return activity.equals(CX) ? fromCell : cell;
    }

    public void setIntCell(int cellX) {
        this.cell = cellX;
    }

    public int getIntTier() {
        return activity.equals(CX) ? fromTier : tier;
    }

    public void setIntTier(int tier) {
        this.tier = tier;
    }


    public String getCntr2() {
        return cntr2;
    }

    public void setCntr2(String cntr2) {
        this.cntr2 = cntr2;
    }

    public String getCntr2Id() {
        return cntr2Id;
    }

    public void setCntr2Id(String cntr2Id) {
        this.cntr2Id = cntr2Id;
    }

    public String getSizeClass2() {
        return sizeClass2;
    }

    public void setSizeClass2(String sizeClass2) {
        this.sizeClass2 = sizeClass2;
    }

    public String getTypeClass2() {
        return typeClass2;
    }

    public void setTypeClass2(String typeClass2) {
        this.typeClass2 = typeClass2;
    }

    public String getFeInd() {
        return feInd;
    }

    public void setFeInd(String feInd) {
        this.feInd = feInd;
    }

    public String getStatus() {
        return TextUtils.isEmpty(status) ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public int getToCell() {
        return toCell;
    }

    public void setToCell(int toCell) {
        this.toCell = toCell;
    }

    public int getFromCell() {
        return fromCell;
    }

    public void setFromCell(int fromCell) {
        this.fromCell = fromCell;
    }

    public int getFromTier() {
        return fromTier;
    }

    public void setFromTier(int fromTier) {
        this.fromTier = fromTier;
    }

    public String getFromYardBlockId() {
        return fromYardBlockId;
    }

    public void setFromYardBlockId(String fromYardBlockId) {
        this.fromYardBlockId = fromYardBlockId;
    }

    public String getToYardBlockId() {
        return toYardBlockId;
    }

    public void setToYardBlockId(String toYardBlockId) {
        this.toYardBlockId = toYardBlockId;
    }

    public String getWorkLine() {
        return workLine;
    }

    public void setWorkLine(String workLine) {
        this.workLine = workLine;
    }

    public String getCntrCond() {
        return cntrCond;
    }

    public void setCntrCond(String cntrCond) {
        this.cntrCond = cntrCond;
    }

    public String getOptr() {
        return optr == null ? "" : optr;
    }

    public void setOptr(String optr) {
        this.optr = optr;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public Object getGrsWgt() {
        return null == grsWgt ? "" : grsWgt;
    }

    public void setGrsWgt(Object grsWgt) {
        this.grsWgt = grsWgt;
    }

    public String getFeIndDesc() {
        return TextUtils.isEmpty(feIndDesc) ? feInd : feIndDesc;
    }

    public void setFeindDesc(String feindDesc) {
        this.feIndDesc = feindDesc;
    }
}
