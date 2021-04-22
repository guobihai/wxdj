package com.smt.wxdj.swxdj.history.model;

import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;

/**
 * Created by xlj on 2018/3/8.
 */

public interface HistoryModel {
    /**
     * 工作量统计接口
     */
    void getWorkStatistics(String data, IPublicResultInterface onLoadBoxsListener);
}
