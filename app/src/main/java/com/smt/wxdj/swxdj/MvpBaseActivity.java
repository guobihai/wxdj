package com.smt.wxdj.swxdj;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smtlibrary.dialog.SweetAlertDialog;

/**
 * Created by gbh on 16/9/8.
 */
public abstract class MvpBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;
    public SweetAlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    public void showProgressDialog(String msg) {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(msg);
        dialog.show();
    }

    public void cancleProgressDialog() {
        if (null != dialog && dialog.isShowing())
            dialog.dismiss();
    }

    /**
     * 错误提示
     *
     * @param msg
     */
    public void showAlertDialog(String msg) {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_view, null);
        TextView message = (TextView) view.findViewById(R.id.message);
        Button btnCancle = (Button) view.findViewById(R.id.btnCancle);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancle.setVisibility(View.GONE);
        message.setText(msg);
        message.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        final AlertDialog showAlertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert)).setCancelable(false).setView(view).show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog.dismiss();
            }
        });
    }

    protected abstract T createPresenter();


    public void onFaile(String msg) {
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
//            case "10":
//                showAlertDialog("移箱失败。error code:10");
//                break;
//            case "ErrDaoCntr":
//                showAlertDialog("请先倒箱再提箱。");
//                break;
//            case "PWDERR":
//                showAlertDialog("密码错误。");
//                break;
//            case "USERNOTFOUND":
//                showAlertDialog("用户名错误。");
//                break;
            default:
                showAlertDialog(msg);
                break;
        }
    }
}
