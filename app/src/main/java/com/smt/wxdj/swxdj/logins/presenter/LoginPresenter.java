package com.smt.wxdj.swxdj.logins.presenter;

import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.boxs.presenter.BaseInterface;
import com.smt.wxdj.swxdj.enums.DataType;

/**
 * Created by gbh on 16/6/27.
 */
public interface LoginPresenter extends BaseInterface {

    //机构列表
    void getTenants();

    void loadToken();

    void login();

    /**
     * 登录吊机
     *
     * @param user
     */
    void saveRTG(User user);

    /**
     * 获取吊机列表
     */
    void loadMachineList();

    /**
     * 获取所有系统设置参数
     */
    void getAllSystem();

    /**
     * 获取自动升级信息
     */
    void getAppUpdate(String data);

    /**
     * 获取数据列表
     * @param dataType
     */
    void getDataList(DataType dataType);

    /**
     * 检测用户是否已登录当前桥
     * @param user
     */
    void CheckSignOnQC(User user);
}
