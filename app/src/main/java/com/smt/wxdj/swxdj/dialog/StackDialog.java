package com.smt.wxdj.swxdj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.adapt.StackAdapt;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.boxs.presenter.TaskBoxPresenterImpl;
import com.smt.wxdj.swxdj.boxs.view.TaskBoxView;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.PlayRing;
import com.smtlibrary.dialog.SweetAlertDialog;
import com.smtlibrary.irecyclerview.IRecyclerView;
import com.smtlibrary.irecyclerview.OnRefreshListener;
import com.smtlibrary.irecyclerview.adapter.OnItemClickListener;
import com.smtlibrary.irecyclerview.animation.ScaleInAnimation;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbh on 16/11/2.
 * 场地列表
 */

public class StackDialog extends Dialog implements View.OnClickListener, TaskBoxView<BoxDetalBean> {
    private Context mContext;
    private Button btnCancle;//取消按钮
    private Button btnOk;//确定按钮
    private IRecyclerView myRecyclerView;
    private StackAdapt adapt;
    private List<StackBean> mList;
    private StackBean mStackBean;
    //新增取消提箱和刷新功能
    private Button btCancelBox;     //取消提箱
    private Button btRefresh;       //刷新
    private SweetAlertDialog mProgressDialog;
    private TaskBoxPresenterImpl mBoxsPresenterImpl;
    //新增倒箱功能
    private Button btFallBox;//倒箱
    private boolean isDefault;//系统设置是否有默认参数
    private List<StackBean> mDataList = new ArrayList<>();//临时list，和传进来的list对比


    private OnSweetClickListener mConfirmClickListener;

