package com.smt.wxdj.swxdj;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.smt.wxdj.swxdj.adapt.MenuAdapt;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.boxs.presenter.TaskBoxPresenterImpl;
import com.smt.wxdj.swxdj.boxs.view.TaskBoxView;
import com.smt.wxdj.swxdj.dialog.IAlertDialog;
import com.smt.wxdj.swxdj.dialog.SearchResultBoxDialog;
import com.smt.wxdj.swxdj.fragment.MainBoxFragment;
import com.smt.wxdj.swxdj.logins.presenter.LogoutPresenter;
import com.smt.wxdj.swxdj.logins.presenter.LogoutPresenterImpl;
import com.smt.wxdj.swxdj.logins.ui.LoginActivity;
import com.smt.wxdj.swxdj.service.AlarmReceiver;
import com.smt.wxdj.swxdj.service.CheckTaskService;
import com.smt.wxdj.swxdj.service.CheckUserService;
import com.smt.wxdj.swxdj.service.TaskAlarmReceiver;
import com.smt.wxdj.swxdj.session.Session;
import com.smt.wxdj.swxdj.session.SessionInfo;
import com.smt.wxdj.swxdj.session.SortEt;
import com.smt.wxdj.swxdj.setting.ui.SettingsActivity;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.ActivityTool;
import com.smt.wxdj.swxdj.utils.SortType;
import com.smt.wxdj.swxdj.viewmodel.WorkViewModel;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.List;
import java.util.Observable;
import java.util.Observer;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RadioGroup.OnCheckedChangeListener, View.OnClickListener,
        AdapterView.OnItemSelectedListener, Observer, TaskBoxView<BoxDetalBean> {

    public static final int TASKTYPE_ALL = 0;//装卸船\车
    public static final int TASKTYPE_ZXBOAT = 1;//装卸船
    public static final int TASKTYPE_ZXC = 2;//装卸车
    public static final int TASKTYPE_CANCLEBOX = 3;//取消提箱
    public static final int TASKTYPE_FALLBOX = 4;//倒箱
    public static final int TASKTYPE_LDBOAT = 5;//装船
    public static final int TASKTYPE_DCBOAT = 6;//卸船

    private Toolbar toolbar;
    private TextView toolTitle;

    private Fragment mContent;
    private MainBoxFragment allBoatFragment;
    private MainBoxFragment cancleTaskFragment;
    private RadioGroup radioGroup;
    private Spinner mTaskSpinner;
    private Spinner mSpinner;
    private Spinner mUpSpinner;
    private int radioId;
    private MenuAdapt menuAdapt;
    private TaskBoxPresenterImpl mBoxsPresenterImpl;
    private BoxDetalBean boxDetalBean;
    private SearchResultBoxDialog boxDialog;
    private AlarmReceiver mReceiver; // 检测用户的定时器
    private TaskAlarmReceiver taskAlarmReceiver; // 检测任务的定时器
    private LogoutPresenter mLogoutPresenter;
    public static boolean isGoLogin;
    private WorkViewModel workViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityTool.addActivity(this);
        workViewModel = new ViewModelProvider(ViewModelStore::new, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkViewModel.class);

        Session session = Session.getInstance();
        session.deleteObserver(this);
        session.addObserver(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolTitle = (TextView) findViewById(R.id.taskTitle);
        toolTitle.setText(String.format(getString(R.string.currentTask), getString(R.string.zxboat)));
        findViewById(R.id.taskTitle).setOnClickListener(this);
        mTaskSpinner = (Spinner) findViewById(R.id.menuSpinner);
        mSpinner = (Spinner) findViewById(R.id.sortSpinner);
        mUpSpinner = (Spinner) findViewById(R.id.sortBoxSpinner);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //获取headview
        View headerView = navigationView.getHeaderView(0);

        radioGroup = (RadioGroup) headerView.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        //20180224 增加抄场功能是否显示功能，默认显示
        RadioButton ccButton = (RadioButton) radioGroup.findViewById(R.id.menu_verfy);
        boolean ccSwitch = LruchUtils.isSwitch(getString(R.string.open_cc_switch));
        if (!ccSwitch) {
            ccButton.setVisibility(View.GONE);
        }

        headerView.findViewById(R.id.menu_zlBox).setOnClickListener(this);
        radioId = R.id.menu_zxboat;
        TextView username = (TextView) headerView.findViewById(R.id.username);
        TextView desc = (TextView) headerView.findViewById(R.id.desc);
        if (null != MyApplication.user) {
//            username.setText("欢迎 " + MyApplication.user.getSignonUSERID());
            username.setText(getString(R.string.welcome) + MyApplication.user.getSignonUSERID());
//            desc.setText("吊机 " + MyApplication.user.getCRANE());
//            desc.setText(getString(R.string.crane) + MyApplication.user.getCRANE());
            desc.setVisibility(View.GONE);
        }

        //加载默认的View
        allBoatFragment = MainBoxFragment.newInstance(TASKTYPE_ALL);
        mContent = allBoatFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.content, allBoatFragment).commit();

        String[] mArray = this.getResources().getStringArray(R.array.sortArray);
        MenuAdapt adapter = new MenuAdapt(this, mArray, Color.WHITE);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0, true);
        mSpinner.setOnItemSelectedListener(this);

        String[] menuArray = this.getResources().getStringArray(R.array.menuArray);
        menuAdapt = new MenuAdapt(this, menuArray, Color.WHITE);

        mTaskSpinner.setAdapter(menuAdapt);
        mTaskSpinner.setSelection(0, true);
        mTaskSpinner.setOnItemSelectedListener(this);

        String[] mBoxArray = this.getResources().getStringArray(R.array.sortUpBoxArray);
        MenuAdapt boxAdapter = new MenuAdapt(this, mBoxArray, Color.WHITE);
        mUpSpinner.setAdapter(boxAdapter);
        mUpSpinner.setSelection(0, true);
        mUpSpinner.setOnItemSelectedListener(this);
        mBoxsPresenterImpl = new TaskBoxPresenterImpl(this);

        /**
         * 开启检测用户服务
         */
