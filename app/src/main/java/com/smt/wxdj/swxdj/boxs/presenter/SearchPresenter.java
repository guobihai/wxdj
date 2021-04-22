package com.smt.wxdj.swxdj.boxs.presenter;

/**
 * Created by gbh on 16/12/5.
 */

public interface SearchPresenter {
    /**
     * 附带任务箱号,模糊查询箱子
     *
     * @param oldCntr
     * @param param
     */
    void searchBox(String oldCntr, String param);


    /**
     * 直接提箱
     */
    void loadUpBoxTask();

    /**
     * 判断是否能够交换箱子
     *
     * @param currCNTR   要提的箱号
     * @param changeCNTR 当前交换的箱号
     */
    void LoadCheckChangeBox(String currCNTR, String changeCNTR);

    /**
     * 交换箱子
     */
    void LoadChangeBox();


}
