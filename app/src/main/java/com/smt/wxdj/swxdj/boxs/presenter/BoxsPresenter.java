package com.smt.wxdj.swxdj.boxs.presenter;

import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.enums.DataType;

/**
 * Created by gbh on 16/6/27.
 */
public interface BoxsPresenter extends BaseInterface{

    /**
     * 获取该贝位箱子信息
     *
     * @param bay
     */
    void GetCntrListByConditionNotCell(String bay, String cntr, boolean iProgress);

    /**
     * 移动箱子
     *
     * @param toCell
     * @param toTier
     */
    void loadCheckMoveForCell(BoxDetalBean boxDetalBean, int toCell, int toTier);

    /**
     * 移动箱子
     *
     * @param toCell
     * @param toTier
     */
    void loadMoveBoxForCell(BoxDetalBean boxDetalBean, int toCell, int toTier);


    /**
     * 放箱子
     *
     * @param toCell
     * @param toTier
     */
    void putBoxForCell(int toCell, int toTier);


    /**
     * 装卸车提箱
     */
    void loadUpBoxTask();

    /**
     * 判断是否能够交换箱子
     */
    void LoadCheckChangeBox();

    /**
     * 交换箱子
     */
    void LoadChangeBox();

    /**
     * 抄场
     */
    void putBoxToParkingSpace();

    /**
     * 模糊搜索箱子
     */
    void searchBox(String param);

//----------------新码头--------------------------------------//

//    /**
//     * 获取推荐位系统参数
//     */
//    void GetSysParam();

    /**
     * 获取放箱推荐位
     */
    void GetCntrCellForRFCWS();


    /**
     * 检查场地是否被锁
     *
     * @param stacks
     */
    void CheckStackIsLock(String stacks);

    /**
     * 锁定 原因
     */
    void GetLockReasonList();

    /**
     * 解锁
     */
    void ReleaseStackLock();

    /**
     * 更改 YARD_STACK.OPR_STA 状态
     *
     * @param stacks
     */
    void UpdateYardStackChecked(String stacks);


    /**
     * 验证提箱
     *
     * @param boxDetalBean
     */
    void isValidOperation(BoxDetalBean boxDetalBean);

    /**
     * 提箱前对车架的状况检测
     *
     * @param boxDetalBean 要提的箱子
     */
    void CheckTruckSize(BoxDetalBean boxDetalBean);

    /**
     * 判断改拖车是否放箱完毕
     *
     * @param trk
     */
    void IsPKHasGR(String trk);


    /**
     * 取得计算过后的箱子
     *
     * @param cntrs
     */
    void GetCntrInfoConver(BoxDetalBean cntrs);


    /**
     * 获取该贝位详细信息
     *
     * @param bay
     */
    void CheckMaxCellTier(String bay);

    /**
     * 获取
     */
    void loadTrkList(DataType dataType, String crane);

}
