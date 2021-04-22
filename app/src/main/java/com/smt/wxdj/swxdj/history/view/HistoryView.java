package com.smt.wxdj.swxdj.history.view;

/**
 * Created by xlj on 2018/3/8.
 */

public interface HistoryView {
    /**
     * 显示加载进度
     */
    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();

    void getWorkStatisticsData(Object obj);

    /**
     * 加载失败结果
     * @param msg
     */
    void onFaile(String msg);
}
