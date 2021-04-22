package com.smt.wxdj.swxdj;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smt.wxdj.swxdj.utils.ActivityTool;
import com.smt.wxdj.swxdj.utils.ScreenUtils;
import com.smtlibrary.dialog.SweetAlertDialog;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by gbh on 16/6/16.
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {
    //    public ProgressDialog mProgressDialog;
    public float mDensity;
    protected T mPresenter;
    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDensity = getResources().getDisplayMetrics().density;
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
//        LogUtils.e("HW", ScreenUtils.getScreenHeight2Width(this));
        ActivityTool.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        ActivityTool.finishActivity(this);
    }

    @Override
    public void onRefresh() {

    }


    /**
     * 启动动画
     *
     * @param view
     */
    public void animateOpen(final View view, int origWidth) {
        view.setVisibility(View.VISIBLE);
        origWidth = (int) (mDensity * origWidth + 0.5);
        ValueAnimator animator = createDropAnimator(view, 0, origWidth);
        animator.start();
    }


    /**
     * 关闭动画
     *
     * @param view
     */
    public void animateClose(final View view) {
        int origWidth = view.getWidth();
        ValueAnimator animator = createDropAnimator(view, origWidth, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }


    /**
     * 创建动画
     *
     * @param view
     * @param start
     * @param end
     * @return
     */
    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /**
     * 创建列、排View 展示      右边底部数字的高度
     *
     * @param i==0   start
     * @param width  宽
     * @param height 高
     * @param type   0 表示X轴，1表示Y轴
     * @return
     */
    @NonNull
    protected TextView createTextView(int i, int width, int height, int type) {
        TextView itemView = new TextView(this);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT || "736*1280".equals(ScreenUtils.getScreenHeight2Width(this))) {
            height = width / 5;
        } else if("1136*1920".equals(ScreenUtils.getScreenHeight2Width(this))
          || "720*1366".equals(ScreenUtils.getScreenHeight2Width(this))){
            height = width / 7;
        }else {
            height = width / 4;
        }
        LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(width, height);
        switch (type) {
            case 0:
                itemView.setText(String.format(String.valueOf(i + 1)));
                break;
            case 1:
                itemView.setText(String.valueOf(i));
                break;
        }

        itemView.setLayoutParams(tParams);
        itemView.setBackgroundResource(R.drawable.shapee);
        itemView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        itemView.setTextSize((int) (getResources().getDimension(R.dimen.textsize18) / mDensity));
        itemView.setGravity(Gravity.CENTER);
        itemView.getPaint().setFakeBoldText(true);
        return itemView;
    }

    protected void createYLayoutTextView(RelativeLayout yLayout, int mRow, int width) {
        int height = (int) this.getResources().getDimension(R.dimen.box_item_height);
        for (int i = 0; i < mRow; i++) {
            TextView itemView = new TextView(this);
            itemView.setId(generateViewId());
            RelativeLayout.LayoutParams tParams = new RelativeLayout.LayoutParams(width, height);
            itemView.setText(String.valueOf(i + 1));
            int childCount = yLayout.getChildCount();
            if (childCount > 0) {
                View view = yLayout.getChildAt(childCount - 1);
                if (null != view) {
                    int id = view.getId();
                    tParams.addRule(RelativeLayout.ABOVE, id);
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                        tParams.bottomMargin = (int) (getResources().getDimension(R.dimen.activity_menu_padding) / mDensity);
                    }
                }
            } else {
                tParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            }
            itemView.setLayoutParams(tParams);
            itemView.setBackgroundResource(R.drawable.shapee);
            itemView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            itemView.setTextSize((int) (getResources().getDimension(R.dimen.textsize18) / mDensity));
            itemView.setGravity(Gravity.CENTER);
            itemView.getPaint().setFakeBoldText(true);
            yLayout.addView(itemView);
        }
    }

    /**
     * An {@code int} value that may be updated atomically.
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 动态生成View ID
     * API LEVEL 17 以上View.generateViewId()生成
     * API LEVEL 17 以下需要手动生成
     */
    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public synchronized void showProgress() {
        hideProgress();
        if (null == dialog) {
            dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            dialog.setTitleText(getString(R.string.progress_msg));
        }
        dialog.show();
    }

    public synchronized void hideProgress() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }


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
//            case "4002":
//                showAlertDialog("同一排，不能混放，相关贝的位置已经有箱，位置不可用");
//                break;
            default:
                showAlertDialog(msg);
                break;
        }
    }

    protected abstract T createPresenter();
}
