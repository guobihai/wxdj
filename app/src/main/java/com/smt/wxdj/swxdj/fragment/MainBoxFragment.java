package com.smt.wxdj.swxdj.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.smt.wxdj.swxdj.BuildConfig;
import com.smt.wxdj.swxdj.MainActivity;
import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.MyGridViewActivity;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.adapt.BoxDetailAdapt;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.boxs.presenter.TaskBoxPresenterImpl;
import com.smt.wxdj.swxdj.boxs.view.TaskBoxView;
import com.smt.wxdj.swxdj.dialog.BayDialog;
import com.smt.wxdj.swxdj.dialog.CancleBoxDialog;
import com.smt.wxdj.swxdj.dialog.StackDialog;
import com.smt.wxdj.swxdj.session.Session;
import com.smt.wxdj.swxdj.session.SessionInfo;
import com.smt.wxdj.swxdj.session.SortEt;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.DisplayUtil;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.PlayRing;
import com.smt.wxdj.swxdj.utils.SettingConfig;
import com.smt.wxdj.swxdj.utils.SortType;
import com.smt.wxdj.swxdj.view.MyRecyclerView;
import com.smt.wxdj.swxdj.viewmodel.WorkViewModel;
import com.smt.wxdj.swxdj.viewmodel.nbean.ChaneStackInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardTaskInfo;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_DCBOAT;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_FALLBOX;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_LDBOAT;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_ZXBOAT;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_ZXC;
import static com.smt.wxdj.swxdj.utils.BoxTool.CTRL_GETBOX;
import static com.smt.wxdj.swxdj.utils.BoxTool.CTRL_GETBOXIP;
import static com.smt.wxdj.swxdj.utils.BoxTool.CTRL_PUTBOX;
import static com.smt.wxdj.swxdj.utils.BoxTool.CTRL_PUTBOXIG;

/**
 * Created by gbh on 16/6/21.
 * ??????????????????
 */
public class MainBoxFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TaskBoxView<YardTaskInfo>, Observer {
    private static final int TIMETASK = 1;
    private static final int STARTAC = 2;
    MyRecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager mLayoutManager;
    private BoxDetailAdapt boxDetailAdapt;

    private SweetAlertDialog mProgressDialog;
    private YardTaskInfo yardTaskInfo;
    private String sType = "all";
    private String sortType = "all";
    private String spuType = "AllBox";

    public static List<YardTaskInfo> mData = new ArrayList<>();
    public static int mType;//??????
    public static String mTaskSql;//
    //?????????????????????
    public static ChaneStackInfo mStackBean;
    public static String mCurrentStack;


    private int delayTime = 30000;//30?????????????????????
    private boolean isStart;//??????????????????
    private List<StackBean> listStack;
    private StackDialog mStackDialog;

    private MyApplication app;
    private TaskBoxPresenterImpl mBoxsPresenterImpl;
    private String searchKey;
    private boolean isSearch;
    private WorkViewModel workViewModel;

