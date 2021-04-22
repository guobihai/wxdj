package com.smt.wxdj.swxdj.boxs.view;

import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.User;

import java.util.List;

/**
 * Created by gbh on 16/7/6.
 */
public interface TaskBoxView<T> {

    /**
     * 当前登录用户
     *
     * @return
     */
    User getUser();

    /**
     * 当前选择的场地
     *
     * @return
     */
    String getCurStack();

    /**
     * 显示加载进度
     *
     * @param type
     */
    void showProgress(int type);

    /**
     * 隐藏进度
     */
    void hideProgress();

    /**
     * 箱子列表
     *
     * @param list
     */
    void addList(List<T> list);


    /**
     * 加载失败结果
     *
     * @param msg
     */
    void onFaile(String msg);


    /**
     * 场地数据
     *
     * @param list
     */
    void addListCd(List<StackBean> list);

    /**
     * 贝位信息
     *
     * @param bay
     */
    void addBay(Bay bay);

    /**
     * 场地没有被锁住
     *
     * @param list
     */
    void unLockStackList(List<StackBean> list);

    /**
     * 场地被锁住原因
     */
    void lockStack();

    /**
     * 卸船自动放箱
     */
    void putBoxForCellData(Object object);
}
