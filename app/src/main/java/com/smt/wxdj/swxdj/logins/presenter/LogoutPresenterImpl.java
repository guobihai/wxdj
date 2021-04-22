package com.smt.wxdj.swxdj.logins.presenter;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smt.wxdj.swxdj.webservice.ParamObject;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.LogUtils;

/**
 * Created by gbh on 16/8/23.
 */
public class LogoutPresenterImpl implements LogoutPresenter {

    public LogoutPresenterImpl() {

    }

    @Override
    public void Logout() {
        OkHttpUtils.post(URLTool.getUrl(), signoffRFQCParam(), new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.sysout("===============Logout result:", result);
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.sysout("============Logout error:", e);
            }
        });
    }

    /**
     * 检测用户数据体
     *
     * @return
     */
    private String signoffRFQCParam() {
        User user = MyApplication.user;
        return new ParamObject("SignoffRFCWS", user.toString()).toString();
    }
}