    //?????????????????????
    private boolean isDefault;//?????????????????????????????????


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == TIMETASK) {
                if (getUserVisibleHint()) {
                    if (mStackDialog != null && mStackDialog.isShowing()) {
//                        mBoxsPresenterImpl.loadBoxCdData(MyApplication.user.getCrn(), isDefault);
//                        loadStackInfoo();
                        if (TextUtils.isEmpty(mCurCraneId)) return false;
                        workViewModel.getJobTicketTaskStack(mCurCraneId);
                    }else{
                        onRefresh();
                    }
                }
                mHandler.sendEmptyMessageDelayed(TIMETASK, delayTime);
            } else if (msg.what == STARTAC) {
                isStart = false;
            }
            return false;
        }
    });
    public static String mCurCraneId;//????????????ID
    public static String mYardSiteId;//??????ID


    public static MainBoxFragment newInstance(int type) {
        MainBoxFragment fragment = new MainBoxFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getActivity().getApplication();
        Session session = Session.getInstance();
        session.deleteObserver(this);
        session.addObserver(this);
        mType = this.getArguments().getInt("type");

        mProgressDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.setTitleText(getString(R.string.loading));
        mBoxsPresenterImpl = new TaskBoxPresenterImpl(this);
        isDefault = LruchUtils.isSwitch(getString(R.string.open_stack_list_switch));

//        mBoxsPresenterImpl.loadBoxCdData(MyApplication.user.getCrn(), isDefault);
        workViewModel = new ViewModelProvider(ViewModelStore::new, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(WorkViewModel.class);
        workViewModel.getCurMachineNo();

        //????????????
        workViewModel.getMachineList().observe(this, nMachineInfos -> {
            if (null != nMachineInfos && nMachineInfos.size() > 0) {
                mCurCraneId = nMachineInfos.get(0).getCraneId();
                mYardSiteId = nMachineInfos.get(0).getYardSiteId();
            }
//            mCurCraneId = "39faf629-ef1f-85b9-4c9a-c376ffc48804";
            loadStackInfoo();
            hideProgress();
        });

        //??????????????????
        workViewModel.getChaneStackInfoList().observe(this, chaneStackInfos -> {
            selectStack(chaneStackInfos);
//            app.putStackMap(BayDialog.ALL_STATCK, chaneStackInfos);
            hideProgress();
        });

        workViewModel.ALlChaneStackInfoList.observe(this, chaneStackInfos -> {
            app.putStackMap(BayDialog.ALL_STATCK, chaneStackInfos);
        });

        //????????????
        workViewModel.getYardCntrInfoList().observe(this, yardTaskInfos -> {
            addList(yardTaskInfos);
            hideProgress();
        });

        //????????????
        workViewModel.getCntrInfoMutableLiveData().observe(this, yardBayCntrInfo -> {
            hideProgress();
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.recycleview_layout, null);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.recycle_view);

        mSwipeRefreshWidget.setSize(SwipeRefreshLayout.LARGE);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.accent, R.color.primary_light, R.color.primary_dark);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);

        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
        int length = (int) getActivity().getResources().getDimension(R.dimen.recyclview_width);
        params.width = DisplayUtil.dip2px(getActivity(), length);
        mRecyclerView.setLayoutParams(params);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        boxDetailAdapt = new BoxDetailAdapt(getActivity(), mType);
        mRecyclerView.setAdapter(boxDetailAdapt);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisiblesItems == 0) {
                    mSwipeRefreshWidget.setEnabled(true);
                } else
                    mSwipeRefreshWidget.setEnabled(false);
            }
        });


        boxDetailAdapt.setOnItemClickListener((view1, position) -> {
            yardTaskInfo = boxDetailAdapt.getItem(position);
            if (null == yardTaskInfo) return;
            switch (mType) {
                case MainActivity.TASKTYPE_CANCLEBOX://????????????
                    if (yardTaskInfo.getStatus().equals("WD") || yardTaskInfo.getStatus().equals("CP"))
                        showDialog(String.valueOf(yardTaskInfo.getCntr()));
                    break;
                default:
//                    String str = PreferenceUtils.getString(getActivity(), yardTaskInfo.getYardBayId(), null);
//                    if (str == null) {
//                        workViewModel.GetWithCntrByBayId("39fb7959-6e6b-099a-415e-16f54a658bc2");
//                        showProgress(1);
                    LogUtils.sysout("cntrId:", yardTaskInfo.getCntr());
                    MyGridViewActivity.start(getActivity(), yardTaskInfo.toYardCntrInfo(mYardSiteId), mCurCraneId);
//                    } else {
                    //20190118 ???????????????????????????????????????????????????
//                        Intent it = new Intent(getActivity(), MyGridViewActivity.class);
//                        it.putExtra("boxBean", yardTaskInfo.toYardCntrInfo(mYardSiteId));
//                        it.putExtra("bay", deserialize(str, Bay.class));
//                        startActivityForResult(it, FileKeyName.resultCode);

//                    }
                    break;
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (PreferenceUtils.getBoolean(getActivity(), SettingConfig.OPEN_TIME_TASK, false)) {
            delayTime = PreferenceUtils.getInt(getActivity(), SettingConfig.SETTING_TIME, SettingConfig.TIME_OUT) * 1000;
            mHandler.sendEmptyMessageDelayed(TIMETASK, delayTime);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (PreferenceUtils.getBoolean(getActivity(), SettingConfig.OPEN_TIME_TASK, false))
            mHandler.removeMessages(TIMETASK);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && getUserVisibleHint()) {
//            onRefresh();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.ab_search).getActionView();
        ImageView search_button = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        search_button.setImageResource(R.mipmap.search_icon);
        //?????????TextView?????????
        TextView textView = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //?????????????????????14sp
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DisplayUtil.px2sp(getActivity(), getActivity().getResources().getDimension(R.dimen.textsize28)));//14sp
        textView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setQueryHint(getString(R.string.the_trailer_and_box_number_are_three_places_behind));
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LogUtils.sysout("query:", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LogUtils.sysout("query0: newText", newText);
                if (newText.equals("")) {
                    boxDetailAdapt.setData(mData);
                    searchKey = "";
                    isSearch = false;
                } else {
                    isSearch = true;
                    searchKey = newText;
                    boxDetailAdapt.setData(goIntoBoxNo(newText));
                }
                return false;
            }
        });
    }

    /**
     * ??????????????? AlertDialog
     */
    private void showDialog(String boxNO) {
        new CancleBoxDialog(getActivity(), boxNO)
                .setConfirmClickListener((dialog, view) -> {
                    isStart = true;
                    mHandler.removeMessages(TIMETASK);
                    mHandler.sendEmptyMessageDelayed(STARTAC, 2000);
//                    String str = PreferenceUtils.getString(getActivity(), yardTaskInfo.getRown(), null);
//                    if (TextUtils.isEmpty(str))
//                        mBoxsPresenterImpl.CheckMaxCellTier(yardTaskInfo.getRown());
//                    else {
//                        Bay bay = JsonUtils.deserialize(str, Bay.class);
//                        Intent it = new Intent(getActivity(), MyGridViewActivity.class);
//                        it.putExtra("boxBean", yardTaskInfo.toYardCntrInfo(mYardSiteId));
//                        it.putExtra("bay", bay);
                    MyGridViewActivity.start(getActivity(), yardTaskInfo.toYardCntrInfo(mYardSiteId), mCurCraneId);
//                        startActivityForResult(it, FileKeyName.resultCode);
//                    }
                    dialog.dismiss();
                }).show();

    }

    @Override
    public void onRefresh() {
//        showProgress(1);
        if (null == mStackBean) return;
        String taskType = "";
        switch (mType) {
            case MainActivity.TASKTYPE_ALL://?????????/???
//                mBoxsPresenterImpl.loadBoxs(mTaskSql, isDefault, taskType);
                workViewModel.GetTrkWorkByBlockId(mStackBean.getYardBlockId(), "");
                break;
            case TASKTYPE_ZXBOAT://?????????
                taskType = "VSL";
//                mBoxsPresenterImpl.loadBoxs(mTaskSql, isDefault, taskType);
                break;
            case TASKTYPE_LDBOAT://??????
                taskType = "LD";
//                mBoxsPresenterImpl.loadBoxs(mTaskSql, isDefault, taskType);
                break;
            case TASKTYPE_DCBOAT://??????
                taskType = "DC";
//                mBoxsPresenterImpl.loadBoxs(mTaskSql, isDefault, taskType);
                break;
            case TASKTYPE_ZXC://?????????
                taskType = "CY";
//                mBoxsPresenterImpl.loadBoxs(mTaskSql, isDefault, taskType);
                break;
            case MainActivity.TASKTYPE_CANCLEBOX://??????????????????
//                mBoxsPresenterImpl.FindCancel_WorkList(mTaskSql, isDefault);

                workViewModel.GetTrkWorkIsUPByBlockId(mStackBean.getYardBlockId());
                break;
            case TASKTYPE_FALLBOX://????????????
//                LogUtils.e("tag", "TASKTYPE_FALLBOX mStackBean:" + mStackBean);
                if (isDefault && null == mStackBean) {
                    hideProgress();
                    return;
                }
//                mBoxsPresenterImpl.fallBoxData(mStackBean, isDefault);

                workViewModel.GetTrkWorkIsCTCByBlockId(mStackBean.getYardBlockId());
                break;
        }

    }


    @Override
    public User getUser() {
        return MyApplication.user;
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    @Override
    public String getCurStack() {
        return mCurrentStack;
    }

    @Override
    public void showProgress(int type) {
        switch (type) {
            case 0:
                mSwipeRefreshWidget.post(() -> mSwipeRefreshWidget.setRefreshing(true));
                break;
            case 1:
                if (null != mProgressDialog && !mProgressDialog.isShowing())
                    mProgressDialog.show();
                break;
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
        if (null != mProgressDialog && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    /**
     * ????????????
     *
     * @param list
     */
    @Override
    public void addList(List list) {
        if (isSearch) return;
        //??????????????????????????????????????????
        if (!BuildConfig.DEBUG) {
            if (!compare(mData, list) && !LogUtils.LOG_DEBUG) {
                PlayRing.ring(getActivity());
            }
        }

        mData.clear();
        mData.addAll(list);
//        boxDetailAdapt.setData(mData);
        boxDetailAdapt.setData(filterData());
        LogUtils.e("addList", list.size() + "");
    }


    /**
     * ??????????????????????????????
     *
     * @param a
     * @param b
     * @return
     */
    private boolean compare(List<YardTaskInfo> a, List<YardTaskInfo> b) {
        if (a.size() < b.size())
            return false;
        return true;
    }


    /**
     * ????????????
     *
     * @param list
     */
    @Override
    public void addListCd(List<StackBean> list) {
        listStack = list;
//        app.putStackMap(MyApplication.user.getCrn(), listStack);
//        selectStack(listStack);
    }

    /**
     * ????????????
     *
     * @param bay
     */
    @Override
    public void addBay(Bay bay) {
        PreferenceUtils.putString(getActivity(), yardTaskInfo.getRown(), bay.toString());
        Intent it = new Intent(getActivity(), MyGridViewActivity.class);
        it.putExtra("boxBean", yardTaskInfo);
        it.putExtra("bay", bay);
        startActivityForResult(it, FileKeyName.resultCode);
    }

    /**
     * ?????????????????????
     *
     * @param list
     */
    @Override
    public void unLockStackList(List<StackBean> list) {
        if (null != mStackDialog && mStackDialog.isShowing())
            mStackDialog.dismiss();
        onRefresh();
    }

    /**
     * ?????????????????????
     */
    @Override
    public void lockStack() {
        onFaile(getString(R.string.the_site_has_been_locked_up));
    }

    @Override
    public void putBoxForCellData(Object object) {
        if (null == object) {
            onRefresh();
            return;
        }
    }


    /**
     * ????????????
     *
     * @param list
     */
    private void selectStack(List<ChaneStackInfo> list) {

        if (null == mStackDialog || !mStackDialog.isShowing()) {
            mStackDialog = new StackDialog(getActivity());
        }
        mStackDialog.setNewData(list);
        if (!mStackDialog.isShowing())
            mStackDialog.show();
        mStackDialog.setConfirmClickListener(new StackDialog.OnSweetClickListener() {
            @Override
            public void onClick(Dialog dialog, ChaneStackInfo bean, String sql) {
                if (bean == null) {
                    onFaile(getString(R.string.please_choose_the_venue));
                    return;
                }
                mCurCraneId = bean.getCraneId();
                mYardSiteId = bean.getYardSiteId();
                mType = MainActivity.TASKTYPE_ALL;
//                bean.setMaj_Loc(MyApplication.MAJLOC);
//                bean.setSub_Loc(MyApplication.SUBLOC);
//                mTaskSql = sql;
//                mCurrentStack = bean.getStack();
//                mBoxsPresenterImpl.CheckStackIsLock("'" + bean.getStack() + "'");
//                mBoxsPresenterImpl.UpdateCraneRow(bean);
                showProgress(1);
                workViewModel.GetTrkWorkByBlockId(bean.getYardBlockId(), "");
                mStackDialog.dismiss();
                mStackBean = bean;
//                workViewModel.GetYardBlockListBySiteId(mStackBean.getYardSiteId(), false, false);
                Session.getInstance().notifySelect(FileKeyName.CONFIRMBOX);
            }

            @Override
            public void onCancelSuitcase(Dialog dialog, ChaneStackInfo bean, String sql) {
                if (bean == null) {
                    onFaile(getString(R.string.please_choose_the_venue));
                    return;
                }
                mType = MainActivity.TASKTYPE_CANCLEBOX;

//                mTaskSql = sql;
//                if (null != bean) {
//                    bean.setMaj_Loc(MyApplication.MAJLOC);
//                    bean.setSub_Loc(MyApplication.SUBLOC);
//                    mCurrentStack = bean.getStack();
//                    mBoxsPresenterImpl.CheckStackIsLock("'" + bean.getStack() + "'");
//                    mBoxsPresenterImpl.UpdateCraneRow(bean);
//                    mStackBean = bean;
//                } else {
//                    mTaskSql = null;
//                }
                mCurCraneId = bean.getCraneId();
                mYardSiteId = bean.getYardSiteId();
                mStackBean = bean;
                showProgress(1);
                workViewModel.GetTrkWorkIsUPByBlockId(bean.getYardBlockId());
                mStackDialog.dismiss();
                Session.getInstance().notifySelect(FileKeyName.CANCELBOX);
            }

            @Override
            public void onFallBox(Dialog dialog, ChaneStackInfo bean, String sql) {
//                mTaskSql = sql;
                mType = TASKTYPE_FALLBOX;
                if (bean == null) {
                    onFaile(getString(R.string.please_choose_the_venue));
                    return;
                }
//                mType = TASKTYPE_FALLBOX;
//                bean.setMaj_Loc(MyApplication.MAJLOC);
//                bean.setSub_Loc(MyApplication.SUBLOC);
//                mCurrentStack = bean.getStack();
//                mBoxsPresenterImpl.CheckStackIsLock("'" + bean.getStack() + "'");
//                mBoxsPresenterImpl.UpdateCraneRow(bean);
//                mStackDialog.dismiss();
                mStackBean = bean;
                mStackDialog.dismiss();
                mCurCraneId = bean.getCraneId();
                mYardSiteId = bean.getYardSiteId();
                showProgress(1);
                workViewModel.GetTrkWorkIsCTCByBlockId(bean.getYardBlockId());
//                workViewModel.GetYardBlockListBySiteId(mStackBean.getYardSiteId(),false,false);
//                workViewModel.GetYardBayListByBlockId("39fb7959-6e5f-8549-fa18-ec69cc317f00");
//                workViewModel.GetListByBayId(mStackBean.getCurrentBayId());

                Session.getInstance().notifySelect(FileKeyName.FALLBOX);
            }

            @Override
            public void onReflashData() {
                showProgress(1);
                loadStackInfoo();
            }
        });

    }

    private void loadStackInfoo() {
        if (TextUtils.isEmpty(mCurCraneId)) return;
        workViewModel.getJobTicketTaskStack(mCurCraneId);
        workViewModel.getJobTicketListStackByCraneId(mCurCraneId);

    }


    @Override
    public void onFaile(String msg) {
        if (mSwipeRefreshWidget.isRefreshing())
            mSwipeRefreshWidget.setRefreshing(false);
        hideProgress();
        showAlertDialog(msg);
    }

    /**
     * ????????????
     *
     * @param msg
     */
    public void showAlertDialog(String msg) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_view, null);
        TextView message = (TextView) view.findViewById(R.id.message);
        Button btnCancle = (Button) view.findViewById(R.id.btnCancle);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancle.setVisibility(View.GONE);
        message.setText(msg);
        message.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        final AlertDialog showAlertDialog = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.alert)).setCancelable(false).setView(view).show();
        btnOk.setOnClickListener(view1 -> showAlertDialog.dismiss());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof String) {
            sType = (String) o;
            if (sType.equals("stack")) {//????????????
                if (getUserVisibleHint()) {
                    loadStackInfoo();
                }
            } else if (sType.equals(FileKeyName.TASKTYPE_CANCLEBOX)) {
                mType = MainActivity.TASKTYPE_CANCLEBOX;
                onRefresh();
            } else if (sType.equals(FileKeyName.TASKTYPE_ALL)) {
                mType = MainActivity.TASKTYPE_ALL;
                onRefresh();
            } else if (sType.equals(FileKeyName.TASKTYPE_FALLBOX)) {
                mType = TASKTYPE_FALLBOX;
                onRefresh();
            } else if (sType.equals(FileKeyName.TASKTYPE_ZXBOAT)) {
                mType = TASKTYPE_ZXBOAT;
                onRefresh();
            } else if (sType.equals(FileKeyName.TASKTYPE_ZXC)) {
                mType = TASKTYPE_ZXC;
                onRefresh();
            } else if (sType.equals(FileKeyName.TASKTYPE_LDBOAT)) {
                mType = TASKTYPE_LDBOAT;
                onRefresh();
            } else if (sType.equals(FileKeyName.TASKTYPE_DCBOAT)) {
                mType = TASKTYPE_DCBOAT;
                onRefresh();
            } else if (sType.equals(FileKeyName.CHECKTASKSERVICEMSG)) {
                boxDetailAdapt.setData(filterData());
            }
        } else {
            SessionInfo info = (SessionInfo) o;
            if (info.getSortEt() == SortEt.FE) {
                sortType = info.getSortType();
                boxDetailAdapt.setData(filterData());
            } else if (info.getSortEt() == SortEt.DOTASK) {
                boxDetailAdapt.removeObj(yardTaskInfo);
                mData.remove(yardTaskInfo);
                if (!TextUtils.isEmpty(searchKey) && isSearch) {
                    boxDetailAdapt.setData(goIntoBoxNo(searchKey));
                    return;
                }
                boxDetailAdapt.notifyDataSetChanged();
                onRefresh();
            } else {
                spuType = info.getSortType();
                boxDetailAdapt.setData(filterData());
            }
        }

    }

    /**
     * ??????
     *
     * @return
     */
    private List<YardTaskInfo> filterData() {
        List<YardTaskInfo> tList = new ArrayList<>();
        if (sortType.equals(SortType.ALL) && spuType.equals(SortType.AllBox)) {
            tList.addAll(mData);
            return tList;
        }
        for (YardTaskInfo bean : mData) {
            if (sortType.equals(SortType.ALL) && spuType.equals(SortType.PutBox)) {
                if (bean.getActivity().equals(CTRL_PUTBOX) || bean.getActivity().equals(CTRL_PUTBOXIG))
                    tList.add(bean);
            } else if (sortType.equals(SortType.ALL) && spuType.equals(SortType.UpBox)) {
                if (bean.getActivity().equals(CTRL_GETBOXIP) ||
                        bean.getActivity().equals(CTRL_GETBOX))
                    tList.add(bean);
            } else if (sortType.equals(SortType.EBox) && spuType.equals(SortType.AllBox)) {
                if (bean.getFeInd().equals(BoxTool.E))
                    tList.add(bean);
            } else if (sortType.equals(SortType.EBox) && spuType.equals(SortType.PutBox)) {
                if (bean.getFeInd().equals(BoxTool.E) && (bean.getActivity().equals(CTRL_PUTBOX) ||
                        bean.getActivity().equals(CTRL_PUTBOXIG)))
                    tList.add(bean);
            } else if (sortType.equals(SortType.EBox) && spuType.equals(SortType.UpBox)) {
                if (bean.getFeInd().equals(BoxTool.E) && (bean.getActivity().equals(CTRL_GETBOXIP) ||
                        bean.getActivity().equals(CTRL_GETBOX)))
                    tList.add(bean);
            } else if (sortType.equals(SortType.FBox) && spuType.equals(SortType.AllBox)) {
                if (bean.getFeInd().equals(BoxTool.F))
                    tList.add(bean);
            } else if (sortType.equals(SortType.FBox) && spuType.equals(SortType.PutBox)) {
                if (bean.getFeInd().equals(BoxTool.F) && (bean.getActivity().equals(CTRL_PUTBOX) ||
                        bean.getActivity().equals(CTRL_PUTBOXIG)))
                    tList.add(bean);
            } else if (sortType.equals(SortType.FBox) && spuType.equals(SortType.UpBox)) {
                if (bean.getFeInd().equals(BoxTool.F) && (bean.getActivity().equals(CTRL_GETBOXIP) ||
                        bean.getActivity().equals(CTRL_GETBOX)))
                    tList.add(bean);
            }

        }
        return tList;
    }


    /**
     * ??????????????????
     *
     * @param boxNo
     */
    private List<YardTaskInfo> goIntoBoxNo(String boxNo) {
        List<YardTaskInfo> tempData = new ArrayList<>();
        for (YardTaskInfo bean : mData) {
            if (bean.getCntr().contains(boxNo.toUpperCase()) ||
                    bean.getCntr().contains(boxNo) ||
                    bean.getCntr().endsWith(boxNo) ||
                    bean.getTrkNo().contains(boxNo.toUpperCase()) ||
                    bean.getTrkNo().contains(boxNo) ||
                    bean.getTrkNo().endsWith(boxNo)) {
                tempData.add(bean);
            }
        }
        return tempData;
    }

    @Override
    public void onDestroy() {
        mHandler.removeMessages(TIMETASK);
        Session.getInstance().clear(this);
        if (null != mStackDialog) mStackDialog.dismiss();
        super.onDestroy();
    }


}
