package com.smt.wxdj.swxdj.setting.presenter;

import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.setting.model.SettingModel;
import com.smt.wxdj.swxdj.setting.model.SettingModelImpl;
import com.smt.wxdj.swxdj.setting.views.SettingViews;
import com.smtlibrary.abstracts.BasePresenter;

/**
 * Created by gbh on 16/9/23.
 */

public class SettingPresenterImpl extends BasePresenter<SettingViews> implements SettingPresenter {
    private SettingModel settingModel;

    public SettingPresenterImpl() {
        settingModel = new SettingModelImpl();
    }


    @Override
    public void loadDockData() {
        getView().showProgress();
        settingModel.loadDockData(getView().getRequestBody(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                getView().onSucess(object);
                getView().hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().onFailes(msg);
            }
        });
    }

    @Override
    public void checkDevice() {
        settingModel.checkDevice(getView().getChcekDeviceRequestBody(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                boolean b = (boolean) object;
                if (b)
                    getView().legalDevice();
                else
                    getView().illegalDevice();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().onFailes(msg);
            }
        });

    }
}