    public StackDialog(Context context, List<StackBean> list) {
        super(context);
        this.mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public StackDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setNewData(List<StackBean> list){
        if (list.size() > mDataList.size()){
            PlayRing.ring(mContext);
        }
        mDataList.clear();
        mDataList = list;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public StackDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stack_alert_view);
        int width = PreferenceUtils.getInt(mContext, "width", 400);
        int height = PreferenceUtils.getInt(mContext, "height", 400);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin));
        lp.height = height - height / 6;
        dialogWindow.setAttributes(lp);

        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

        //新增取消提箱和刷新功能
        btCancelBox = (Button) findViewById(R.id.btCancelBox);
        btRefresh = (Button) findViewById(R.id.btRefresh);
        btCancelBox.setOnClickListener(this);
        btRefresh.setOnClickListener(this);
        btFallBox = (Button) findViewById(R.id.btFallBox);
        btFallBox.setOnClickListener(this);

        setTitle(mContext.getResources().getString(R.string.select_stack));
        myRecyclerView = (IRecyclerView) findViewById(R.id.recycle_view);

        mBoxsPresenterImpl = new TaskBoxPresenterImpl(this);
        //新添一个标题
        if (mList.size() > 0 && !TextUtils.isEmpty(mList.get(0).getStack()))
            mList.add(0, new StackBean());
        adapt = new StackAdapt(mContext, mList);
        adapt.openLoadAnimation(new ScaleInAnimation());
        myRecyclerView.setAdapter(adapt);
        adapt.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                if (i == 0) return;
                for (StackBean bean : mList) {
                    bean.setSelect(false);
                }
                adapt.notifyDataSetChanged();
                mStackBean = (StackBean) o;
                mStackBean.setSelect(true);
                adapt.notifyItemChanged(i);
                if (!mStackBean.getSTATUS().equals("A"))
                    btnOk.setEnabled(false);
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });
        myRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                mBoxsPresenterImpl.loadBoxCdData(MyApplication.user.getCrn(), isDefault);
            }
        });
        setBefaultSelect();

        isDefault = LruchUtils.isSwitch(mContext.getResources().getString(R.string.open_stack_list_switch));

    }

    /**
     * 选中的场地
     */
    private void setBefaultSelect() {
        for (StackBean bean : mList) {
            if (bean.isSelect()) {
                mStackBean = bean;
                break;
            }
        }
        if (mList.size() == 0) {
            btnOk.setEnabled(false);
            myRecyclerView.setImageResource(R.drawable.ic_no_data);
            myRecyclerView.setNoDataLayout(mContext.getString(R.string.get_data_failure));
        }
    }


    /**
     * 组合获取任务列表,服务端数据库语句
     *
     * @return
     */
    private String exclSql() {
        if (null == mList || mList.size() == 0) return "";
        List<StackBean> tList = new ArrayList<>();
        for (int i = 1; i < mList.size(); i++) {
            tList.add(mList.get(i));
        }
        String FilterStackString = "";
        for (int i = 0; i < tList.size(); i++) {
            StackBean s = tList.get(i);
            if (i == 0)
                FilterStackString = "((RPAD(Y.BAY,8,'0') BETWEEN RPAD('" + s.getOPR_START_ROWN() + "',8,'0')  AND RPAD('" + s.getOPR_END_ROWN() + "',8,'0')) OR (RPAD(Y.BAY,8,'0') BETWEEN RPAD('" + s.getOPR_START_ROWN() + "',8,'0')  AND RPAD('" + s.getOPR_END_ROWN() + "',8,'0')))";//"Y.STACK in (" + RFCWS_CE.Common.Global.ArrayListToString(RFCWS_CE.Common.Global.IarrList) + ")";
            if (i > 0) {
                if (i == 1) {
                    FilterStackString = "(" + FilterStackString;
                }
                FilterStackString += " OR " + "((RPAD(Y.BAY,8,'0') BETWEEN RPAD('" + s.getOPR_START_ROWN() + "',8,'0')  AND RPAD('" + s.getOPR_END_ROWN() + "',8,'0')) OR (RPAD(Y.BAY,8,'0') BETWEEN RPAD('" + s.getOPR_START_ROWN() + "',8,'0')  AND RPAD('" + s.getOPR_END_ROWN() + "',8,'0')))";
                if (i == tList.size() - 1)
                    FilterStackString += ")";
            }
        }
        return FilterStackString;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk://确定
                if (null != mConfirmClickListener)
                    mConfirmClickListener.onClick(this, mStackBean, exclSql());
                else
                    dismiss();
                break;
            case R.id.btnCancle://取消
                dismiss();
                break;
            case R.id.btCancelBox://取消提箱
                if (null != mConfirmClickListener)
                    mConfirmClickListener.onCancelSuitcase(this, mStackBean, exclSql());
                else
                    dismiss();
                break;
            case R.id.btRefresh://刷新
                mProgressDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
                mProgressDialog.setTitleText(mContext.getString(R.string.loading));
                mProgressDialog.show();
                // 获取场地信息
                mBoxsPresenterImpl.loadBoxCdData(MyApplication.user.getCrn(), isDefault);
                break;
            case R.id.btFallBox://倒箱
                if (null != mConfirmClickListener)
                    mConfirmClickListener.onFallBox(this, mStackBean, exclSql());
                else
                    dismiss();
                break;
        }
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog, StackBean bean, String sql);

        void onCancelSuitcase(Dialog dialog, StackBean bean, String sql);

        void onFallBox(Dialog dialog, StackBean bean, String sql);
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
        if (list.size() > mDataList.size()){
            PlayRing.ring(mContext);
        }
        mDataList.clear();
        mDataList = list;
        myRecyclerView.setOnRefresh(false);
        adapt.clear();
        mList.clear();
        mList.add(0, new StackBean());
        adapt = new StackAdapt(mContext, mList);
        adapt.addAll(list);
        if (null != mProgressDialog && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        adapt.openLoadAnimation(new ScaleInAnimation());
        myRecyclerView.setAdapter(adapt);
        adapt.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                if (i == 0) return;
                for (StackBean bean : mList) {
                    bean.setSelect(false);
                }
                adapt.notifyDataSetChanged();
                mStackBean = (StackBean) o;
                mStackBean.setSelect(true);
                adapt.notifyItemChanged(i);
                if (!mStackBean.getSTATUS().equals("A"))
                    btnOk.setEnabled(false);
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });
    }

    @Override
    public void addBay(Bay bay) {
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
}
