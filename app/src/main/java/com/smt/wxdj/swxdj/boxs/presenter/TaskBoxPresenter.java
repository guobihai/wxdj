package com.smt.wxdj.swxdj.boxs.presenter;

import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.StackBean;

/**
 * Created by gbh on 16/7/6.
 */
public interface TaskBoxPresenter {
    /**
     * 加载任务类型
     */
    void loadBoxs(String stack, boolean flag, String taskType);

    /**
     * 取消提箱任务列表
     */
    void FindCancel_WorkList(String stack, boolean flag);


    /**
     * 获取场地信息
     *
     * @param crn 当前登录吊机号
     */
    void loadBoxCdData(String crn, boolean flag);

    /**
     * 判断场地是否被锁住
     *
     * @param stack
     */
    void CheckStackIsLock(String stack);


    /**
     * 修改场地登录信息
     * @param stackBean
     */
    void UpdateCraneRow(StackBean stackBean);

    /**
     * 获取场地锁定原因
     */
    void GetLockReason();


    /**
     * 获取呗位
     *
     * @param stack
     */
    void getSelectedBay(String stack);


    /**
     * 获取该贝位详细信息
     *
     * @param bay
     */
    void CheckMaxCellTier(String bay);

    /**
     * 倒箱任务列表
     * @param stack
     */
    void fallBoxData(StackBean stack, boolean flag);

    /**
     * 放箱子
     *
     */
    void putBoxForCell(BoxDetalBean bean);

    /**
     * 取得计算过后的箱子
     *
     * @param cntrs
     */
    void GetCntrInfoConver(BoxDetalBean cntrs, Bay bay);
}
