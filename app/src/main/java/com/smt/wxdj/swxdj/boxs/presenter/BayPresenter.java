package com.smt.wxdj.swxdj.boxs.presenter;

/**
 * Created by gbh on 16/7/4.
 * 字典接口
 */
public interface BayPresenter {

    /**
     * 获取所有的场地
     */
    void getAllStackList();

    /**
     * 根据场地获取田位信息
     *
     * @param stack
     */
    void getSelectedBay(String stack);


}
