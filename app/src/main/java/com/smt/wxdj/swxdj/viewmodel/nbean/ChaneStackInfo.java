package com.smt.wxdj.swxdj.viewmodel.nbean;

import android.text.TextUtils;

import com.smt.wxdj.swxdj.bean.StackBean;

import java.util.Comparator;

/**
 * 作业区域
 */
public class ChaneStackInfo extends StackBean implements Comparator<ChaneStackInfo> {

    /**
     * craneNo : RTG01
     * craneName : 龙门吊01
     * yardBlock : S01
     * yardSite : 一站
     * yardSiteId : 39fb1302-e790-9243-06df-83853d8b948e
     * craneId : 39faf629-ef1f-85b9-4c9a-c376ffc48804
     * yardBlockId : 39fb7959-6e5f-8549-fa18-ec69cc317f00
     * beginDate : 2021-05-01T23:02:09
     * endDate : 2021-05-02T23:02:08
     * currentBayId : 39fb7959-7bba-6e27-25f1-7de99c41c44c
     * status : Y
     * statusDesc : 是
     * tenantId : 39f967cb-0049-d2cd-7099-3bacf49973f9
     * rowVersion : 2021-04-24T14:43:04.028358
     * concurrencyStamp : 0b8ca20efb5f43789f14834340cdcc67
     * creationTime : 2021-04-24T14:43:04.069526
     * creatorId : 39f967cb-034d-63f2-a132-c4acda4ae7ff
     * id : 39fc14ca-e8dc-3ea8-44e6-605f8c8c622d
     */

    private String craneNo;
    private String craneName;
    private String yardBlock;
    private String yardSite;
    private String yardSiteId;
    private String craneId;
    private String yardBlockId;
    private String beginDate;
    private String endDate;
    private String currentBayId;
    private String status;
    private String statusDesc;
    private String tenantId;
    private String rowVersion;
    private String concurrencyStamp;
    private String creationTime;
    private String creatorId;
    private String id;
    private String currentBay;
    private int workCount;

    public String getCraneNo() {
        return craneNo;
    }

    public void setCraneNo(String craneNo) {
        this.craneNo = craneNo;
    }

    public String getCraneName() {
        return craneName;
    }

    public void setCraneName(String craneName) {
        this.craneName = craneName;
    }

    public String getYardBlock() {
        return TextUtils.isEmpty(yardBlock)?"":yardBlock;
    }

    public void setYardBlock(String yardBlock) {
        this.yardBlock = yardBlock;
    }

    public String getYardSite() {
        return yardSite;
    }

    public void setYardSite(String yardSite) {
        this.yardSite = yardSite;
    }

    public String getYardSiteId() {
        return yardSiteId;
    }

    public void setYardSiteId(String yardSiteId) {
        this.yardSiteId = yardSiteId;
    }

    public String getCraneId() {
        return craneId;
    }

    public void setCraneId(String craneId) {
        this.craneId = craneId;
    }

    public String getYardBlockId() {
        return yardBlockId;
    }

    public void setYardBlockId(String yardBlockId) {
        this.yardBlockId = yardBlockId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrentBayId() {
        return currentBayId;
    }

    public void setCurrentBayId(String currentBayId) {
        this.currentBayId = currentBayId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(String rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getConcurrencyStamp() {
        return concurrencyStamp;
    }

    public void setConcurrencyStamp(String concurrencyStamp) {
        this.concurrencyStamp = concurrencyStamp;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentBay() {
        return currentBay;
    }

    public void setCurrentBay(String currentBay) {
        this.currentBay = currentBay;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    @Override
    public int compare(ChaneStackInfo o1, ChaneStackInfo o2) {
        return o1.getYardBlock().compareTo(o2.getYardBlock());
    }
}
