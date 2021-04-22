package com.smt.wxdj.swxdj.bean;

import android.text.TextUtils;

import com.smtlibrary.utils.JsonUtils;

import java.io.Serializable;

/**
 * Created by gbh on 16/12/30.
 */

public class CntrEntity extends User implements Serializable, Cloneable {
    private String Cntr;
    private String Category;
    private String Rown;
    private String CntrNo;
    private String Cell;//排(x坐标)
    private String Tier;//层（y 坐标）
    private String Trk_Type;
    private String Eqp_Type;
    private String Eqp_Sta;
    private String Cy_Rsv;
    private String Ld_Rsv;
    private String Intl_Rsv;
    private String Len_Class;
    private String Grs_Ton;
    private String Grs_Wgt;
    private String In_Vvd;
    private String Out_Vvd;
    private String In_Vsl;
    private String Out_Vsl;
    private String VSL;
    private String VVD;
    private String VVDVSL;
    private String Pod; //目的港
    private String Opr;
    private String Job_Id;
    private String Reef_Ind;
    private String Temp_Req;
    private String Maj_Loc;
    private String Sub_Loc;
    private String Loc_Sta;
    private String Chk_DgtStack;
    private String Hold;
    private String Grp;
    private String To_Loc;
    private String Osz_Ind;
    private String Osz_Hgt;
    private String Sub_Tier;
    private String Work_Seq;
    private String Portainer;
    private String Port_Workseq;
    private String Custbay;
    private String Deck;
    private String Bay;
    private String Bay_Index;
    private String Fe_Ind;
    private String Opd;
    private boolean SelectCntr;
    private String Wgt_Class;
    private String iFromRow;
    private String iFromCell;
    private String iFromTier;
    private String iToRow;
    private String iToCell;
    private String iToTier;
    private String Crane;
    private String Status;
    private String Stack;
    private String User;
    private String Activity;
    private String Svc_Code;
    private String Trk;
    private String EndBay;
    private String StartBay;
    private String HAZ_IND;
    private String REEF_IND;
    private String OSZ_IND;
    private String TypeClass;
    private String Htch;
    private String IsCheck;
    private String DoublePos;
    private String ARV_TYPE;
    private String WaitTime;
    private String ActivityDescpt;
    private String Lst_Tx_Date;
    private String Stow_Lock;
    private String CrnOprGrp;
    private String CrnOprUser;

//    private CntrColor FontColor;
//    private CntrColor BackColor;
//    private CntrColor SameLineBackColor;

    private boolean IsSameLine;
    private boolean IsSameTask;
    private boolean IsChange;//判断是否可交换箱子
    private boolean Selected;

    private String SWAP_FLAG;//0 不可以  1可以换箱

    private String MISC_NO;//
    private String POL;//
    private String LST_TX_USER;//最后修改用户
    private String Bkg;//预约号
    private String LST_TX;//

    private String Pick_Method;//拖车方向

    private String Bl_No;//提箱号
    private boolean IsWaiting;//是否预约
    private String VSL_DOCKING;//是否已启航
    private String Eqp_Cond;//箱况（残损信息）

    public String getPOD() {
        return TextUtils.isEmpty(Pod) ? "" : Pod;
    }

    public String getEQP_COND() {
        return TextUtils.isEmpty(Eqp_Cond) ? "" : Eqp_Cond;
    }

    public String getVSL_DOCKING() {
        return VSL_DOCKING;
    }

    public boolean isWaiting() {
        return IsWaiting;
    }

    public String getCntr() {
        return Cntr;
    }

    public void setCntr(String cntr) {
        Cntr = cntr;
    }

    public String getCategory() {
        return Category;
    }

    public String getRown() {
        return Rown;
    }

    public void setRown(String rown) {
        Rown = rown;
    }

    public void setCntrNo(String cntrNo) {
        CntrNo = cntrNo;
    }

    public String getCell() {
        return TextUtils.isEmpty(Cell) ? "0" : Cell;
    }

    public void setCell(String cell) {
        Cell = cell;
    }

    public String getTier() {
        return TextUtils.isEmpty(Tier) ? "0" : Tier;
    }

