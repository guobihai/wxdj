package com.smt.wxdj.swxdj.bean;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.enums.ColorType;
import com.smt.wxdj.swxdj.enums.TextColorType;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.CellTool;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smtlibrary.utils.JsonUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by gbh on 16/6/15.
 * 箱子信息模型类
 */
public class BoxDetalBean extends CntrEntity implements Serializable, Cloneable, Comparable<CntrEntity> {

    //transient 定义可以不被gson 序列化
    /**
     * 箱子动态
     */
    private transient String boxDt;

    /**
     * 箱子位置坐标
     */
    private transient String defaultCell;

    /**
     * 系统推荐的
     */
    private transient ColorType recommendColorType;

    /**
     * 栏目是否选中
     */
    public transient boolean selectedBox;


    /**
     * 判断是否已有箱子（这也是判断是否为箱子）
     */
    private transient boolean isHashBox;


    /**
     * 移动的位置是否选中
     */
    private transient boolean isMoveSelect;

    /**
     * 是否显示倒箱位
     */
    private transient boolean isShowDxw;

    /**
     * 背景颜色
     */
    private transient ColorType colorType;
    /**
     * 字体颜色
     */
    private transient TextColorType textColorType;

    /**
     * 判断提箱类型
     */
    private transient boolean isRmCntr;

    /**
     * 判断是否本贝位的箱子
     */
    private transient boolean isSameBay;


    private String ISORDERLY; //是否排序 Y 有序,N 无序

    public BoxDetalBean() {
    }

    public BoxDetalBean(String cell) {
        this.defaultCell = cell;
    }

    public BoxDetalBean(String cell, int cellX, int CellY, boolean isPutBox) {
        this.defaultCell = cell;
        this.setCell(String.valueOf(cellX));
        this.setCell(String.valueOf(cellX));
        this.setTier(String.valueOf(CellY));
        this.setTier(String.valueOf(CellY));
        setBoxDt(isPutBox ? BoxTool.CTRL_PUTBOX : BoxTool.CTRL_GETBOX);
    }

    public BoxDetalBean(String cell, String boxDt, boolean isHashBox) {
        this.defaultCell = cell;
        this.isHashBox = isHashBox;
        this.boxDt = boxDt;
    }

    public boolean isSameBay() {
        return isSameBay;
    }

    public void setSameBay(boolean sameBay) {
        isSameBay = sameBay;
    }

    public String getDefaultCell() {
        return defaultCell;
    }

    public void setDefaultCell(String defaultCell) {
        this.defaultCell = defaultCell;
    }

    public boolean HashBox() {
        return isHashBox;
    }

    public void setHashBox(boolean hashBox) {
        isHashBox = hashBox;
    }

    public boolean isSelectedBox() {
        return selectedBox;
    }

    public void setSelectedBox(boolean selectedBox) {
        this.selectedBox = selectedBox;
    }


    public String getBoxDt() {
        return boxDt;
    }

    public void setBoxDt(String boxDt) {
        this.boxDt = boxDt;
    }

    public ColorType getRecommendColorType() {
        return recommendColorType;
    }

    public void setRecommendColorType(ColorType recommendColorType) {
        this.recommendColorType = recommendColorType;
    }

    public boolean isMoveSelect() {
        return isMoveSelect;
    }

    public void setMoveSelect(boolean moveSelect) {
        isMoveSelect = moveSelect;
    }

    public boolean isShowDxw() {
        return isShowDxw;
    }

