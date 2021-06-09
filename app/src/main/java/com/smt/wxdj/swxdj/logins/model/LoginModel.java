package com.smt.wxdj.swxdj.logins.model;

import com.smt.wxdj.swxdj.dao.HostSettingInfo;
import com.smt.wxdj.swxdj.dao.Tenants;
import com.smt.wxdj.swxdj.enums.DataType;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smtlibrary.https.OkHttpUtils;

import java.util.List;

/**
 * Created by gbh on 16/6/27.
 */
public interface LoginModel {


    //获取机构
    void getTenants(IPublicResultInterface<Tenants> onLoginListener);
    //设置信息
    void getSettingInfo(IPublicResultInterface<List<HostSettingInfo>> onLoginListener);

    void loadToken(List<OkHttpUtils.Param> data, IPublicResultInterface onLoginListener);
    /**
     * 登录
     * @param data
     * @param onLoginListener
     */

    void login(List<OkHttpUtils.Param> data, IPublicResultInterface onLoginListener);

    /**
     * 用户信息
     * @param onLoginListener
     */
    void getUserInfo(IPublicResultInterface onLoginListener);

    /**
     * 登录吊机
     * @param data
     * @param onLoginListener
     */
    void saveRTG(String data,IPublicResultInterface onLoginListener);
    /**
     * 获取吊机列表
     * @param data json  数据体
     * @param onLoginListener
     */
    void loadMachineList(String data,IPublicResultInterface onLoginListener);
    /**
     * 获取系统参数
     *
     * @param data
     * @param iPublicResultInterface
     */
    void GetSysParam(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 获取所有系统参数
     * @param iPublicResultInterface
     */
    void GetAllSysParam(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 检查更新
     * @param iPublicResultInterface
     */
    void getAppUpdate(String data, IPublicResultInterface iPublicResultInterface);

    /**
     * 获取
     */
    void GetBasicData(String data, IPublicResultInterface iPublicResultInterface);

}