    public void setTier(String tier) {
        Tier = tier;
    }

    public String getTrk_Type() {
        return TextUtils.isEmpty(Trk_Type) ? "" : Trk_Type;
    }

    public void setTrk_Type(String trk_Type) {
        Trk_Type = trk_Type;
    }

    public String getEqp_Type() {
        return Eqp_Type;
    }

    public String getEqp_Sta() {
        return TextUtils.isEmpty(Eqp_Sta) ? "" : Eqp_Sta;
    }

    public String getCy_Rsv() {
        return Cy_Rsv;
    }

    public String getLd_Rsv() {
        return Ld_Rsv;
    }

    public String getLen_Class() {
        return Len_Class;
    }

    public String getGrs_Ton() {
        return Grs_Ton;
    }

    public String getIn_Vsl() {
        return In_Vsl;
    }

    public String getOut_Vsl() {
        return Out_Vsl;
    }

    public String getVVDVSL() {
        return VVDVSL;
    }

    public String getOpr() {
        return Opr;
    }

    public void setOpr(String opr) {
        Opr = opr;
    }

    public String getJob_Id() {
        return Job_Id;
    }

    public void setJob_Id(String job_Id) {
        Job_Id = job_Id;
    }

    public void setMaj_Loc(String maj_Loc) {
        Maj_Loc = maj_Loc;
    }

    public void setSub_Loc(String sub_Loc) {
        Sub_Loc = sub_Loc;
    }

    public String getPortainer() {
        return Portainer;
    }

    public String getBay() {
        return Bay;
    }

    public void setBay(String bay) {
        Bay = bay;
    }

    public String getFe_Ind() {
        return Fe_Ind;
    }

    public String getOpd() {
        return Opd;
    }

    public void setiFromRow(String iFromRow) {
        this.iFromRow = iFromRow;
    }

    public void setiFromCell(String iFromCell) {
        this.iFromCell = iFromCell;
    }

    public String getiFromTier() {
        return iFromTier;
    }

    public void setiFromTier(String iFromTier) {
        this.iFromTier = iFromTier;
    }

    public String getiToRow() {
        return iToRow;
    }

    public void setiToRow(String iToRow) {
        this.iToRow = iToRow;
    }

    public String getiToCell() {
        return iToCell;
    }

    public void setiToCell(String iToCell) {
        this.iToCell = iToCell;
    }

    public String getiToTier() {
        return iToTier;
    }

    public void setiToTier(String iToTier) {
        this.iToTier = iToTier;
    }

    public String getCrane() {
        return Crane;
    }

    public void setCrane(String crane) {
        Crane = crane;
    }

