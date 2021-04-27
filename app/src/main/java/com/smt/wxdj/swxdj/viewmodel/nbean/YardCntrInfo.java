package com.smt.wxdj.swxdj.viewmodel.nbean;

import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.utils.BoxTool;

/**
 * 任务类型
 */
public class YardCntrInfo extends BoxDetalBean {

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
    private String cntr1;
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


    public YardCntrInfo(String cell, int cellX, int CellY, boolean isPutBox) {
        this.defaultCell = cell;
        this.setCell(String.valueOf(cellX));
        this.setCell(String.valueOf(cellX));
        this.setTier(String.valueOf(CellY));
        this.setTier(String.valueOf(CellY));
        setBoxDt(isPutBox ? BoxTool.CTRL_PUTBOX : BoxTool.CTRL_GETBOX);
    }

    public YardCntrInfo(String cell, String boxDt, boolean isHashBox) {
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

    public String getCntr1() {
        return cntr1;
    }

    public void setCntr1(String cntr1) {
        this.cntr1 = cntr1;
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
        return trkNo;
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
}
