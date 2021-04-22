package com.smt.wxdj.swxdj.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by gbh on 16/7/2.
 */
public class BoxBean extends User implements Serializable, Cloneable {
    private String CNTR;//集装箱编号
    private String CNTRNO;
    private String MISC_NO;//船名航次
    private String MISC_SEQ;
    private String WORK_SEQ;
    private String STATUS;//wd  状态
    private String STOW_POSITION;
    private String OPR;//持箱人
    private String EQP_TYPE;//箱型
    private String LEN_CLASS;//LEN_CLASS
    private String FE_IND;//空重
    private String GRS_TON;//总重(吨)
    private String EQP_STA;//所属基本状态
    private String CATEGORY;//箱状态
    private String IN_DATE;//进场时间
    private String CNTR_STA;
    private String IN_TIME;
    private String RSV;//预留
    private String GRS_WGT;//总重(克)
    private String TEMP_REQ;//TEMP_REQ
    private String REEF_IND;//REEF_IND
    private String HAZ_IND;//HAZ_IND
    private String OSZ_IND;//超码箱标示
    private String OUT_VSL_VVD;
    private String IN_VSL_VVD;
    private String VVD;//航次
    private String IN_VSL;//进口航次
    private String IN_VVD;//进口航次
    private String OUT_VSL;//出口航次
    private String OUT_VVD;//出口航次
    private String ENTRY_DATE;//进场日期
    private String POL;//载货港
    private String OPD;//卸货港
    private String POD;//目的港
    private String DST;//目的地
    private String BL_NO;//提单号
    private String SO_NO;
    private String SEAL;//铅封号
    private String PORTCRN;
    private String PORT_WORKSEQ;//PORT_WORKSEQ
    private String YARDCRN;//吊机好
    private String TRUCK;//车号
    private String TRK_TYPE;//拖车类型  拖车作业类型 MRE装卸船，CY场地，BRG驳船(装船)
    private String Rown;//当前田位
    private String Cell;//当前排（X）
    private String Tier;//当前层（Y）
    private String TO_LOC;//前往位置
    private String ToRow;//前往的田位
    private String ToCell;//前往的排（X）
    private String ToTier;//前往的层（Y）
    private String JOB_ID;//工作编号
    private String Stack;//场地
    private String Activity;//动态
    private String ACTIVITY;//动态
    private String Svc_Code;//服务代码
    private String IsCheck;
    private String BL_SO;
    private String CGOOD_TYPE;
    private String EGOOD_TYPE;
    private String ROWN;//呗位／田位
    private String CELL;//排
    private String TIER;//层
    private String OSZ_HGT;//高超出
    private String OSZ_FRT;//前超出
    private String OSZ_LFT;//左超出
    private String OSZ_RGT;//右超出
    private String OSZ_RER;//后超出
    private String ARV_TYPE;//进场方式		Y	G代表闸口，V代表船
    private String GATEID;
    private String IDCARD;
    private String CUST_PASS_NO;//海关放行号
    private String DRIVER;//司机
    private String CRN_OPR_GRP;
    private String CRN_OPR_USER;//CRN_OPR_USER
    private String QC_DRIVER;
    private String CAL_TRK_WORK;
    private String MajLoc;//主分区
    private String SubLoc;//子分区
    private String Vessel;
    private String InVoyage;
    private String OutVoyage;
    private String LST_TX;//最后模块
    private String LST_TX_USER;//最后修改用户
    private String CDESCPT;//货名
    private String HOLD;//暂留
    private String BKG;  //VARCHAR2(20) 预约编号			Y	跟预约表关联

    private String SWAPFLAG;//0 不可以  1可以换箱

    public String getCNTR() {
        return CNTR;
    }

    public void setCNTR(String CNTR) {
        this.CNTR = CNTR;
    }

    public String getCNTRNO() {
        return CNTRNO;
    }

    public void setCNTRNO(String CNTRNO) {
        this.CNTRNO = CNTRNO;
    }

    public String getMISC_NO() {
        return MISC_NO;
    }

    public void setMISC_NO(String MISC_NO) {
        this.MISC_NO = MISC_NO;
    }

    public String getMISC_SEQ() {
        return MISC_SEQ;
    }

    public void setMISC_SEQ(String MISC_SEQ) {
        this.MISC_SEQ = MISC_SEQ;
    }

    public String getWORK_SEQ() {
        return WORK_SEQ;
    }

