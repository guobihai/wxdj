package com.smt.wxdj.swxdj.logins.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Dock;
import com.smt.wxdj.swxdj.setting.presenter.SettingPresenterImpl;
import com.smt.wxdj.swxdj.setting.views.SettingViews;
import com.smt.wxdj.swxdj.utils.ReadRawFileUtils;
import com.smt.wxdj.swxdj.utils.SettingConfig;
import com.smtlibrary.abstracts.MVPBaseActivity;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;
import com.smtlibrary.utils.SystemUtils;
import com.smtlibrary.views.EditTextWithDel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogoActivity extends MVPBaseActivity<SettingViews, SettingPresenterImpl> implements SettingViews {
    @Bind(R.id.editText)
    EditTextWithDel input;
    @Bind(R.id.button)
    Button btnJh;
    private SettingPresenterImpl settingPresenter;
    private List<Dock> lists;
    private Dock mDock;

    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_layout);
        ButterKnife.bind(this);
        //判断是否设置过
        if (PreferenceUtils.getBoolean(this, "setting", false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            PreferenceUtils.putBoolean(this, SettingConfig.OPEN_TIME_TASK, true);
            PreferenceUtils.putInt(this, SettingConfig.SETTING_TIME, SettingConfig.TIME_OUT);
        }

        if (!LogUtils.LOG_DEBUG)
            btnJh.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!LogUtils.LOG_DEBUG)
            checkNetwork();
    }


    private boolean checkNetwork() {
        if (!SystemUtils.isNetworkAvailable(this)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.tips))
                    .setContentText(getString(R.string.netword_error))
                    .setConfirmText(getString(android.R.string.ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = null;
                            //判断手机系统的版本  即API大于10 就是3.0或以上版本
                            if (Build.VERSION.SDK_INT > 10) {
                                intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
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
            if (!PreferenceUtils.getBoolean(this, "isLagalDevice", false))
                settingPresenter.checkDevice();
        }
        return true;
    }

    @Override
    protected SettingPresenterImpl createPresenter() {
        return settingPresenter = new SettingPresenterImpl();
    }


    @Override
    public String getRequestBody() {
        return ReadRawFileUtils.readCheckSoapDock(this);
    }

    @Override
    public String getChcekDeviceRequestBody() {
        return ReadRawFileUtils.readCheckSoapFile(this);
    }

    @Override
    public void showProgress() {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(getString(R.string.loading));
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void onSucess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean isTrue = false;
                if (obj instanceof List) {
                    lists = (List<Dock>) obj;
                    String name = input.getText().toString().trim().toLowerCase();
                    for (Dock dock : lists) {
                        if (dock.getDock().toLowerCase().equals(name)) {
                            isTrue = true;
                            mDock = dock;
                        }
                    }
                }

                if (isTrue && null != mDock) {
                    PreferenceUtils.putBoolean(LogoActivity.this, "setting", true);
                    PreferenceUtils.putString(LogoActivity.this, "dock", mDock.getDock());
                    PreferenceUtils.putString(LogoActivity.this, "majloc", mDock.getMajloc());
                    PreferenceUtils.putString(LogoActivity.this, "subloc", mDock.getSubloc());
                    PreferenceUtils.putString(LogoActivity.this, "webUrl", mDock.getWebUrl());
//                    Toast.makeText(LogoActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                    PreferenceUtils.putBoolean(LogoActivity.this, "setting", true);
                    startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                    finish();
                } else {
                    new SweetAlertDialog(LogoActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(getString(R.string.the_validation_failed))
                            .setConfirmText(getString(android.R.string.ok))
                            .show();
                }
            }
        });
    }

    @Override
    public void onFailes(String errorMsg) {

    }

    @Override
    public void legalDevice() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnJh.setEnabled(true);
                PreferenceUtils.putBoolean(LogoActivity.this, "isLegalDevice", true);
            }
        });
    }

    @Override
    public void illegalDevice() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SweetAlertDialog dialog = new SweetAlertDialog(LogoActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(getString(R.string.iollegal_equipment))
                        .setContentText(getString(R.string.not_registered))
                        .setConfirmText(getString(android.R.string.ok))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                finish();
                            }
                        });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }


    @OnClick(R.id.button)
    public void onClick() {
        if (TextUtils.isEmpty(input.getText().toString().trim())) {
            input.setError(getString(R.string.please_enter_the_verification_code));
            return;
        }
        settingPresenter.loadDockData();
    }
}