    public String getStatus() {
        return TextUtils.isEmpty(Status) ? "" : Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStack() {
        return Stack;
    }

    public void setStack(String stack) {
        Stack = stack;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getActivity() {
        return TextUtils.isEmpty(Activity) ? "" : Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getSvc_Code() {
        return Svc_Code;
    }

    public void setSvc_Code(String svc_Code) {
        Svc_Code = svc_Code;
    }

    public String getTrk() {
        return Trk;
    }

    public void setTrk(String trk) {
        Trk = trk;
    }

    public String getEndBay() {
        return EndBay;
    }

    public void setEndBay(String endBay) {
        EndBay = endBay;
    }

    public String getStartBay() {
        return StartBay;
    }

    public void setStartBay(String startBay) {
        StartBay = startBay;
    }

    public String getHAZ_IND() {
        return HAZ_IND;
    }

    public void setHAZ_IND(String HAZ_IND) {
        this.HAZ_IND = HAZ_IND;
    }

    public String getREEF_IND() {
        return REEF_IND;
    }

    public void setREEF_IND(String REEF_IND) {
        this.REEF_IND = REEF_IND;
    }

    public String getOSZ_IND() {
        return OSZ_IND;
    }

    public void setOSZ_IND(String OSZ_IND) {
        this.OSZ_IND = OSZ_IND;
    }

    public String getTypeClass() {
        return TypeClass;
    }

    public void setTypeClass(String typeClass) {
        TypeClass = typeClass;
    }

    public String getHtch() {
        return Htch;
    }

    public void setHtch(String htch) {
        Htch = htch;
    }

    public String getIsCheck() {
        return IsCheck;
    }

    public void setIsCheck(String isCheck) {
        IsCheck = isCheck;
    }

    public String getDoublePos() {
        return DoublePos;
    }

    public void setDoublePos(String doublePos) {
        DoublePos = doublePos;
    }

    public String getARV_TYPE() {
        return ARV_TYPE;
    }

    public void setARV_TYPE(String ARV_TYPE) {
        this.ARV_TYPE = ARV_TYPE;
    }

    public String getWaitTime() {
        return WaitTime;
    }

    public void setWaitTime(String waitTime) {
        WaitTime = waitTime;
    }

    public String getActivityDescpt() {
        return ActivityDescpt;
    }

    public void setActivityDescpt(String activityDescpt) {
        ActivityDescpt = activityDescpt;
    }

    public String getLst_Tx_Date() {
        return Lst_Tx_Date;
    }

    public void setLst_Tx_Date(String lst_Tx_Date) {
        Lst_Tx_Date = lst_Tx_Date;
    }

    public String getStow_Lock() {
        return Stow_Lock;
    }

    public void setStow_Lock(String stow_Lock) {
        Stow_Lock = stow_Lock;
    }

    public String getCrnOprGrp() {
        return CrnOprGrp;
    }

    public void setCrnOprGrp(String crnOprGrp) {
        CrnOprGrp = crnOprGrp;
    }

    public String getCrnOprUser() {
        return CrnOprUser;
    }

    public void setCrnOprUser(String crnOprUser) {
        CrnOprUser = crnOprUser;
    }

//    public CntrColor getFontColor() {
//        return FontColor;
//    }
//
//    public void setFontColor(CntrColor fontColor) {
//        FontColor = fontColor;
//    }
//
//    public CntrColor getBackColor() {
//        return BackColor;
//    }
//
//    public void setBackColor(CntrColor backColor) {
//        BackColor = backColor;
//    }
//
//    public CntrColor getSameLineBackColor() {
//        return SameLineBackColor;
//    }
//
//    public void setSameLineBackColor(CntrColor sameLineBackColor) {
//        SameLineBackColor = sameLineBackColor;
//    }

    public boolean isSameLine() {
        return IsSameLine;
    }

    public void setSameLine(boolean sameLine) {
        IsSameLine = sameLine;
    }

    public boolean isSameTask() {
        return IsSameTask;
    }

    public void setSameTask(boolean sameTask) {
        IsSameTask = sameTask;
    }

    public boolean isChange() {
        return IsChange;
    }

    public void setChange(boolean change) {
        IsChange = change;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public boolean isSWAPFLAG() {
        return TextUtils.isEmpty(SWAP_FLAG) ? false : SWAP_FLAG.equals("0") ? false : true;
    }


    public String getMISC_NO() {
        return TextUtils.isEmpty(MISC_NO) ? "" : MISC_NO;
    }

    public void setMISC_NO(String MISC_NO) {
        this.MISC_NO = MISC_NO;
    }

    public String getPOL() {
        return TextUtils.isEmpty(POL) ? "" : POL;
    }

    public void setPOL(String POL) {
        this.POL = POL;
    }

    public String getLST_TX_USER() {
        return TextUtils.isEmpty(LST_TX_USER) ? "" : LST_TX_USER;
    }

    public void setLST_TX_USER(String LST_TX_USER) {
        this.LST_TX_USER = LST_TX_USER;
    }

    public String getBkg() {
        return TextUtils.isEmpty(Bkg) ? "" : Bkg;
    }

    public void setBkg(String bkg) {
        Bkg = bkg;
    }

    public String getLST_TX() {
        return LST_TX;
    }

    public void setLST_TX(String LST_TX) {
        this.LST_TX = LST_TX;
    }

    public String getPick_Method() {
        return Pick_Method;
    }

    public void setPick_Method(String pick_Method) {
        Pick_Method = pick_Method;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
