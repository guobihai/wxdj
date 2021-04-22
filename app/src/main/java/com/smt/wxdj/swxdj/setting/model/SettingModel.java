package com.smt.wxdj.swxdj.setting.model;

import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;

/**
 * Created by gbh on 16/9/21.
 */

public interface SettingModel {
    /**
     * 加载码头信息
     * @param param
     * @param iLoadResultinterfaces
     */
    void loadDockData(String param, IPublicResultInterface iLoadResultinterfaces);

    void checkDevice(String param, IPublicResultInterface iLoadResultinterfaces);
}
