package com.smt.wxdj.swxdj.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.MyGridViewActivity;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.adapt.SearchResultAdapt;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.boxs.presenter.SearchPresenterImpl;
import com.smt.wxdj.swxdj.boxs.view.SearchView;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smtlibrary.dialog.SweetAlertDialog;

import java.util.List;


/**
 * Created by gbh on 16/11/2.
 * 模糊搜索箱子结果显示
 */

public class SearchResultBoxDialog extends Dialog implements View.OnClickListener,
        SearchView, SearchResultAdapt.OnItemClickListener {
    private static final String ISORDERLY = "Y";//是否有序的箱子
    private static final String SWAPFLAG = "1";//交换箱标志
    private Context mContext;
    private TextView cnteText;

    private Button btnCancle;
    private Button btnOk;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout bottom_layout;
    private SearchResultAdapt adapt;
    private BoxDetalBean mBoxDetalBean;
    private BoxDetalBean mOldBoxDetalBean;
    private OnSweetClickListener mConfirmClickListener;
    private StringBuffer cnter = new StringBuffer(5);//存储输入的箱号
    private Vibrator mVibrator;
    private SweetAlertDialog dialog;
    private SearchPresenterImpl searchPresenter;
    private boolean isGetBox;//是否提箱
    private Handler handler = new Handler();
    private int maxLength;


    public SearchResultBoxDialog(Context context) {
        super(context, R.style.AppTheme_Dialog_Fullscreen);
        this.mContext = context;
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        adapt = new SearchResultAdapt(mContext, isGetBox);
    }

    public SearchResultBoxDialog(Context context, BoxDetalBean boxDetalBean, boolean isGetBox) {
        super(context, R.style.AppTheme_Dialog_Fullscreen);
        this.mContext = context;
        this.mOldBoxDetalBean = boxDetalBean;
        this.isGetBox = isGetBox;
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        adapt = new SearchResultAdapt(mContext, isGetBox);
        if (isGetBox) {
            mOldBoxDetalBean.setSelectedBox(true);
            adapt.addBean(mOldBoxDetalBean);
        }
    }

    public SearchResultBoxDialog(Context context, List<BoxDetalBean> list) {
        super(context, R.style.AppTheme_Dialog_Fullscreen);
        this.mContext = context;
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        adapt = new SearchResultAdapt(mContext, false);
        adapt.setData(list);
    }

    public SearchResultBoxDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_search_layout);
        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        cnteText = (TextView) findViewById(R.id.txt_cntr);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_d).setOnClickListener(this);
        findViewById(R.id.btn_t).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);

        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        setTitle(mContext.getResources().getString(R.string.confimToast));
        bottom_layout.setVisibility(isGetBox ? View.GONE : View.VISIBLE);
        myRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        myRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.setAdapter(adapt);
        adapt.setOnItemClickListener(this);
        searchPresenter = new SearchPresenterImpl(this);

        String length = LruchUtils.getValues(mContext.getString(R.string.search_cntr_count));
        if (TextUtils.isEmpty(length)) {
            maxLength = 5;
        } else {
            maxLength = Integer.parseInt(length);
        }
        cnteText.setHint(mContext.getString(R.string.please_input) + maxLength + mContext.getString(R.string.number_cntr));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (null != mConfirmClickListener)
                    mConfirmClickListener.onClick(this, mBoxDetalBean);
                else
                    dismiss();
                break;
            case R.id.btnCancle:
                adapt.resetSelect();
                break;
            case R.id.btn_0:
                addCntr(0);
                break;
            case R.id.btn_1:
                addCntr(1);
                break;
            case R.id.btn_2:
                addCntr(2);
                break;
            case R.id.btn_3:
                addCntr(3);
                break;
            case R.id.btn_4:
                addCntr(4);
                break;
            case R.id.btn_5:
                addCntr(5);
                break;
            case R.id.btn_6:
                addCntr(6);
                break;
            case R.id.btn_7:
                addCntr(7);
                break;
            case R.id.btn_8:
                addCntr(8);
                break;
            case R.id.btn_9:
                addCntr(9);
                break;
            case R.id.btn_d:
                if (cnter.toString().length() > 0) {
                    mVibrator.vibrate(100);
                    cnter.delete(cnter.length() - 1, cnter.length());
                    cnteText.setText(cnter.toString());
                } else {
                    cnteText.setText("");
                }
                break;
            case R.id.btn_t:
                mVibrator.vibrate(100);
                cnteText.setText("");
                cnter.delete(0, cnter.length());
                break;
            case R.id.btn_back:
                dismiss();
                break;

        }
    }


    private void addCntr(int num) {
        mVibrator.vibrate(100);
        if (cnter.toString().length() < maxLength) {
            cnter.append(num);
            cnteText.setText(cnter.toString());
            if (cnter.toString().length() == maxLength) {
                searchPresenter.searchBox(isGetBox ? mOldBoxDetalBean.getCntr() : "", cnter.toString());
            }
        }
    }

    /**
     * 获取当前任务箱子
     *
     * @return
     */
    @Override
    public BoxDetalBean getBoxDealBean() {
        return mBoxDetalBean;
    }

    /**
     * 获取当前任务箱子
     *
     * @return
     */
    @Override
    public BoxDetalBean getUpDealBean() {
        return mOldBoxDetalBean;
    }

    /**
     * 模糊搜索箱子结果
     *
     * @param list
     */
    @Override
    public void setSearchResult(List<BoxDetalBean> list) {
        cnteText.setText("");
        cnter.delete(0, cnter.length());
        adapt.clearData();
        if (isGetBox)
            adapt.addBean(mOldBoxDetalBean);
        adapt.setData(list);
    }

    /**
     * 加载失败结果
     *
     * @param msg
     */
    @Override
    public void onFaile(String msg) {
        cnteText.setText("");
        cnter.delete(0, cnter.length());
        hideProgress();

        switch (msg) {
//            case "CntrOutLoc":
//                showAlertDialog("集装箱不在该位置。");
//                break;
//            case "LocOccupy":
//                showAlertDialog("此位置已有其它集装箱摆放！");
//                break;
//            case "NotUnplug":
//                showAlertDialog("冷冻箱没有拔电！");
//                break;
//            case "PICKUPON":
//                showAlertDialog("请先提走上面的集装箱！");
//                break;
//            case "DisableRow":
//                showAlertDialog("田位暂停使用。");
//                break;
//            case "Locempty":
//                showAlertDialog("集装箱不能悬空");
//                break;
//            case "CNotExist":
//                showAlertDialog("集装箱编号无效。");
//                break;
//            case "JOBFINISH":
//                showAlertDialog("工作已经完成，请刷新工作任务！");
//                break;
//            case "CANTSWPJOB":
//                showAlertDialog("不能交换集装箱的工作！");
//                break;
//            case "TMLMON007":
//                showAlertDialog("找不到该拖车！");
//                break;
//            case "PickErr20":
//                showAlertDialog("车在场内，车上有两个２０尺的箱，不能再提箱！");
//                break;
//            case "PickErr40":
//                showAlertDialog("车在场内，车上有一个４０尺的箱，不能再提箱！");
//                break;
//            case "RFCWS1016":
//                showAlertDialog("车在场内，车上有一个４０尺的箱，不能再提箱！");
//                break;
//            case "TRKSIZEERR":
//                showAlertDialog("拖车车架类型错误!");
//                break;
//            case "NoPlug":
//                showAlertDialog("此田位没有电源插头，冷藏箱不能放在这里！");
//                break;
//            case "InadeqPlug":
//                showAlertDialog("冷藏箱电源插头不足！");
//                break;
//            case "TipOSZHGT":
//                showAlertDialog("集装箱是框架箱,不能在其上面放置其它箱!");
//                break;
//            case "BelowOH":
//                showAlertDialog("此位置的下面位置配载有超高箱，不能在放置其它箱。");
//                break;
//            case "InvalidRow":
//                showAlertDialog("田位不可用，联系中控！");
//                break;
//            case "PICKREFA":
//                showAlertDialog("冷藏箱没有拔电，无法提箱！");
//                break;
//            case "Not_IP":
//                showAlertDialog("该集装箱不能提箱。");
//                break;
//            case "InvalidIP":
//                showAlertDialog("无效提箱。");
//            case "BCNOCC":
//                showAlertDialog("田位与集装箱内外贸属性不符合");
//                break;
//            case "ErrLoc":
//                showAlertDialog("位置不正确");
//                break;
//            case "ErrPutLoc":
//                showAlertDialog("位置跟箱型不匹配");
//                break;
//            case "ErrGrnd20":
//                showAlertDialog("位置跟箱型不匹配");
//                break;
//            case "ErrGrnd40":
//                showAlertDialog("位置跟箱型不匹配");
//                break;
//            case "1012":
//                showAlertDialog("提箱失败。");
//                break;
//            case "1014":
//                showAlertDialog("上面有压箱。");
//                break;
//            case "1034":
//                showAlertDialog("该箱子上面有集装箱。");
//                break;
            default:
                showAlertDialog(msg);
                break;
        }
    }

    /**
     * 处理成功
     */
    @Override
    public void onSuccess() {
        if (null != mConfirmClickListener)
            mConfirmClickListener.onClick(this, mBoxDetalBean);
        dismiss();
    }

    /**
     * 显示加载进度
     */
    @Override
    public void showProgress() {
        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(mContext.getResources().getString(R.string.progress_msg));
        dialog.show();
    }

    /**
     * 隐藏进度
     */
    @Override
    public void hideProgress() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    /**
     * 错误提示
     *
     * @param msg
     */
    public void showAlertDialog(String msg) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alert_view, null);
        TextView message = (TextView) view.findViewById(R.id.message);
        Button btnCancle = (Button) view.findViewById(R.id.btnCancle);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancle.setVisibility(View.GONE);
        message.setText(msg);
        message.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        final AlertDialog showAlertDialog = new AlertDialog.Builder(mContext).setTitle(mContext.getResources().getString(R.string.alert)).setCancelable(false).setView(view).show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(View v, final BoxDetalBean bean, int positon) {
        mBoxDetalBean = bean;
        if (isGetBox) {
            //提的箱子跟任务箱子是同一个箱,直接提走
            if (bean.getCntr().equals(mOldBoxDetalBean.getCntr())) {
                new GetBoxAlertDialog(mContext, bean)
                        .setConfirmClickListener(new GetBoxAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(Dialog getBoxAlertDialog, View view) {
                                getBoxAlertDialog.dismiss();
                                searchPresenter.loadUpBoxTask();
                            }
                        }).show();
                return;
            }

            if (!mBoxDetalBean.isSWAPFLAG()) {
                showAlertDialog(mContext.getString(R.string.the_box_does_not_conform_to_the_exchange_suitcase));
                return;
            }

            //如果有序的,直接走正常流程提箱
            if (mBoxDetalBean.getISORDERLY().equalsIgnoreCase(ISORDERLY)) {
                mBoxDetalBean.setMaj_Loc(MyApplication.MAJLOC);
                mBoxDetalBean.setSub_Loc(MyApplication.SUBLOC);

                Intent it = new Intent(mContext, MyGridViewActivity.class);
                it.putExtra("notOrderBean", mOldBoxDetalBean);
                it.putExtra("boxBean", mBoxDetalBean);
                it.putExtra("isOrder", false);
                mContext.startActivity(it);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 1000);
            } else {
                new ChangeBoxAlertDialog(mContext, mBoxDetalBean).setConfirmClickListener(new ChangeBoxAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        dialog.dismiss();
                        searchPresenter.LoadCheckChangeBox(mBoxDetalBean.getCntr(), mOldBoxDetalBean.getCntr());
                    }
                }).show();
            }

        } else {
            if (null != mConfirmClickListener)
                mConfirmClickListener.onItemClick(this, bean);
        }
    }

    public void hideView() {
        btnCancle.setVisibility(View.GONE);
        btnOk.setVisibility(View.GONE);
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog, BoxDetalBean bean);

        void onItemClick(Dialog dialog, BoxDetalBean bean);
    }
}
