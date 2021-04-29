package com.smt.wxdj.swxdj;

import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.smt.wxdj.swxdj.adapt.DragAdapter;
import com.smt.wxdj.swxdj.adapt.DxwAdapt;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.Trk;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.boxs.presenter.BoxsPresenterImpl;
import com.smt.wxdj.swxdj.boxs.view.BoxsView;
import com.smt.wxdj.swxdj.dialog.AutoPutBoxDialog;
import com.smt.wxdj.swxdj.dialog.BayDialog;
import com.smt.wxdj.swxdj.dialog.ChangeBoxAlertDialog;
import com.smt.wxdj.swxdj.dialog.ClearBoxDialog;
import com.smt.wxdj.swxdj.dialog.CommitDialog;
import com.smt.wxdj.swxdj.dialog.CommitDxwDialog;
import com.smt.wxdj.swxdj.dialog.GetBoxAlertDialog;
import com.smt.wxdj.swxdj.dialog.SearchResultBoxDialog;
import com.smt.wxdj.swxdj.dialog.ShowPutWgtBoxDialog;
import com.smt.wxdj.swxdj.dialog.TrkDialog;
import com.smt.wxdj.swxdj.dialogFragment.SettingPatternDialogFragment;
import com.smt.wxdj.swxdj.enums.BOXCTRLTYPE;
import com.smt.wxdj.swxdj.enums.BOXTYPE;
import com.smt.wxdj.swxdj.enums.DataType;
import com.smt.wxdj.swxdj.session.Session;
import com.smt.wxdj.swxdj.session.SessionInfo;
import com.smt.wxdj.swxdj.session.SortEt;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.CellTool;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.SettingConfig;
import com.smt.wxdj.swxdj.view.DragGrid;
import com.smt.wxdj.swxdj.viewmodel.WorkViewModel;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayCntrInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.smt.wxdj.swxdj.enums.BOXTYPE.DWX;
import static com.smt.wxdj.swxdj.utils.CellTool.BOX_TYPE_EB;
import static com.smt.wxdj.swxdj.utils.CellTool.BOX_TYPE_EC;


public class MyGridViewActivity extends BaseActivity<BoxsView, BoxsPresenterImpl> implements BoxsView, View.OnClickListener {
    private static final int DEFAULT_TYPE = -1;//默认添加新任务状态
    private static final int NOTHING_TYPE = -2;//无任务状态
    private static final String CHECK = "CHECK";//是否抄场
    private ScrollView mScrollview;
    private HorizontalScrollView horizontalScrollView;

    private LinearLayout xLayout;
    private RelativeLayout yLayout;
    private LinearLayout getBoxLayout;
    private BoxsPresenterImpl mBoxsPresenterImpl;
    private DragGrid userGridView;//用户栏目的GRIDVIEW
    private DragAdapter userAdapter;//用户栏目对应的适配器，可以拖动
    private DragGrid dxwGridView;// 倒箱位
    private DxwAdapt dxwAdapter;//倒箱位
    private Toolbar toolbar;
    private TextView toolTitle;//标题
    private Button cdStack;//场地
    private Button twSpinner;//田位
    private Button tcSpinner, btnTcOk, btnCc;//
    private final ArrayList<YardCntrInfo> defaultBoxList = new ArrayList<>();
    private final ArrayList<YardCntrInfo> mDxwData = new ArrayList<>();//倒箱位数据
    private int mCol = 0;//gridview 排数
    private int mRow = 0;// gridview 层数
    private YardCntrInfo bean;//任务对象
    private YardCntrInfo tempBoxBean;//临时存储换场地，换田位的BOX对象
    private YardCntrInfo getBoxBean;//临时存储换场地，换田位的BOX对象
    private boolean select;//是否处于选中箱子状态
    private int selectPositon = DEFAULT_TYPE;//相当从外部引用一个新得对象
    private int forwordSelectPositon = DEFAULT_TYPE;//选择要前往位置
    private int selectDwxPositon = DEFAULT_TYPE;//相当从外部引用一个新得对象
    private int forwordDwxPositon = DEFAULT_TYPE;//相当从外部引用一个新得对象
    private String mStackName = "";//场地名称
    private String mBayName = "";//田位名称
    private Bay mBay;//贝位
    private Bay curBay;//当前贝位，即传进来的贝位
    private boolean isHandler;//标记是否已经被处理过了，回调后更新数据
    private BOXCTRLTYPE mBoxCtrlType;//判断箱子动作行为
    private BOXTYPE mBoxType;//箱子类型
    private boolean isArrange = true;//是否是抄场
    private boolean mArrangeTag;//整理堆场标示
    private List<String> mRmList;//推荐位
    private boolean isDoRmList;//是否已经处理过放箱推荐位置
    private Map<String, YardCntrInfo> mBoxMaps;//箱子信息
    private List<Trk> mTrkList;
    private LinearLayout llBottom;//底部显示
    private Button btnRefresh;//新增刷新功能
    private int cellY;//左边倒箱位的高度/tier
    private boolean isStay; // 参数控制是否停留在界面， 默认不停留


    private WorkViewModel workViewModel;
    private YardBayCntrInfo mYardBayCntrInfo;//贝位信息
    private String mCntrNo;//传过来的箱子

    public static void start(Context context, String cntrNo, String bayId) {
        Intent starter = new Intent(context, MyGridViewActivity.class);
        starter.putExtra("bayId", bayId);
        starter.putExtra("isArrange", true);
        starter.putExtra("cntrNo", cntrNo);
        context.startActivity(starter);
    }

