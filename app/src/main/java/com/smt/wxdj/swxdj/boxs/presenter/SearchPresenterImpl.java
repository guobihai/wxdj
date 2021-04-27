package com.smt.wxdj.swxdj.boxs.presenter;


import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.boxs.model.BoxModelImpl;
import com.smt.wxdj.swxdj.boxs.model.BoxsModel;
import com.smt.wxdj.swxdj.boxs.view.SearchView;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.webservice.BizObject;
import com.smt.wxdj.swxdj.webservice.ParamObject;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

import java.util.List;

/**
 * Created by gbh on 16/12/5.
 */

public class SearchPresenterImpl implements SearchPresenter {
    /**
     * 模糊搜索箱子
     *
     * @param param
     */
    private BoxsModel mBoxsModel;
    private SearchView searchView;

    public SearchPresenterImpl(SearchView searchView) {
        this.searchView = searchView;
        this.mBoxsModel = new BoxModelImpl();
    }


    /**
     * 附带任务箱号,模糊查询箱子
     *
     * @param param
     */
    @Override
    public void searchBox(String oldCntr, String param) {
        searchView.showProgress();
        mBoxsModel.searchBox(getSearchDataWithOldCntr(oldCntr, param), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (null == searchView) return;
                searchView.hideProgress();
//                searchView.setSearchResult((List<BoxDetalBean>) object);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (null == searchView) return;
                searchView.onFaile(msg);
            }
        });
    }

    /**
     * 直接提箱
     */
    @Override
    public void loadUpBoxTask() {
        searchView.showProgress();
        mBoxsModel.loadUpBoxTask(getUpBoxRequestData(), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                searchView.hideProgress();
                if (bol) {
                    searchView.onSuccess();
                } else
                    searchView.onFaile(MyApplication.getContext().getString(R.string.there_is_a_pressure_box_on_it));
            }

            @Override
            public void onFailure(String msg, Exception e) {
                searchView.hideProgress();
                searchView.onFaile(msg);
            }
        });
    }

    /**
     * 判断是否能够交换箱子
     *
     * @param currCNTR   要提的箱号
     * @param changeCNTR 当前交换的箱号
     */
    @Override
    public void LoadCheckChangeBox(String currCNTR, String changeCNTR) {
        searchView.showProgress();
        mBoxsModel.loadCheckChangeBoxTask(getCheckChangeBoxRequestData(currCNTR, changeCNTR), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (bol) {
                    LoadChangeBox();
                    LogUtils.sysout("bol", "" + bol);
                } else {
                    searchView.onFaile(MyApplication.getContext().getString(R.string.failure_to_meet_exchange_box_conditions));
                    searchView.hideProgress();
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (null == searchView) return;
                searchView.onFaile(MyApplication.getContext().getString(R.string.failure_to_meet_exchange_box_conditions));
                searchView.hideProgress();
            }
        });
    }

    /**
     * 交换箱子
     */
    @Override
    public void LoadChangeBox() {
        mBoxsModel.loadUpBoxTask(getChangeBoxRequestData(), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                searchView.hideProgress();
                if (bol) {
                    searchView.onSuccess();
                } else
                    searchView.onFaile(MyApplication.getContext().getString(R.string.there_is_a_pressure_box_on_it));
            }

            @Override
            public void onFailure(String msg, Exception e) {
                searchView.hideProgress();
                searchView.onFaile(msg);
            }
        });
    }

    /**
     * 模糊搜索
     *
     * @param param
     * @return
     */
    private String getSearchDataWithOldCntr(String oldCntr, String param) {
        BizObject bizObject = new BizObject();
        bizObject.setCNTR(param);
        bizObject.setOLDCNTR(oldCntr);
        ParamObject m = new ParamObject("FuzzyQueryCntrListByCntr");
        m.setBizObject(bizObject.toString());
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
        String obj = currCNTR + ";" + changeCNTR + ";" + MyApplication.user.getSignonUSERID();
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
        BoxDetalBean bean = searchView.getBoxDealBean();
        bean.setStatus(searchView.getUpDealBean().getStatus());//状态
        bean.setSvc_Code(searchView.getUpDealBean().getSvc_Code());
        bean.setActivity(searchView.getUpDealBean().getActivity());
        bean.setStack(searchView.getUpDealBean().getStack());
        bean.setTrk(searchView.getUpDealBean().getTrk());
        bean.setJob_Id(searchView.getUpDealBean().getJob_Id());
        bean.setBkg(searchView.getUpDealBean().getBkg());// 预约号
        bean.setCRANE(MyApplication.user.getCRANE());
        bean.setLST_TX_USER(MyApplication.user.getSignonUSERID());//用户名
        bean.setLST_TX("RFCWS");
        bean.setIsCheck("N");
        bean.setiToRow("");
        bean.setiToCell("");
        bean.setiToTier("");

        ParamObject m = new ParamObject("CraneConfirmNotOrderlyEx");
        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }


    /**
     * 提箱子数据体
     *
     * @return
     */
    private String getUpBoxRequestData() {
        BoxDetalBean bean = (BoxDetalBean) searchView.getUpDealBean().clone();
        bean.setStatus("PL");//状态
        bean.setCntr(searchView.getUpDealBean().getCntr());
        bean.setCntrNo(null);
        bean.setCRANE(MyApplication.user.getCRANE());
        bean.setLST_TX_USER(MyApplication.user.getSignonUSERID());//用户名
        bean.setLST_TX("RFCWS");
        bean.setCell(searchView.getUpDealBean().getiToCell());
        bean.setCell(searchView.getUpDealBean().getiToCell());
        bean.setTier(searchView.getUpDealBean().getiToTier());
        bean.setTier(searchView.getUpDealBean().getiToTier());
        bean.setiToRow("");
        bean.setiToCell("");
        bean.setiToTier("");

        ParamObject m = new ParamObject("CraneConfirmNotOrderlyEx");
        m.setBizObject(JsonUtils.serialize(bean));
        return m.toString();
    }
}
