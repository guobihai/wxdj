package com.smt.wxdj.swxdj.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.boxs.presenter.BayPresenter;
import com.smt.wxdj.swxdj.boxs.presenter.BayPresenterImpl;
import com.smt.wxdj.swxdj.boxs.view.BayView;
import com.smt.wxdj.swxdj.viewmodel.WorkViewModel;
import com.smt.wxdj.swxdj.viewmodel.nbean.ChaneStackInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayInfo;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.irecyclerview.IRecyclerView;
import com.smtlibrary.irecyclerview.OnRefreshListener;
import com.smtlibrary.irecyclerview.adapter.CommonRecycleViewAdapter;
import com.smtlibrary.irecyclerview.adapter.OnItemClickListener;
import com.smtlibrary.irecyclerview.adapter.ViewHolderHelper;
import com.smtlibrary.irecyclerview.animation.ScaleInAnimation;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbh on 16/11/2.
 * 贝位列表
 */

public class BayDialog extends Dialog implements View.OnClickListener {
    public static final String ALL_STATCK = "AllStatck";
    private Context mContext;
    private Button btnCancle;
    private Button btnOk;
    private IRecyclerView mStackRecyclerView;
    private IRecyclerView mBayRecyclerView;
    private CommonRecycleViewAdapter<YardBayInfo> bayAdapter;
    private List<ChaneStackInfo> mListStack;
    private ChaneStackInfo mStackBean;
    private YardBayInfo mBay;
    private String mCurBayName;//当前田位
    private String mCurStack;//当前场地
    private SweetAlertDialog sweetAlertDialog;
    //    private BayPresenter bayPresenter;
    private MyApplication app;
    private int index;

    private CommonRecycleViewAdapter<ChaneStackInfo> adapter;

    private OnSweetClickListener mConfirmClickListener;

    private WorkViewModel workViewModel;

    public BayDialog setWorkViewModel(WorkViewModel workViewModel) {
        this.workViewModel = workViewModel;
        return this;
    }

    public BayDialog(Context context, String curStack, String curBay, List<ChaneStackInfo> listStack) {
        super(context);
        this.mContext = context;
        this.mListStack = new ArrayList<>();
        this.mCurBayName = curBay;
        this.mCurStack = curStack;
        if (null != listStack) {
            mListStack.addAll(listStack);
            for (int i = 0; i < mListStack.size(); i++) {
                ChaneStackInfo bean = mListStack.get(i);
                if (bean.getYardBlock().equals(curStack)) {
                    bean.setSelect(true);
                    mStackBean = bean;
                    index = i;
                } else
                    bean.setSelect(false);
            }
        }
    }

    public BayDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stack_bay_alert_view);
        int width = PreferenceUtils.getInt(mContext, "width", 400);
        int height = PreferenceUtils.getInt(mContext, "height", 400);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width - width / 3;
        lp.height = height - height / 4;
        dialogWindow.setAttributes(lp);
        app = (MyApplication) ((Activity) mContext).getApplication();
        sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText(mContext.getString(R.string.loading));