    // 参数控制自动刷新
    private int delayTime = 30000;//30秒自动刷新请求
    private static final int TIMETASK = 1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == TIMETASK) {
                if (null == tempBoxBean || null == getBoxBean) return false;
                tempBoxBean = getBoxBean;
                if (null != mBay)
                    mBoxsPresenterImpl.GetCntrInfoConver(getBoxBean);
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grid_view);
        workViewModel = new ViewModelProvider(ViewModelStore::new, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkViewModel.class);

        if (null != this.getIntent().getExtras()) {
            bean = (YardCntrInfo) this.getIntent().getExtras().getSerializable("boxBean");
            mBay = (Bay) this.getIntent().getExtras().getSerializable("bay");
            curBay = mBay;
            tempBoxBean = bean;
            if (null != bean)
                getBoxBean = (YardCntrInfo) bean.clone();
//            LogUtils.sysout("bean:",bean.toString());
            mCntrNo = this.getIntent().getExtras().getString("cntrNo");
            String bayId = this.getIntent().getExtras().getString("bayId");
            workViewModel.GetWithCntrByBayId(bayId);
        }


        showProgress();
        workViewModel.getCntrInfoMutableLiveData().observe(this, info -> {
            mYardBayCntrInfo = info;
            initData();
            hideProgress();
            List<YardCntrInfo> curListInfo = workViewModel.getCurBayCntrInfo().getValue();
            List<YardCntrInfo> dwCurListInfo = workViewModel.getDwListCntrInfo().getValue();
            Map<String, YardCntrInfo> cntrInfoMap = workViewModel.getCntrYardCntrInfo().getValue();
            if (null != curListInfo && null != cntrInfoMap) {
                addList(curListInfo, cntrInfoMap);
            }
            if (null != dwCurListInfo) {
                addDxwList(dwCurListInfo, cntrInfoMap);
            }
        });
        initView();
    }

    /**
     * 初始化VIEW
     */
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolTitle = findViewById(R.id.taskTitle);
        cdStack = findViewById(R.id.stack);
        twSpinner = findViewById(R.id.twbay);
        tcSpinner = findViewById(R.id.tcSpinner);
        btnTcOk = findViewById(R.id.btnTcOk);
        btnCc = findViewById(R.id.btnCc);
        getBoxLayout = findViewById(R.id.getBoxLayout);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        toolTitle.setOnClickListener(this);
        cdStack.setOnClickListener(this);
        twSpinner.setOnClickListener(this);
        tcSpinner.setOnClickListener(this);
        btnTcOk.setOnClickListener(this);
        btnCc.setOnClickListener(this);
        setSupportActionBar(toolbar);
        mScrollview = findViewById(R.id.scrollView);
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        userGridView = findViewById(R.id.userGridView);
        xLayout = findViewById(R.id.xLayout);
        yLayout = findViewById(R.id.yLayout);
        dxwGridView = findViewById(R.id.dxwGridView);
        mScrollview.fullScroll(ScrollView.FOCUS_DOWN);

        llBottom = findViewById(R.id.bottom_layout);
        llBottom.setOnClickListener(this);
    }

    private void initData() {
        isDoRmList = false;
        //加载场地
        if (null != bean) {
            initCntrTaskType();
        } else {//抄场，整理箱区
            mArrangeTag = true;
            isArrange = this.getIntent().getExtras().getBoolean("isArrange");
            //箱子整理,默认初始化6行4排
            toolTitle.setText(isArrange ? getString(R.string.zlCellBox) : getString(R.string.chaochang));
            btnCc.setVisibility(isArrange ? View.GONE : View.VISIBLE);
            btnRefresh.setVisibility(View.GONE);
        }
        mCol = mYardBayCntrInfo == null ? 6 : (int) mYardBayCntrInfo.getMaxCell();
        mRow = mYardBayCntrInfo == null ? 5 : (int) mYardBayCntrInfo.getMaxTier();
        init();

        if (null != mBay) {
            mBoxsPresenterImpl.GetCntrInfoConver(getBoxBean);
        }

        String value = LruchUtils.getValues(FileKeyName.isStayWorkActivity);
        if (!TextUtils.isEmpty(value) || "YES".equals(value)) {
            isStay = true;
        }
        if (isStay && PreferenceUtils.getBoolean(this, SettingConfig.OPEN_TIME_TASK, false)) {
            delayTime = PreferenceUtils.getInt(this, SettingConfig.SETTING_TIME, SettingConfig.TIME_OUT) * 1000;
        }
    }

    private void initCntrTaskType() {
        if (bean.isPutCntr()) {//卸箱
            getBoxLayout.setVisibility(View.VISIBLE);
            btnTcOk.setVisibility(View.GONE);
            toolTitle.setText(getString(R.string.putBoxFormat));
            tcSpinner.setText(String.format(getString(R.string.rztc), getBoxBean.getTrk()));
            select = true;
            mBoxCtrlType = BOXCTRLTYPE.PUTBOX;
            mBoxType = BOXTYPE.DEFAULT;
            bean.setStatus(BoxTool.STATE_PL);
            tempBoxBean.setStatus(BoxTool.STATE_PL);
            getBoxBean.setStatus(BoxTool.STATE_PL);
            btnRefresh.setVisibility(View.GONE);
        } else if (bean.isGetCntr()) {//提箱
            getBoxLayout.setVisibility(View.VISIBLE);
            mBoxCtrlType = BOXCTRLTYPE.GETBOX;
            toolTitle.setText(getString(R.string.getBoxFormat));
            tcSpinner.setText(String.format(getString(R.string.cztc), getBoxBean.getTrk()));
            bean.setStatus(BoxTool.STATE_PL);
            tempBoxBean.setStatus(BoxTool.STATE_PL);
            getBoxBean.setStatus(BoxTool.STATE_PL);
            mBoxsPresenterImpl.loadTrkList(DataType.TRK, getBoxBean.getTrk());
            btnRefresh.setVisibility(View.VISIBLE);
        } else if (bean.cancleGetCntr()) {//取消提箱
            getBoxLayout.setVisibility(View.GONE);
            toolTitle.setText(getString(R.string.cancleBox));
            mBoxCtrlType = BOXCTRLTYPE.PUTBOX;
            bean.setSvc_Code(bean.getTrk_Type());//MAR转场，BRG装卸船，CY闸口，CSV修改箱信息,CP
            bean.setRown(bean.getRown());
            bean.setCell(bean.getiToCell());
            bean.setTier(bean.getiToTier());
            if (bean.getActivity().equals(BoxTool.CTRL_UPBOX))
                bean.setActivity(BoxTool.CTRL_GETBOX);
            else bean.setActivity(BoxTool.CTRL_GETBOXIP);

            switch (bean.getStatus()) {
                case BoxTool.STATE_WD://外拖车、装卸船
                    bean.setStatus(BoxTool.STATE_PW);
                    break;
                case BoxTool.STATE_CP://内部转场
                    bean.setStatus(BoxTool.STATE_WD);
                    break;
                default:
                    break;
            }
            tempBoxBean = bean;
            if (null != bean) getBoxBean = (YardCntrInfo) bean.clone();
            btnRefresh.setVisibility(View.GONE);
        } else if (bean.isPJCntr()) {//倒箱
            toolTitle.setText(getString(R.string.pjBoxFormat));
            select = true;
            mBoxCtrlType = BOXCTRLTYPE.MOVEBOX;
            mBoxType = BOXTYPE.DEFAULT;
            bean.setStatus(BoxTool.STATE_PL);
            tempBoxBean.setStatus(BoxTool.STATE_PL);
            getBoxBean.setStatus(BoxTool.STATE_PL);
            LogUtils.sysout("bean", bean);
            btnRefresh.setVisibility(View.GONE);
        } else {
            toolTitle.setText("");
        }
        mBayName = getBoxBean.getRown();
        mStackName = getBoxBean.getStack();
        cdStack.setText(String.format(getString(R.string.cd), mStackName));
        twSpinner.setText(String.format(getString(R.string.tw), mBayName));
    }

    private void init() {
        int itemWidth = (int) this.getResources().getDimension(R.dimen.box_item_width);
        int gridviewWidth = (mCol * (itemWidth + 2));
        //重新计算宽度
        ViewGroup.LayoutParams params = userGridView.getLayoutParams();
        params.width = gridviewWidth;
//        params.height = mCol * itemWidth + mCol;
        userGridView.setColumnWidth(itemWidth); // 设置列表项宽
        userGridView.setStretchMode(GridView.NO_STRETCH);
        userGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        userGridView.setNumColumns(mCol);
        userGridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键

        if (null == userAdapter) userAdapter = new DragAdapter(this, getBoxBean);
        userGridView.setAdapter(userAdapter);
        userGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectContrller(position);
            }
        });
        initCell(itemWidth);
        initDefaultBoxData();
        //系统参数判断是否能倒箱
        if (!mArrangeTag) {
            mBoxsPresenterImpl.GetSysParam(String.valueOf(LruchUtils.isSwitch(getString(R.string.rm_color_switch))));
        }
    }

    /**
     * 初始化本田位的位置
     */
    private void initDefaultBoxData() {
        //1.提前绘制好所有的该田位下的所有排、层的位置
        defaultBoxList.clear();
        int count = mCol * mRow;
        for (int i = 0; i < count; i++) {
            String cell = CellTool.getCell(i, mCol, mRow);
            defaultBoxList.add(new YardCntrInfo(cell, CellTool.getCell(), CellTool.getTier(), false));
        }
        userAdapter.setListDate(defaultBoxList);
    }

    /**
     * 绘制坐标显示View
     *
     * @param itemWidth
     */
    private void initCell(int itemWidth) {
        xLayout.removeAllViews();
        yLayout.removeAllViews();
        //init x  value view
        int height = (int) this.getResources().getDimension(R.dimen.item_x_width);
        for (int i = 0; i < mCol; i++) {
            TextView itemView = createTextView(i, itemWidth, height, 0);
            xLayout.addView(itemView);
        }

        //itemWidth / 6 左边倒箱位数字的宽度
        createYLayoutTextView(yLayout, mRow, itemWidth / 6);
        initDaoXiangWei();
    }

    /**
     * 初始化倒箱位View
     */
    private void initDaoXiangWei() {
        mDxwData.clear();
        for (int i = 0; i < mRow; i++)
            mDxwData.add(new YardCntrInfo(CellTool.getDxwCell(i, 1, mRow), BoxTool.CTRL_GETBOX, false));
        if (null == dxwAdapter) dxwAdapter = new DxwAdapt(this);
        dxwAdapter.setListDate(mDxwData);
        dxwGridView.setAdapter(dxwAdapter);
        dxwGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                userAdapter.resetSelectBox();
                if (dxwAdapter.HashBox(position)) {//如果有箱子，则处理从倒箱位选中状态,(把倒箱位的箱子重新放到田位中)
                    int res = dxwAdapter.exchangeSelect(1, position);
                    switch (res) {
                        case CellTool.BOX_TYPE_SUCESS:
                            select = true;
                            bean = dxwAdapter.getItem(position);
                            //还是选择克隆一个新的对象
                            tempBoxBean = (YardCntrInfo) bean.clone();
                            selectPositon = DEFAULT_TYPE;
                            selectDwxPositon = position;
                            mBoxType = DWX;
                            break;
                        case BOX_TYPE_EB:
                            showAlertDialog(getString(R.string.error_msg1));
                            break;
                        default:
                            break;
                    }
                } else {
                    if (!dxwAdapter.isSelect(position)) return;
//                    LogUtils.sysout("dxw bean=1=", JsonUtils.serialize(tempBoxBean));
                    if (select) {//判断田位的排是否选择箱子了,如果有，则把箱子放到倒箱位中;默认叠加
                        forwordDwxPositon = position;
                        if (mBoxType == BOXTYPE.DEFAULT) {
                            mBoxType = BOXTYPE.DEFAULT_TO_DXW;
                        }
                        if (null == tempBoxBean) return;
                        cellY = dxwAdapter.getCellY(tempBoxBean, position, 1);
                        int tier = Integer.parseInt(bean.getTier());
                        int cell = Integer.parseInt(bean.getCell());
//                        LogUtils.e("tag", "cellY:"+cellY);
//                        LogUtils.e("tag", "tier:"+tier);
//                        LogUtils.e("tag", "cell:"+cell);
                        if (cell == 0 && cellY > tier) {
                            showAlertDialog(getString(R.string.cc_select_error));
                            return;
                        }
                        onCommitDxwDialog(tempBoxBean.getCntr(), position);
                    } else {
                        showAlertDialog(getString(R.string.error_no_box));
                    }
                }
            }
        });
    }

    /**
     * 箱子控制处理行为
     *
     * @param position
     */
    private void selectContrller(int position) {
        //判断是否是抄场
        if (!isArrange) {
            arrangeAction(position);
        } else {//放箱、提箱、整理箱操作
            if (!select) {
                selectBox(position);
            } else {
                handlerTask(position);
            }
        }
    }

    /**
     * 抄场处理
     *
     * @param position
     */
    private void arrangeAction(int position) {
        if (TextUtils.isEmpty(mBayName) || TextUtils.isEmpty(mStackName)) {
            onFaile(getString(R.string.cc_cd_error));
            return;
        }
        int result = userAdapter.checkClearBox(position, mCol);
        switch (result) {
            case CellTool.BOX_TYPE_EA:
                onFaile(getString(R.string.cc_select_error));
                return;
            case BOX_TYPE_EB:
            case BOX_TYPE_EC:
                onFaile(getString(R.string.cc_select_has_error));
                return;
            default:
                break;
        }
        select = true;
        forwordSelectPositon = position;//保存选中的箱子位置倒缓存当中
        mBoxType = BOXTYPE.DEFAULT;

        new SearchResultBoxDialog(this).setConfirmClickListener(new SearchResultBoxDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, YardCntrInfo YardCntrInfo) {
                if (null == YardCntrInfo) {
                    onFaile(getString(R.string.please_select_box_info));
                    return;
                }
                dialog.dismiss();
                selectPositon = DEFAULT_TYPE;
                //内部放箱
                bean = YardCntrInfo;
                bean.setMaj_Loc(MyApplication.MAJLOC);
                bean.setSub_Loc(MyApplication.SUBLOC);
                tempBoxBean = bean;
                if (null != bean)
                    getBoxBean = (YardCntrInfo) bean.clone();
                if (bean.getRown().equalsIgnoreCase(CHECK)) {
                    handlerTask(forwordSelectPositon);
                } else {
                    new ClearBoxDialog(MyGridViewActivity.this, getString(R.string.cc_clear_error))
                            .setConfirmClickListener(new ClearBoxDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(Dialog dialog, View view) {
                                    dialog.dismiss();
                                    handlerTask(forwordSelectPositon);
                                }
                            }).show();
                }
            }

            @Override
            public void onItemClick(Dialog dialog, YardCntrInfo bean) {

            }
        }).show();
    }

    /**
     * 已有处理任务
     *
     * @param position
     */
    private void handlerTask(int position) {
        //如果选择同一个，则不需要重新选择
        if (selectPositon == position) return;
        if (userAdapter.HashBox(position)) {
            selectBox(position);
            return;
        }
        //大小贝不符合规则，直接返回
//        if (null != getBoxBean && !getBoxBean.getRown().equals(mBayName)) {
//            return;
//        }

        //增加这个判断导致提箱选择另外箱子有问题(新会新增获取所有参数设置导致,默认参数为false时，可用）
        //系统参数判断是否能倒箱
        if (null != getBoxBean && getBoxBean.isGetCntr() && !LruchUtils.isSwitch(getString(R.string.open_rm_switch))) {
            return;
        }

//        LogUtils.e("tag", "mCol:"+mCol+"==mRow:"+mRow+"==position:"+position);
//        LogUtils.e("tag", "判断 bean:"+bean.toString());
        //判断是否悬空选泽
        if (userAdapter.isNullLocation(mCol, mRow, position, bean, isArrange)) {
            onFaile(getString(R.string.error_null_location));
            return;
        }
        //判断是否是推荐位
        if (!mArrangeTag && !userAdapter.getRecomLocation(position)) {
            showAlertDialog(getString(R.string.error_rm_location));
            return;
        }

        //如果该位置没有箱子，首先判断当前箱子类型，再判断底下的箱子类型。
        //1.如果当前放的箱子是重箱，底下的箱子是空箱子，则提示是否放箱;
        if (!userAdapter.hashPutWgtBox(mCol, bean, position)) {
            showPutWgtBoxDialog(position);
        } else {
            showPutBoxConfirm(position);
        }
    }

    /**
     * 显示放置箱子确认对话框
     *
     * @param position
     */
    private void showPutBoxConfirm(int position) {
        int cellX = CellTool.getCellX(position, mCol, mRow);
        int cellY = CellTool.getCellY(position, mCol, mRow);
        forwordSelectPositon = position;
        if (mBoxType == DWX) {
            mBoxType = BOXTYPE.DXW_TO_DEFAULT;
        }
        if (null == bean) return;
        //要放置箱子的位置
        userAdapter.exchangeMoveSelect(position);
        String alertMsg = getString(android.R.string.ok) + ":" + bean.getCntr() + getString(R.string.put_on) + mBayName + "/" + cellX + "/" + cellY + getString(R.string.on);
        onCommitDialog(alertMsg, bean.getCntr(), cellX, cellY);
    }

    /**
     * 选择要移动的箱子
     *
     * @param position
     */
    private void selectBox(int position) {
        dxwAdapter.resetSelectBox();
        int res = userAdapter.exchangeSelect(mCol, position);
        YardCntrInfo detalBean1 = userAdapter.getItem(position);
        LogUtils.e("detalBean1", detalBean1.toString());
        switch (res) {
            case CellTool.BOX_TYPE_SUCESS:
                YardCntrInfo detalBean = userAdapter.getItem(position);
                if (!detalBean.getYardBayId().equals(mBayName)) {
                    return;
                }

                bean = userAdapter.getItem(position);
                tempBoxBean = (YardCntrInfo) bean.clone();//临时缓存箱子对象
                select = true;
                selectPositon = position;//保存选中的箱子位置倒缓存当中
                mBoxType = BOXTYPE.DEFAULT;
                if (null != bean && null != getBoxBean && !bean.getCntr().equals(getBoxBean.getCntr())) {
                    mBoxCtrlType = BOXCTRLTYPE.MOVEBOX;
                }
                break;
            case BOX_TYPE_EB:
                showAlertDialog(getString(R.string.error_msg1));
                break;
            default:
                if (!isHandler) {//任务未处理完成，继续处理
                    if (mBoxCtrlType == BOXCTRLTYPE.PUTBOX || mBoxCtrlType == BOXCTRLTYPE.PLPUTBOX) {
                        mBoxCtrlType = BOXCTRLTYPE.PLPUTBOX;
                        bean = getBoxBean;
                        tempBoxBean = getBoxBean;
//                        LogUtils.sysout("getBoxBean:", JsonUtils.serialize(bean));
                        select = true;
                        handlerTask(position);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stack:
            case R.id.twbay:
                //提箱，是否开启场地切换倒箱
                LogUtils.sysout("=========open_rm_stack_switch", LruchUtils.isSwitch(getString(R.string.open_rm_stack_switch)));
                if (null != getBoxBean && getBoxBean.isGetCntr() && !LruchUtils.isSwitch(getString(R.string.open_rm_stack_switch))) {
                    showAlertDialog(getString(R.string.error_rm_msg));
                    return;
                }
                new BayDialog(this, mStackName, mBayName, null)
                        .setConfirmClickListener(new BayDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(Dialog dialog, StackBean stackBean, Bay bay) {
                                dialog.dismiss();
                                isDoRmList = false;
                                mStackName = stackBean.getStack();
                                mBay = bay;
                                userAdapter.setBay(bay);
                                mBayName = bay.getBay();
                                cdStack.setText(String.format(getString(R.string.cd), mStackName));
                                twSpinner.setText(String.format(getString(R.string.tw), mBayName));
                                selectPositon = DEFAULT_TYPE;
//                                String str = PreferenceUtils.getString(MyGridViewActivity.this, bay.getBay(), null);
//                                if (str == null)
                                if (null != getBoxBean) getBoxBean.setRown(bay.getBay());
                                mBoxsPresenterImpl.CheckMaxCellTier(bay.getBay());
//                                if (null != mRmList) {
//                                    mRmList.clear();
//                                    mRmList = null;
//                                }
                            }
                        }).show();
                break;
            case R.id.tcSpinner:
                if (null == mTrkList) {
                    mBoxsPresenterImpl.loadTrkList(DataType.TRK, MyApplication.user.getCRANE());
                    return;
                }
                new TrkDialog(this, mTrkList).setConfirmClickListener(new TrkDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog, Trk bean) {
                        tempBoxBean.setTrk(bean.getTrk());
                        getBoxBean.setTrk(bean.getTrk());
                        tcSpinner.setText(String.format(getString(R.string.cztc), getBoxBean.getTrk()));
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.btnTcOk://确认提箱
//                LogUtils.e("tag", "getBoxBean:"+getBoxBean.toString());
//                LogUtils.e("tag", "bean:"+bean.toString());
//                LogUtils.e("tag", "tempBoxBean:"+tempBoxBean.toString());
                if (tempBoxBean.getCntr().equals(getBoxBean.getCntr())) {//同一个箱子
//                    LogUtils.e("tag", "同一个箱子...");
//                    onFaile("同一个箱子.");
                    onGetBoxAlertDialog(getBoxBean, true);
                } else {//不同箱子
//                    LogUtils.e("tag", "不同箱子...");
                    if (!bean.isChange()) {
                        onFaile(getString(R.string.boxes_are_irreplaceable));
                        return;
                    }
                    if (!bean.isChange() && bean.isRmCntr()) {
                        onFaile(getString(R.string.the_box_is_illega));
                        return;
                    }
                    bean.setTrk_Type(getBoxBean.getTrk_Type());
                    onGetBoxAlertDialog(bean, true);
                }
                break;
            case R.id.taskTitle:
                if (isHandler)
                    Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));
                finish();
                break;
            case R.id.btnCc://抄场
                if (TextUtils.isEmpty(mStackName) || TextUtils.isEmpty(mBayName)) {
                    onFaile(getString(R.string.cc_cd_error));
                    return;
                }
                new ClearBoxDialog(this).setConfirmClickListener(new ClearBoxDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        mBoxsPresenterImpl.putBoxToParkingSpace();
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.bottom_layout://点击显示图案
                SettingPatternDialogFragment fragment = new SettingPatternDialogFragment();
                fragment.show(getFragmentManager(), "");
                break;
            case R.id.btnRefresh://新增刷新功能
                tempBoxBean = getBoxBean;
                initData();
                break;
            default:
                break;
        }

    }

    /**
     * 是否重箱压轻箱提示框
     *
     * @param selectPositon
     */
    public void showPutWgtBoxDialog(final int selectPositon) {
        new ShowPutWgtBoxDialog(this).setConfirmClickListener(new ShowPutWgtBoxDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, View view) {
                showPutBoxConfirm(selectPositon);
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 箱子放置提示确认框
     *
     * @param position
     */
    private void onCommitDxwDialog(String boxNo, final int position) {
        if (null == boxNo) return;
        new CommitDxwDialog(this, boxNo)
                .setConfirmClickListener(new CommitDxwDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {

                        mBoxsPresenterImpl.loadCheckMoveForCell(tempBoxBean, 0, cellY);
                        dialog.dismiss();
                    }
                }).setCancelClickListener(new CommitDxwDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, View view) {
                selectDwxPositon = DEFAULT_TYPE;
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 确认箱子换位置的对话框
     *
     * @param msg
     * @param boxNo 箱号
     * @param cellX 移动的X坐标
     * @param cellY 移动Y的坐标
     */
    private void onCommitDialog(String msg, String boxNo, final int cellX, final int cellY) {
        new CommitDialog(this, msg, boxNo, cellX, cellY, mBayName)
                .setConfirmClickListener(new CommitDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
//                        mBoxsPresenterImpl.loadCheckMoveForCell(tempBoxBean, cellX, cellY);
                        Toast.makeText(MyGridViewActivity.this,"换箱确认",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }).setCancelClickListener(new CommitDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, View view) {
                resetValue();
                select = true;
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 提箱确认
     *
     * @param bean
     * @param isSame
     */
    private void onGetBoxAlertDialog(final YardCntrInfo bean, final boolean isSame) {
        if (null == bean) return;
        new GetBoxAlertDialog(this, bean)
                .setConfirmClickListener((getBoxAlertDialog, view) -> {
                    getBoxAlertDialog.dismiss();
                    if (isSame) {
                        if (userAdapter.AboveCntr(bean)) {
                            onFaile("\"" + bean.getCntr() + getString(R.string.the_box_was_pressed_down));
                        } else if (bean.isCntrReeferAStatus())
                            onFaile("\"" + bean.getCntr() + getString(R.string.refrigerator_has_not_been_unplugged));
                        else
                            mBoxsPresenterImpl.isValidOperation(bean);
                    } else
                        mBoxsPresenterImpl.LoadCheckChangeBox();
                }).show();
    }

    /**
     * 交换提箱子确认
     */
    private void onChangeBoxAlertDialog(final YardCntrInfo tBean) {
        if (null == tBean) return;
        new ChangeBoxAlertDialog(this, tBean).setConfirmClickListener(new ChangeBoxAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, View view) {
                dialog.dismiss();
//                LogUtils.e("tag", "是否交换箱子:" + bean.isSWAPFLAG());
                if (userAdapter.AboveCntr(bean)) {
                    onFaile("\"" + bean.getCntr() + getString(R.string.the_box_was_pressed_down));
                } else {
                    mBoxsPresenterImpl.GetCntrInfoConver(bean);
                }
//                    mBoxsPresenterImpl.LoadCheckChangeBox();
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (isHandler) {
                Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public User getUser() {
        return MyApplication.user;
    }

    @Override
    public BOXCTRLTYPE getBoxCtrlType() {
        return mBoxCtrlType;
    }

    @Override
    public YardCntrInfo getBoxDealBean() {
        return tempBoxBean;
    }

    @Override
    public YardCntrInfo getUpDealBean() {
        if (bean.getCntr().equals(getBoxBean.getCntr())) {
            return getBoxDealBean();
        } else {
            return bean;
        }
//        return null != bean && bean.isChange() ? bean : getBoxDealBean();
    }

    @Override
    public String getStack() {
        return mStackName;
    }

    @Override
    public String getBay() {
        return mBayName;
    }


    @Override
    public void addList(List<YardCntrInfo> list, Map<String, YardCntrInfo> maps) {
        this.mBoxMaps = maps;
        int currentIndex = 0;
        //3.根据坐标填充对于的位置
        for (int i = 0; i < defaultBoxList.size(); i++) {
            YardCntrInfo dItem = defaultBoxList.get(i);
            YardCntrInfo mItem = maps.get(dItem.getDefaultCell());
            if (null == mItem) continue;
            mItem.setHashBox(true);//设置位置有箱子
            mItem.setMoveSelect(false);
            if (TextUtils.equals(mCntrNo, dItem.getCntr())) {
                bean = mItem;
                tempBoxBean = bean;
                if (null != bean)
                    getBoxBean = (YardCntrInfo) bean.clone();
                initCntrTaskType();
            }
            //提箱
            if (getBoxBean != null && getBoxBean.isGetCntr()) {
                //判断一车两箱颜色
                mItem.isSameTrk(getBoxBean);
                //判断当前提箱字体颜色
                if (mItem.isSameCntr(getBoxBean)) {
                    //同步任务箱，与在场箱位置一样
                    getBoxBean.setCell(mItem.getCell());
                    getBoxBean.setTier(mItem.getTier());
                    tempBoxBean.setCell(mItem.getCell());
                    tempBoxBean.setTier(mItem.getTier());
                    bean.setCell(mItem.getCell());
                    bean.setTier(mItem.getTier());
                    currentIndex = Integer.parseInt(mItem.getCell());
                }

                //提箱背景颜色判断
                mItem.setGetCntrBackGroupColor(getBoxBean, maps);
                boolean b = mItem.getCntr().equals(bean.getCntr());
                if (b) mItem.setTXDefaultBackgroundColor();
                bean.setCell(mItem.getCell());
            } else if (getBoxBean != null && getBoxBean.isPJCntr()) {//倒箱
                //判断当前提箱字体颜色
                if (mItem.isSameCntr(getBoxBean)) {
                    mItem.setSelectedBox(true);
                    getBoxBean.setStatus(BoxTool.STATE_PL);
                    bean.setStatus(BoxTool.STATE_PL);
                    tempBoxBean.setStatus(BoxTool.STATE_PL);
                    selectPositon = i;
                }
            } else {
                if (null != bean)
                    currentIndex = Integer.parseInt(bean.getCell());
            }
            defaultBoxList.set(i, mItem);
        }

        userAdapter.setListDate(defaultBoxList);
        userAdapter.findRmCntrList(mCol, mRow, maps);
        mScrollview.fullScroll(ScrollView.FOCUS_DOWN);

        //平移到箱子的位置
        int length = (int) this.getResources().getDimension(R.dimen.box_item_width);
        horizontalScrollView.scrollTo((currentIndex - 3) * length, 0);

        //设置推荐位置
        if (!isDoRmList) {
            isDoRmList = userAdapter.enadblAllLocation(mCol, mRow, mBay, mRmList, bean, mBoxMaps);
        }

        //20191015
        // 参数控制停留在界面，并自动刷新
        if (isStay && PreferenceUtils.getBoolean(this, SettingConfig.OPEN_TIME_TASK, false)) {
            mHandler.sendEmptyMessageDelayed(TIMETASK, delayTime);
        }


        //参数控制卸船是否显示确认对话框
        if (null == bean) return;
        if (null == mBay) return;
        if (null == curBay) return;
        if (!curBay.getBay().equals(mBay.getBay())) return;
        boolean isAuto = LruchUtils.isSwitch(getString(R.string.automatic_discharge));
        boolean isDC = BoxTool.CTRL_PUTBOX.equals(bean.getActivity());
        if (isDC && !isAuto) {
            if (TextUtils.isEmpty(mBay.getMax_Tier())) {
                return;
            }
            //获取同一排的田位
            final List<Integer> beanList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                YardCntrInfo detalBean = list.get(i);
                if (!bean.getCell().equals(detalBean.getCell())) continue;
                beanList.add(Integer.parseInt(detalBean.getTier()));
            }
            //取最大层
            int maxTier = 0;
            if (beanList.size() > 0) {
                maxTier = Collections.max(beanList);
            }
            //放在同一排的层上
            maxTier++;
            int maxBayTier = Integer.parseInt(mBay.getMax_Tier());
            if (maxTier > maxBayTier) {
                return;
            }
            bean.setTier(String.valueOf(maxTier));
            String alertMsg = getString(android.R.string.ok) + ":" + bean.getCntr() + getString(R.string.put_on) + mBayName + "/" + bean.getCell() + "/" + bean.getTier() + getString(R.string.on);
            AutoPutBoxDialog dialog = new AutoPutBoxDialog(this, alertMsg);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.y = 100;
            window.setAttributes(lp);
            window.setGravity(Gravity.TOP | Gravity.RIGHT);
            dialog.show();
            dialog.setConfirmClickListener((dialog1, view) -> {
                dialog1.dismiss();
                mBoxsPresenterImpl.putBoxForCell(Integer.parseInt(bean.getCell()), Integer.parseInt(bean.getTier()));
            });
            //田位最大排
            int maxBayCell = Integer.parseInt(mBay.getMax_Cell());
            //箱子的排层
            int boxCell = Integer.parseInt(bean.getCell());
            int boxTier = Integer.parseInt(bean.getTier());
            //位置 = 田位最大排*（田位最大层-箱子的层）+（箱子的排）-1
            userAdapter.exchangeMoveSelect(maxBayCell * (maxBayTier - boxTier) + boxCell - 1);
        }


    }

    @Override
    public void addDxwList(List<YardCntrInfo> dxwlist, Map<String, YardCntrInfo> maps) {
        //处理倒箱位数据
        for (int i = 0; i < mDxwData.size(); i++) {
            YardCntrInfo dItem = mDxwData.get(i);
            YardCntrInfo temp = maps.get(dItem.getDefaultCell());
            if (null != temp) {
                temp.setHashBox(true);//设置位置有箱子
                mDxwData.set(i, temp);
            }
        }
        dxwAdapter.setListDate(mDxwData);
        hideProgress();
    }

    /**
     * 放箱推荐位
     *
     * @param rmList
     */
    @Override
    public void addRmList(List<String> rmList) {
        mRmList = rmList;
        if (!isDoRmList) {
            isDoRmList = userAdapter.enadblAllLocation(mCol, mRow, mBay, rmList, bean, mBoxMaps);
        }
    }

    /**
     * 贝位信息
     *
     * @param bay
     */
    @Override
    public void addBay(Bay bay) {
        mBay = bay;
        mCol = Integer.parseInt(mBay.getMax_Cell());
        mRow = Integer.parseInt(mBay.getMax_Tier());
        init();

        mBoxsPresenterImpl.GetCntrListByConditionNotCell(bay.getBay(), bean == null ? "" : bean.getCntr(), false);
//        mBoxsPresenterImpl.GetCntrInfoConver(bean == null ? null : bean);
    }

    /**
     * 内部放箱成功后刷新界面
     */
    @Override
    public void updateAdapt() {
        int res = -1;
        switch (mBoxType) {
            case DEFAULT:
                LogUtils.sysout("DEFAULT======", "DEFAULT");
                if (selectPositon == DEFAULT_TYPE) {//任务列表处理数据
                    tempBoxBean.setRown(mBayName);
                    res = userAdapter.exchange(tempBoxBean, forwordSelectPositon, mCol);
                    if (res == 0 && tempBoxBean.getCntr().equals(getBoxBean.getCntr())) {
                        isHandler = true;//任务列表处理完后，返回任务列表，更新任务列表数据
                        mBoxsPresenterImpl.CheckMaxCellTier(mBayName);
                        mArrangeTag = true;
                    }
                } else {//田位内部整理箱子
                    res = userAdapter.exchange(selectPositon, forwordSelectPositon, mCol);
//                    tempBoxBean = getBoxBean;
//                    initData();
//                    mBoxsPresenterImpl.GetCntrInfoConver(getBoxBean);
//                    mBoxsPresenterImpl.CheckMaxCellTier(mBayName);
                }
                break;
            case DWX:
                LogUtils.sysout("DWX======", "DWX");
                if (dxwAdapter.exchange(tempBoxBean, forwordDwxPositon, 1) == 0) {
                    userAdapter.resetBox(selectPositon);
                    dxwAdapter.sortDxw();
                }
                break;
            case DEFAULT_TO_DXW:
                LogUtils.sysout("DEFAULT_TO_DXW======", "DEFAULT_TO_DXW");
                if (dxwAdapter.exchange(tempBoxBean, forwordDwxPositon, 1) == 0) {
                    userAdapter.resetBox(selectPositon);
                }
                break;
            case DXW_TO_DEFAULT:
                LogUtils.sysout("DXW_TO_DEFAULT======", "DXW_TO_DEFAULT");
                res = userAdapter.exchange(tempBoxBean, forwordSelectPositon, mCol);
                break;
            default:
                break;
        }

        switch (res) {
            case CellTool.BOX_TYPE_EA:
            case BOX_TYPE_EC:
                showAlertDialog(getString(R.string.error_msg));
                break;
            case BOX_TYPE_EB:
                showAlertDialog(getString(R.string.error_msg1));
                break;
            case CellTool.BOX_TYPE_SUCESS:
                //更新倒箱位列表
                dxwAdapter.resetBox(selectDwxPositon);
                dxwAdapter.sortDxw();
                break;
            default:
                break;
        }

        resetValue();
        isDoRmList = false;
        userAdapter.resetRecommentLocation();
        userAdapter.reflashMap(mBoxMaps);
        if (!mArrangeTag)
            mBoxsPresenterImpl.GetSysParam(String.valueOf(LruchUtils.isSwitch(getString(R.string.rm_color_switch))));
        if (getBoxBean.isPJCntr() && getBoxBean.isSameCntr(tempBoxBean)) {
            Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));
            finish();
        }
    }

    /**
     * 提箱成功后刷新界面
     */
    @Override
    public void updateUpBoxData() {
        userAdapter.exchangeGetBox(getBoxBean);
        isHandler = true;
        mBoxCtrlType = BOXCTRLTYPE.MOVEBOX;
        getBoxBean = null;
        Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));


        if (isStay) {
            finish();
        }
    }

    /**
     * 放箱成功后刷新界面
     */
    @Override
    public void updatePutBoxData() {
        if (mBoxType == BOXTYPE.DEFAULT_TO_DXW) {
            dxwAdapter.exchange(tempBoxBean, forwordDwxPositon, 1);
        } else {
            getBoxBean.setStack(mStackName);
            getBoxBean.setRown(mBayName);
            getBoxBean.setRown(mBayName);
            getBoxBean.setActivity("");
            YardCntrInfo putBox = (YardCntrInfo) getBoxBean.clone();
            userAdapter.exchange(putBox, forwordSelectPositon, mCol);
            isHandler = true;
            mBoxCtrlType = BOXCTRLTYPE.MOVEBOX;
            getBoxBean = null;
            resetValue();
        }
        Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));


        if (isStay) {
            finish();
        }
    }

    @Override
    public void checkChangeBoxSucess() {
        getBoxBean = tempBoxBean;
    }

    @Override
    public void checkChangeBoxFaile() {
        userAdapter.exchangeCheckGetBox(tempBoxBean);
    }

    @Override
    public String getSTATUS() {
        return BoxTool.STATE_PL;
    }

    /**
     * 抄场结果
     */
    @Override
    public void putBoxToParkingSpaceResult() {
        userAdapter.resetDefault();
    }

    /**
     * 模糊搜索箱子结果
     *
     * @param list
     */
    @Override
    public void setSearchResult(List<YardCntrInfo> list) {
        new SearchResultBoxDialog(this, list).setConfirmClickListener(new SearchResultBoxDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, YardCntrInfo YardCntrInfo) {
                if (null == YardCntrInfo) {
                    onFaile(getString(R.string.please_select_box_info));
                    return;
                }
                dialog.dismiss();
                selectPositon = DEFAULT_TYPE;
                //内部放箱
                bean = YardCntrInfo;
                bean.setMaj_Loc(MyApplication.MAJLOC);
                bean.setSub_Loc(MyApplication.SUBLOC);
                tempBoxBean = bean;
                if (null != bean) {
                    getBoxBean = (YardCntrInfo) bean.clone();
                }
                if (bean.getRown().equalsIgnoreCase("CHECK")) {
                    handlerTask(forwordSelectPositon);
                } else {
                    new ClearBoxDialog(MyGridViewActivity.this, getString(R.string.cc_clear_error)).setConfirmClickListener(new ClearBoxDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(Dialog dialog, View view) {
                            dialog.dismiss();
                            handlerTask(forwordSelectPositon);
                        }
                    }).show();
                }
            }

            @Override
            public void onItemClick(Dialog dialog, YardCntrInfo bean) {

            }
        }).show();
    }


    @Override
    public void onFaile(String msg) {
        resetValue();
        super.onFaile(msg);
    }

    @Override
    public void setTrkList(List<Trk> trkList) {
        mTrkList = trkList;
    }

    @Override
    protected BoxsPresenterImpl createPresenter() {
        return mBoxsPresenterImpl = new BoxsPresenterImpl();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isHandler) {
                Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 重置
     */
    private void resetValue() {
        select = false;
        //恢復成无用状态
        selectPositon = NOTHING_TYPE;
        selectDwxPositon = DEFAULT_TYPE;
        forwordSelectPositon = DEFAULT_TYPE;
        forwordDwxPositon = DEFAULT_TYPE;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getInstance().notifySelect(FileKeyName.CHECKTASKSERVICEMSG);
    }
}