    public void setWORK_SEQ(String WORK_SEQ) {
        this.WORK_SEQ = WORK_SEQ;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTOW_POSITION() {
        return STOW_POSITION;
    }

    public void setSTOW_POSITION(String STOW_POSITION) {
        this.STOW_POSITION = STOW_POSITION;
    }

    public String getOPR() {
        return OPR;
    }

    public void setOPR(String OPR) {
        this.OPR = OPR;
    }

    public String getEQP_TYPE() {
        return EQP_TYPE;
    }

    public void setEQP_TYPE(String EQP_TYPE) {
        this.EQP_TYPE = EQP_TYPE;
    }

    public String getLEN_CLASS() {
        return LEN_CLASS;
    }

    public void setLEN_CLASS(String LEN_CLASS) {
        this.LEN_CLASS = LEN_CLASS;
    }

    public String getFE_IND() {
        return FE_IND;
    }

    public void setFE_IND(String FE_IND) {
        this.FE_IND = FE_IND;
    }

    public String getGRS_TON() {
        return GRS_TON;
    }

    public void setGRS_TON(String GRS_TON) {
        this.GRS_TON = GRS_TON;
    }

    public String getEQP_STA() {
        return EQP_STA;
    }

    public void setEQP_STA(String EQP_STA) {
        this.EQP_STA = EQP_STA;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getIN_DATE() {
        return IN_DATE;
    }

    public void setIN_DATE(String IN_DATE) {
        this.IN_DATE = IN_DATE;
    }

    public String getCNTR_STA() {
        return CNTR_STA;
    }

    public void setCNTR_STA(String CNTR_STA) {
        this.CNTR_STA = CNTR_STA;
    }

    public String getIN_TIME() {
        return IN_TIME;
    }

    public void setIN_TIME(String IN_TIME) {
        this.IN_TIME = IN_TIME;
    }

    public String getRSV() {
        return RSV;
    }

    public void setRSV(String RSV) {
        this.RSV = RSV;
    }

    public String getGRS_WGT() {
        return GRS_WGT;
    }

    public void setGRS_WGT(String GRS_WGT) {
        this.GRS_WGT = GRS_WGT;
    }

    public String getTEMP_REQ() {
        return TEMP_REQ;
    }

    public void setTEMP_REQ(String TEMP_REQ) {
        this.TEMP_REQ = TEMP_REQ;
    }

    public String getREEF_IND() {
        return REEF_IND;
    }

    public void setREEF_IND(String REEF_IND) {
        this.REEF_IND = REEF_IND;
    }

    public String getHAZ_IND() {
        return HAZ_IND;
    }

    public void setHAZ_IND(String HAZ_IND) {
        this.HAZ_IND = HAZ_IND;
    }

    public String getOSZ_IND() {
        return OSZ_IND;
    }

    public void setOSZ_IND(String OSZ_IND) {
        this.OSZ_IND = OSZ_IND;
    }

    public String getOUT_VSL_VVD() {
        return OUT_VSL_VVD;
    }

    public void setOUT_VSL_VVD(String OUT_VSL_VVD) {
        this.OUT_VSL_VVD = OUT_VSL_VVD;
    }

    public String getIN_VSL_VVD() {
        return IN_VSL_VVD;
    }

    public void setIN_VSL_VVD(String IN_VSL_VVD) {
        this.IN_VSL_VVD = IN_VSL_VVD;
    }

    public String getVVD() {
        return VVD;
    }

    public void setVVD(String VVD) {
        this.VVD = VVD;
    }

    public String getIN_VSL() {
        return IN_VSL;
    }

    public void setIN_VSL(String IN_VSL) {
        this.IN_VSL = IN_VSL;
    }

    public String getIN_VVD() {
        return IN_VVD;
    }

    public void setIN_VVD(String IN_VVD) {
        this.IN_VVD = IN_VVD;
    }

    public String getOUT_VSL() {
        return OUT_VSL;
    }

    public void setOUT_VSL(String OUT_VSL) {
        this.OUT_VSL = OUT_VSL;
    }

    public String getOUT_VVD() {
        return OUT_VVD;
    }

    public void setOUT_VVD(String OUT_VVD) {
        this.OUT_VVD = OUT_VVD;
    }

    public String getENTRY_DATE() {
        return ENTRY_DATE;
    }

    public void setENTRY_DATE(String ENTRY_DATE) {
        this.ENTRY_DATE = ENTRY_DATE;
    }

    public String getPOL() {
        return POL;
    }

    public void setPOL(String POL) {
        this.POL = POL;
    }

    public String getOPD() {
        return OPD;
    }

    public void setOPD(String OPD) {
        this.OPD = OPD;
    }

    public String getPOD() {
        return POD;
    }

    public void setPOD(String POD) {
        this.POD = POD;
    }

    public String getDST() {
        return DST;
    }

    public void setDST(String DST) {
        this.DST = DST;
    }

    public String getBL_NO() {
        return BL_NO;
    }

    public void setBL_NO(String BL_NO) {
        this.BL_NO = BL_NO;
    }

    public String getSO_NO() {
        return SO_NO;
    }

    public void setSO_NO(String SO_NO) {
        this.SO_NO = SO_NO;
    }

    public String getSEAL() {
        return SEAL;
    }

    public void setSEAL(String SEAL) {
        this.SEAL = SEAL;
    }

    public String getPORTCRN() {
        return PORTCRN;
    }

    public void setPORTCRN(String PORTCRN) {
        this.PORTCRN = PORTCRN;
    }

    public String getPORT_WORKSEQ() {
        return PORT_WORKSEQ;
    }

    public void setPORT_WORKSEQ(String PORT_WORKSEQ) {
        this.PORT_WORKSEQ = PORT_WORKSEQ;
    }

    public String getYARDCRN() {
        return YARDCRN;
    }

    public void setYARDCRN(String YARDCRN) {
        this.YARDCRN = YARDCRN;
    }

    public String getTRUCK() {
        return TRUCK;
    }

    public void setTRUCK(String TRUCK) {
        this.TRUCK = TRUCK;
    }

    public String getTRK_TYPE() {
        return TRK_TYPE;
    }

    public void setTRK_TYPE(String TRK_TYPE) {
        this.TRK_TYPE = TRK_TYPE;
    }

    public String getRown() {
        return Rown;
    }

    public void setRown(String rown) {
        Rown = rown;
    }

    public String getCell() {
        return Cell;
    }

    public void setCell(String cell) {
        Cell = cell;
    }

    public String getTier() {
        return Tier;
    }

    public void setTier(String tier) {
        Tier = tier;
    }

    public String getTO_LOC() {
        return TO_LOC;
    }

    public void setTO_LOC(String TO_LOC) {
        this.TO_LOC = TO_LOC;
    }

    public String getToRow() {
        return ToRow;
    }

    public void setToRow(String toRow) {
        ToRow = toRow;
    }

    public String getToCell() {
        return ToCell;
    }

    public void setToCell(String toCell) {
        ToCell = toCell;
    }

    public String getToTier() {
        return ToTier;
    }

    public void setToTier(String toTier) {
        ToTier = toTier;
    }

    public String getJOB_ID() {
        return JOB_ID;
    }

    public void setJOB_ID(String JOB_ID) {
        this.JOB_ID = JOB_ID;
    }

    public String getStack() {
        return Stack;
    }

    public void setStack(String stack) {
        Stack = stack;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getACTIVITY() {
        return ACTIVITY;
    }

    public void setACTIVITY(String ACTIVITY) {
        this.ACTIVITY = ACTIVITY;
    }

    public String getSvc_Code() {
        return Svc_Code;
    }

    public void setSvc_Code(String svc_Code) {
        Svc_Code = svc_Code;
    }

    public String getIsCheck() {
        return IsCheck;
    }

    public void setIsCheck(String isCheck) {
        IsCheck = isCheck;
    }

    public String getBL_SO() {
        return BL_SO;
    }

    public void setBL_SO(String BL_SO) {
        this.BL_SO = BL_SO;
    }

    public String getCGOOD_TYPE() {
        return CGOOD_TYPE;
    }

    public void setCGOOD_TYPE(String CGOOD_TYPE) {
        this.CGOOD_TYPE = CGOOD_TYPE;
    }

    public String getEGOOD_TYPE() {
        return EGOOD_TYPE;
    }

    public void setEGOOD_TYPE(String EGOOD_TYPE) {
        this.EGOOD_TYPE = EGOOD_TYPE;
    }

    public String getROWN() {
        return ROWN;
    }

    public void setROWN(String ROWN) {
        this.ROWN = ROWN;
    }

    public String getCELL() {
        return CELL;
    }

    public void setCELL(String CELL) {
        this.CELL = CELL;
    }

    public String getTIER() {
        return TIER;
    }

    public void setTIER(String TIER) {
        this.TIER = TIER;
    }

    public String getOSZ_HGT() {
        return OSZ_HGT;
    }

    public void setOSZ_HGT(String OSZ_HGT) {
        this.OSZ_HGT = OSZ_HGT;
    }

    public String getOSZ_FRT() {
        return OSZ_FRT;
    }

    public void setOSZ_FRT(String OSZ_FRT) {
        this.OSZ_FRT = OSZ_FRT;
    }

    public String getOSZ_LFT() {
        return OSZ_LFT;
    }

    public void setOSZ_LFT(String OSZ_LFT) {
        this.OSZ_LFT = OSZ_LFT;
    }

    public String getOSZ_RGT() {
        return OSZ_RGT;
    }

    public void setOSZ_RGT(String OSZ_RGT) {
        this.OSZ_RGT = OSZ_RGT;
    }

    public String getOSZ_RER() {
        return OSZ_RER;
    }

    public void setOSZ_RER(String OSZ_RER) {
        this.OSZ_RER = OSZ_RER;
    }

    public String getARV_TYPE() {
        return ARV_TYPE;
    }

    public void setARV_TYPE(String ARV_TYPE) {
        this.ARV_TYPE = ARV_TYPE;
    }

    public String getGATEID() {
        return GATEID;
    }

    public void setGATEID(String GATEID) {
        this.GATEID = GATEID;
    }

    public String getIDCARD() {
        return IDCARD;
    }

    public void setIDCARD(String IDCARD) {
        this.IDCARD = IDCARD;
    }

    public String getCUST_PASS_NO() {
        return CUST_PASS_NO;
    }

    public void setCUST_PASS_NO(String CUST_PASS_NO) {
        this.CUST_PASS_NO = CUST_PASS_NO;
    }

    public String getDRIVER() {
        return DRIVER;
    }

    public void setDRIVER(String DRIVER) {
        this.DRIVER = DRIVER;
    }

    public String getCRN_OPR_GRP() {
        return CRN_OPR_GRP;
    }

    public void setCRN_OPR_GRP(String CRN_OPR_GRP) {
        this.CRN_OPR_GRP = CRN_OPR_GRP;
    }

    public String getCRN_OPR_USER() {
        return CRN_OPR_USER;
    }

    public void setCRN_OPR_USER(String CRN_OPR_USER) {
        this.CRN_OPR_USER = CRN_OPR_USER;
    }

    public String getQC_DRIVER() {
        return QC_DRIVER;
    }

    public void setQC_DRIVER(String QC_DRIVER) {
        this.QC_DRIVER = QC_DRIVER;
    }

    public String getCAL_TRK_WORK() {
        return CAL_TRK_WORK;
    }

    public void setCAL_TRK_WORK(String CAL_TRK_WORK) {
        this.CAL_TRK_WORK = CAL_TRK_WORK;
    }

    public String getMajLoc() {
        return MajLoc;
    }

    public void setMajLoc(String majLoc) {
        MajLoc = majLoc;
    }

    public String getSubLoc() {
        return SubLoc;
    }

    public void setSubLoc(String subLoc) {
        SubLoc = subLoc;
    }

    public String getVessel() {
        return Vessel;
    }

    public void setVessel(String vessel) {
        Vessel = vessel;
    }

    public String getInVoyage() {
        return InVoyage;
    }

    public void setInVoyage(String inVoyage) {
        InVoyage = inVoyage;
    }

    public String getOutVoyage() {
        return OutVoyage;
    }

    public void setOutVoyage(String outVoyage) {
        OutVoyage = outVoyage;
    }

    public String getLST_TX() {
        return LST_TX;
    }

    public void setLST_TX(String LST_TX) {
        this.LST_TX = LST_TX;
    }

    public String getLST_TX_USER() {
        return LST_TX_USER;
    }

    public void setLST_TX_USER(String LST_TX_USER) {
        this.LST_TX_USER = LST_TX_USER;
    }

    public String getCDESCPT() {
        return CDESCPT;
    }

    public void setCDESCPT(String CDESCPT) {
        this.CDESCPT = CDESCPT;
    }

    public String getHOLD() {
        return HOLD;
    }

    public void setHOLD(String HOLD) {
        this.HOLD = HOLD;
    }

    public String getSWAPFLAG() {
        return TextUtils.isEmpty(SWAPFLAG) ? "0" : SWAPFLAG;
    }

    public void setSWAPFLAG(String SWAPFLAG) {
        this.SWAPFLAG = SWAPFLAG;
    }

    public String getBKG() {
        return BKG;
    }

    public void setBKG(String BKG) {
        this.BKG = BKG;
    }

    /**
     * 克隆一个对象
     *
     * @return
     */
    @Override
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
