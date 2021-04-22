package com.smt.wxdj.swxdj.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.bean.CheckBean;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.logins.ui.LoginActivity;
import com.smt.wxdj.swxdj.utils.ActivityTool;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smt.wxdj.swxdj.webservice.ParamObject;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

/**
 * @author xlj
 * @date 2017/10/30
 */
public class CheckUserService extends Service {

    private Handler handler;
    private int TIMEOUT = 10000;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                String activityTag = ActivityTool.currentActivity().getLocalClassName();
//                LogUtils.e("activityTag", activityTag);
                OkHttpUtils.post(URLTool.getUrl(), checkSignOnQCParam(), new OkHttpUtils.ResultCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtils.e("CheckUserService CheckSignOnQC", result);
                        CheckBean bean = JsonUtils.deserialize(result, CheckBean.class);
                        if (null != bean && bean.getD().getReturnObject().equals("1")) {
                            Bundle bundle = new Bundle();
                            bundle.putString(FileKeyName.LOGOUT, FileKeyName.LOGOUT);
                            ActivityTool.skipActivityAndFinishAll(ActivityTool.currentActivity(), LoginActivity.class, bundle);
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(FileKeyName.OPEN);
                            sendBroadcast(intent);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                    }
                });
            }
        }, TIMEOUT);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 检测用户数据体
     *
     * @return
     */
    private String checkSignOnQCParam() {
        User user = MyApplication.user;
        return new ParamObject("CheckSignOnQC", user.toString()).toString();
    }


}
