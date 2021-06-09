package com.smt.wxdj.swxdj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.smt.wxdj.swxdj.adapt.HostSettingAdapt;
import com.smt.wxdj.swxdj.adapt.SettingAdapt;
import com.smt.wxdj.swxdj.bean.Dock;
import com.smt.wxdj.swxdj.bean.DriverList;
import com.smt.wxdj.swxdj.bean.MachineNo;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.dao.HostSettingInfo;
import com.smt.wxdj.swxdj.dao.Tenants;
import com.smt.wxdj.swxdj.logins.presenter.LoginPresenterImpl;
import com.smt.wxdj.swxdj.logins.view.LoginView;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smt.wxdj.swxdj.view.MyRecyclerView;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.List;

public class DebugActivity extends MvpBaseActivity<LoginView, LoginPresenterImpl> implements LoginView {
    HostSettingAdapt settingAdapt;
    private LoginPresenterImpl mLoginPresenterImpl;

    public static void start(Context context) {
        Intent starter = new Intent(context, DebugActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected LoginPresenterImpl createPresenter() {
        return mLoginPresenterImpl = new LoginPresenterImpl();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recylerview);
        mPresenter.getSettingInfo();
        MyRecyclerView myRecyclerView = findViewById(R.id.recycle_view);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        settingAdapt = new HostSettingAdapt();
        myRecyclerView.setAdapter(settingAdapt);
        settingAdapt.setOnItemClickListener(new HostSettingAdapt.OnItemClickListener() {
            @Override
            public void onItemClick(View v, HostSettingInfo dock, int positon) {
                settingAdapt.setSelect(positon);
                PreferenceUtils.putString(DebugActivity.this, "authUrl", dock.getAuthUrl().concat("/"));
                PreferenceUtils.putString(DebugActivity.this, "apiUrl", dock.getApiUrl().concat("/api/"));
            }
        });
    }


    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassWord() {
        return null;
    }

    @Override
    public String getMachineNo() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoginFaileMsg(String msg) {

    }

    @Override
    public void loginCrnSuccess() {

    }

    @Override
    public void showSuccess(User user) {

    }

    @Override
    public void addMachListData(List<MachineNo> list) {

    }

    @Override
    public void setAllSystemResult(boolean flag) {

    }

    @Override
    public String getAppUpdateRequestBody(String fileName) {
        return null;
    }

    @Override
    public void getAppUpdateData(Object object) {

    }

    @Override
    public void showLoadMsg() {

    }

    @Override
    public void getDataList(List<DriverList> list) {

    }

    @Override
    public void showTenants(Tenants tenants) {

    }

    @Override
    public void checkSignOnQCResult(boolean flag) {

    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public void onSuccess(Object obj) {
        List<HostSettingInfo> list = (List<HostSettingInfo>) obj;
        String value = PreferenceUtils.getString(this, "apiUrl", URLTool.getUrl());
        for (HostSettingInfo info : list) {
            if (TextUtils.equals(info.getApiUrl().concat("/api/"), value)) {
                info.setSelect(true);
            }
        }
        settingAdapt.setData(list);
    }
}
