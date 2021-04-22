package com.smt.wxdj.swxdj.boxs.view;


import com.smt.wxdj.swxdj.bean.BoxDetalBean;

import java.util.List;

/**
 * Created by gbh on 16/12/5.
 */

public interface SearchView {
    /**
     * 获取当前任务箱子
     *
     * @return
     */
    BoxDetalBean getBoxDealBean();


    /**
     * 获取当前任务箱子
     *
     * @return
     */
    BoxDetalBean getUpDealBean();

    /**
     * 模糊搜索箱子结果
     *
     * @param list
     */
    void setSearchResult(List<BoxDetalBean> list);

    /**
     * 加载失败结果
     *
     * @param msg
     */
    void onFaile(String msg);

    /**
     * 处理成功
     */
    void onSuccess();

    /**
     * 显示加载进度
     */
    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();

}
