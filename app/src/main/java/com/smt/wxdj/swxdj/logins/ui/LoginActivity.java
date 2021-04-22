package com.smt.wxdj.swxdj.logins.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smt.wxdj.swxdj.MainActivity;
import com.smt.wxdj.swxdj.MvpBaseActivity;
import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.adapt.MyCdOrTwAdapt;
import com.smt.wxdj.swxdj.api.LoginInterface;
import com.smt.wxdj.swxdj.bean.AppUpdateBean;
import com.smt.wxdj.swxdj.bean.Dock;
import com.smt.wxdj.swxdj.bean.DriverList;
import com.smt.wxdj.swxdj.bean.MachineNo;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.dao.Tenants;
import com.smt.wxdj.swxdj.dao.TokenInfo;
import com.smt.wxdj.swxdj.enums.DataType;
import com.smt.wxdj.swxdj.logins.presenter.LoginPresenterImpl;
import com.smt.wxdj.swxdj.logins.view.LoginView;
import com.smt.wxdj.swxdj.network.RetrofitManager;
import com.smt.wxdj.swxdj.network.account.AccountManager;
import com.smt.wxdj.swxdj.network.entity.BaseResponse;
import com.smt.wxdj.swxdj.network.observer.ResponseObserver;
import com.smt.wxdj.swxdj.network.utils.RxUtils;
import com.smt.wxdj.swxdj.setting.ui.SettingsActivity;
import com.smt.wxdj.swxdj.switchlang.Constant;
import com.smt.wxdj.swxdj.switchlang.LangUtils;
import com.smt.wxdj.swxdj.switchlang.SPUtils;
import com.smt.wxdj.swxdj.update.DownloadUtil;
import com.smt.wxdj.swxdj.update.StorageUtils;
import com.smt.wxdj.swxdj.utils.AppTool;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.FileTool;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.ReadRawFileUtils;
import com.smt.wxdj.swxdj.utils.SettingConfig;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smtlibrary.dialog.PassWdDialog;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.permisson.MPermissions;
import com.smtlibrary.permisson.PermissionDenied;
import com.smtlibrary.permisson.PermissionGrant;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.MD5Code;
import com.smtlibrary.utils.PreferenceUtils;
import com.smtlibrary.utils.SystemUtils;
import com.smtlibrary.views.EditTextWithDel;
import com.smtlibrary.views.SpinnerEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends MvpBaseActivity<LoginView, LoginPresenterImpl>
        implements LoginView {
    public static final int REQUECT_CODE_SDCARD = 1;
    //    @Bind(R.id.email)
//    EditTextWithDel mEmailView;
    @Bind(R.id.password)
    EditTextWithDel mPasswordView;
    @Bind(R.id.spinner)
    Spinner mSpinner;
    @Bind(R.id.spUser)
    Spinner spUser;
    @Bind(R.id.spLanguage)
    Spinner spLanguage;
    @Bind(R.id.save)
    CheckBox checkBox;
    @Bind(R.id.email_sign_in_button)
    Button emailSignInButton;
    @Bind(R.id.setting)
    ImageView setting;
    @Bind(R.id.tvVersionCode)
    TextView tvVersionCode;
    @Bind(R.id.spUserName)
    SpinnerEditText spUserName;
    private LoginPresenterImpl mLoginPresenterImpl;

    private String machineNo;
    private String[] mArray;
    private String[] mArrayValue;

    private boolean isGetAllSystem;//获取所有系统设置参数是否成功
    private boolean isLoadMachineList;//是否加载所有吊机列表
    private String downUrl = "";
    private String updateMessage = "";
    private boolean isQuit;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    cancleProgressDialog();
                    updateAppDialog();
                    break;
                case 1:
                    onFaile(msg.obj.toString());
                    break;
                default:
                    break;
            }

        }
    };

    private void updateAppDialog() {
        dialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.android_auto_update_notify_content))
                .setContentText(updateMessage)
                .setConfirmText(getString(R.string.android_auto_update_dialog_btn_download))
                .setCancelText(getString(R.string.android_auto_update_dialog_btn_cancel))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        download();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        cancleProgressDialog();
                    }
                });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        WindowManager wm = this.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        PreferenceUtils.putInt(this, "width", width);
        PreferenceUtils.putInt(this, "height", height);
        needPermission();
        mLoginPresenterImpl.getTenants();


