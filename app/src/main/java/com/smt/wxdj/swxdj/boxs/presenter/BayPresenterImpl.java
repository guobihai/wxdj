package com.smt.wxdj.swxdj.boxs.presenter;

import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.boxs.model.BoxModelImpl;
import com.smt.wxdj.swxdj.boxs.model.BoxsModel;
import com.smt.wxdj.swxdj.boxs.view.BayView;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.webservice.BizObject;
import com.smt.wxdj.swxdj.webservice.ParamObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbh on 16/7/4.
 * 实现字典接口
 */
public class BayPresenterImpl implements BayPresenter {

    private BayView mBayView;
    private BoxsModel mBoxModelImpl;

    public BayPresenterImpl(BayView bayView) {
        this.mBayView = bayView;
        mBoxModelImpl = new BoxModelImpl();
    }


    /**
     * 获取所有的场地
     */
    @Override
    public void getAllStackList() {
        mBayView.showProgress();
        mBoxModelImpl.LoadBoxCd(getBoxCdRequestData(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (object instanceof List) {
                    List<StackBean> list = new ArrayList<>();
                    List<StackBean> data = (List<StackBean>) object;
                    for (StackBean bean : data) {
                        if (!bean.getStack().trim().equals("CHECK"))
                            list.add(bean);
                    }
                    mBayView.addListCd(list);
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBayView.onFaile(msg);
            }
        });
    }

    /**
     * 根据场地获取田位信息
     *
     * @param stack
     */
    @Override
    public void getSelectedBay(String stack) {
        mBayView.showProgress();
        mBoxModelImpl.getSelectedBay(getBayByStackData(stack), new IPublicResultInterface<List<String>>() {
            @Override
            public void onSucess(List<String> bayList) {
                mBayView.addListBay(bayList);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mBayView.onFaile(msg);
            }
        });
    }


    /**
     * 场地
     *
     * @return
     */
    private String getBoxCdRequestData() {
        BizObject bizObject = new BizObject();
        ParamObject m = new ParamObject("GetAllStackList");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }

    /**
     * 贝位信息
     *
     * @return
     */
    private String getBayByStackData(String stack) {
        BizObject bizObject = new BizObject();
        bizObject.setStack(stack);
        ParamObject m = new ParamObject("GetSelectedBay");
        m.setBizObject(bizObject.toString());
        return m.toString();
    }
}
