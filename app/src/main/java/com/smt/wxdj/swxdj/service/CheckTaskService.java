package com.smt.wxdj.swxdj.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.smt.wxdj.swxdj.MainActivity;
import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.CntrEntity;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.fragment.MainBoxFragment;
import com.smt.wxdj.swxdj.session.Session;
import com.smt.wxdj.swxdj.utils.ActivityTool;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.PlayRing;
import com.smt.wxdj.swxdj.utils.PraseJsonUtils;
import com.smt.wxdj.swxdj.utils.SettingConfig;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smt.wxdj.swxdj.webservice.BizObject;
import com.smt.wxdj.swxdj.webservice.ParamObject;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.LogUtils;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_DCBOAT;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_FALLBOX;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_LDBOAT;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_ZXBOAT;
import static com.smt.wxdj.swxdj.MainActivity.TASKTYPE_ZXC;

/**
 * @author xlj
 * @date 2017/10/30
 */
public class CheckTaskService extends Service {

    private Handler handler;
    private int TIMEOUT = 10000;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(MainActivity.isGoLogin) return;
                String data = "";
                String taskType = "";
                final boolean isDefault = LruchUtils.isSwitch(getString(R.string.open_stack_list_switch));
                Log.e("mTaskSql", MainBoxFragment.mTaskSql + "");
                switch (MainBoxFragment.mType) {
                    case MainActivity.TASKTYPE_ALL://装卸船/车
                        data = getBoxsTaskRequestData(MainBoxFragment.mTaskSql, isDefault, taskType);
                        break;
                    case TASKTYPE_ZXBOAT://装卸船
                        taskType = "VSL";
                        data = getBoxsTaskRequestData(MainBoxFragment.mTaskSql, isDefault, taskType);
                        break;
                    case TASKTYPE_LDBOAT://装船
                        taskType = "LD";
                        data = getBoxsTaskRequestData(MainBoxFragment.mTaskSql, isDefault, taskType);
                        break;
                    case TASKTYPE_DCBOAT://卸船
                        taskType = "DC";
                        data = getBoxsTaskRequestData(MainBoxFragment.mTaskSql, isDefault, taskType);
                        break;
                    case TASKTYPE_ZXC://装卸车
                        taskType = "CY";
                        data = getBoxsTaskRequestData(MainBoxFragment.mTaskSql, isDefault, taskType);
                        break;
                    case MainActivity.TASKTYPE_CANCLEBOX://取消提箱功能
                        data = getCancleGetBoxsRequestData(MainBoxFragment.mTaskSql, isDefault);
                        break;
                    case TASKTYPE_FALLBOX://倒箱功能
                        if (isDefault && null == MainBoxFragment.mStackBean) {
                            return;
                        }
                        data = getFallBoxRequestData(MainBoxFragment.mStackBean, isDefault);
                        break;
                }

                //参数
                OkHttpUtils.post(URLTool.getUrl(), data,
                        new OkHttpUtils.ResultCallBack<String>() {
                            @Override
                            public void onSuccess(String response) {
                                LogUtils.e("CheckTaskService loadBoxs response====:", response);
                                List<BoxDetalBean> tempList = PraseJsonUtils.praseBoxListData(response);
                                LogUtils.e("CheckTaskService loadBoxs list", tempList.size() + "");
                                List<BoxDetalBean> list = new ArrayList<>();
                                if (isDefault) {
                                    List<BoxDetalBean> tList = new ArrayList<>();
                                    for (BoxDetalBean bean : tempList) {
                                        if (bean.getStack().equals(MainBoxFragment.mCurrentStack))
                                            tList.add(bean);
                                    }
                                    list.addAll(tList);
                                } else {
                                    list.addAll(tempList);
                                }

                                if (null == MainBoxFragment.mData) return;
                                if (MainBoxFragment.mData.size() == 0) {
                                    if (list.size() > 0) {
                                        PlayRing.ring(MyApplication.getContext());
                                    }
                                } else if (list.size() > 0) {
                                    if (MainBoxFragment.mData.get(0).getStack().equals(list.get(0).getStack()) && list.size() > MainBoxFragment.mData.size()) {
                                        PlayRing.ring(MyApplication.getContext());
                                    } else if (!MainBoxFragment.mData.get(0).getStack().equals(list.get(0).getStack())) {
                                        PlayRing.ring(MyApplication.getContext());
                                    }
                                }

                                MainBoxFragment.mData.clear();
//                                MainBoxFragment.mData.addAll(list);
                                Session.getInstance().notifySelect(FileKeyName.CHECKTASKSERVICEMSG);

                                Intent intent = new Intent();
                                intent.setAction(FileKeyName.CHECKTASK);
                                sendBroadcast(intent);
                            }

                            @Override
                            public void onFailure(Exception e) {
                            }
                        });
            }
        }, PreferenceUtils.getInt(MyApplication.getContext(), SettingConfig.SETTING_TIME, SettingConfig.TIME_OUT) * 1000);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 任务列表信息
     *
     * @return
     */
    private String getBoxsTaskRequestData(String stack, boolean flag, String taskType) {
        ParamObject m = new ParamObject(flag ? "Find_WorkList" : "Find_AllWorkList");
        BizObject b = new BizObject();
        b.setStack(stack);//非空
        b.setResult("1");
        b.setLanguage("C");
        b.setTaskType(taskType);
        b.setCrane(MyApplication.user.getCrn());
        m.setBizObject(b.toString());
        return m.toString();
    }

    /**
     * 取消提箱任务列表信息
     *
     * @return
     */
    private String getCancleGetBoxsRequestData(String stack, boolean flag) {
        ParamObject m = new ParamObject("FindCancel_WorkList");
        BizObject b = new BizObject();
        b.setStack(TextUtils.isEmpty(stack) ? "" : stack);//非空
        b.setResult("1");
        b.setCrane(MyApplication.user.getCrn());
        b.setLanguage("C");
        m.setBizObject(b.toString());
        return m.toString();
    }

    /**
     * 倒箱任务列表信息
     *
     * @return String
     */
    private String getFallBoxRequestData(StackBean bean, boolean flag) {
        ParamObject m = new ParamObject("GetRMCntrList");
        CntrEntity b = new CntrEntity();
        if (flag) {
            b.setStack(bean.getStack());//非空
            b.setMaj_Loc(bean.getMaj_Loc());
            b.setSub_Loc(bean.getSub_Loc());
            b.setStartBay(bean.getOPR_START_ROWN());
            b.setEndBay(bean.getOPR_END_ROWN());
        } else {
            b.setStack(null);
            b.setMaj_Loc(MyApplication.MAJLOC);
            b.setSub_Loc(MyApplication.SUBLOC);
        }
        m.setBizObject(b.toString());
        return m.toString();
    }
}
