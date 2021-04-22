package com.smt.wxdj.swxdj.boxs.view;

import com.smt.wxdj.swxdj.bean.StackBean;

import java.util.List;

/**
 * Created by gbh on 17/1/4.
 */

public interface BayView {
    void showProgress();

    /**
     * 场地数据
     *
     * @param list
     */
    void addListCd(List<StackBean> list);

    /**
     * 田位数据
     *
     * @param dbay
     */
    void addListBay(List<String> dbay);



    void onFaile(String msg);

}
