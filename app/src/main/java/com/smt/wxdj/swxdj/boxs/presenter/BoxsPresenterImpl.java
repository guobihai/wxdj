package com.smt.wxdj.swxdj.boxs.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.smt.wxdj.swxdj.BasePresenter;
import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.BasicParam;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.Trk;
import com.smt.wxdj.swxdj.boxs.model.BoxModelImpl;
import com.smt.wxdj.swxdj.boxs.model.BoxsModel;
import com.smt.wxdj.swxdj.boxs.view.BoxsView;
import com.smt.wxdj.swxdj.enums.BOXCTRLTYPE;
import com.smt.wxdj.swxdj.enums.DataType;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.PraseJsonUtils;
import com.smt.wxdj.swxdj.webservice.BizObject;
import com.smt.wxdj.swxdj.webservice.ParamObject;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gbh on 16/6/27.
 */
public class BoxsPresenterImpl extends BasePresenter<BoxsView> implements BoxsPresenter {
    private BoxsModel mBoxsModel;
    private final List<BoxDetalBean> tempList = new ArrayList<>();
    private final List<BoxDetalBean> tempDxwList = new ArrayList<>();
    private final Map<String, BoxDetalBean> maps = new HashMap<>();

    public BoxsPresenterImpl() {
        this.mBoxsModel = new BoxModelImpl();
    }

    /**
     * 获取该贝位箱子信息
     *
     * @param bay
     */
    @Override
    public void GetCntrListByConditionNotCell(String bay, String cntr, boolean iProgress) {
        if (iProgress)
            getView().showProgress();
        tempList.clear();
        tempDxwList.clear();
        mBoxsModel.loadBoxs(getCntrListByCondition(bay, cntr), new IPublicResultInterface<List<BoxDetalBean>>() {
            @Override
            public void onSucess(List<BoxDetalBean> list) {
                if (!isViewAttached()) return;
                maps.clear();
                //倒箱位数据
                if (list.size() > 0) {
                    tempList.addAll(list);
                    for (BoxDetalBean b : list) {
                        if (b.getCell().equals("0")) {
                            tempDxwList.add(b);
                            tempList.remove(b);
                        }
                        //如果相等，替换表格数据，箱子填充
                        b.setDefaultCell(String.format("(%s,%s)", b.getCell(), b.getTier()));
                        maps.put(b.getDefaultCell(), b);
                    }
                }
//                getView().addList(tempList, maps);
//                getView().addDxwList(tempDxwList, maps);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                tempList.clear();
                tempDxwList.clear();
                if (!isViewAttached()) return;
//                LogUtils.sysout("加载数据失败............", e.toString());
                getView().hideProgress();
                getView().onFaile(e.toString());
            }
        });
    }

    @Override
    public void loadCheckMoveForCell(BoxDetalBean boxDetalBean, final int toCell, final int toTier) {
        if (!isViewAttached()) return;
        getView().showProgress();
        if (getView().getBoxCtrlType() == BOXCTRLTYPE.PLPUTBOX || getView().getBoxCtrlType() == BOXCTRLTYPE.PUTBOX) {
            putBoxForCell(toCell, toTier);
        } else {//倒箱
            loadMoveBoxForCell(boxDetalBean, toCell, toTier);
        }
    }


