package com.smt.wxdj.swxdj;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
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
import com.smt.wxdj.swxdj.viewmodel.nbean.ChaneStackInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayCntrInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardTaskInfo;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.smt.wxdj.swxdj.dialog.BayDialog.ALL_STATCK;
import static com.smt.wxdj.swxdj.enums.BOXTYPE.DWX;
import static com.smt.wxdj.swxdj.utils.CellTool.BOX_TYPE_EB;
import static com.smt.wxdj.swxdj.utils.CellTool.BOX_TYPE_EC;


public class MyGridViewActivity extends BaseActivity<BoxsView, BoxsPresenterImpl> implements BoxsView, View.OnClickListener {
    private static final int DEFAULT_TYPE = -1;//???????????????????????????
    private static final int NOTHING_TYPE = -2;//???????????????
    private static final String CHECK = "CHECK";//????????????
    private ScrollView mScrollview;
    private HorizontalScrollView horizontalScrollView;

    private LinearLayout xLayout;
    private RelativeLayout yLayout;
    private LinearLayout getBoxLayout;
    private BoxsPresenterImpl mBoxsPresenterImpl;
    private DragGrid userGridView;//???????????????GRIDVIEW
    private DragAdapter userAdapter;//?????????????????????????????????????????????
    private DragGrid dxwGridView;// ?????????
    private DxwAdapt dxwAdapter;//?????????
    private Toolbar toolbar;
    private TextView toolTitle;//??????
    private Button cdStack;//??????
    private Button twSpinner;//??????
    private Button btnRmdBay;//????????????
    private Button tcSpinner, btnTcOk, btnCc;//
    private final ArrayList<YardCntrInfo> defaultBoxList = new ArrayList<>();
    private final ArrayList<YardCntrInfo> mDxwData = new ArrayList<>();//???????????????
    private int mCol = 0;//gridview ??????
    private int mRow = 0;// gridview ??????
    private YardCntrInfo bean;//????????????
    private YardCntrInfo tempBoxBean;//????????????????????????????????????BOX??????
    private YardCntrInfo getBoxBean;//????????????????????????????????????BOX??????
    private boolean select;//??????????????????????????????
    private int selectPositon = DEFAULT_TYPE;//???????????????????????????????????????
    private int forwordSelectPositon = DEFAULT_TYPE;//?????????????????????
    private int selectDwxPositon = DEFAULT_TYPE;//???????????????????????????????????????
    private int forwordDwxPositon = DEFAULT_TYPE;//???????????????????????????????????????
    private String mStackName = "";//????????????
    private String mBayName = "";//????????????
    private String mBayId = "";//????????????
    private YardBayInfo mBay;//??????
    private YardBayInfo curBay;//????????????????????????????????????
    private boolean isHandler;//?????????????????????????????????????????????????????????
    private BOXCTRLTYPE mBoxCtrlType;//????????????????????????
    private BOXTYPE mBoxType;//????????????
    private boolean isArrange = true;//???????????????
    private boolean mArrangeTag;//??????????????????
    private List<String> mRmList;//?????????
    private boolean isDoRmList;//???????????????????????????????????????
    private Map<String, YardCntrInfo> mBoxMaps;//????????????
    private List<Trk> mTrkList;
    private LinearLayout llBottom;//????????????
    private Button btnRefresh;//??????????????????
    private int cellY;//????????????????????????/tier
    private boolean isStay; // ???????????????????????????????????? ???????????????


    private WorkViewModel workViewModel;
    private YardBayCntrInfo mYardBayCntrInfo;//????????????
    private String mCurCraneId;//???????????????
    private MyApplication app;
    private BayDialog bayDialog;

    private SearchResultBoxDialog searchResultBoxDialog;

    public static void start(Context context, YardCntrInfo yardCntrInfo, String curCraneId) {
        Intent starter = new Intent(context, MyGridViewActivity.class);
        starter.putExtra("bayId", yardCntrInfo.getYardBayId());
        starter.putExtra("curCraneId", curCraneId);
        starter.putExtra("isArrange", false);
        starter.putExtra("boxBean", yardCntrInfo);
        context.startActivity(starter);
    }


    // ????????????????????????
    private int delayTime = 30000;//30?????????????????????
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
        app = (MyApplication) this.getApplication();
        workViewModel = new ViewModelProvider(ViewModelStore::new, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WorkViewModel.class);
        initView();

        if (null != this.getIntent().getExtras()) {
            //??????
//            mYardTaskInfo = (YardTaskInfo) this.getIntent().getExtras().getSerializable("yardTaskInfo");
//            if (null != mYardTaskInfo) {
//                mCntrNo = mYardTaskInfo.getCntr1();
//            }

            bean = (YardCntrInfo) this.getIntent().getExtras().getSerializable("boxBean");
            mBay = (YardBayInfo) this.getIntent().getExtras().getSerializable("bay");
            curBay = mBay;
            tempBoxBean = bean;
            if (null != bean)
                getBoxBean = (YardCntrInfo) bean.clone();
//            LogUtils.sysout("bean:",bean.toString());
            //?????????????????????
            String bayId = this.getIntent().getExtras().getString("bayId");
            mCurCraneId = this.getIntent().getExtras().getString("curCraneId");
            if (!TextUtils.isEmpty(bayId)) {
                mBayId = bayId;
                workViewModel.GetWithCntrByBayId(bayId);
                workViewModel.GetYardBayListByBlockId(bean.getYardBlockId());
                showProgress();
            } else {
                init();
            }
        }
        initData();

