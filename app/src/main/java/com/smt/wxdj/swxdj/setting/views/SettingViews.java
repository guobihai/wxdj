package com.smt.wxdj.swxdj.setting.views;

/**
 * Created by gbh on 16/9/21.
 */

public interface SettingViews {

    String getRequestBody();
    String getChcekDeviceRequestBody();

    void showProgress();

    void hideProgress();

    void onSucess(Object obj);

    void onFailes(String errorMsg);
    /**
     * 合法设备
     */
    void legalDevice();

    /**
     * 非法设备
     */
    void illegalDevice();
}