//        emailSignInButton.setEnabled(false);
        //点击回车键触发
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                machineNo = mArrayValue[position];
                OkHttpUtils.setTenant(mArrayValue[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceUtils.putBoolean(LoginActivity.this, FileKeyName.ISSAVE, isChecked);
            }
        });

        //判断是否保存登录数据过
        boolean isSave = PreferenceUtils.getBoolean(this, FileKeyName.ISSAVE, false);
        if (isSave) {
            checkBox.setChecked(true);
            spUserName.setText(PreferenceUtils.getString(this, FileKeyName.USERNAME, ""));
            mPasswordView.setText(PreferenceUtils.getString(this, FileKeyName.PASSWORD, ""));
        }

        Intent intent = getIntent();
        if (null != intent.getExtras()) {
            String msg = intent.getExtras().getString(FileKeyName.LOGOUT);
            if (!TextUtils.isEmpty(msg)) {
                SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getString(R.string.has_been_forcibly_logged_out))
                        .setConfirmText(getString(android.R.string.ok))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        }

        tvVersionCode.setText(getString(R.string.version_code) + AppTool.getAppVersionCode(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.MAJLOC = PreferenceUtils.getString(this, SettingConfig.MAJLOC, "");
        MyApplication.SUBLOC = PreferenceUtils.getString(this, SettingConfig.SUBLOC, "");
        URLTool.setUrl(PreferenceUtils.getString(this, SettingConfig.WEBURL, ""));
        LogUtils.sysout("MyApplication.MAJLOC", MyApplication.MAJLOC);
        LogUtils.sysout("MyApplication.SUBLOC", MyApplication.SUBLOC);
        LogUtils.sysout("MyApplication.webUrl", URLTool.getUrl());
        if (!SystemUtils.isNetworkAvailable(this)) {
            showAlertDialog(getString(R.string.netword_error));
        } else {
            setInternationalization();

//            mLoginPresenterImpl.loadMachineList();
//            mLoginPresenterImpl.getAllSystem();
//            mLoginPresenterImpl.getAppUpdate(FileKeyName.APPUPDATEFILENAME);
//            mLoginPresenterImpl.getDataList(DataType.DRIVER);

        }
    }

    /**
     * 国际化设置
     */
    private void setInternationalization() {
//        Log.e("language", "" + LanguageUtil.getInstance().getAppLocale(this).getLanguage());
//        Log.e("country", "" + LanguageUtil.getInstance().getAppLocale(this).getCountry());
        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (SPUtils.getInstance(Constant.SP_NAME).getInt(Constant.SP_USER_LANG) == position) {
                    return;
                }
                changeLanguage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spLanguage.setSelection(SPUtils.getInstance(Constant.SP_NAME).getInt(Constant.SP_USER_LANG));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LangUtils.getAttachBaseContext(newBase, SPUtils.getInstance(Constant.SP_NAME).getInt(Constant.SP_USER_LANG)));
    }

    private void changeLanguage(int language) {
        //将选择的language保存到SP中
        SPUtils.getInstance(Constant.SP_NAME).put(Constant.SP_USER_LANG, language);
        //重新启动Activity,并且要清空栈
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void attemptLogin() {
        // Reset errors.
//        mEmailView.setError(null);
        mPasswordView.setError(null);

//        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
//        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            showAlertDialog(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
            cancel = true;
            return;
        }

        String userName = spUserName.getValue();
        if (userName.contains(":")) {
            userName = userName.split(":")[0];
        }
        if (TextUtils.isEmpty(userName)) {
            showAlertDialog(getString(R.string.error_field_required));
            cancel = true;
        }

//        machineNo = "1244";

        if (TextUtils.isEmpty(machineNo)) {
            showAlertDialog(getString(R.string.error_field_aqh));
            cancel = true;
        }
        //check save data
        if (checkBox.isChecked()) {
//            PreferenceUtils.putString(this, "username", email);
            PreferenceUtils.putString(this, FileKeyName.USERNAME, spUserName.getValue());
            PreferenceUtils.putString(this, FileKeyName.PASSWORD, password);
            PreferenceUtils.putString(this, FileKeyName.MACHINENO, machineNo);
        }
        if (cancel) {
//            focusView.requestFocus();
        } else {
            mLoginPresenterImpl.login();
//            User user = new User();
//            user.setSignonUSERID(userName);
//            user.setSignonPWD(password);
//            user.setCRANE(machineNo);
//            mLoginPresenterImpl.CheckSignOnQC(user);
        }
    }


    @Override
    public String getUserName() {
//        return mEmailView.getText().toString().trim();
//        String user = spUser.getSelectedItem().toString();
//        if (user.contains(":")) {
//            user = user.split(":")[0];
//        }
//        return user;
        String user = spUserName.getValue();
        if (user.contains(":")) {
            user = user.split(":")[0];
        }
        return user;
    }

    @Override
    public String getPassWord() {
        return mPasswordView.getText().toString();
    }

    @Override
    public String getMachineNo() {
        return TextUtils.isEmpty(machineNo) ? "" : machineNo;
    }

    @Override
    public void showProgress() {
        showProgressDialog(getString(R.string.action_loging));
    }

    @Override
    public void hideProgress() {
        cancleProgressDialog();
    }

    @Override
    public void showLoginFaileMsg(String msg) {
//        mEmailView.setText("");
        mPasswordView.setText("");
//        showAlertDialog(msg);
        onFaile(msg);
    }

    @Override
    public void loginCrnSuccess() {
        User user = new User();
        user.setSignonUSERID(getUserName());
        user.setCrn(machineNo);
        MyApplication.setUser(user);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void showSuccess(User user) {
        user.setCrn(machineNo);
        MyApplication.setUser(user);
        mLoginPresenterImpl.saveRTG(user);
    }

    @Override
    public void addMachListData(List<MachineNo> list) {
        isLoadMachineList = true;
        if (LruchUtils.isExist() && isLoadMachineList) {
            findViewById(R.id.email_sign_in_button).setEnabled(true);
//            isGetAllSystem = false;
//            isLoadMachineList = false;
        }
        int index = 0;
        String aqh = PreferenceUtils.getString(this, FileKeyName.MACHINENO, "");
        mArray = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            MachineNo no = list.get(i);
            mArray[i] = no.getCRN();
            if (aqh.equals(no.getCRN())) {
                index = i;
            }
        }
        MyCdOrTwAdapt adapter = new MyCdOrTwAdapt(this, mArray, Color.BLACK);
        mSpinner.setAdapter(adapter);
        if (PreferenceUtils.getBoolean(this, FileKeyName.ISSAVE, false))
            mSpinner.setSelection(index);
    }

    @Override
    public void setAllSystemResult(boolean flag) {
        isGetAllSystem = flag;
        if (isGetAllSystem && isLoadMachineList) {
            findViewById(R.id.email_sign_in_button).setEnabled(true);
        }
    }

    @Override
    public String getAppUpdateRequestBody(String fileName) {
        return ReadRawFileUtils.readCheckSoapUpdate(this, fileName);
    }

    @Override
    public void getAppUpdateData(Object object) {
        AppUpdateBean appUpdateBean = (AppUpdateBean) object;
        long versionCode = AppTool.getAppVersionCode(this);
        String packageName = AppTool.getAppPackageName(this);
        for (int i = 0; i < appUpdateBean.getApp().size(); i++) {
            AppUpdateBean.AppBean bean = appUpdateBean.getApp().get(i);
            if (!packageName.equals(bean.getPackageName())) {
//                LogUtils.e("tag", "包名error");
                continue;
            }
            if (TextUtils.isEmpty(bean.getName()) || TextUtils.isEmpty(bean.getPackageName()) || TextUtils.isEmpty(bean.getVersionCode())
                    || TextUtils.isEmpty(bean.getUpdateMessage()) || TextUtils.isEmpty(bean.getUrl())) {
//                onFaile("服务端返回参数有误.");
                return;
            }
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            boolean isMatcher = pattern.matcher(bean.getVersionCode()).matches();
            if (!isMatcher) {
//                onFaile("下载地址有误.");
                return;
            }
            if (Long.parseLong(bean.getVersionCode()) <= versionCode) {
                LogUtils.e("tag", "已是最新版本");
                return;
            }
            downUrl = bean.getUrl();
            updateMessage = bean.getUpdateMessage();
            break;
        }
        if (TextUtils.isEmpty(downUrl) || TextUtils.isEmpty(updateMessage)) {
            return;
        }
        String apkUrl = downUrl.substring(downUrl.lastIndexOf(".") + 1);
        if (!"apk".equals(apkUrl)) {
//            onFaile("文件后缀名有误.");
            return;
        }

        handler.sendEmptyMessage(0);

    }

    private void download() {
//        DownloadUtil.getInstance().setHandler(handler);
        cancleProgressDialog();
        showProgressDialog(getString(R.string.down_loading));
        File dir = StorageUtils.getCacheDirectory(this);
        DownloadUtil.getInstance().download(downUrl, dir.getAbsolutePath(), new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                if (isQuit) return;
                cancleProgressDialog();
                DownloadUtil.getInstance().installAPk(LoginActivity.this, new File(path));
            }

            @Override
            public void onDownloading(int progress) {
                LogUtils.e("onDownloading", "已下载" + progress + "%");
            }

            @Override
            public void onDownloadFailed(String msg) {
                if (isQuit) return;
                cancleProgressDialog();
                Message message = Message.obtain();
                message.what = 1;
                message.obj = msg;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void showLoadMsg() {
        showProgressDialog(getString(R.string.android_auto_update_dialog_checking));
    }

    @Override
    public void getDataList(List<DriverList> list) {

        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String phoneNumber = list.get(i).getSmCode() + ":" + list.get(i).getSmName();
            tempList.add(phoneNumber);
        }
        spUserName.setShowType(1);
        spUserName.setNeedShowSpinner(true);
        spUserName.setList(tempList);

    }

    @Override
    public void showTenants(Tenants tenants) {
        int index = 0;
        String aqh = PreferenceUtils.getString(this, FileKeyName.MACHINENO, "");
        mArray = new String[tenants.getItems().size()];
        mArrayValue = new String[tenants.getItems().size()];
        for (int i = 0; i < tenants.getItems().size(); i++) {
            Tenants.ItemsBean no = tenants.getItems().get(i);
            mArray[i] = no.getName();
            mArrayValue[i] = no.getId();
            if (aqh.equals(no.getId())) {
                index = i;
            }
        }
        MyCdOrTwAdapt adapter = new MyCdOrTwAdapt(this, mArray, Color.BLACK);
        mSpinner.setAdapter(adapter);
        if (PreferenceUtils.getBoolean(this, FileKeyName.ISSAVE, false)) {
            mSpinner.setSelection(index);
        }

    }

    @Override
    public void checkSignOnQCResult(boolean flag) {
        if (flag) {
            mLoginPresenterImpl.login();
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.has_user_login))
                    .setContentText(getString(R.string.is_the_login_mandatory))
                    .setCancelText(getString(android.R.string.cancel))
                    .setConfirmText(getString(android.R.string.ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            mLoginPresenterImpl.login();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public String getRequestBody() {
        return ReadRawFileUtils.readCheckSoapDock(this);
    }

    @Override
    public void onSuccess(final Object obj) {
        if (obj instanceof TokenInfo) {
            TokenInfo info = (TokenInfo) obj;
            AccountManager.setTokenInfo(info.getAccess_token());
            OkHttpUtils.setToken(info.getAccess_token());
            loginCrnSuccess();
            return;
        }
        if (obj instanceof List) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Dock> lists = (List<Dock>) obj;
                    String name = PreferenceUtils.getString(LoginActivity.this, SettingConfig.DOCK, "");
                    for (Dock dock : lists) {
                        if (dock.getDock().equals(name)) {
                            PreferenceUtils.putString(LoginActivity.this, SettingConfig.DOCK, dock.getDock());
                            PreferenceUtils.putString(LoginActivity.this, SettingConfig.MAJLOC, dock.getMajloc());
                            PreferenceUtils.putString(LoginActivity.this, SettingConfig.SUBLOC, dock.getSubloc());
                            PreferenceUtils.putString(LoginActivity.this, SettingConfig.WEBURL, dock.getWebUrl());
                            Toast.makeText(LoginActivity.this, "更新服务成功.", Toast.LENGTH_LONG).show();
//                            ToastUtils.showSuccessMsg(LoginActivity.this, "更新服务成功.");
                            break;
                        }
                    }
                }
            });
            return;
        }
    }

    @Override
    protected LoginPresenterImpl createPresenter() {
        return mLoginPresenterImpl = new LoginPresenterImpl();
    }

    @OnClick({R.id.email_sign_in_button, R.id.setting})
    public void onClick(View v) {
        if (v.getId() == R.id.setting) {
            PassWdDialog dialog = new PassWdDialog(this);
            dialog.setConfirmClickListener(new PassWdDialog.OnSweetClickListener() {
                @Override
                public void onClick(PassWdDialog inputDialog, String value) {
                    if (TextUtils.isEmpty(value)) {
                        inputDialog.dismiss();
                        mPresenter.loadDockData();
                        return;
                    }
                    if (new MD5Code().getMD5ofStr(value.trim().toLowerCase()).equals("A85E0847E94E471258F2D6C758E1A9B5")) {
                        startActivity(new Intent(LoginActivity.this, SettingsActivity.class).putExtra(SettingsActivity.SETTINGTYPE, SettingsActivity.TYPE_ADMINISTRATORS));
                        inputDialog.dismiss();
                    } else
                        inputDialog.setError("");
                }
            });
            dialog.show();
        } else if (v.getId() == R.id.email_sign_in_button) {
            attemptLogin();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            needPermission();
        }
    }

    private void needPermission() {
        MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestWSdcardSuccess() {
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestWSdcardFailed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileTool.cleanCustomCache(StorageUtils.getCacheDirectory(this));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL                      //退格的拦截，用来过滤返回键
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) { //返回键的操作
            isQuit = true;
            finish();
            return false;
        }

        return false;
    }


}