//        Intent startIntent = new Intent(this, CheckUserService.class);
//        startService(startIntent);
        /**
         * 开启检测任务服务
         */
//        startIntent = new Intent(this, CheckTaskService.class);
//        startService(startIntent);

        mLogoutPresenter = new LogoutPresenterImpl();
        doRegisterReceiver();

        //搜索结果
        workViewModel.mSearchCntrInfo.observe(this,yardCntrInfos -> {
            boxDialog.hideProgress();
            boxDialog.setSearchResult(yardCntrInfos);
        });
    }

    /**
     * 注册广播接收者
     */
    private void doRegisterReceiver() {
        mReceiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter(FileKeyName.OPEN);
        filter.addAction(FileKeyName.CHECKTASK);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int radioButtonId = group.getCheckedRadioButtonId();
        switch (radioButtonId) {
            case R.id.menu_cancleBox:
                drawer.closeDrawer(GravityCompat.START);
                toolTitle.setText(String.format(getString(R.string.currentTask), getString(R.string.cancleBox)));
                if (null == cancleTaskFragment) {
                    cancleTaskFragment = MainBoxFragment.newInstance(TASKTYPE_CANCLEBOX);
                }
                switchFragment(cancleTaskFragment);
                radioId = radioButtonId;
                break;
            case R.id.menu_verfy://堆场抄场
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, MyGridViewActivity.class).putExtra("isArrange", false));
                radioGroup.check(radioId);
                break;
            case R.id.menu_zlBox://箱区整理
                drawer.closeDrawer(GravityCompat.START);
                startActivity(
                        new Intent(MainActivity.this, MyGridViewActivity.class)
                        .putExtra("curCraneId", MainBoxFragment.mCurCraneId)
                        .putExtra("isArrange", true)
                );
                radioGroup.check(radioId);
                break;
            case R.id.menu_history://历史记录
                startActivity(new Intent(MainActivity.this, HistoryActivity.class).putExtra("isArrange", true));
                drawer.closeDrawer(GravityCompat.START);
                radioGroup.check(radioId);
                break;
            case R.id.menu_search_location://查找堆位
                drawer.closeDrawer(GravityCompat.START);
                radioGroup.check(radioId);
                boxDialog = new SearchResultBoxDialog(this);
                boxDialog.setWorkViewModel(workViewModel);
                boxDialog.setConfirmClickListener(new SearchResultBoxDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog, YardCntrInfo bean) {

                    }

                    @Override
                    public void onItemClick(Dialog dialog, YardCntrInfo bean) {
                        if (null == bean) return;
                        dialog.dismiss();
                        boxDetalBean = bean;
//                        bean.setMaj_Loc(PreferenceUtils.getString(MainActivity.this, "majloc", ""));
//                        bean.setSub_Loc(PreferenceUtils.getString(MainActivity.this, "subloc", ""));
//                        String str = PreferenceUtils.getString(MainActivity.this, bean.getRown(), null);
//                        if (null == str) {
//                            mBoxsPresenterImpl.CheckMaxCellTier(bean.getRown());
//                        } else {
//                            Intent it = new Intent(MainActivity.this, MyGridViewActivity.class);
//                            it.putExtra("boxBean", bean);
//                            it.putExtra("bay", deserialize(str, Bay.class));
//                            startActivityForResult(it, 1001);
//                        }
                        MyGridViewActivity.start(MainActivity.this, bean, MainBoxFragment.mCurCraneId);
                    }
                });
                boxDialog.show();
                boxDialog.hideView();
                break;
            case R.id.menu_stack://切换场地
                Session.getInstance().notifySelect("stack");
                group.clearCheck();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_qhzh://切换账户
                qhzh();
                break;
            case R.id.menu_setting://设置
                startActivity(new Intent(MainActivity.this, SettingsActivity.class).putExtra(SettingsActivity.SETTINGTYPE, SettingsActivity.TYPE_USER));
                drawer.closeDrawer(GravityCompat.START);
                radioGroup.check(radioId);
                break;
            case R.id.menu_exit://退出系统
                exit();
                break;
            default:
                break;
        }

    }

    public SearchResultBoxDialog getBoxDialog() {
        return boxDialog;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getCurStack() {
        return null;
    }

    @Override
    public void showProgress(int type) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addList(List<BoxDetalBean> list) {

    }

    @Override
    public void onFaile(String msg) {

    }

    @Override
    public void addListCd(List<StackBean> list) {

    }

    @Override
    public void addBay(Bay bay) {
        PreferenceUtils.putString(this, bay.getBay(), bay.toString());
        Intent it = new Intent(this, MyGridViewActivity.class);
        it.putExtra("boxBean", boxDetalBean);
        it.putExtra("bay", bay);
        startActivityForResult(it, 1);
    }

    @Override
    public void unLockStackList(List<StackBean> list) {

    }

    @Override
    public void lockStack() {

    }

    @Override
    public void putBoxForCellData(Object object) {

    }

    /**
     * 切换页面
     *
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        if (mContent != fragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            if (!fragment.isAdded()) {
                mContent.setUserVisibleHint(false);
                fragmentTransaction.hide(mContent).add(R.id.content, fragment).commit();
            } else {
                fragment.setUserVisibleHint(true);
                mContent.setUserVisibleHint(false);
                fragmentTransaction.hide(mContent).show(fragment).commit();
            }
            mContent = fragment;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.taskTitle:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    private void exit() {
        new IAlertDialog(this)
                .setContentText(getString(R.string.is_exit))
                .setComfirmBtnText(getString(android.R.string.ok))
                .setCancleBtnText(getString(android.R.string.cancel))
                .setConfirmClickListener(new IAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                        isGoLogin = true;
                        finish();
                    }
                }).setCancelClickListener(new IAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                dialog.dismiss();
                radioGroup.setOnCheckedChangeListener(null);
                radioGroup.clearCheck();
                radioGroup.setOnCheckedChangeListener(MainActivity.this);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        }).show();

    }

    private void qhzh() {
        new IAlertDialog(this)
//                .setContentText("是否要切换账户?")
                .setContentText(getString(R.string.do_you_want_to_switch_accounts))
                .setComfirmBtnText(getString(android.R.string.ok))
                .setCancleBtnText(getString(android.R.string.cancel))
                .setConfirmClickListener(new IAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        isGoLogin = true;
                        finish();
                    }
                }).setCancelClickListener(new IAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                dialog.dismiss();
                radioGroup.setOnCheckedChangeListener(null);
                radioGroup.clearCheck();
                radioGroup.setOnCheckedChangeListener(MainActivity.this);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        }).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        radioGroup.setOnCheckedChangeListener(null);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(MainActivity.this);


        switch (adapterView.getId()) {
            case R.id.sortSpinner://空重箱
//                LogUtils.e("tag", "空重箱："+i);
                switch (i) {
                    case 0:
                        Session.getInstance().notifySelect(new SessionInfo(SortType.ALL, SortEt.FE));
                        break;
                    case 1:
                        Session.getInstance().notifySelect(new SessionInfo(SortType.FBox, SortEt.FE));
                        break;
                    case 2:
                        Session.getInstance().notifySelect(new SessionInfo(SortType.EBox, SortEt.FE));
                        break;
                }
                break;
            case R.id.menuSpinner://装卸船/车和取消提箱,新增倒箱功能
                switch (i) {
                    case 0://装卸船/车
                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_ALL);
                        break;
//                    case 1://装卸船
//                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_ZXBOAT);
//                        break;
//                    case 2://装船
////                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_LDBOAT);
//                        //倒箱
//                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_FALLBOX);
//                        break;
//                    case 3://卸船
//                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_DCBOAT);
//                        break;
//                    case 4://装卸车
//                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_ZXC);
//                        break;
                    case 1://取消提箱
                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_CANCLEBOX);
                        break;
                    case 2://倒箱
                        Session.getInstance().notifySelect(FileKeyName.TASKTYPE_FALLBOX);
                        break;
                }
                mTaskSpinner.setSelection(i);
                break;
            case R.id.sortBoxSpinner:
                switch (i) {
                    case 0:
                        Session.getInstance().notifySelect(new SessionInfo(SortType.AllBox, SortEt.PU));
                        break;
                    case 1:
                        Session.getInstance().notifySelect(new SessionInfo(SortType.PutBox, SortEt.PU));
                        break;
                    case 2:
                        Session.getInstance().notifySelect(new SessionInfo(SortType.UpBox, SortEt.PU));
                        break;
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String msg = (String) arg;
            if (FileKeyName.CANCELBOX.equals(msg)) {
                mTaskSpinner.setSelection(1);
                if (mTaskSpinner.getSelectedItemPosition() == 1) {
                    Session.getInstance().notifySelect(FileKeyName.TASKTYPE_CANCLEBOX);
                }
            } else if (FileKeyName.CONFIRMBOX.equals(msg)) {
                mTaskSpinner.setSelection(0);
                if (mTaskSpinner.getSelectedItemPosition() == 0) {
                    Session.getInstance().notifySelect(FileKeyName.TASKTYPE_ALL);
                }
            } else if (FileKeyName.FALLBOX.equals(msg)) {
                mTaskSpinner.setSelection(2);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        Intent stopIntent = new Intent(this, CheckUserService.class);
        stopService(stopIntent);
        stopIntent = new Intent(this, CheckTaskService.class);
        stopService(stopIntent);
        if (null != mLogoutPresenter) mLogoutPresenter.Logout();
//        ActivityTool.finishActivity();
        if (!isGoLogin)
            System.exit(0);
    }
}