    @Override
    public void loadMoveBoxForCell(BoxDetalBean boxDetalBean, int toCell, int toTier) {
        String data = getMoveBoxRequestData(boxDetalBean, toCell, toTier);
        mBoxsModel.loadMoveBoxForCell(data, new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (!isViewAttached()) return;
                getView().hideProgress();
//                LogUtils.sysout("-======================move box resutlt", String.valueOf(bol));
                if (bol) {
                    getView().updateAdapt();
                } else {
                    getView().onFaile(MyApplication.getContext().getString(R.string.failure_please_try_again));
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().onFaile(msg);
            }
        });
    }

    @Override
    public void putBoxForCell(int toCell, int toTier) {
//        Log.e("getPutBoxRequestData", getPutBoxRequestData(toCell, toTier));
        mBoxsModel.loadMoveBoxForCell(getPutBoxRequestData(toCell, toTier), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (!isViewAttached()) return;
                getView().hideProgress();
//                LogUtils.sysout("put box resutlt", String.valueOf(bol));
                if (bol)
                    getView().updatePutBoxData();
                else
                    getView().onFaile(MyApplication.getContext().getString(R.string.failure_please_try_again));
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().onFaile(msg);
            }
        });
    }

    @Override
    public void loadUpBoxTask() {

    }

    //    @Override
    public void loadUpBoxTask(BoxDetalBean bean) {
        if (!isViewAttached()) return;
//        getView().showProgress();
        mBoxsModel.loadUpBoxTask(getUpBoxRequestData(bean), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean obj) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                if (obj) {
//                    MyApplication.isChange = false;
                    getView().updateUpBoxData();
                } else {
//                    MyApplication.isChange = false;
                    getView().onFaile(MyApplication.getContext().getString(R.string.suitcase_failure));
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().onFaile(msg);
//                MyApplication.isChange = false;
            }
        });
    }

    @Override
    public void LoadCheckChangeBox() {
        if (!isViewAttached()) return;
        getView().showProgress();
        String currCNTR = getView().getBoxDealBean().getCntr();
        String changeCNTR = getView().getUpDealBean().getCntr();
        mBoxsModel.loadCheckChangeBoxTask(getCheckChangeBoxRequestData(currCNTR, changeCNTR), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (!isViewAttached()) return;
                if (bol) {
                    LoadChangeBox();
                    LogUtils.sysout("bol", "" + bol);
                } else {
                    LogUtils.sysout("res", "can not change box");
                    getView().checkChangeBoxFaile();
                    getView().onFaile(MyApplication.getContext().getString(R.string.failure_to_meet_exchange_box_conditions));
                    getView().hideProgress();
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().onFaile(msg);
                getView().hideProgress();
            }
        });
    }

    @Override
    public void LoadChangeBox() {
        mBoxsModel.loadUpBoxTask(getChangeBoxRequestData(), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                if (bol) {
                    getView().checkChangeBoxSucess();
                    getView().updateUpBoxData();
                } else
                    getView().onFaile(MyApplication.getContext().getString(R.string.there_is_a_pressure_box_on_it));
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().onFaile(msg);
            }
        });

    }


    /**
     * 抄场
     */
    @Override
    public void putBoxToParkingSpace() {
        getView().showProgress();
        mBoxsModel.putBoxToParkingSpace(getClearBox(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().putBoxToParkingSpaceResult();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().onFaile(msg);
                getView().hideProgress();
            }
        });
    }

    /**
     * 模糊搜索箱子
     */
    @Override
    public void searchBox(String param) {
        getView().showProgress();
        mBoxsModel.searchBox(getSearchData(param), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                getView().hideProgress();
//                getView().setSearchResult((List<BoxDetalBean>) object);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().onFaile(msg);
            }
        });
    }

    /**
     * 获取推荐位系统参数
     */
    @Override
    public void GetSysParam(String param) {
        boolean bol = param.equals("true") ? true : false;
        BoxDetalBean cntrinfo = getView().getBoxDealBean();
        if (null != cntrinfo) {
            String act = cntrinfo.getActivity();
            if (TextUtils.isEmpty(act) || cntrinfo.getActivity().equals("PK") ||
                    cntrinfo.getActivity().equals("IP") ||
                    cntrinfo.getActivity().equals("LD")) {
                act = "RM";
            }
            if (!(act.equals("RM") && !bol))
                GetCntrCellForRFCWS();
        }
    }

    /**
     * 获取放箱推荐位
     */
    @Override
    public void GetCntrCellForRFCWS() {
        mBoxsModel.GetCntrCellForRFCWS(getCntrCellForRFCWSParam(), new IPublicResultInterface<List<String>>() {
            @Override
            public void onSucess(List<String> rmList) {
                if (!isViewAttached()) return;
                LogUtils.e("GetCntrCellForRFCWS", rmList.toString());
                LogUtils.sysout("=====rmList===========", rmList.size());
                getView().addRmList(rmList);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
            }
        });
    }

    /**
     * 检查场地是否被锁
     *
     * @param stacks
     */
    @Override
    public void CheckStackIsLock(String stacks) {

    }

    /**
     * 锁定 原因
     */
    @Override
    public void GetLockReasonList() {

    }

    /**
     * 解锁
     */
    @Override
    public void ReleaseStackLock() {

    }

    /**
     * 更改 YARD_STACK.OPR_STA 状态
     *
     * @param stacks
     */
    @Override
    public void UpdateYardStackChecked(String stacks) {

    }

    /**
     * 验证提箱
     *
     * @param boxDetalBean
     */
    @Override
    public void isValidOperation(final BoxDetalBean boxDetalBean) {
        getView().showProgress();
        mBoxsModel.ValidOperation(getValidOperationParam(boxDetalBean), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (bol) {
//                    MyApplication.isChange = true;
                    CheckTruckSize(boxDetalBean);
                } else {
                    if (!isViewAttached()) return;
                    getView().hideProgress();
                    getView().onFaile(MyApplication.getContext().getString(R.string.failure_to_verify_suitcase));
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().onFaile(msg);
//                MyApplication.isChange = false;
            }
        });
    }

    /**
     * 提箱前对车架的状况检测
     *
     * @param info 要提的箱子
     */
    @Override
    public void CheckTruckSize(final BoxDetalBean info) {
        if (TextUtils.isEmpty(info.getIsCheck()) && info.getIsCheck().equals("Y")) return;
        //捣箱
        if (info.getTrk().startsWith("******") && info.getActivity().equals("RM")) {
            return;
        }
        if (info.getTrk().equals("********")) {
            loadUpBoxTask(info);
            return;
        }

        mBoxsModel.CheckTruckSize(getTruckSizeParam(info.getTrk()), new IPublicResultInterface<String>() {
            @Override
            public void onSucess(String trkSize) {
                if (TextUtils.isEmpty(trkSize) || "null".equals(trkSize)) {
                    getView().onFaile(MyApplication.getContext().getString(R.string.the_size_of_the_box_does_not_match_the_size_of_the_car));
                    return;
                }
                //判断车型是否一样
                if (!trkSize.equals("*") && !trkSize.equals(info.getLen_Class())) {
                    getView().onFaile(MyApplication.getContext().getString(R.string.the_size_of_the_box_does_not_match_the_size_of_the_car));
                    return;
                }

                //判断该拖车是否完全放完箱子
//                IsPKHasGR(info.getTrk());
                //直接提箱
                loadUpBoxTask(info);

            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().onFaile(msg);
                getView().hideProgress();
//                MyApplication.isChange = false;
            }
        });

    }

    /**
     * 判断该拖车是否放箱完毕
     *
     * @param trk
     */
    @Override
    public void IsPKHasGR(final String trk) {
        mBoxsModel.CheckTruckSize(getIsPKHasGRParam(trk), new IPublicResultInterface<String>() {
            @Override
            public void onSucess(String str) {
                if (!isViewAttached()) return;
                if (!(TextUtils.isEmpty(str) || str.equals("null"))) {
                    if (str.equals("_WSFail_")) return;
                    getView().onFaile(MyApplication.getContext().getString(R.string.has_box_feeding_operation));
                    getView().hideProgress();
                    return;
                }

//                LogUtils.sysout("=====提箱操作:", trk);
                //直接提箱
                loadUpBoxTask();
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }


    /**
     * 取得计算过后的箱子
     */
    @Override
    public void GetCntrInfoConver(final BoxDetalBean bean) {
        if (!isViewAttached()) {
            return;
        }
        getView().showProgress();
        mBoxsModel.GetCntrInfoConver(getCntrInfoConver(bean), new IPublicResultInterface<List<BoxDetalBean>>() {
            @Override
            public void onSucess(List<BoxDetalBean> list) {
                if (!isViewAttached()) {
                    return;
                }
                maps.clear();
                //倒箱位数据
                if (list.size() > 0) {
                    tempList.addAll(list);
                    for (BoxDetalBean b : list) {
                        if (b.getCell().equals("0")) {
                            tempDxwList.add(b);
                            tempList.remove(b);
                        }
                        //如果相等，替换表格数据，箱子填充
                        b.setDefaultCell(String.format("(%s,%s)", b.getCell(), b.getTier()));
                        maps.put(b.getDefaultCell(), b);
                    }
                }
                LogUtils.e("GetCntrInfoConver", "" + tempList.size());
//                getView().addList(tempList, maps);
//                getView().addDxwList(tempDxwList, maps);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                getView().onFaile(msg);
            }
        });
    }

    /**
     * 取得计算过后的箱子
     *
     * @return
     */
    private String getCntrInfoConver(BoxDetalBean boxDetalBean) {
//        LogUtils.e("tag", "boxDetalBean:"+boxDetalBean.toString());
//        BizObject bizObject = new BizObject();
//        bizObject.setBay(bay);
//        bizObject.setCntr(cntr);
//        ParamObject m = new ParamObject("GetCntrInfoConver");
//        m.setBizObject(bizObject.toString());
//        return m.toString();
//        BoxDetalBean cntrInfo = new BoxDetalBean();
//        boxDetalBean.setMaj_Loc(getView().getUser().getSignonMAJLOC());
//        boxDetalBean.setSub_Loc(getView().getUser().getSignonSUBLOC());
//        cntrInfo.setSignonMAJLOC(getView().getUser().getSignonMAJLOC());
//        cntrInfo.setSignonSUBLOC(getView().getUser().getSignonSUBLOC());
//        cntrInfo.setCntr(boxDetalBean.getCntr());
        boxDetalBean.setTrk(TextUtils.isEmpty(boxDetalBean.getTrk()) ? "********" : boxDetalBean.getTrk());
//        cntrInfo.setTrk_Type(boxDetalBean.getTrk_Type());
        boxDetalBean.setActivity(TextUtils.isEmpty(boxDetalBean.getActivity()) ? "RM" : boxDetalBean.getActivity());
        ParamObject m = new ParamObject("GetCntrInfoConver");
        m.setBizObject(boxDetalBean.toString());
        return m.toString();
    }

    /**
     * 获取该贝位详细信息
     *
     * @param bay
     */
    @Override
    public void CheckMaxCellTier(String bay) {
        getView().showProgress();
        mBoxsModel.CheckMaxCellTier(checkMaxCellTierParam(bay), new IPublicResultInterface<Bay>() {
            @Override
            public void onSucess(Bay obj) {
                if (!isViewAttached()) return;
                if (null != obj)
                    getView().addBay(obj);
                else {
                    getView().onFaile(MyApplication.getContext().getString(R.string.get_data_failure));
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                getView().onFaile(msg);
            }
        });
    }

    /**
     * 获取拖车列表
     */
    @Override
    public void loadTrkList(final DataType dataType, String trk) {
        mBoxsModel.GetBasicData(getBaseListrParam(dataType.TRK, trk), new IPublicResultInterface<String>() {
            @Override
            public void onSucess(String response) {
                if (!isViewAttached()) return;
                List<Trk> list = PraseJsonUtils.praseUserList(response, dataType.getType(), Trk.class);
                getView().setTrkList(list);
//                Lacuche.getLacuche().putTrkData("TRK", list);
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }


    /**
     * 根据田位获取排的箱子
     *
     * @return
     */
    private String getBoxListRequestData(int cell) {
        BizObject bizObject = new BizObject();
        bizObject.setStack(getView().getStack());
        bizObject.setRown(getView().getBay());
        bizObject.setToRow(getView().getBay());
        bizObject.setCell(String.valueOf(cell));
        if (null != getView().getUpDealBean() && null != getView().getUpDealBean().getActivity()) {
            if (getView().getUpDealBean().getActivity().equals(BoxTool.CTRL_PUTBOX)) {
                bizObject.setCntr("");
            } else {//提箱，判断同类型的能够提的箱子
                bizObject.setCntr(getView().getUpDealBean().getCntr());
            }
        }

        ParamObject m = new ParamObject("");
        m.setBizObject(JsonUtils.serialize(bizObject));
        return m.toString();
    }

    /**
     * 移动箱子数据体
     *
     * @param toCell
     * @param toTier
     * @return
     */
    private String getCheckBoxRequestData(int toCell, int toTier) {
        BoxDetalBean bean = getView().getBoxDealBean();
        bean.setCntr(getView().getBoxDealBean().getCntr());
        bean.setStack(getView().getStack());
        bean.setiToRow(getView().getBay());
        bean.setiToCell(String.valueOf(toCell));
        bean.setiToTier(String.valueOf(toTier));
        ParamObject m = new ParamObject("VALIDCANPUTCNTR");
        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }

    /**
     * 移动箱子数据体
     *
     * @param tempBoxBean
     * @param toCell
     * @param toTier
     * @return
     */
    private String getMoveBoxRequestData(BoxDetalBean tempBoxBean, int toCell, int toTier) {
        BoxDetalBean bean = tempBoxBean;
        bean.setActivity("RM");
        bean.setSvc_Code("MAR");
        bean.setStatus(getView().getSTATUS());//状态
        bean.setCrane(getView().getUser().getCRANE());
        bean.setTrk("*******");
        bean.setSignonUSERID(MyApplication.user.getSignonUSERID());
        bean.setUser(MyApplication.user.getSignonUSERID());
        bean.setResult("");
        bean.setStack(getView().getStack());

        bean.setiFromCell(bean.getCell());
        bean.setiFromRow(bean.getRown());
        bean.setiFromTier(bean.getTier());

        bean.setiToRow(getView().getBay());
        bean.setiToCell(String.valueOf(toCell));
        bean.setiToTier(String.valueOf(toTier));


        ParamObject m = new ParamObject("CntrDaoConfirm");
        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }


    /**
     * 放箱子数据体
     *
     * @param toCell
     * @param toTier
     * @return
     */
    private String getPutBoxRequestData(int toCell, int toTier) {
        BoxDetalBean bean = getView().getBoxDealBean();
        bean.setCntr(getView().getBoxDealBean().getCntr());
        bean.setCrane(getView().getUser().getCRANE());
        bean.setSignonUSERID(MyApplication.user.getSignonUSERID());
        bean.setUser(MyApplication.user.getSignonUSERID());
        bean.setResult("Y");
        bean.setCell(String.valueOf(toCell));
        bean.setTier(String.valueOf(toTier));
        bean.setiToRow(getView().getBay());
        bean.setiToCell(String.valueOf(toCell));
        bean.setiToTier(String.valueOf(toTier));
        bean.setiFromCell("");
        bean.setiFromRow("");
        bean.setiFromRow("");
        switch (bean.getStatus()) {
            case "PW"://场外取消放箱
            case "WD"://场内中转取消放箱
                break;
            default://放箱,移箱
                bean.setCell(String.valueOf(toCell));
                bean.setTier(String.valueOf(toTier));
                break;
        }
        bean.setIsCheck("N");
        ParamObject m = new ParamObject("CntrDaoConfirm");
        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }


    /**
     * 提箱子数据体
     *
     * @return
     */
    private String getUpBoxRequestData(BoxDetalBean bean) {
        bean.setCrane(getView().getUser().getCRANE());

        bean.setSignonUSERID(MyApplication.user.getSignonUSERID());
        bean.setUser(MyApplication.user.getSignonUSERID());
        bean.setResult("Y");
        bean.setStatus(getView().getSTATUS());//状态

        bean.setiFromCell(bean.getCell());
        bean.setiFromRow(bean.getRown());
        bean.setiFromTier(bean.getTier());

        bean.setiToRow("");
        bean.setiToCell("");
        bean.setiToTier("");

        ParamObject m = new ParamObject("CntrDaoConfirm");
        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }

    /**
     * 提箱子数据体
     *
     * @return
     */
    private String getUpBoxRequestData2() {
        BoxDetalBean bean = null;
        bean = (BoxDetalBean) getView().getUpDealBean().clone();
        bean.setStatus(getView().getSTATUS());//状态
        bean.setCntr(getView().getBoxDealBean().getCntr());
        bean.setCntrNo(null);
        bean.setCrane(getView().getUser().getCRANE());

        bean.setSignonUSERID(MyApplication.user.getSignonUSERID());
        bean.setUser(MyApplication.user.getSignonUSERID());
        bean.setResult("Y");

        bean.setiFromCell(getView().getUpDealBean().getCell());
        bean.setiFromRow(getView().getUpDealBean().getRown());
        bean.setiFromTier(getView().getUpDealBean().getTier());

        bean.setiToRow("");
        bean.setiToCell("");
        bean.setiToTier("");

        ParamObject m = new ParamObject("CntrDaoConfirm");
        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }


    /**
     * 判断箱子是否可交换
     *
     * @param currCNTR   默认要提的箱子
     * @param changeCNTR 要选择交换的箱子
     * @return
     */
    private String getCheckChangeBoxRequestData(String currCNTR, String changeCNTR) {
        ParamObject m = new ParamObject("CheckChangeCntrEx");
        String obj = currCNTR + ";" + changeCNTR + ";" + getView().getUser().getSignonUSERID();
        String b = "\"" + obj + "\"";
        m.setBizObject(b);
        return m.toString();
    }


    /**
     * 获取交换箱请求数据
     *
     * @return
     */
    private String getChangeBoxRequestData() {
        BoxDetalBean bean = getView().getBoxDealBean();
        bean.setStatus(getView().getUpDealBean().getStatus());//状态
        bean.setSvc_Code(getView().getUpDealBean().getSvc_Code());
        bean.setActivity(getView().getUpDealBean().getActivity());
        bean.setStack(getView().getUpDealBean().getStack());
        bean.setTrk(getView().getUpDealBean().getTrk());
        bean.setJob_Id(getView().getUpDealBean().getJob_Id());
        bean.setBkg(getView().getUpDealBean().getBkg());// 预约号
        bean.setCrane(getView().getUser().getCRANE());
        bean.setLST_TX_USER(MyApplication.user.getSignonUSERID());//用户名
        bean.setLST_TX("RFCWS");
        bean.setIsCheck("N");
        bean.setiToRow("");
        bean.setiToCell("");
        bean.setiToTier("");

        ParamObject m = new ParamObject("CraneConfirmEx");

        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }


    /**
     * 模糊搜索
     *
     * @param param
     * @return
     */
    private String getSearchData(String param) {
        BizObject bizObject = new BizObject();
        bizObject.setCntr(param);
        ParamObject m = new ParamObject("GetCntrListByCntr");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }

    /**
     * 抄场清场
     *
     * @return
     */
    private String getClearBox() {
        BizObject bizObject = new BizObject();
        bizObject.setStack(getView().getStack());
        bizObject.setRown(getView().getBay());
        bizObject.setLST_TX_USER(getView().getUser().getSignonUSERID());
        ParamObject m = new ParamObject("CraneYardCheck");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }

    /**
     * 获取放箱推荐位数据题
     *
     * @return
     */
    private String getSysParam(String param) {
        ParamObject m = new ParamObject("GetSysParam");
        BoxDetalBean bean = new BoxDetalBean();
        bean.setActivity(param);
        m.setBizObject(bean.toString());
        return m.toString();
    }

    /**
     * 获取放箱推荐位数
     *
     * @return
     */
    private String getCntrCellForRFCWSParam() {
        ParamObject m = new ParamObject("GetCntrCellForRFCWS");
        BoxDetalBean bean = (BoxDetalBean) getView().getUpDealBean().clone();

        String sCell = bean.getCell();
        BoxDetalBean cntrInfo = new BoxDetalBean();
        cntrInfo.setActivity(bean.getActivity());
        cntrInfo.setCntr(bean.getCntr());
        cntrInfo.setStack(getView().getStack());
        cntrInfo.setRown(getView().getBay());
        cntrInfo.setARV_TYPE(bean.getARV_TYPE());

        if (bean.getActivity().equals("PK") || bean.getActivity().equals("IP") || bean.getActivity().equals("LD")) {
            cntrInfo.setActivity("RM");
        }
        if (bean.getActivity().equals("IG"))
            sCell = "0";

        if (!bean.getRown().equals(getView().getBay())) {
            sCell = "0";
        } else {
            cntrInfo.setCell(sCell);
        }
        cntrInfo.setMaj_Loc(getView().getUser().getSignonMAJLOC());
        cntrInfo.setSub_Loc(getView().getUser().getSignonSUBLOC());
        cntrInfo.setSignonMAJLOC(getView().getUser().getSignonMAJLOC());
        cntrInfo.setSignonSUBLOC(getView().getUser().getSignonSUBLOC());
        m.setBizObject(cntrInfo.toString());
        return m.toString();
    }

    /**
     * 根据bay 获取箱子列表
     *
     * @param bay
     * @return
     */
    private String getCntrListByCondition(String bay, String cntr) {
        BizObject bizObject = new BizObject();
        bizObject.setBay(bay);
        bizObject.setCntr(cntr);
        ParamObject m = new ParamObject("GetCntrListByCondition");
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
     * 验证能否提箱
     *
     * @param boxDetalBean
     * @return
     */
    private String getValidOperationParam(BoxDetalBean boxDetalBean) {
        BoxDetalBean cntrInfo = boxDetalBean;
        cntrInfo.setMaj_Loc(getView().getUser().getSignonMAJLOC());
        cntrInfo.setSub_Loc(getView().getUser().getSignonSUBLOC());
        cntrInfo.setSignonMAJLOC(getView().getUser().getSignonMAJLOC());
        cntrInfo.setSignonSUBLOC(getView().getUser().getSignonSUBLOC());
//        cntrInfo.setCntr(boxDetalBean.getCntr());
        cntrInfo.setTrk(TextUtils.isEmpty(boxDetalBean.getTrk()) ? "********" : boxDetalBean.getTrk());
//        cntrInfo.setTrk_Type(boxDetalBean.getTrk_Type());
//        if(!MyApplication.isChange){
        cntrInfo.setActivity(TextUtils.isEmpty(boxDetalBean.getActivity()) ? "RM" : boxDetalBean.getActivity());
//        }else{
//            cntrInfo.setActivity("PK");
//        }
        ParamObject m = new ParamObject("IsValidOperation");
//        if(!MyApplication.isChange){
        m.setBizObject(cntrInfo.toString());
//        }else {
//            MyApplication.myBean.setActivity("PK");
//            m.setBizObject(MyApplication.myBean.toString());
//        }
        return m.toString();
    }

    /**
     * 获取拖车size大小
     *
     * @param trk
     * @return
     */
    private String getTruckSizeParam(String trk) {
        BizObject bizObject = new BizObject();
        bizObject.setTrk(trk);
        ParamObject m = new ParamObject("GetTruckSize");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }

    /**
     * 判断拖车是否放完箱子参数
     *
     * @param trk
     * @return
     */
    private String getIsPKHasGRParam(String trk) {
        BizObject bizObject = new BizObject();
        bizObject.setTrk(trk);
        ParamObject m = new ParamObject("IsPKHasGR");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }


    /**
     * 获取任务列表
     *
     * @return
     */
    private String getBaseListrParam(DataType dataType, String trk) {
        BasicParam bizObject = new BasicParam(dataType.getType(), trk);
        ParamObject paramObject = new ParamObject("GetBasicData");
        paramObject.setBizObject(bizObject.toString());
        return paramObject.toString();
    }

}
