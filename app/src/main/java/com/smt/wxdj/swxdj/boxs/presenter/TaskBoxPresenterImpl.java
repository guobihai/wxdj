package com.smt.wxdj.swxdj.boxs.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.CntrEntity;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.boxs.model.BoxModelImpl;
import com.smt.wxdj.swxdj.boxs.view.TaskBoxView;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.TimeUtils;
import com.smt.wxdj.swxdj.webservice.BizObject;
import com.smt.wxdj.swxdj.webservice.ParamObject;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gbh on 16/7/6.
 */
public class TaskBoxPresenterImpl implements TaskBoxPresenter {
    private TaskBoxView mBoxsView;
    private BoxModelImpl mBoxsModel;

    public TaskBoxPresenterImpl(TaskBoxView boxView) {
        this.mBoxsView = boxView;
        mBoxsModel = new BoxModelImpl();
    }


    @Override
    public void loadBoxs(String stack, final boolean flag, String taskType) {
        mBoxsModel.loadBoxs(getBoxsTaskRequestData(stack, flag, taskType), new IPublicResultInterface<List<BoxDetalBean>>() {
            @Override
            public void onSucess(List<BoxDetalBean> list) {
                if (flag) {
                    List<BoxDetalBean> tList = new ArrayList<>();
                    for (BoxDetalBean bean : list) {
                        if (bean.getStack().equals(mBoxsView.getCurStack()))
                            tList.add(bean);
                    }
                    mBoxsView.addList(tList);
                } else mBoxsView.addList(list);
                mBoxsView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.hideProgress();
                mBoxsView.onFaile(msg);
            }
        });
    }

    /**
     * 取消提箱任务列表
     *
     * @param stack
     */
    @Override
    public void FindCancel_WorkList(final String stack, final boolean flag) {
//        LogUtils.sysout("======执行取消提箱FindCancel_WorkList", stack);
        mBoxsModel.loadBoxs(getCancleGetBoxsRequestData(stack, flag), new IPublicResultInterface<List<BoxDetalBean>>() {
            @Override
            public void onSucess(List<BoxDetalBean> list) {
                if (!TextUtils.isEmpty(stack)) {
                    List<BoxDetalBean> tList = new ArrayList<>();
                    for (BoxDetalBean bean : list) {
                        if (bean.getStack().equals(mBoxsView.getCurStack()))
                            tList.add(bean);
                    }
                    mBoxsView.addList(tList);
                } else mBoxsView.addList(list);
                mBoxsView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.hideProgress();
                mBoxsView.onFaile(msg);
            }
        });
    }


