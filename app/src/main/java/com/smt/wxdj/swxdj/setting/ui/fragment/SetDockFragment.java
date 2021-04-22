package com.smt.wxdj.swxdj.setting.ui.fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.adapt.SettingAdapt;
import com.smt.wxdj.swxdj.bean.Dock;
import com.smt.wxdj.swxdj.setting.presenter.SettingPresenterImpl;
import com.smt.wxdj.swxdj.setting.views.SettingViews;
import com.smt.wxdj.swxdj.utils.ReadRawFileUtils;
import com.smt.wxdj.swxdj.utils.SettingConfig;
import com.smtlibrary.abstracts.MVPBaseFragment;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.utils.PreferenceUtils;
import com.smtlibrary.utils.SystemUtils;

import java.util.Collections;
import java.util.List;


public class SetDockFragment extends MVPBaseFragment<SettingViews, SettingPresenterImpl>
        implements SettingViews, SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private Dock mDock;

    private SettingPresenterImpl settingPresenter;
    private List<Dock> lists;
    private SettingAdapt settingAdapt;
    private SweetAlertDialog dialog;

    public static SetDockFragment newInstance() {
        SetDockFragment fragment = new SetDockFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_dock_setting, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        settingAdapt = new SettingAdapt();
        recyclerView.setAdapter(settingAdapt);

        settingAdapt.setOnItemClickListener(new SettingAdapt.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Dock dock, int positon) {
                mDock = dock;
                settingAdapt.setSelect(positon);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkNetwork();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (null != mDock)
                    save();
                else
                    getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected SettingPresenterImpl createPresenter() {
        return settingPresenter = new SettingPresenterImpl();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != mDock)
                save();
            else
                getActivity().finish();
            return true;
        }
        return true;
    }

    private void save() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("是否要保存设置")
                .setTitleText(getString(R.string.do_you_want_to_save_settings))
                .setConfirmText(getString(android.R.string.ok))
                .setCancelText(getString(android.R.string.cancel))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (null != mDock) {
                            PreferenceUtils.putString(getActivity(), SettingConfig.DOCK, mDock.getDock());
                            PreferenceUtils.putString(getActivity(), SettingConfig.MAJLOC, mDock.getMajloc());
                            PreferenceUtils.putString(getActivity(), SettingConfig.SUBLOC, mDock.getSubloc());
                            PreferenceUtils.putString(getActivity(), SettingConfig.WEBURL, mDock.getWebUrl());
                        }
                        sweetAlertDialog.dismiss();
                        getActivity().finish();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        getActivity().finish();
                    }
                })
                .show();
    }

    private boolean checkNetwork() {
        if (!SystemUtils.isNetworkAvailable(getActivity())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.tips))
                    .setContentText(getString(R.string.netword_error))
                    .setConfirmText(getString(android.R.string.ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = null;
                            //判断手机系统的版本  即API大于10 就是3.0或以上版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {
                                intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                                intent.setComponent(component);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            startActivity(intent);
                        }
                    })
                    .show();
            return false;
        } else {
            onRefresh();
        }
        return true;
    }


    @Override
    public String getRequestBody() {
        return ReadRawFileUtils.readCheckSoapDock(getActivity());
    }

    @Override
    public String getChcekDeviceRequestBody() {
        return ReadRawFileUtils.readCheckSoapFile(getActivity());
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSucess(final Object obj) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                settingAdapt.isShowFooter(false);
                if (obj instanceof List) {
                    lists = (List<Dock>) obj;
                    addTestService();
                    Collections.sort(lists);
                    for (Dock dock : lists) {
                        if (dock.getDock().equals(PreferenceUtils.getString(getActivity(), "dock", null))
                                && dock.getWebUrl().equals(PreferenceUtils.getString(getActivity(), "webUrl", null))) {
                            dock.setSelect(true);
                        }
                    }
                    settingAdapt.setData(lists);
                }
            }
        });
    }

    //测试服务器连接
    private void addTestService(){
//        Dock bean = new Dock();
//        bean.setMajloc("JMA");
//        bean.setSubloc("GDXH");
//        bean.setWebUrl("http://106.75.138.45/EvPlatformSrv/WebService.asmx");
//        bean.setDock("更新测试");
//        lists.add(bean);
    }

    @Override
    public void onFailes(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void legalDevice() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onRefresh();
                PreferenceUtils.putBoolean(getActivity(), "isLegalDevice", true);
            }
        });
    }

    @Override
    public void illegalDevice() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != dialog && dialog.isShowing()) return;
                dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(getString(R.string.iollegal_equipment))
                        .setContentText(getString(R.string.not_registered))
                        .setConfirmText(getString(android.R.string.ok))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                getActivity().finish();
                            }
                        });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

    }

    @Override
    public void onRefresh() {
        settingPresenter.loadDockData();
    }
}
