package com.smt.wxdj.swxdj.boxs.view;

import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.Trk;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.enums.BOXCTRLTYPE;

import java.util.List;
import java.util.Map;

/**
 * Created by gbh on 16/6/27.
 */
public interface BoxsView {

    User getUser();

    /**
     * 获取枚举类型
     *
     * @return
     */
    BOXCTRLTYPE getBoxCtrlType();

    /**
     * 获取箱子对象
     *
     * @return
     */
    BoxDetalBean getBoxDealBean();

    /**
     * 获取提箱子对象
     *
     * @return
     */
    BoxDetalBean getUpDealBean();

    /**
     * h获取场地名称
     *
     * @return
     */
    String getStack();

    /**
     * 获取呗/田位
     *
     * @return
     */
    String getBay();


    /**
     * 显示加载进度
     */
    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();

    /**
     * 箱子列表
     *
     * @param list
     */
    void addList(List<BoxDetalBean> list, Map<String, BoxDetalBean> maps);

    /**
     * 倒箱位数据
     *
     * @param dxwList
     */
    void addDxwList(List<BoxDetalBean> dxwList, Map<String, BoxDetalBean> maps);

    /**
     * 放箱推荐位
     *
     * @param rmList
     */
    void addRmList(List<String> rmList);

    /**
     * 贝位信息
     *
     * @param bay
     */
    void addBay(Bay bay);

    /**
     * 更新放箱
     */
    void updateAdapt();

    /**
     * 更新提箱后的数据
     */
    void updateUpBoxData();

    /**
     * 更新放箱后的数据
     */
    void updatePutBoxData();

    /**
     * 检查交换箱子的结果
     */
    void checkChangeBoxSucess();

    /**
     * 不符合交换箱子
     */
    void checkChangeBoxFaile();

    /**
     * 获取处理状态
     *
     * @return
     */
    String getSTATUS();


    /**
     * 抄场结果
     */
    void putBoxToParkingSpaceResult();

    /**
     * 模糊搜索箱子结果
     *
     * @param list
     */
    void setSearchResult(List<BoxDetalBean> list);

    /**
     * 加载失败结果
     *
     * @param msg
     */
    void onFaile(String msg);

    void setTrkList(List<Trk> trkList);

}