        //??????????????????
        workViewModel.getCntrInfoMutableLiveData().observe(this, info -> {
            mYardBayCntrInfo = info;
            init();
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
            if (null != bean && (bean.isPutCntr() || bean.cancleGetCntr() || isArrange)) {
                workViewModel.GetBestCell(bean.getId(), mBayId, TextUtils.isEmpty(bean.getActivity()) ? "CX" : bean.getActivity());
            }
        });

        //????????????
        workViewModel.ErrorMsg.observe(this, str -> {
            hideProgress();
            onFaile(str);
        });

        workViewModel.DoTaskStatus.observe(this, integer -> {
            hideProgress();
            switch (integer.intValue()) {
                case WorkViewModel.UP_BOX:
                    updateUpBoxData();
                    break;
                case WorkViewModel.MOVE_BOX:
                    updateAdapt();
                    break;
                case WorkViewModel.PUT_BOX:
                    updatePutBoxData();
                    break;
                case WorkViewModel.DX_BOX:
                    updateAdapt();
                    break;
                default:
                    break;
            }
        });

        //???????????????
        workViewModel.mRmdLocationInfo.observe(this, rmdLocationInfo -> {
            if (null == rmdLocationInfo) {
                hideProgress();
                return;
            }
            getBoxBean.setYardBlockId(rmdLocationInfo.getYardBolckId());
            getBoxBean.setYardBayId(rmdLocationInfo.getYardBayId());
            getBoxBean.setFromYardBayId(rmdLocationInfo.getYardBayId());
            getBoxBean.setToYardBayId(rmdLocationInfo.getYardBayId());
            bean = (YardCntrInfo) getBoxBean.clone();
            mBayId = rmdLocationInfo.getYardBayId();
            workViewModel.GetWithCntrByBayId(mBayId);
            workViewModel.GetYardBayListByBlockId(rmdLocationInfo.getYardBolckId());
            workViewModel.GetBestCell(bean.getId(), bean.getToYardBayId(), bean.getActivity());
        });


        //?????????????????????
        workViewModel.mRmdCellList.observe(this, stringList -> {
            isDoRmList = false;
            addRmList(stringList);
        });

        //????????????
        workViewModel.mSearchCntrInfo.observe(this, yardCntrInfos -> {
            searchResultBoxDialog.hideProgress();
            searchResultBoxDialog.setSearchResult(yardCntrInfos);
        });

