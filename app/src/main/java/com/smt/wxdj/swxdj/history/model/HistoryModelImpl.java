package com.smt.wxdj.swxdj.history.model;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smtlibrary.https.OkHttpUtils;

/**
 * Created by xlj on 2018/3/8.
 */

public class HistoryModelImpl implements HistoryModel {
    private final String SOCKET_TIMEOUT = MyApplication.getContext().getString(R.string.network_error);

    @Override
    public void getWorkStatistics(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                onLoadBoxsListener.onSucess(response);
            }

            @Override
            public void onFailure(Exception e) {
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }
}
