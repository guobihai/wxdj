package com.smt.wxdj.swxdj.viewmodel.nbean;

import android.text.TextUtils;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.enums.ColorType;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 任务类型
 */
public class YardCntrInfo extends BoxDetalBean {


    /**
     * cntr : CFSE03250001
     * cntrType : 20GP
     * ownerId : 39fb1301-f741-fb80-6860-c0ea6318827c
     * optrId : 39fb76dc-00d9-da6d-3d5f-cf42d1e46247
     * tradeType : ST
     * feInd : E
     * feIndDesc : 空箱
     * cntrCode :
     * cntrCodeDesc :
     * tranType :
     * tranTypeDesc :
     * sealNo :
     * blNo : BL0324001
     * soNo :
     * grsWgt : 0
     * cargoWgt : 0
     * cargoPkg : 0
     * cargoCbm : 0
     * consignee : COS
     * recType :
     * recTypeDesc :
     * shipper : COS
     * dlyType :
     * dlyTypeDesc :
     * schSec :
     * bookingNo :
     * org : SHA
     * pof : SHA
     * pol : SHA
     * pod : SHA
     * pdt : SHA
     * dst : SHA
     * locSta : Y
     * locStaDesc : 堆场
     * entryType : G
     * entryTypeDesc : 闸口
     * entryTime : 2020-12-14T09:11:57.71
     * yardSiteId : 39fb1302-e790-9243-06df-83853d8b948e
     * yardAreaId : 39fb7956-b237-9acf-6abf-de3b1ff2a93f
     * yardBlockId : 39fb7959-6e5f-8549-fa18-ec69cc317f00
     * yardBayId : 39fb7959-6e6b-099a-415e-16f54a658bc2
     * cell : 1
     * tier : 1
     * pickHand :
     * pickHandDesc :
     * pickOrder : 0
     * pickRestowRight : 0
     * hazInd : N
     * hazIndDesc : 否
     * hazImco :
     * hazImcoDesc :
     * hazClass :
     * hazClassDesc :
     * hazUnno :
     * hazType :
     * reefInd : N
     * reefIndDesc : 否
     * reefReq : 0
     * reefSet : 0
     * reefRead : 0
     * intake :
     * intakeUnit :
     * oszInd : N
     * oszIndDesc : 否
     * oszHgt : 0
     * oszFrt : 0
     * oszRer : 0
     * oszLft : 0
     * oszRgt : 0
     * reefPti :
     * reefPtiDesc :
     * ptiInd :
     * ptiIndDesc :
     * yardRemark :
     * vesselRemark :
     * cfsRemark :
     * gateRemark :
     * srvCy :
     * srvLd :
     * srvIntl :
     * srvCustom :
     * srvCfs :
     * tenantId : 39f967cb-0049-d2cd-7099-3bacf49973f9
     * rowVersion : 2021-03-25T10:54:16.992139
     * concurrencyStamp : 43e780ab17a24439b5a7315b4a27042f
     * creationTime : 2021-03-25T10:54:17.222468
     * creatorId : 39f967cb-034d-63f2-a132-c4acda4ae7ff
     * id : 39fb797a-aba0-7a37-8c2f-7f2ce7f8fcdb
     */

    private String cntr;
    private String cntrType;
    private String ownerId;
    private String optrId;
    private String optr;//运营人
    private String tradeType;
    private String feInd;
    private String feIndDesc;
    private String cntrCode;
    private String cntrCodeDesc;
    private String tranType;
    private String tranTypeDesc;
    private String sealNo;
    private String blNo;
    private String soNo;
    private double grsWgt;
    private double cargoWgt;
    private double cargoPkg;
    private double cargoCbm;
    private String consignee;
    private String recType;
    private String recTypeDesc;
    private String shipper;
    private String dlyType;
    private String dlyTypeDesc;
    private String schSec;
    private String bookingNo;
    private String org;
    private String pof;
    private String pol;
    private String pod;
    private String pdt;
    private String dst;
    private String locSta;
    private String locStaDesc;
    private String entryType;
    private String entryTypeDesc;
    private String entryTime;
    private String yardSiteId;
    private String yardAreaId;
    private String yardBlockId;
    private String yardBayId;
    private int cell;
    private int tier;
    private String pickHand;
    private String pickHandDesc;
    private int pickOrder;
    private int pickRestowRight;
    private String hazInd;
    private String hazIndDesc;
    private String hazImco;
    private String hazImcoDesc;
    private String hazClass;
    private String hazClassDesc;
    private String hazUnno;
    private String hazType;
    private String reefInd;
    private String reefIndDesc;
    private int reefReq;
    private int reefSet;
    private int reefRead;
    private String intake;
    private String intakeUnit;
    private String oszInd;
    private String oszIndDesc;
    private int oszHgt;
    private int oszFrt;
    private int oszRer;
    private int oszLft;
    private int oszRgt;
    private String reefPti;
    private String reefPtiDesc;
    private String ptiInd;
    private String ptiIndDesc;
    private String yardRemark;
    private String vesselRemark;
    private String cfsRemark;
    private String gateRemark;
    private String srvCy;
    private String srvLd;
    private String srvIntl;
    private String srvCustom;
    private String srvCfs;
    private String tenantId;
    private String id;
    private  String trkWorkId;
    private String sizeClass;//箱型
    private String typeClass;//箱型单位
    private String location;//位置