        //??????
        workViewModel.getYardBayInfoList().observe(this, yardBayInfos -> {
            hideProgress();
            if (null != bayDialog && bayDialog.isShowing()) return;
            List<ChaneStackInfo> stackInfoList = app.getKeyListStack(ALL_STATCK);
            if (null != stackInfoList && null != bean) {
                for (ChaneStackInfo stackInfo : stackInfoList) {
                    if (TextUtils.equals(stackInfo.getYardBlockId(), bean.getYardBlockId())) {
                        mStackName = stackInfo.getYardBlock();
                        break;
                    }
                }
            }
            if (null != yardBayInfos && null != bean) {
                for (YardBayInfo bayInfo : yardBayInfos) {
//                    LogUtils.sysout("bayInfo", "" + bayInfo.getId() +"==="+bayInfo.getBay()+ "===" + bean.getToYardBayId());
                    if (TextUtils.equals(bayInfo.getId(), bean.getYardBayId())) {
                        mBayName = bayInfo.getBay();
                    }
                }
            }
            cdStack.setText(String.format(getString(R.string.cd), mStackName));
            twSpinner.setText(String.format(getString(R.string.tw), mBayName));
        });
    }

    /**
     * ?????????VIEW
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
        btnRmdBay = findViewById(R.id.btnRmdBay);
        getBoxLayout = findViewById(R.id.getBoxLayout);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        toolTitle.setOnClickListener(this);
        cdStack.setOnClickListener(this);
        twSpinner.setOnClickListener(this);
        tcSpinner.setOnClickListener(this);
        btnTcOk.setOnClickListener(this);
        btnCc.setOnClickListener(this);
        btnRmdBay.setOnClickListener(this);
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
        //????????????
        if (null != bean) {
            initCntrTaskType();
        } else {//?????????????????????
            mArrangeTag = true;
            isArrange = this.getIntent().getExtras().getBoolean("isArrange");
            //????????????,???????????????6???4???
            toolTitle.setText(isArrange ? getString(R.string.zlCellBox) : getString(R.string.chaochang));
            btnCc.setVisibility(isArrange ? View.GONE : View.VISIBLE);
            btnRefresh.setVisibility(View.GONE);
        }


//        if (null != mBay) {
//            mBoxsPresenterImpl.GetCntrInfoConver(getBoxBean);
//        }

        String value = LruchUtils.getValues(FileKeyName.isStayWorkActivity);
//        if (!TextUtils.isEmpty(value) || "YES".equals(value)) {
        isStay = true;
//        }
        if (isStay && PreferenceUtils.getBoolean(this, SettingConfig.OPEN_TIME_TASK, false)) {
            delayTime = PreferenceUtils.getInt(this, SettingConfig.SETTING_TIME, SettingConfig.TIME_OUT) * 1000;
        }
    }

    private void initCntrTaskType() {
        if (bean.isPutCntr()) {//??????
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
        } else if (bean.isGetCntr()) {//??????
            getBoxLayout.setVisibility(View.VISIBLE);
            mBoxCtrlType = BOXCTRLTYPE.GETBOX;
            toolTitle.setText(getString(R.string.getBoxFormat));
            tcSpinner.setText(String.format(getString(R.string.cztc), getBoxBean.getTrk()));
            bean.setStatus(BoxTool.STATE_PL);
            tempBoxBean.setStatus(BoxTool.STATE_PL);
            getBoxBean.setStatus(BoxTool.STATE_PL);
            mBoxsPresenterImpl.loadTrkList(DataType.TRK, getBoxBean.getTrk());
            btnRefresh.setVisibility(View.VISIBLE);
        } else if (bean.cancleGetCntr()) {//????????????
            getBoxLayout.setVisibility(View.GONE);
            toolTitle.setText(getString(R.string.cancleBox));
            mBoxCtrlType = BOXCTRLTYPE.PUTBOX;
            bean.setSvc_Code(bean.getTrk_Type());//MAR?????????BRG????????????CY?????????CSV???????????????,CP
            bean.setRown(bean.getRown());
            bean.setCell(bean.getiToCell());
            bean.setTier(bean.getiToTier());
//            if (bean.getActivity().equals(BoxTool.CTRL_UPBOX))
//                bean.setActivity(BoxTool.CTRL_GETBOX);
//            else bean.setActivity(BoxTool.CTRL_GETBOXIP);

            switch (bean.getStatus()) {
                case BoxTool.STATE_WD://?????????????????????
                    bean.setStatus(BoxTool.STATE_PW);
                    break;
                case BoxTool.STATE_CP://????????????
                    bean.setStatus(BoxTool.STATE_WD);
                    break;
                default:
                    break;
            }
            tempBoxBean = bean;
            if (null != bean) getBoxBean = (YardCntrInfo) bean.clone();
            btnRefresh.setVisibility(View.GONE);
        } else if (bean.isPJCntr()) {//??????
            toolTitle.setText(getString(R.string.pjBoxFormat));
            select = true;
            mBoxCtrlType = BOXCTRLTYPE.MDXEBOX;
            mBoxType = BOXTYPE.DEFAULT;
            bean.setStatus(BoxTool.STATE_PL);
            tempBoxBean.setStatus(BoxTool.STATE_PL);
            getBoxBean.setStatus(BoxTool.STATE_PL);
            LogUtils.sysout("bean", bean);
            btnRefresh.setVisibility(View.GONE);
            btnRmdBay.setVisibility(View.VISIBLE);
            //?????????????????????????????????
//            if (TextUtils.isEmpty(bean.getYardBayId())) {
//                workViewModel.GetYardLocation(bean.getCntr());
//            }
        } else {
            toolTitle.setText("");
        }

//        mBayName = getBoxBean.getRown();
//        mStackName = getBoxBean.getStack();
        cdStack.setText(String.format(getString(R.string.cd), mStackName));
        twSpinner.setText(String.format(getString(R.string.tw), mBayName));
    }

    private void init() {
        mCol = mYardBayCntrInfo == null ? 6 : (int) mYardBayCntrInfo.getMaxCell();
        mRow = mYardBayCntrInfo == null ? 5 : (int) mYardBayCntrInfo.getMaxTier();
        int itemWidth = (int) this.getResources().getDimension(R.dimen.box_item_width);
        int gridviewWidth = (mCol * (itemWidth + 2));
        //??????????????????
        ViewGroup.LayoutParams params = userGridView.getLayoutParams();
        params.width = gridviewWidth;
//        params.height = mCol * itemWidth + mCol;
        userGridView.setColumnWidth(itemWidth); // ??????????????????
        userGridView.setStretchMode(GridView.NO_STRETCH);
        userGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        userGridView.setNumColumns(mCol);
        userGridView.setLayoutParams(params); // ??????GirdView????????????,?????????????????????

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
        //?????????????????????????????????
        if (!mArrangeTag) {
            mBoxsPresenterImpl.GetSysParam(String.valueOf(LruchUtils.isSwitch(getString(R.string.rm_color_switch))));
        }
    }

    /**
     * ???????????????????????????
     */
    private void initDefaultBoxData() {
        //1.???????????????????????????????????????????????????????????????
        defaultBoxList.clear();
        int count = mCol * mRow;
        for (int i = 0; i < count; i++) {
            String cell = CellTool.getCell(i, mCol, mRow);
            defaultBoxList.add(new YardCntrInfo(cell, CellTool.getCell(), CellTool.getTier(), false));
        }
        userAdapter.setListDate(defaultBoxList);
    }

    /**
     * ??????????????????View
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

        //itemWidth / 6 ??????????????????????????????
        createYLayoutTextView(yLayout, mRow, itemWidth / 6);
        initDaoXiangWei();
    }

    /**
     * ??????????????????View
     */
    private void initDaoXiangWei() {
        mDxwData.clear();
        for (int i = 0; i < mRow; i++)
            mDxwData.add(new YardCntrInfo(CellTool.getDxwCell(i, 1, mRow), BoxTool.CTRL_GETBOX, false));
        if (null == dxwAdapter) dxwAdapter = new DxwAdapt(this);
        dxwAdapter.setListDate(mDxwData);
        dxwGridView.setAdapter(dxwAdapter);
        dxwGridView.setOnItemClickListener((adapterView, view, position, id) -> {
            userAdapter.resetSelectBox();
            if (dxwAdapter.HashBox(position)) {//???????????????????????????????????????????????????,(??????????????????????????????????????????)
                int res = dxwAdapter.exchangeSelect(1, position);
                switch (res) {
                    case CellTool.BOX_TYPE_SUCESS:
                        select = true;
                        bean = dxwAdapter.getItem(position);
                        //????????????????????????????????????
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
                if (select) {//???????????????????????????????????????,??????????????????????????????????????????;????????????
                    forwordDwxPositon = position;
                    if (mBoxType == BOXTYPE.DEFAULT) {
                        mBoxType = BOXTYPE.DEFAULT_TO_DXW;
                    }
                    if (null == tempBoxBean) return;
                    cellY = dxwAdapter.getCellY(tempBoxBean, position, 1);
                    int tier = bean.getCurTier();
                    int cell = bean.getCurCell();
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
        });
    }

    /**
     * ????????????????????????
     *
     * @param position
     */
    private void selectContrller(int position) {
        //?????????????????????
        if (!isArrange) {
            arrangeAction(position);
        } else {//?????????????????????????????????
            if (!select) {
                selectBox(position);
            } else {
                handlerTask(position);
            }
        }
    }

    /**
     * ????????????
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
        forwordSelectPositon = position;//??????????????????????????????????????????
        mBoxType = BOXTYPE.DEFAULT;

        searchResultBoxDialog = new SearchResultBoxDialog(this)
                .setWorkViewModel(workViewModel);
        searchResultBoxDialog.setConfirmClickListener(new SearchResultBoxDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, YardCntrInfo yardCntrInfo) {
                if (null == yardCntrInfo) {
                    onFaile(getString(R.string.please_select_box_info));
                    return;
                }
                dialog.dismiss();
                selectPositon = DEFAULT_TYPE;
                //????????????
                bean = yardCntrInfo;
                bean.setMaj_Loc(MyApplication.MAJLOC);
                bean.setSub_Loc(MyApplication.SUBLOC);
                tempBoxBean = bean;
                if (null != bean)
                    getBoxBean = (YardCntrInfo) bean.clone();
                if (TextUtils.equals(bean.getRown(), CHECK)) {
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
     * ??????????????????
     *
     * @param position
     */
    private void handlerTask(int position) {
        //????????????????????????????????????????????????
        if (selectPositon == position) return;
        if (userAdapter.HashBox(position)) {
            selectBox(position);
            return;
        }
        //???????????????????????????????????????
//        if (null != getBoxBean && !getBoxBean.getRown().equals(mBayName)) {
//            return;
//        }

        //?????????????????????????????????????????????????????????(??????????????????????????????????????????,???????????????false???????????????
        //?????????????????????????????????
        if (null != getBoxBean && getBoxBean.isGetCntr() && !LruchUtils.isSwitch(getString(R.string.open_rm_switch))) {
            return;
        }
        //??????????????????????????????????????????????????????????????????????????????
        if (null != bean && bean.isPJCntr() && !TextUtils.equals(bean.getFromYardBayId(), bean.getToYardBayId())) {
            showAlertDialog("?????????????????????????????????????????????");
            return;
        }

//        LogUtils.e("tag", "mCol:"+mCol+"==mRow:"+mRow+"==position:"+position);
//        LogUtils.e("tag", "?????? bean:"+bean.toString());
        //????????????????????????
        if (userAdapter.isNullLocation(mCol, mRow, position, bean, isArrange)) {
            onFaile(getString(R.string.error_null_location));
            return;
        }
        //????????????????????????
        if (!mArrangeTag && !userAdapter.getRecomLocation(position)) {
            showAlertDialog(getString(R.string.error_rm_location));
            return;
        }

        //????????????????????????????????????????????????????????????????????????????????????????????????
        //1.???????????????????????????????????????????????????????????????????????????????????????;
        if (!userAdapter.hashPutWgtBox(mCol, bean, position)) {
            showPutWgtBoxDialog(position);
        } else {
            showPutBoxConfirm(position);
        }
    }

    /**
     * ?????????????????????????????????
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
        //????????????????????????
        userAdapter.exchangeMoveSelect(position);
        String alertMsg = getString(android.R.string.ok) + ":" + bean.getCntr() + getString(R.string.put_on) + mBayName + "/" + cellX + "/" + cellY + getString(R.string.on);
        onCommitDialog(alertMsg, bean.getCntr(), cellX, cellY);
    }

    /**
     * ????????????????????????
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
                if (!detalBean.getYardBayId().equals(mBayId)) {
                    return;
                }

                bean = userAdapter.getItem(position);
                //????????????????????????????????????????????????
                if (null != getBoxBean && getBoxBean.isPJCntr() && TextUtils.equals(bean.getCntr(), getBoxBean.getCntr())) {
                    return;
                }


                tempBoxBean = (YardCntrInfo) bean.clone();//????????????????????????
                select = true;
                selectPositon = position;//??????????????????????????????????????????
                mBoxType = BOXTYPE.DEFAULT;
                if (null != bean && null != getBoxBean && !bean.getCntr().equals(getBoxBean.getCntr())) {
                    mBoxCtrlType = BOXCTRLTYPE.MOVEBOX;
                }
                break;
            case BOX_TYPE_EB:
                showAlertDialog(getString(R.string.error_msg1));
                break;
            default:
                if (!isHandler) {//????????????????????????????????????
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
                //?????????????????????????????????-????????? ????????????????????????????????????
//                if (bean.isPJCntr()) return;
                //???????????????????????????????????????
//                LogUtils.sysout("=========open_rm_stack_switch", LruchUtils.isSwitch(getString(R.string.open_rm_stack_switch)));
                if (null != bean && bean.isPJCntr()) {
                    onFaile("???????????????????????????");
                    return;
                }
                if (null != getBoxBean && getBoxBean.isGetCntr() && !LruchUtils.isSwitch(getString(R.string.open_rm_stack_switch))) {
                    showAlertDialog(getString(R.string.error_rm_msg));
                    return;
                }
                bayDialog = new BayDialog(this, mStackName, mBayName, null)
                        .setWorkViewModel(workViewModel)
                        .setConfirmClickListener((dialog, stackBean, bay) -> {
                            dialog.dismiss();
                            isDoRmList = false;
                            mStackName = stackBean.getYardBlock();
                            mBay = bay;
                            userAdapter.setBay(bay);
                            mBayName = bay.getBay();
                            mBayId = bay.getId();
                            mCol = (int) mBay.getMaxCell();
                            mRow = (int) mBay.getMaxTier();
                            cdStack.setText(String.format(getString(R.string.cd), mStackName));
                            twSpinner.setText(String.format(getString(R.string.tw), mBayName));
                            selectPositon = DEFAULT_TYPE;
//                                String str = PreferenceUtils.getString(MyGridViewActivity.this, bay.getBay(), null);
//                                if (str == null)
                            if (null != getBoxBean) getBoxBean.setRown(bay.getBay());
                            showProgress();
                            workViewModel.GetWithCntrByBayId(bay.getId());
//                            mBoxsPresenterImpl.CheckMaxCellTier(bay.getBay());
//                                if (null != mRmList) {
//                                    mRmList.clear();
//                                    mRmList = null;
//                                }
                        });
                bayDialog.show();
                break;
            case R.id.tcSpinner:
                if (null == mTrkList) {
                    mBoxsPresenterImpl.loadTrkList(DataType.TRK, MyApplication.user.getCRANE());
                    return;
                }
                new TrkDialog(this, mTrkList).setConfirmClickListener((dialog, bean) -> {
                    tempBoxBean.setTrk(bean.getTrk());
                    getBoxBean.setTrk(bean.getTrk());
                    tcSpinner.setText(String.format(getString(R.string.cztc), getBoxBean.getTrk()));
                    dialog.dismiss();
                }).show();
                break;
            case R.id.btnTcOk://????????????
//                LogUtils.e("tag", "getBoxBean:"+getBoxBean.toString());
//                LogUtils.e("tag", "bean:"+bean.toString());
//                LogUtils.e("tag", "tempBoxBean:"+tempBoxBean.toString());
                if (tempBoxBean.getCntr().equals(getBoxBean.getCntr())) {//???????????????
//                    LogUtils.e("tag", "???????????????...");
//                    onFaile("???????????????.");
                    onGetBoxAlertDialog(getBoxBean, true);
                } else {//????????????
//                    LogUtils.e("tag", "????????????...");
//                    if (!bean.isChange()) {
//                        onFaile(getString(R.string.boxes_are_irreplaceable));
//                        return;
//                    }
//                    if (!bean.isChange() && bean.isRmCntr()) {
//                        onFaile(getString(R.string.the_box_is_illega));
//                        return;
//                    }
//                    bean.setTrk_Type(getBoxBean.getTrk_Type());
                    onGetBoxAlertDialog(bean, false);
                }
                break;
            case R.id.taskTitle:
                if (isHandler)
                    Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));
                finish();
                break;
            case R.id.btnCc://??????
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
            case R.id.bottom_layout://??????????????????
                SettingPatternDialogFragment fragment = new SettingPatternDialogFragment();
                fragment.show(getFragmentManager(), "");
                break;
            case R.id.btnRefresh://??????????????????
                tempBoxBean = getBoxBean;
//                initData();
                workViewModel.GetWithCntrByBayId(mBayId);
                showProgress();
                break;
            case R.id.btnRmdBay://?????????????????????
                if (null == getBoxBean) return;
                int curIndex = -1;
                for (int i = 0; i < userAdapter.getChannelList().size(); i++) {
                    if (TextUtils.equals(userAdapter.getChannelList().get(i).getCntr(), getBoxBean.getCntr())) {
                        curIndex = i;
                    }
                }
                int res = userAdapter.exchangeSelect(mCol, curIndex);
                if (res == BOX_TYPE_EB) {
                    showAlertDialog(getString(R.string.error_msg1));
                    return;
                }
                btnRmdBay.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(getBoxBean.getToYardBayId())) {
                    // ????????????????????????????????????
                    if (!TextUtils.equals(getBoxBean.getFromYardBayId(), getBoxBean.getToYardBayId())) {
                        //??????????????????
                        showProgress();
                        getBoxBean.setYardBlockId(getBoxBean.getToYardBlockId());
                        getBoxBean.setYardBayId(getBoxBean.getToYardBayId());
                        getBoxBean.setFromYardBayId(getBoxBean.getToYardBayId());
                        mBayId = getBoxBean.getToYardBayId();
                        bean = (YardCntrInfo) getBoxBean.clone();
                        tempBoxBean = (YardCntrInfo) getBoxBean.clone();
                        workViewModel.GetWithCntrByBayId(getBoxBean.getToYardBayId());
                        workViewModel.GetYardBayListByBlockId(getBoxBean.getToYardBlockId());
                        select = true;
                    }
                    workViewModel.GetBestCell(getBoxBean.getId(), getBoxBean.getToYardBayId(), getBoxBean.getActivity());
                } else {
                    //???????????????????????????
                    showProgress();
                    workViewModel.GetYardLocation(bean.getId());
                }
                break;
            default:
                break;
        }

    }

    /**
     * ??????????????????????????????
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
     * ???????????????????????????
     *
     * @param position
     */
    private void onCommitDxwDialog(String boxNo, final int position) {
        if (null == boxNo) return;
        new CommitDxwDialog(this, boxNo)
                .setConfirmClickListener((dialog, view) -> {
//                    mBoxsPresenterImpl.loadCheckMoveForCell(tempBoxBean, 0, cellY);
                    workViewModel.GroundConfirm(tempBoxBean.getYardSiteId(),
                            tempBoxBean.getId(),
                            tempBoxBean.getYardBayId(),
                            tempBoxBean.getTrkWorkId(), mCurCraneId, 0, cellY);
                    dialog.dismiss();
                }).setCancelClickListener((dialog, view) -> {
            selectDwxPositon = DEFAULT_TYPE;
            dialog.dismiss();
        }).show();
    }

    /**
     * ?????????????????????????????????
     *
     * @param msg
     * @param boxNo ??????
     * @param cellX ?????????X??????
     * @param cellY ??????Y?????????
     */
    private void onCommitDialog(String msg, String boxNo, final int cellX, final int cellY) {
        new CommitDialog(this, msg, boxNo, cellX, cellY, mBayName)
                .setConfirmClickListener((dialog, view) -> {
//                    mBoxsPresenterImpl.loadCheckMoveForCell(tempBoxBean, cellX, cellY);
                    if (getBoxCtrlType() == BOXCTRLTYPE.PLPUTBOX || getBoxCtrlType() == BOXCTRLTYPE.PUTBOX || getBoxCtrlType() == BOXCTRLTYPE.MDXEBOX) {
                        workViewModel.GroundConfirm(bean.getYardSiteId(),
                                bean.getId(), mBayId,
                                bean.getTrkWorkId(), mCurCraneId, cellX, cellY);
                    } else {
                        workViewModel.PutOtherConfirm(bean.getYardSiteId(), bean.getId(), mBayId, bean.getTrkWorkId(), mCurCraneId, cellX, cellY);
                    }
//                    workViewModel.PutOtherConfirm(bean.getYardSiteId(), bean.getId(), bean.getYardBayId(), mCurCraneId, cellX, cellY);
//                        Toast.makeText(MyGridViewActivity.this, "????????????", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }).setCancelClickListener((dialog, view) -> {
            resetValue();
            select = true;
            dialog.dismiss();
        }).show();
    }

    /**
     * ????????????
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
                        } else if (bean.isCntrReeferAStatus()) {
                            onFaile("\"" + bean.getCntr() + getString(R.string.refrigerator_has_not_been_unplugged));
                        } else {
//                            mBoxsPresenterImpl.isValidOperation(bean);
                            workViewModel.PickupConfirm(bean.getYardSiteId(), bean.getId(), bean.getYardBayId(), bean.getTrkWorkId(), mCurCraneId);
                            showProgress();
                        }
                    } else {
                        //???????????????????????????
                        workViewModel.ExchangeCntr(bean.getCntr(), mCurCraneId, getBoxBean.getTrkWorkId());
//                        mBoxsPresenterImpl.LoadCheckChangeBox();
                    }
                }).show();
    }

    /**
     * ?????????????????????
     */
    private void onChangeBoxAlertDialog(final YardCntrInfo tBean) {
        if (null == tBean) return;
        new ChangeBoxAlertDialog(this, tBean).setConfirmClickListener((dialog, view) -> {
            dialog.dismiss();
//                LogUtils.e("tag", "??????????????????:" + bean.isSWAPFLAG());
            if (userAdapter.AboveCntr(bean)) {
                onFaile("\"" + bean.getCntr() + getString(R.string.the_box_was_pressed_down));
            } else {
                mBoxsPresenterImpl.GetCntrInfoConver(bean);
            }
//                    mBoxsPresenterImpl.LoadCheckChangeBox();
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

    int currentIndex = 0;

    @Override
    public void addList(List<YardCntrInfo> list, Map<String, YardCntrInfo> maps) {
        this.mBoxMaps = maps;
        //3.?????????????????????????????????
        for (int i = 0; i < defaultBoxList.size(); i++) {
            YardCntrInfo dItem = defaultBoxList.get(i);
            YardCntrInfo mItem = maps.get(dItem.getDefaultCell());
            if (null == mItem) continue;
            mItem.setHashBox(true);//?????????????????????
            mItem.setMoveSelect(false);
//            if (TextUtils.equals(mCntrNo, mItem.getCntr())) {
//                bean = mItem;
//
//                bean.setActivity(mYardTaskInfo.getActivity());
////                if (BuildConfig.DEBUG)
////                    bean.setActivity(BoxTool.CTRL_UPBOX);
//
//                bean.setTrk(mYardTaskInfo.getTrkNo());
//                tempBoxBean = bean;
//                if (null != bean) {
//                    getBoxBean = (YardCntrInfo) bean.clone();
//                    userAdapter.setGetBox(getBoxBean);
//                }
//                initCntrTaskType();
//            }
            //??????
            if (getBoxBean != null && getBoxBean.isGetCntr()) {
                //????????????????????????
                mItem.isSameTrk(getBoxBean);
                //??????????????????????????????
                if (mItem.isSameCntr(getBoxBean)) {
                    //??????????????????????????????????????????
//                    getBoxBean.setCell(mItem.getCell());
//                    getBoxBean.setTier(mItem.getTier());
//                    tempBoxBean.setCell(mItem.getCell());
//                    tempBoxBean.setTier(mItem.getTier());
//                    bean.setCell(mItem.getCell());
//                    bean.setTier(mItem.getTier());
                    currentIndex = Integer.parseInt(mItem.getCell());
                }
                //????????????????????????
                mItem.setGetCntrBackGroupColor(getBoxBean, maps);
                boolean b = mItem.getCntr().equals(bean.getCntr());
                if (b) mItem.setTXDefaultBackgroundColor();
                bean.setCell(mItem.getCell());
            } else if (getBoxBean != null && getBoxBean.isPJCntr()) {//??????
                //??????????????????????????????
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

        //????????????????????????
        int length = (int) this.getResources().getDimension(R.dimen.box_item_width);
        horizontalScrollView.scrollTo((currentIndex - 3) * length, 0);

        //??????????????????
        if (!isDoRmList) {
            isDoRmList = userAdapter.enadblAllLocation(mCol, mRow, mBayId, mRmList, bean, mBoxMaps);
        }

        //20191015
        // ?????????????????????????????????????????????
        if (isStay && PreferenceUtils.getBoolean(this, SettingConfig.OPEN_TIME_TASK, false)) {
            mHandler.sendEmptyMessageDelayed(TIMETASK, delayTime);
        }


        //?????????????????????????????????????????????
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
            //????????????????????????
            final List<Integer> beanList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                YardCntrInfo detalBean = list.get(i);
                if (!bean.getCell().equals(detalBean.getCell())) continue;
                beanList.add(Integer.parseInt(detalBean.getTier()));
            }
            //????????????
            int maxTier = 0;
            if (beanList.size() > 0) {
                maxTier = Collections.max(beanList);
            }
            //????????????????????????
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
            //???????????????
            int maxBayCell = Integer.parseInt(mBay.getMax_Cell());
            //???????????????
            int boxCell = Integer.parseInt(bean.getCell());
            int boxTier = Integer.parseInt(bean.getTier());
            //?????? = ???????????????*??????????????????-???????????????+??????????????????-1
            userAdapter.exchangeMoveSelect(maxBayCell * (maxBayTier - boxTier) + boxCell - 1);
        }


    }

    @Override
    public void addDxwList(List<YardCntrInfo> dxwlist, Map<String, YardCntrInfo> maps) {
        //?????????????????????
        for (int i = 0; i < mDxwData.size(); i++) {
            YardCntrInfo dItem = mDxwData.get(i);
            YardCntrInfo temp = maps.get(dItem.getDefaultCell());
            if (null != temp) {
                temp.setHashBox(true);//?????????????????????
                mDxwData.set(i, temp);
            }
        }
        dxwAdapter.setListDate(mDxwData);
        hideProgress();
    }

    /**
     * ???????????????
     *
     * @param rmList
     */
    @Override
    public void addRmList(List<String> rmList) {
        mRmList = rmList;
        if (!isDoRmList) {
            isDoRmList = userAdapter.enadblAllLocation(mCol, mRow, mBayId, rmList, bean, mBoxMaps);
        }
    }

    /**
     * ????????????
     *
     * @param bay
     */
    @Override
    public void addBay(Bay bay) {
//        mBay = bay;
//        mCol = Integer.parseInt(mBay.getMax_Cell());
//        mRow = Integer.parseInt(mBay.getMax_Tier());
//        init();
//
//        mBoxsPresenterImpl.GetCntrListByConditionNotCell(bay.getBay(), bean == null ? "" : bean.getCntr(), false);
//        mBoxsPresenterImpl.GetCntrInfoConver(bean == null ? null : bean);
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public void updateAdapt() {
        int res = -1;
        switch (mBoxType) {
            case DEFAULT:
                LogUtils.sysout("DEFAULT======", "DEFAULT");
                if (selectPositon == DEFAULT_TYPE) {//????????????????????????
                    tempBoxBean.setRown(mBayName);
                    res = userAdapter.exchange(tempBoxBean, forwordSelectPositon, mCol);
                    YardCntrInfo dropItem = userAdapter.getItem(forwordSelectPositon);
                    dropItem.setYardBayId(mBayId);
                    if (res == 0 && getBoxBean != null && tempBoxBean.getCntr().equals(getBoxBean.getCntr())) {
                        isHandler = true;//????????????????????????????????????????????????????????????????????????
//                        mBoxsPresenterImpl.CheckMaxCellTier(mBayName);
                        mArrangeTag = true;
                    }
                } else {//????????????????????????
                    YardCntrInfo dropItem = userAdapter.getItem(forwordSelectPositon);
                    dropItem.setYardBayId(mBayId);
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
                //?????????????????????
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
//        if (!mArrangeTag)
//            mBoxsPresenterImpl.GetSysParam(String.valueOf(LruchUtils.isSwitch(getString(R.string.rm_color_switch))));
        if (null != getBoxBean && getBoxBean.isPJCntr() && getBoxBean.isSameCntr(tempBoxBean)) {
            Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));
            finish();
        }
//        bean = null;
    }

    /**
     * ???????????????????????????
     */
    @Override
    public void updateUpBoxData() {
        userAdapter.exchangeGetBox(getBoxBean);
        isHandler = true;
        mBoxCtrlType = BOXCTRLTYPE.MOVEBOX;
        getBoxBean = null;
        Session.getInstance().notifySelect(new SessionInfo("", SortEt.DOTASK));
        hideProgress();

        if (isStay) {
            finish();
        }
    }

    /**
     * ???????????????????????????
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
     * ????????????
     */
    @Override
    public void putBoxToParkingSpaceResult() {
        userAdapter.resetDefault();
    }

    /**
     * ????????????????????????
     *
     * @param list
     */
    @Override
    public void setSearchResult(List<YardCntrInfo> list) {
        searchResultBoxDialog = new SearchResultBoxDialog(this, list)
                .setWorkViewModel(workViewModel);
        searchResultBoxDialog.setConfirmClickListener(new SearchResultBoxDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, YardCntrInfo yardCntrInfo) {
                if (null == yardCntrInfo) {
                    onFaile(getString(R.string.please_select_box_info));
                    return;
                }
                dialog.dismiss();
                selectPositon = DEFAULT_TYPE;
                //????????????
                bean = yardCntrInfo;
                bean.setMaj_Loc(MyApplication.MAJLOC);
                bean.setSub_Loc(MyApplication.SUBLOC);
                tempBoxBean = bean;
                if (null != bean) {
                    getBoxBean = (YardCntrInfo) bean.clone();
                }
                if (TextUtils.equals(bean.getRown(), "CHECK")) {
                    handlerTask(forwordSelectPositon);
                } else {
                    new ClearBoxDialog(MyGridViewActivity.this, getString(R.string.cc_clear_error))
                            .setConfirmClickListener((dialog1, view) -> {
                                dialog1.dismiss();
                                handlerTask(forwordSelectPositon);
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
     * ??????
     */
    private void resetValue() {
        select = false;
        //?????????????????????
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
