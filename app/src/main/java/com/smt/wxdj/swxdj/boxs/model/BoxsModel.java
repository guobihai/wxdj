package com.smt.wxdj.swxdj.boxs.model;

import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;

/**
 * Created by gbh on 16/6/27.
 */
public interface BoxsModel {
    /**
     * 获取任务箱子
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void loadBoxs(String data, IPublicResultInterface onLoadBoxsListener);

    /**
     * 取消提箱
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void cancleUpBox(String data, IPublicResultInterface onLoadBoxsListener);


    /**
     * 获取场地信息
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void LoadBoxCd(String data, IPublicResultInterface onLoadBoxsListener);

    /**
     * 判断场地是否被锁住
     *
     * @param data
     */
    void CheckStackIsLock(String data, IPublicResultInterface onLoadBoxsListener);

    /**
     * 获取场地被锁原因
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void GetLockReason(String data, IPublicResultInterface onLoadBoxsListener);


    /**
     * 获取田位信息
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void LoadBoxTw(String data, IPublicResultInterface onLoadBoxsListener);

    /**
     * 获取田位信息
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void getSelectedBay(String data, IPublicResultInterface onLoadBoxsListener);

    /**
     * 获取贝位详细信息
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void CheckMaxCellTier(String data, IPublicResultInterface onLoadBoxsListener);

    /**
     * 根据排获取对应的层的箱子
     *
     * @param data
     * @param onLoadBoxsListener
     */
    void loadListBoxForCell(String data, IPublicResultInterface onLoadBoxsListener);

    /**
     * 检测箱子移动的位置是否合法
     *
     * @param data
     * @param iPublicResultInterface
     */
    void loadCheckBoxForCell(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 移动箱子
     *
     * @param data
     * @param iPublicResultInterface
     */
    void loadMoveBoxForCell(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 提箱子
     *
     * @param data
     * @param iPublicResultInterface
     */
    void loadUpBoxTask(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 判断箱子是否能交换
     *
     * @param data
     * @param iPublicResultInterface
     */
    void loadCheckChangeBoxTask(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 交换箱子
     *
     * @param data
     * @param iPublicResultInterface
     */
    void loadChangeBoxTask(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 抄场
     *
     * @param data
     * @param iPublicResultInterface
     */
    void putBoxToParkingSpace(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 模糊搜索箱子
     *
     * @param data
     * @param iPublicResultInterface
     */
    void searchBox(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 获取系统参数
     *
     * @param data
     * @param iPublicResultInterface
     */
    void GetSysParam(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 获取放箱推荐位
     *
     * @param data
     * @param iPublicResultInterface
     */
    void GetCntrCellForRFCWS(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 获取拖车Size 大小
     *
     * @param data
     * @param iPublicResultInterface
     */
    void CheckTruckSize(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 验证
     *
     * @param data
     * @param iPublicResultInterface
     */
    void ValidOperation(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 修改场地登录
     *
     * @param data
     * @param iPublicResultInterface
     */
    void UpdateCraneRow(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 根据类型获取对应的数据
     *
     * @param data
     * @param iPublicResultInterface
     */
    void GetBasicData(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 根据类型获取对应的数据
     *
     * @param data
     * @param iPublicResultInterface
     */
    void GetCntrInfoConver(String data, IPublicResultInterface iPublicResultInterface);


}