    public void setShowDxw(boolean showDxw) {
        isShowDxw = showDxw;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public void setColorType(ColorType colorType) {
        this.colorType = colorType;
    }

    public TextColorType getTextColorType() {
        return textColorType;
    }

    public void setTextColorType(TextColorType textColorType) {
        this.textColorType = textColorType;
    }

    public String getISORDERLY() {
        return TextUtils.isEmpty(ISORDERLY) ? "Y" : ISORDERLY;
    }

    public void setISORDERLY(String ISORDERLY) {
        this.ISORDERLY = ISORDERLY;
    }

    /**
     * 获取显示内容
     *
     * @return
     */
    public String getContent() {
//        return String.format("%s%s%s\n%s%s\n%s%s%s",
//                TextUtils.isEmpty(getEqp_Type()) ? "" : getEqp_Type() + "/", TextUtils.isEmpty(getFe_Ind()) ? "" : getFe_Ind() + "/", TextUtils.isEmpty(getOpr()) ? "" : getOpr(),
//                TextUtils.isEmpty(getCategory()) ? "" : getCategory() + "/", TextUtils.isEmpty(getOpd()) ? "" : getOpd(),
//                TextUtils.isEmpty(getStatus()) ? "" : getStatus() + "/", TextUtils.isEmpty(getHold()) ? "" : getHold() + "/", TextUtils.isEmpty(getGrs_Ton().trim()) ? "" : getGrs_Ton());
        double grs_Ton = TextUtils.isEmpty(getGrs_Ton()) ? 0.0 : Double.parseDouble(getGrs_Ton());
        String fe_ind = "";
        if(!TextUtils.isEmpty(getFe_Ind())){
            if("E".equals(getFe_Ind())){
                fe_ind = MyApplication.getContext().getString(R.string.empty);
            }else if("F".equals(getFe_Ind())){
                fe_ind = MyApplication.getContext().getString(R.string.full);
            }
        }
        return String.format("%s%s%s\n%s%s%s\n%s",
                TextUtils.isEmpty(getEqp_Type()) ? "" : getEqp_Type() + "/", fe_ind + "/", TextUtils.isEmpty(getOpr()) ? "" : getOpr(),
                TextUtils.isEmpty(getCategory()) ? "" : getCategory() + "/", TextUtils.isEmpty(getOpd()) ? "" : getOpd() + "/",
                new DecimalFormat("#.0").format(grs_Ton), getBoatName());
    }

    /**
     * 获取船名
     *
     * @return
     */
    public String getBoatName() {
        String name = "";
        switch (getBoatState()) {
            case CellTool.IN_NOT_DOCKING://内贸进口
            case CellTool.IN_DOCKING://内贸进口
                name = getIn_Vsl();
                break;
            case CellTool.OUT_DOCKING://内贸进口
            case CellTool.OUT_NOT_DOCKING://内贸进口
                name = getOut_Vsl();
                break;
            default:
                break;
        }
        return name;
    }

    /**
     * 获取进口，出口底色状态
     *
     * @return
     */
    public int getBoatState() {
        int state = -1;
        if (!TextUtils.isEmpty(getCategory())) {
            switch (getCategory()) {
                case "DI"://内贸进口
                case "IP"://外贸进口
                    if (!TextUtils.isEmpty(getVSL_DOCKING()) && "Y".equals(getVSL_DOCKING())) {
                        state = CellTool.IN_NOT_DOCKING;
                    } else {
                        state = CellTool.IN_DOCKING;
                    }
                    break;
                case "DR"://内贸中转（公路）
                case "DT"://内贸中转（水路/驳船）
                case "DW"://内贸中转（铁路）
                case "FR"://外贸中转（公路）
                case "FT"://外贸中转（水路/驳船）
                case "FW"://外贸中转（公路）
                case "TS"://国际中转（大船中转）
                case "DE"://内贸出口
                case "EP"://外贸出口
                case "TG"://出闸处理
                    if (!TextUtils.isEmpty(getVSL_DOCKING()) && "Y".equals(getVSL_DOCKING())) {
                        state = CellTool.OUT_NOT_DOCKING;
                    } else {
                        state = CellTool.OUT_DOCKING;
                    }
                    break;
                default:
                    break;
            }
        }
        return state;
    }


    /**
     * 是否卸箱动态
     *
     * @return
     */
    public boolean isPutBoxDt() {
        if (TextUtils.isEmpty(getBoxDt())) return false;
        if (getBoxDt().equals(BoxTool.CTRL_PUTBOX))
            return true;
        return false;
    }

    /**
     * 是否提箱动态
     *
     * @return
     */
    public boolean isGetBoxDt() {
        if (TextUtils.isEmpty(getBoxDt())) return false;
        if (getBoxDt().equals(BoxTool.CTRL_GETBOX))
            return true;
        return false;
    }

    /**
     * 是否放箱
     *
     * @return
     */
    public boolean isPutCntr() {
        if (getActivity().equals(BoxTool.CTRL_PUTBOX) || getActivity().equals(BoxTool.CTRL_PUTBOXIG)) {
            return true;
        }
        return false;
    }

    /**
     * 倒箱
     *
     * @return
     */
    public boolean isPJCntr() {
        if (getActivity().equals(BoxTool.STATE_PJ)) {
            return true;
        }
        return false;
    }

    /**
     * 是否提箱
     *
     * @return
     */
    public boolean isGetCntr() {
        if (TextUtils.isEmpty(getActivity())) return false;
        if (getActivity().equals(BoxTool.CTRL_GETBOX) || getActivity().equals(BoxTool.CTRL_GETBOXIP)) {
            return true;
        }
        return false;
    }

    /**
     * 取消提箱
     *
     * @return
     */
    public boolean cancleGetCntr() {
        if ((getActivity().equals(BoxTool.CTRL_UPBOX) || getActivity().equals(BoxTool.CTRL_UIBOX))
                && (getStatus().equals(BoxTool.STATE_WD)
                || getStatus().equals(BoxTool.STATE_CP))) {
            return true;
        }
        return false;
    }


    /**
     * 设置提箱的时候背景颜色
     */
    public void setGetCntrBackGroupColor(BoxDetalBean getBoxBean, Map<String, BoxDetalBean> maps) {
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
    private boolean isColorGreen(BoxDetalBean getBoxBean) {
        if(!LruchUtils.isSwitch(FileKeyName.rm_color_switch)) return false;
        //A)出口或转口集装箱，有相同船名，航次，中转港及相同重量等级;
        // B空箱，有相同持箱人及集装箱类型
        if ((getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) && getBoxBean.getMISC_NO().equals(getMISC_NO()) &&
                getBoxBean.getGrs_Ton().equals(getGrs_Ton()) && getBoxBean.getPOL().equals(getPOL()) ||
                (getBoxBean.getFe_Ind().equals(BoxTool.E) && getBoxBean.getOpr().equals(getOpr()) &&
                        getBoxBean.getEqp_Type().equals(getEqp_Type())))) {
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
    private boolean isColorYellow(BoxDetalBean getBoxBean, Map<String, BoxDetalBean> maps) {
        //A)出口或转口集装箱，有相同船名，航次，中转港及重箱压在轻箱上面。
        // B)空箱，放置到首层
        String firstTier = "(1,1)";
        if ((getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) &&
                getBoxBean.getMISC_NO().equals(getMISC_NO()) &&
                getBoxBean.getPOL().equals(getPOL())) ||
                (getDefaultCell().equals(firstTier) && getFe_Ind().equals(BoxTool.E))) {
            //A)出口或转口集装箱，有相同船名，航次，中转港及重箱压在轻箱上面。
            // B)空箱，放置到首层
            int tier = Integer.parseInt(getTier());
            if (tier != 1 && getFe_Ind().equals(BoxTool.F)) {
                String key = String.format("(%s,%s)", getCell(), (tier - 1));
                BoxDetalBean box = maps.get(key);
                if (null != box && box.getFe_Ind().equals(BoxTool.E)) {
                    setColorType(ColorType.YELLOW);
                    return true;
                }
            } else if (getFe_Ind().equals(BoxTool.E)) {
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
    private boolean isColorPink(BoxDetalBean getBoxBean, Map<String, BoxDetalBean> maps) {
        if ((getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) &&
                getBoxBean.getMISC_NO().equals(getMISC_NO()) &&
                getBoxBean.getPOL().equals(getPOL()))) {
            //出口或转口集装箱，有相同船名，航次，中转港及轻箱压在重箱上面。
            int tier = Integer.parseInt(getTier());
            if (tier == 1 || getFe_Ind().equals(BoxTool.F))
                return false;//说明该箱在最底下,不存在压箱;或者该箱为重箱,则返回
            //取出压在地下的箱子
            String key = String.format("(%s,%s)", getCell(), (tier - 1));
            BoxDetalBean box = maps.get(key);
            if (null == box || box.getFe_Ind().equals(BoxTool.E))
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
    private boolean isColorRed(BoxDetalBean getBoxBean) {
        // A) 出口或转口集装箱，有相同船名及航次。
        // B)出口或转口集装箱，有相同的中转港。
        //C)空箱，有相同的持箱人。
        //D) 空箱，并有不同的集装箱类型。
        if (getBoxBean.getEqp_Sta().equals(BoxTool.EQP_STA) && getBoxBean.getMISC_NO().equals(getMISC_NO()) ||
                getBoxBean.getPOL().equals(getPOL()) ||//(已屏蔽,待恢复)
                (getFe_Ind().equals(BoxTool.E) && getBoxBean.getOpr().equals(getOpr())) ||
                (getFe_Ind().equals(BoxTool.E) && !getBoxBean.getEqp_Type().equals(getEqp_Type()))) {
//            setColorType(ColorType.RED);//20180207,滔哥说去掉
            return true;
        }
        return false;
    }

    /**
     * 判断是否同一辆拖车
     *
     * @param getBoxBean
     * @return
     */
    public boolean isSameTrk(BoxDetalBean getBoxBean) {
        if (null != getTrk() && getTrk().equals(getBoxBean.getTrk()) && !getCntr().equals(getBoxBean.getCntr())) {
            setTextColorType(TextColorType.BLUE);
            return true;
        }
        return false;
    }

    /**
     * 判断是否同一个箱子
     *
     * @param getBoxBean
     * @return
     */
    public boolean isSameCntr(BoxDetalBean getBoxBean) {
        if (!TextUtils.isEmpty(getBoxBean.getCntr()) && getCntr().equals(getBoxBean.getCntr())) {
            setTextColorType(TextColorType.RED);
            return true;
        }
        return false;
    }

    /**
     * 冷藏箱是否拔电
     *
     * @return
     */
    public boolean isCntrReeferAStatus() {
        if (TextUtils.isEmpty(getStatus())) return false;
        if (getStatus().equals("A")) return true;
        return false;
    }

    @Override
    public int compareTo(CntrEntity boxDetalBean) {
        return this.getCntr().compareTo(boxDetalBean.getCntr());
    }

    /**
     * 是否上边方向
     *
     * @return
     */
    public boolean isTrkUp() {
        if (TextUtils.isEmpty(getPick_Method())) return false;
        return getPick_Method().equals("0") ? true : false;
    }


    /**
     * 是否右边方向
     *
     * @return
     */
    public boolean isTrkRight() {
        if (TextUtils.isEmpty(getPick_Method())) return false;
        return getPick_Method().equals("2") ? true : false;
    }

    /**
     * 是否左边方向
     *
     * @return
     */
    public boolean isTrkLeft() {
        if (TextUtils.isEmpty(getPick_Method())) return false;
        return getPick_Method().equals("1") ? true : false;
    }


    /**
     * 是否左、右边方向
     *
     * @return
     */
    public boolean isTrkLeftOrRight() {
        if (TextUtils.isEmpty(getPick_Method())) return false;
        return getPick_Method().equals("3") ? true : false;
    }

    public boolean isRmCntr() {
        return isRmCntr;
    }

    public void setRmCntr(boolean rmCntr) {
        isRmCntr = rmCntr;
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

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