    /**
     * 获取场地信息
     *
     * @param crn 当前登录吊机号
     */
    @Override
    public void loadBoxCdData(String crn, boolean flag) {
        if (!flag) {
            loadBoxs(null, flag, "");
            return;
        }
        mBoxsView.showProgress(1);
        mBoxsModel.LoadBoxCd(getBoxCdRequestData(crn), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (object instanceof List) {
                    List<StackBean> list = new ArrayList<>();
                    List<StackBean> data = (List<StackBean>) object;
                    for (StackBean bean : data) {
                        if (!bean.getStack().trim().equals("CHECK"))
                            list.add(bean);
                    }
//                    mBoxsView.addListCd(list);
                }
                mBoxsView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.onFaile(msg);
            }
        });
    }

    /**
     * 判断场地是否被锁住
     *
     * @param stack
     */
    @Override
    public void CheckStackIsLock(String stack) {
        mBoxsView.showProgress(1);
        mBoxsModel.CheckStackIsLock(getStackIsCheck(stack), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                List<StackBean> list = (List<StackBean>) object;
                if (list.size() > 0)//没有锁住
                    mBoxsView.unLockStackList(list);
                else {
                    //该场地被锁
                    GetLockReason();
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.onFaile(msg);
            }
        });
    }

    /**
     * 修改场地登录信息
     *
     * @param stackBean
     */
    @Override
    public void UpdateCraneRow(StackBean stackBean) {
        mBoxsModel.UpdateCraneRow(updateCraneRowParam(stackBean), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {

            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }

    /**
     * 获取场地锁定原因
     */
    @Override
    public void GetLockReason() {
        mBoxsModel.GetLockReason(getLockReason(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                mBoxsView.lockStack();
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }


    /**
     * 获取呗位
     *
     * @param stack
     */
    @Override
    public void getSelectedBay(String stack) {

    }

    /**
     * 获取该贝位详细信息
     *
     * @param bay
     */
    @Override
    public void CheckMaxCellTier(String bay) {
        mBoxsView.showProgress(1);
        mBoxsModel.CheckMaxCellTier(checkMaxCellTierParam(bay), new IPublicResultInterface<Bay>() {
            @Override
            public void onSucess(Bay obj) {
                mBoxsView.hideProgress();
                if (null != obj)
                    mBoxsView.addBay(obj);
                else {
                    mBoxsView.onFaile(MyApplication.getContext().getString(R.string.get_data_failure));
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.onFaile(msg);
            }
        });
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
        b.setCrane(mBoxsView.getUser().getCrn());
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
        b.setCrane(mBoxsView.getUser().getCrn());
        b.setLanguage("C");
        m.setBizObject(b.toString());
        return m.toString();
    }

    /**
     * 场地
     *
     * @return
     */
    private String getBoxCdRequestData(String crn) {
        BizObject bizObject = new BizObject();
        bizObject.setCrn(crn);
        ParamObject m = new ParamObject("ShowCraneStackInfor");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }


    /**
     * 判断场地是否被锁定的参数
     *
     * @param stack
     * @return
     */
    private String getStackIsCheck(String stack) {
        if (!TextUtils.isEmpty(stack)) {
            if (stack.indexOf("'") < 0) {
                stack = "'" + stack.replace(",", "','") + "'";
            }
        }
        BizObject bizObject = new BizObject();
        bizObject.setStack(stack);
        ParamObject m = new ParamObject("CheckStackIsLock");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }

    /**
     * 获取锁定原因
     *
     * @return
     */
    private String getLockReason() {
        BizObject bizObject = new BizObject();
        ParamObject m = new ParamObject("GetLockReason");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }

    /**
     * 根据bay 获取贝位详细信息
     *
     * @param bay
     * @return
     */
    private String checkMaxCellTierParam(String bay) {
        BizObject bizObject = new BizObject();
        bizObject.setBay(bay);
        bizObject.setEnable_Ind("Y");
        bizObject.setAvail_Ind("Y");
        ParamObject m = new ParamObject("GetBayCellTier");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }


    /**
     * 修改场地登录信息
     *
     * @return
     */
    private String updateCraneRowParam(StackBean stackBean) {
        BizObject bizObject = new BizObject();
        bizObject.setCrn(mBoxsView.getUser().getCrn());
        bizObject.setASSIGN_ROW(stackBean.getStack());
        bizObject.setSTART_TIME(TimeUtils.chanceTime1(stackBean.getSTART_TIME()));
        bizObject.setEND_TIME(TimeUtils.chanceTime1(stackBean.getEND_TIME()));
        ParamObject m = new ParamObject("UpdateCraneRow");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }

    /**
     * 获取倒箱任务列表
     *
     * @return
     */
    @Override
    public void fallBoxData(StackBean bean, final boolean flag) {
        mBoxsModel.loadBoxs(getFallBoxRequestData(bean, flag), new IPublicResultInterface<List<BoxDetalBean>>() {
            @Override
            public void onSucess(List<BoxDetalBean> list) {
                if (flag) {
                    List<BoxDetalBean> tList = new ArrayList<>();
                    for (BoxDetalBean bean : list) {
                        if (bean.getStack().equals(mBoxsView.getCurStack()))
                            tList.add(bean);
                    }
                    mBoxsView.addList(tList);
                } else {
                    mBoxsView.addList(list);
                }
                mBoxsView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.hideProgress();
                mBoxsView.onFaile(msg);
            }
        });
    }

    @Override
    public void putBoxForCell(BoxDetalBean bean) {
        mBoxsView.showProgress(1);
        mBoxsModel.loadMoveBoxForCell(getPutBoxRequestData(bean), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                mBoxsView.hideProgress();
                mBoxsView.putBoxForCellData(null);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.hideProgress();
                mBoxsView.onFaile(msg);
            }
        });
    }

    @Override
    public void GetCntrInfoConver(final BoxDetalBean bean, final Bay bay) {
        mBoxsView.showProgress(1);
        mBoxsModel.GetCntrInfoConver(getCntrInfoConverData(bean), new IPublicResultInterface<List<BoxDetalBean>>() {
            @Override
            public void onSucess(List<BoxDetalBean> list) {
                mBoxsView.hideProgress();
                if (TextUtils.isEmpty(bay.getMax_Tier())) {
                    mBoxsView.onFaile(MyApplication.getContext().getString(R.string.the_maximum_layer_is_empty));
                    return;
                }
                List<Integer> beanList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    BoxDetalBean detalBean = list.get(i);
                    if (!bean.getCell().equals(detalBean.getCell())) continue;
                    beanList.add(Integer.parseInt(detalBean.getTier()));
                }
                int maxTier = 0;
                if(beanList.size() > 0){
                    maxTier = Collections.max(beanList);
                }
                maxTier++;
                int maxBayTier = Integer.parseInt(bay.getMax_Tier());
                if(maxTier > maxBayTier){
                    mBoxsView.onFaile(MyApplication.getContext().getString(R.string.the_floor_is_full));
                    return;
                }
                bean.setTier(String.valueOf(maxTier));
//                putBoxForCell(bean);
                mBoxsView.putBoxForCellData(bean);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBoxsView.onFaile(msg);
            }
        });
    }

    /**
     * 取得计算过后的箱子
     *
     * @return
     */
    private String getCntrInfoConverData(BoxDetalBean boxDetalBean) {
        boxDetalBean.setTrk(TextUtils.isEmpty(boxDetalBean.getTrk()) ? "********" : boxDetalBean.getTrk());
//        cntrInfo.setTrk_Type(boxDetalBean.getTrk_Type());
        boxDetalBean.setActivity(TextUtils.isEmpty(boxDetalBean.getActivity()) ? "RM" : boxDetalBean.getActivity());
        ParamObject m = new ParamObject("GetCntrInfoConver");
        m.setBizObject(boxDetalBean.toString());
        return m.toString();
    }

    /**
     * 放箱子数据体
     *
     * @return
     */
    private String getPutBoxRequestData(BoxDetalBean bean) {
        bean.setCrane(MyApplication.user.getCRANE());
        bean.setSignonUSERID(MyApplication.user.getSignonUSERID());
        bean.setUser(MyApplication.user.getSignonUSERID());
        bean.setResult("Y");
        bean.setiToRow(bean.getRown());
        bean.setiToCell(bean.getCell());
        bean.setiToTier(bean.getTier());
        bean.setiFromCell("");
        bean.setiFromRow("");
        bean.setiFromRow("");
        bean.setIsCheck("N");
        bean.setStatus(BoxTool.STATE_PL);
        ParamObject m = new ParamObject("CntrDaoConfirm");
        m.setBizObject(JsonUtils.serialize(bean));
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
