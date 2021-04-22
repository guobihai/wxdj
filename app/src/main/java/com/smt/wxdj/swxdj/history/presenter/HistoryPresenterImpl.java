package com.smt.wxdj.swxdj.history.presenter;

import android.text.TextUtils;

import com.smt.wxdj.swxdj.BasePresenter;
import com.smt.wxdj.swxdj.bean.WorkStatisticsBean;
import com.smt.wxdj.swxdj.history.model.HistoryModel;
import com.smt.wxdj.swxdj.history.model.HistoryModelImpl;
import com.smt.wxdj.swxdj.history.view.HistoryView;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.utils.JsonUtils;
import com.smt.wxdj.swxdj.webservice.ParamObject;

/**
 * Created by xlj on 2018/3/8.
 */

public class HistoryPresenterImpl extends BasePresenter<HistoryView> implements HistoryPresenter {
    private HistoryModel model;

    public HistoryPresenterImpl() {
        model = new HistoryModelImpl();
    }

    @Override
    public void getWorkStatistics(String params) {
        getView().showProgress();
        model.getWorkStatistics(getWorkStatisticsParam(params), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                getView().hideProgress();
                WorkStatisticsBean bean = JsonUtils.toObject(object.toString(), WorkStatisticsBean.class);
                if (!TextUtils.isEmpty(bean.getD().getResultCode()) && bean.getD().getResultCode().equals("0")) {
                    int length = bean.getD().getReturnObject().size();
                    if (length > 0) {
                        getView().getWorkStatisticsData(bean.getD().getReturnObject());
                    } else {
//                        getView().onFaile("暂无数据.");
                    }
                } else {
//                    getView().onFaile("暂无数据.");
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                getView().hideProgress();
                getView().onFaile(msg);
            }
        });
    }

    private String getWorkStatisticsParam(String params) {
        return new ParamObject("GetWorkStatistics", params).toString();
    }
}