//        bayPresenter = new BayPresenterImpl(this);

        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        setTitle(mContext.getResources().getString(R.string.select_bay));
        mStackRecyclerView = (IRecyclerView) findViewById(R.id.stack_recycle_view);
        mBayRecyclerView = (IRecyclerView) findViewById(R.id.bay_recycle_view);

        adapter = new CommonRecycleViewAdapter<ChaneStackInfo>(mContext, R.layout.stack_bay_item_layout,
                mListStack) {
            @Override
            public void convert(ViewHolderHelper holder, ChaneStackInfo bean, int i) {
                TextView title = holder.getView(R.id.stack_title);
                title.setText(bean.getYardBlock());
                if (bean.isSelect()) {
                    title.setBackgroundColor(Color.YELLOW);
                } else {
                    title.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        };
        adapter.openLoadAnimation(new ScaleInAnimation());
        mStackRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                for (StackBean bean : mListStack) {
                    bean.setSelect(false);
                }
                adapter.notifyDataSetChanged();
                mStackBean = (ChaneStackInfo) o;
                mStackBean.setSelect(true);
                adapter.notifyItemChanged(i);

                if (app.getMapBay(mStackBean.getYardBlock()) == null) {
//                    bayPresenter.getSelectedBay(mStackBean.getYardBlock());
                    showProgress();
                    workViewModel.GetYardBayListByBlockId(mStackBean.getYardBlockId());
                } else {
                    bayAdapter.clear();
                    bayAdapter.addAll(app.getMapBay(mStackBean.getYardBlock()));
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });

        bayAdapter = new CommonRecycleViewAdapter<YardBayInfo>(mContext, R.layout.stack_bay_item_layout) {
            @Override
            public void convert(ViewHolderHelper holder, YardBayInfo bay, int i) {
                TextView title = holder.getView(R.id.stack_title);
                title.setText(bay.getBay());
                if (bay.isSelect()) {
                    title.setBackgroundColor(Color.YELLOW);
                } else {
                    title.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        };
        bayAdapter.openLoadAnimation(new ScaleInAnimation());
        mBayRecyclerView.setAdapter(bayAdapter);
        bayAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                for (Bay bay : bayAdapter.getAll()) {
                    bay.setSelect(false);
                }
                bayAdapter.notifyDataSetChanged();
                mBay = (YardBayInfo) o;
                mBay.setSelect(true);
                bayAdapter.notifyItemChanged(i);
                btnOk.setEnabled(true);
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });

        if (app.getKeyListStack(ALL_STATCK) == null) {
            //bayPresenter.getAllStackList();
        } else {
            mListStack.addAll(app.getKeyListStack(ALL_STATCK));
            adapter.notifyDataSetChanged();
            for (int i = 0; i < mListStack.size(); i++) {
                ChaneStackInfo bean = mListStack.get(i);
                if (!TextUtils.isEmpty(mCurStack) && mCurStack.equals(bean.getYardBlock())) {
                    mStackBean = bean;
                    mStackBean.setSelect(true);
                    index = i;
                    break;
                }
            }
        }
        mStackRecyclerView.setSelect(index);
        setBefaultSelect();

        if (null != workViewModel) {
            workViewModel.getYardBayInfoList().observe((LifecycleOwner) mContext, yardBayInfos -> {
                bayAdapter.clear();
                bayAdapter.addAll(yardBayInfos);
                hideProgress();
            });
        }
    }

    /**
     * 选中的场地
     */
    private void setBefaultSelect() {
        if (null == mStackBean) return;
        if (mListStack.size() == 0) {
            btnOk.setEnabled(false);
            mBayRecyclerView.setImageResource(R.drawable.ic_no_data);
            mBayRecyclerView.setNoDataLayout(mContext.getString(R.string.get_data_failure));
        } else {
            if (app.getMapBay(mStackBean.getYardBlock()) == null) {
//                bayPresenter.getSelectedBay(mStackBean.getYardBlock());
                workViewModel.GetYardBayListByBlockId(mStackBean.getYardBlockId());
            } else
                setBayList();
        }

        mStackRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStackRecyclerView.setOnRefresh(false);
            }
        });
        mBayRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBayRecyclerView.setOnRefresh(false);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (null != mConfirmClickListener && null != mBay)
                    mConfirmClickListener.onClick(this, mStackBean, mBay);
                else
                    dismiss();
                break;
            case R.id.btnCancle:
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        for (StackBean bean : mListStack) {
            bean.setSelect(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void showProgress() {
        if (!sweetAlertDialog.isShowing())
            sweetAlertDialog.show();
    }
    public void hideProgress() {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
    }

    /**
     * 场地数据
     *
     * @param list
     */
//    @Override
    public void addListCd1(List<ChaneStackInfo> list) {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
        this.mListStack = list;
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        app.putStackMap(ALL_STATCK, mListStack);
        if (TextUtils.isEmpty(mCurStack)) return;
        //定位到当前场地
        for (int i = 0; i < list.size(); i++) {
            ChaneStackInfo bean = list.get(i);
            if (mCurStack.equals(bean.getYardBlock())) {
                bean.setSelect(true);
                mStackBean = bean;
                index = i;
                break;
            }
        }
        mStackRecyclerView.setSelect(index);
        if (app.getMapBay(mStackBean.getYardBlock()) == null) {
//            bayPresenter.getSelectedBay(mStackBean.getYardBlock());
            workViewModel.GetYardBayListByBlockId(mStackBean.getYardBlockId());
        } else {
            List<String> dbay = new ArrayList<>();
            List<YardBayInfo> bayList = app.getMapBay(mStackBean.getYardBlock());
            for (int i = 0; i < bayList.size(); i++) {
                dbay.add(bayList.get(i).getBay());
            }
            addListBay(dbay);
        }
    }

    /**
     * 田位数据
     *
     * @param dbay
     */
    public void addListBay(List<String> dbay) {
        if (sweetAlertDialog.isShowing())
            sweetAlertDialog.dismiss();
        bayAdapter.clear();
        List<YardBayInfo> listBay = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < dbay.size(); i++) {
            String s = dbay.get(i);
            YardBayInfo b = new YardBayInfo();
            b.setBay(s);
            if (s.equals(mCurBayName)) {
                b.setSelect(true);
                mBay = b;
                index = i;
            } else
                b.setSelect(false);
            listBay.add(b);
        }
        app.putBayMap(mStackBean.getYardBlock(), listBay);
        bayAdapter.addAll(listBay);
        mBayRecyclerView.setSelect(index);
    }

    private void setBayList() {
        int pos = 0;
        List<YardBayInfo> listBay = app.getMapBay(mStackBean.getYardBlock());
        if (null == listBay) return;
        for (int i = 0; i < listBay.size(); i++) {
            Bay b = listBay.get(i);
            if (b.getBay().equals(mCurBayName)) {
                pos = i;
                b.setSelect(true);
            } else
                b.setSelect(false);
        }
        bayAdapter.addAll(listBay);
        mBayRecyclerView.setSelect(pos);
    }


    public void onFaile(String msg) {
        sweetAlertDialog.dismiss();
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(msg)
                .setConfirmText(mContext.getString(android.R.string.ok))
                .show();
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog, ChaneStackInfo stackBean, YardBayInfo bay);
    }
}