    private String toYardBayId;
    private String fromYardBayId;

    private String fromYardBlockId;
    private String toYardBlockId;


    public YardCntrInfo() {
    }

    public YardCntrInfo(String cell) {
        this.defaultCell = cell;
    }

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

    public String getOptr() {
        return optr;
    }

    public void setOptr(String optr) {
        this.optr = optr;
    }

    public String getCntr() {
        return cntr;
    }

    public void setCntr(String cntr) {
        this.cntr = cntr;
    }

    public String getCntrType() {
        return cntrType;
    }

    public void setCntrType(String cntrType) {
        this.cntrType = cntrType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOptrId() {
        return optrId;
    }

    public void setOptrId(String optrId) {
        this.optrId = optrId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getFeInd() {
        return feInd;
    }

    public void setFeInd(String feInd) {
        this.feInd = feInd;
    }

    public String getFeIndDesc() {
        return feIndDesc;
    }

    public void setFeIndDesc(String feIndDesc) {
        this.feIndDesc = feIndDesc;
    }

    public String getCntrCode() {
        return cntrCode;
    }

    public void setCntrCode(String cntrCode) {
        this.cntrCode = cntrCode;
    }

    public String getCntrCodeDesc() {
        return cntrCodeDesc;
    }

    public void setCntrCodeDesc(String cntrCodeDesc) {
        this.cntrCodeDesc = cntrCodeDesc;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranTypeDesc() {
        return tranTypeDesc;
    }

    public void setTranTypeDesc(String tranTypeDesc) {
        this.tranTypeDesc = tranTypeDesc;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public double getGrsWgt() {
        return grsWgt;
    }

    public void setGrsWgt(int grsWgt) {
        this.grsWgt = grsWgt;
    }

    public double getCargoWgt() {
        return cargoWgt;
    }

    public void setCargoWgt(int cargoWgt) {
        this.cargoWgt = cargoWgt;
    }

    public double getCargoPkg() {
        return cargoPkg;
    }

    public void setCargoPkg(int cargoPkg) {
        this.cargoPkg = cargoPkg;
    }

    public double getCargoCbm() {
        return cargoCbm;
    }

    public void setCargoCbm(int cargoCbm) {
        this.cargoCbm = cargoCbm;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
    }

    public String getRecTypeDesc() {
        return recTypeDesc;
    }

    public void setRecTypeDesc(String recTypeDesc) {
        this.recTypeDesc = recTypeDesc;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getDlyType() {
        return dlyType;
    }

    public void setDlyType(String dlyType) {
        this.dlyType = dlyType;
    }

    public String getDlyTypeDesc() {
        return dlyTypeDesc;
    }

    public void setDlyTypeDesc(String dlyTypeDesc) {
        this.dlyTypeDesc = dlyTypeDesc;
    }

    public String getSchSec() {
        return schSec;
    }

    public void setSchSec(String schSec) {
        this.schSec = schSec;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getPof() {
        return pof;
    }

    public void setPof(String pof) {
        this.pof = pof;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getPdt() {
        return pdt;
    }

    public void setPdt(String pdt) {
        this.pdt = pdt;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getLocSta() {
        return locSta;
    }

    public void setLocSta(String locSta) {
        this.locSta = locSta;
    }

    public String getLocStaDesc() {
        return locStaDesc;
    }

    public void setLocStaDesc(String locStaDesc) {
        this.locStaDesc = locStaDesc;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getEntryTypeDesc() {
        return entryTypeDesc;
    }

    public void setEntryTypeDesc(String entryTypeDesc) {
        this.entryTypeDesc = entryTypeDesc;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getYardSiteId() {
        return yardSiteId;
    }

    public void setYardSiteId(String yardSiteId) {
        this.yardSiteId = yardSiteId;
    }

    public String getYardAreaId() {
        return yardAreaId;
    }

    public void setYardAreaId(String yardAreaId) {
        this.yardAreaId = yardAreaId;
    }

    public String getYardBlockId() {
        return yardBlockId;
    }

    public void setYardBlockId(String yardBlockId) {
        this.yardBlockId = yardBlockId;
    }

    public String getYardBayId() {
        return yardBayId;
    }

    public void setYardBayId(String yardBayId) {
        this.yardBayId = yardBayId;
    }

    public int getCurCell() {
        return cell;
    }

    public void setCurCell(int cell) {
        this.cell = cell;
    }

    public int getCurTier() {
        return tier;
    }

    public void setCurTier(int tier) {
        this.tier = tier;
    }

    public String getPickHand() {
        return pickHand;
    }

    public void setPickHand(String pickHand) {
        this.pickHand = pickHand;
    }

    public String getPickHandDesc() {
        return pickHandDesc;
    }

    public void setPickHandDesc(String pickHandDesc) {
        this.pickHandDesc = pickHandDesc;
    }

    public int getPickOrder() {
        return pickOrder;
    }

    public void setPickOrder(int pickOrder) {
        this.pickOrder = pickOrder;
    }

    public int getPickRestowRight() {
        return pickRestowRight;
    }

    public void setPickRestowRight(int pickRestowRight) {
        this.pickRestowRight = pickRestowRight;
    }

    public String getHazInd() {
        return hazInd;
    }

    public void setHazInd(String hazInd) {
        this.hazInd = hazInd;
    }

    public String getHazIndDesc() {
        return hazIndDesc;
    }

    public void setHazIndDesc(String hazIndDesc) {
        this.hazIndDesc = hazIndDesc;
    }

    public String getHazImco() {
        return hazImco;
    }

    public void setHazImco(String hazImco) {
        this.hazImco = hazImco;
    }

    public String getHazImcoDesc() {
        return hazImcoDesc;
    }

    public void setHazImcoDesc(String hazImcoDesc) {
        this.hazImcoDesc = hazImcoDesc;
    }

    public String getHazClass() {
        return hazClass;
    }

    public void setHazClass(String hazClass) {
        this.hazClass = hazClass;
    }

    public String getHazClassDesc() {
        return hazClassDesc;
    }

    public void setHazClassDesc(String hazClassDesc) {
        this.hazClassDesc = hazClassDesc;
    }

    public String getHazUnno() {
        return hazUnno;
    }

    public void setHazUnno(String hazUnno) {
        this.hazUnno = hazUnno;
    }

    public String getHazType() {
        return hazType;
    }

    public void setHazType(String hazType) {
        this.hazType = hazType;
    }

    public String getReefInd() {
        return reefInd;
    }

    public void setReefInd(String reefInd) {
        this.reefInd = reefInd;
    }

    public String getReefIndDesc() {
        return reefIndDesc;
    }

    public void setReefIndDesc(String reefIndDesc) {
        this.reefIndDesc = reefIndDesc;
    }

    public int getReefReq() {
        return reefReq;
    }

    public void setReefReq(int reefReq) {
        this.reefReq = reefReq;
    }

    public int getReefSet() {
        return reefSet;
    }

    public void setReefSet(int reefSet) {
        this.reefSet = reefSet;
    }

    public int getReefRead() {
        return reefRead;
    }

    public void setReefRead(int reefRead) {
        this.reefRead = reefRead;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getIntakeUnit() {
        return intakeUnit;
    }

    public void setIntakeUnit(String intakeUnit) {
        this.intakeUnit = intakeUnit;
    }

    public String getOszInd() {
        return oszInd;
    }

    public void setOszInd(String oszInd) {
        this.oszInd = oszInd;
    }

    public String getOszIndDesc() {
        return oszIndDesc;
    }

    public void setOszIndDesc(String oszIndDesc) {
        this.oszIndDesc = oszIndDesc;
    }

    public int getOszHgt() {
        return oszHgt;
    }

    public void setOszHgt(int oszHgt) {
        this.oszHgt = oszHgt;
    }

    public int getOszFrt() {
        return oszFrt;
    }

    public void setOszFrt(int oszFrt) {
        this.oszFrt = oszFrt;
    }

    public int getOszRer() {
        return oszRer;
    }

    public void setOszRer(int oszRer) {
        this.oszRer = oszRer;
    }

    public int getOszLft() {
        return oszLft;
    }

    public void setOszLft(int oszLft) {
        this.oszLft = oszLft;
    }

    public int getOszRgt() {
        return oszRgt;
    }

    public void setOszRgt(int oszRgt) {
        this.oszRgt = oszRgt;
    }

    public String getReefPti() {
        return reefPti;
    }

    public void setReefPti(String reefPti) {
        this.reefPti = reefPti;
    }

    public String getReefPtiDesc() {
        return reefPtiDesc;
    }

    public void setReefPtiDesc(String reefPtiDesc) {
        this.reefPtiDesc = reefPtiDesc;
    }

    public String getPtiInd() {
        return ptiInd;
    }

    public void setPtiInd(String ptiInd) {
        this.ptiInd = ptiInd;
    }

    public String getPtiIndDesc() {
        return ptiIndDesc;
    }

    public void setPtiIndDesc(String ptiIndDesc) {
        this.ptiIndDesc = ptiIndDesc;
    }

    public String getYardRemark() {
        return yardRemark;
    }

    public void setYardRemark(String yardRemark) {
        this.yardRemark = yardRemark;
    }

    public String getVesselRemark() {
        return vesselRemark;
    }

    public void setVesselRemark(String vesselRemark) {
        this.vesselRemark = vesselRemark;
    }

    public String getCfsRemark() {
        return cfsRemark;
    }

    public void setCfsRemark(String cfsRemark) {
        this.cfsRemark = cfsRemark;
    }

    public String getGateRemark() {
        return gateRemark;
    }

    public void setGateRemark(String gateRemark) {
        this.gateRemark = gateRemark;
    }

    public String getSrvCy() {
        return srvCy;
    }

    public void setSrvCy(String srvCy) {
        this.srvCy = srvCy;
    }

    public String getSrvLd() {
        return srvLd;
    }

    public void setSrvLd(String srvLd) {
        this.srvLd = srvLd;
    }

    public String getSrvIntl() {
        return srvIntl;
    }

    public void setSrvIntl(String srvIntl) {
        this.srvIntl = srvIntl;
    }

    public String getSrvCustom() {
        return srvCustom;
    }

    public void setSrvCustom(String srvCustom) {
        this.srvCustom = srvCustom;
    }

    public String getSrvCfs() {
        return srvCfs;
    }

    public void setSrvCfs(String srvCfs) {
        this.srvCfs = srvCfs;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrkWorkId() {
        return trkWorkId;
    }

    public void setTrkWorkId(String trkWorkId) {
        this.trkWorkId = trkWorkId;
    }

    /**
     * 获取显示内容
     *
     * @return
     */
    public String getContent() {
        String fe_ind = "";
        if(!TextUtils.isEmpty(getFeInd())){
            if("E".equals(getFeInd())){
                fe_ind = MyApplication.getContext().getString(R.string.empty);
            }else if("F".equals(getFeInd())){
                fe_ind = MyApplication.getContext().getString(R.string.full);
            }
        }
        return String.format("%s%s%s\n%s%s%s\n%s%s",
                TextUtils.isEmpty(getCntrType()) ? "" : getCntrType() + "/", fe_ind + "/", TextUtils.isEmpty(getOpr()) ? "" : getOptr(),
                TextUtils.isEmpty(getTradeType()) ? "" : getTradeType() + "/", TextUtils.isEmpty(getHazInd()) ? "" : getHazInd() + "/"
                ,getSizeClass(),getTypeClass()+"/",
                new DecimalFormat("#.0").format( getGrsWgt()));
    }


    /**
     * 设置提箱的时候背景颜色
     */
    public void setGetCntrBackGroupColor(YardCntrInfo getBoxBean, Map<String, YardCntrInfo> maps) {
        isColorGreen(getBoxBean);
        isColorYellow(getBoxBean, maps);
        isColorPink(getBoxBean, maps);
        isColorRed(getBoxBean);
        setBoxDt(BoxTool.CTRL_GETBOX);
    }

    /**
     * 设置提箱默认背景颜色
     */
    public void setTXDefaultBackgroundColor() {
        setColorType(ColorType.GRAY);
    }

    /**
     * 判断提箱是否绿色背景
     *
     * @param getBoxBean
     * @return
     */
    public boolean isColorGreen(YardCntrInfo getBoxBean) {
        if(!LruchUtils.isSwitch(FileKeyName.rm_color_switch)) return false;
        //A)出口或转口集装箱，有相同船名，航次，中转港及相同重量等级;
        // B空箱，有相同持箱人及集装箱类型
        if ((getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) && getBoxBean.getMISC_NO().equals(getMISC_NO()) &&
//                getBoxBean.getGrs_Ton().equals(getGrs_Ton()) && getBoxBean.getPOL().equals(getPOL()) ||
                (getBoxBean.getFeInd().equals(BoxTool.E) && getBoxBean.getOpr().equals(getOpr()) &&
                        getBoxBean.getCntrType().equals(getCntrType())))) {
            setColorType(ColorType.GREEN);
            return true;
        }
        return false;
    }

    /**
     * 判断是否是黄色背景
     *
     * @param getBoxBean 提箱
     * @param maps
     * @return
     */
    public boolean isColorYellow(YardCntrInfo getBoxBean, Map<String, YardCntrInfo> maps) {
        //A)出口或转口集装箱，有相同船名，航次，中转港及重箱压在轻箱上面。
        // B)空箱，放置到首层
        String firstTier = "(1,1)";
        if ((getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) &&
                getBoxBean.getMISC_NO().equals(getMISC_NO()) &&
                getBoxBean.getPOL().equals(getPOL())) ||
                (getDefaultCell().equals(firstTier) && getFeInd().equals(BoxTool.E))) {
            //A)出口或转口集装箱，有相同船名，航次，中转港及重箱压在轻箱上面。
            // B)空箱，放置到首层
            int tier = getCurTier();
            if (tier != 1 && getFeInd().equals(BoxTool.F)) {
                String key = String.format("(%s,%s)", getCell(), (tier - 1));
                YardCntrInfo box = maps.get(key);
                if (null != box && box.getFeInd().equals(BoxTool.E)) {
                    setColorType(ColorType.YELLOW);
                    return true;
                }
            } else if (getFeInd().equals(BoxTool.E)) {
                setColorType(ColorType.YELLOW);
                return true;
            }
        }
        return false;
    }

    /**
     * 判断背景颜色是否粉色
     *
     * @param getBoxBean
     * @param maps
     * @return
     */
    public boolean isColorPink(YardCntrInfo getBoxBean, Map<String, YardCntrInfo> maps) {
        if ((getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) &&
                getBoxBean.getMISC_NO().equals(getMISC_NO()) &&
                getBoxBean.getPOL().equals(getPOL()))) {
            //出口或转口集装箱，有相同船名，航次，中转港及轻箱压在重箱上面。
            int tier = getCurTier();
            if (tier == 1 || getFe_Ind().equals(BoxTool.F))
                return false;//说明该箱在最底下,不存在压箱;或者该箱为重箱,则返回
            //取出压在地下的箱子
            String key = String.format("(%s,%s)", getCell(), (tier - 1));
            YardCntrInfo box = maps.get(key);
            if (null == box || box.getFeInd().equals(BoxTool.E))
                return false;//箱子不存在,或者是空箱,直接返回
            setColorType(ColorType.PINK);
            return true;
        }
        return false;
    }

    /**
     * 判断背景颜色是否红色
     *
     * @param getBoxBean
     * @return
     */
    public boolean isColorRed(YardCntrInfo getBoxBean) {
        // A) 出口或转口集装箱，有相同船名及航次。
        // B)出口或转口集装箱，有相同的中转港。
        //C)空箱，有相同的持箱人。
        //D) 空箱，并有不同的集装箱类型。
        if (getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) && getBoxBean.getMISC_NO().equals(getMISC_NO()) ||
                getBoxBean.getPOL().equals(getPOL()) ||//(已屏蔽,待恢复)
                (getFeInd().equals(BoxTool.E) && getBoxBean.getOpr().equals(getOpr())) ||
                (getFeInd().equals(BoxTool.E) && !getBoxBean.getCntrType().equals(getCntrType()))) {
//            setColorType(ColorType.RED);//20180207,滔哥说去掉
            return true;
        }
        return false;
    }

    public String getSizeClass() {
        return sizeClass==null?"":sizeClass;
    }

    public void setSizeClass(String sizeClass) {
        this.sizeClass = sizeClass;
    }

    @Override
    public String getTypeClass() {
        return typeClass==null?"":typeClass;
    }

    @Override
    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public String getLocation() {
        return location==null?"":location;
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
}
