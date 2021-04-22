package com.smt.wxdj.swxdj.logins.view;

import com.smt.wxdj.swxdj.bean.DriverList;
import com.smt.wxdj.swxdj.bean.MachineNo;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.dao.Tenants;

import java.sql.Driver;
import java.util.List;

/**
 * Created by gbh on 16/6/27.
 */
public interface LoginView {
    String getUserName();
    String getPassWord();
    String getMachineNo();
    void showProgress();
    void hideProgress();
    void showLoginFaileMsg(String msg);
    void loginCrnSuccess();
    void showSuccess(User user);
    void addMachListData(List<MachineNo>list);
    void setAllSystemResult(boolean flag);
    String getAppUpdateRequestBody(String fileName);
    void getAppUpdateData(Object object);
    void showLoadMsg();
    void getDataList(List<DriverList> list);

    void showTenants(Tenants tenants);
    /**
     * 用户登录检查
     */
    void checkSignOnQCResult(boolean flag);

    /**
     * 获取请求参数
     * @return
     */
    String getRequestBody();

    void onSuccess(Object obj);


}
