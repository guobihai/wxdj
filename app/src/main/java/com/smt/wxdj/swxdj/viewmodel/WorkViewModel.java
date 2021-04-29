package com.smt.wxdj.swxdj.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.viewmodel.api.WorkInterface;
import com.smt.wxdj.swxdj.viewmodel.nbean.ChaneStackInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.NMachineInfo;
import com.smt.wxdj.swxdj.network.RetrofitManager;
import com.smt.wxdj.swxdj.network.account.AccountManager;
import com.smt.wxdj.swxdj.network.observer.ResponseObserver;
import com.smt.wxdj.swxdj.network.utils.RxUtils;
import com.smt.wxdj.swxdj.param.ParamUtils;
import com.smt.wxdj.swxdj.utils.JsonUtils;
import com.smt.wxdj.swxdj.viewmodel.nbean.OprTaskInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayCntrInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardTaskInfo;
import com.smtlibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorkViewModel extends ViewModel {

    //设备列表
    private MutableLiveData<List<NMachineInfo>> machineList;

    //设备场地区域
    private MutableLiveData<List<ChaneStackInfo>> ChaneStackInfoList;
    //呗位信息
    private MutableLiveData<List<YardBayInfo>> YardBayInfoList;
    //作业任务列表
    private MutableLiveData<List<YardTaskInfo>> YardCntrInfoList;

    //贝位信息(包含箱子信息)
    private MutableLiveData<YardBayCntrInfo> cntrInfoMutableLiveData;
    //箱子对应的键值对信息
    private MutableLiveData<Map<String, YardCntrInfo>> mCntrYardCntrInfo;
    //倒箱信息
    private MutableLiveData<List<YardCntrInfo>> mCurBayCntrInfo;//贝位中的箱子
    private MutableLiveData<List<YardCntrInfo>> mDwListCntrInfo;//倒箱的信息


    public WorkViewModel() {
        machineList = new MutableLiveData<>();
        ChaneStackInfoList = new MutableLiveData<>();
        YardBayInfoList = new MutableLiveData<>();
        YardCntrInfoList = new MutableLiveData<>();
        cntrInfoMutableLiveData = new MutableLiveData<>();
        mCntrYardCntrInfo = new MutableLiveData<>();
        mCurBayCntrInfo = new MutableLiveData<>();
    }

    public MutableLiveData<List<NMachineInfo>> getMachineList() {
        return machineList;
    }

    public MutableLiveData<List<ChaneStackInfo>> getChaneStackInfoList() {
        return ChaneStackInfoList;
    }

    public MutableLiveData<List<YardTaskInfo>> getYardCntrInfoList() {
        return YardCntrInfoList;
    }

    public MutableLiveData<YardBayCntrInfo> getCntrInfoMutableLiveData() {
        return cntrInfoMutableLiveData;
    }

    public MutableLiveData<List<YardCntrInfo>> getCurBayCntrInfo() {
        return mCurBayCntrInfo;
    }

    public MutableLiveData<Map<String, YardCntrInfo>> getCntrYardCntrInfo() {
        if (null == mCntrYardCntrInfo) mCntrYardCntrInfo = new MutableLiveData<>();
        return mCntrYardCntrInfo;
    }

    //倒箱
    public MutableLiveData<List<YardCntrInfo>> getDwListCntrInfo() {
        if(null == mDwListCntrInfo)mDwListCntrInfo = new MutableLiveData<>();
        return mDwListCntrInfo;
    }

    /**
     * 获取当前吊机号
     */
    public void getCurMachineNo() {
        RetrofitManager.createToken(WorkInterface.class)
                .getJobTicketEqpSearch(ParamUtils.getCurChaneParam(AccountManager.getUser()))
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<NMachineInfo>>() {
                    @Override
                    public void onSuccess(List<NMachineInfo> data) {
                        LogUtils.sysout("===吊机列表数据===", JsonUtils.serialize(data));
                        machineList.setValue(data);
                    }

                    @Override
                    public void onError(String code, String msg) {
                        super.onError(code, msg);
                        LogUtils.sysout("==error code===" + code, msg);
                    }
                });

    }


    /**
     * 根据设备ID获取有作业任务的作业街区
     *
     * @param chanId
     */
    public void getJobTicketTaskStack(String chanId) {
        RetrofitManager.createToken(WorkInterface.class)
                .getJobTicketTaskStack(chanId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<ChaneStackInfo>>() {
                    @Override
                    public void onSuccess(List<ChaneStackInfo> data) {
                        ChaneStackInfoList.setValue(data);
                        LogUtils.sysout("===getCurTaskListInfo===", JsonUtils.serialize(data));
//                        GetYardBayListByBlockId("39fb7959-6e5f-8549-fa18-ec69cc317f00");
//                        GetYardBayListBySiteId("39fb1302-e790-9243-06df-83853d8b948e",true,true);
//                        GetYardBlockListBySiteId("39fb1302-e790-9243-06df-83853d8b948e",true,true);
//                        GetYardBlockGetByBlockId("39fb7959-6e5f-8549-fa18-ec69cc317f00",true,true);
//                        GetTrkWorkByBlockId("39fb7959-6e5f-8549-fa18-ec69cc317f00","");
//                        GetTrkWorkIsUPByBlockId("39fb7959-6e5f-8549-fa18-ec69cc317f00");
//                        GetTrkWorkIsCTCByBlockId("39fb7959-6e5f-8549-fa18-ec69cc317f00");
                    }
                });

    }


    /**
     * 根据场站ID获取贝位
     *
     * @param blockId
     */
    public void GetYardBayListByBlockId(String blockId) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetYardBayListByBlockId(blockId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<YardBayInfo>>() {
                    @Override
                    public void onSuccess(List<YardBayInfo> data) {
                        YardBayInfoList.setValue(data);
                        String strData = JsonUtils.serialize(data);
                        LogUtils.sysout("===根据场站ID获取贝位===", strData);
                    }
                });
    }

    /**
     * 根据贝位获取id 获取列信息
     *
     * @param bayId
     */
    public void GetListByBayId(String bayId) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetListByBayId(bayId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data) {
                        String strData = JsonUtils.serialize(data);
                        LogUtils.sysout("===根据贝位获取id 获取列信息===", strData);
                    }
                });
    }


    /**
     * 获取贝位信息，包括箱子信息
     *
     * @param bayId
     */
    public void GetWithCntrByBayId(String bayId) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetWithCntr(bayId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<YardBayCntrInfo>() {
                    @Override
                    public void onSuccess(YardBayCntrInfo data) {
                        String strData = JsonUtils.serialize(data);
                        LogUtils.sysout("===获取贝位信息，包括箱子信息===", strData);
                        if (null != data.getContainers()) {
                            List<YardCntrInfo> cntrList = new ArrayList<>(data.getContainers());
                            List<YardCntrInfo> dxList = new ArrayList<>();
                            Map<String, YardCntrInfo> maps = new HashMap<>();
                            for (YardCntrInfo cntrInfo : data.getContainers()) {
                                if (cntrInfo.getCell().equals("0")) {
                                    dxList.add(cntrInfo);
//                                    cntrList.remove(cntrInfo);
                                }
                                //如果相等，替换表格数据，箱子填充
                                cntrInfo.setDefaultCell(String.format("(%s,%s)", cntrInfo.getCurCell(), cntrInfo.getCurTier()));
                                maps.put(cntrInfo.getDefaultCell(), cntrInfo);
                            }
                            mCurBayCntrInfo.setValue(cntrList);
                            getCntrYardCntrInfo().setValue(maps);
                            getDwListCntrInfo().setValue(dxList);
                        }
                        cntrInfoMutableLiveData.setValue(data);
                    }
                });
    }

    /**
     * 根据场站ID获取街区信息
     *
     * @param siteId
     * @param includeCell
     * @param includeCntr
     */
    public void GetYardBayListBySiteId(String siteId, boolean includeCell, boolean includeCntr) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetYardBayListBySiteId(siteId, includeCell, includeCntr)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data) {
                        LogUtils.sysout("===根据场站ID获取街区信息===", JsonUtils.serialize(data));
                    }
                });
    }


    /**
     * 根据场站ID获取街区信息
     *
     * @param siteId
     * @param includeCell
     * @param includeCntr
     */
    public void GetYardBlockListBySiteId(String siteId, boolean includeCell, boolean includeCntr) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetYardBlockListBySiteId(siteId, includeCell, includeCntr)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data) {
                        LogUtils.sysout("===根据场站ID获取街区信息===", JsonUtils.serialize(data));
                    }
                });
    }

    /**
     * 根据场站区域ID获取街区信息
     *
     * @param areaId
     * @param includeCell
     * @param includeCntr
     */
    public void GetYardBlockGetListByAreaId(String areaId, boolean includeCell, boolean includeCntr) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetYardBlockGetListByAreaId(areaId, includeCell, includeCntr)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data) {
                        LogUtils.sysout("===根据场站区域ID获取街区信息===", JsonUtils.serialize(data));
                    }
                });
    }

    /**
     * 根据街区信息ID获取街区信息
     *
     * @param blockId
     * @param includeCell
     * @param includeCntr
     */
    public void GetYardBlockGetByBlockId(String blockId, boolean includeCell, boolean includeCntr) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetYardBlockGetByBlockId(blockId, includeCell, includeCntr)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data) {
                        LogUtils.sysout("===根据街区信息ID获取街区信息===", JsonUtils.serialize(data));
                    }
                });
    }

    /**
     * 获取指定街区的作业任务列表
     *
     * @param blockId  街区Id
     * @param activity 作业类型Id
     */
    public void GetTrkWorkByBlockId(String blockId, String activity) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetTrkWorkByBlockId(blockId, activity)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<YardTaskInfo>>() {
                    @Override
                    public void onSuccess(List<YardTaskInfo> data) {
                        LogUtils.sysout("===获取指定街区的作业任务列表===", JsonUtils.serialize(data));
                        YardCntrInfoList.setValue(data);
                    }
                });
    }

    /**
     * 获取指定街区的取消提箱任务列表
     *
     * @param blockId 街区Id
     */
    public void GetTrkWorkIsUPByBlockId(String blockId) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetTrkWorkIsUPByBlockId(blockId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<YardTaskInfo>>() {
                    @Override
                    public void onSuccess(List<YardTaskInfo> data) {
                        LogUtils.sysout("===获取指定街区的取消提箱任务列表===", JsonUtils.serialize(data));
                        YardCntrInfoList.setValue(data);
                    }
                });
    }

    /**
     * 获取指定街区的倒箱任务列表
     *
     * @param blockId 街区Id
     */
    public void GetTrkWorkIsCTCByBlockId(String blockId) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetTrkWorkIsCTCByBlockId(blockId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<YardTaskInfo>>() {
                    @Override
                    public void onSuccess(List<YardTaskInfo> data) {
                        LogUtils.sysout("===获取指定街区的倒箱任务列表===", JsonUtils.serialize(data));
                        YardCntrInfoList.setValue(data);
                    }
                });
    }

    /**
     * 放箱确认
     *
     * @param yardSiteId  yardSiteId	 场站Id
     * @param containerId 集装箱Id
     * @param yardBayId   贝位Id
     * @param trkWorkId   拖车作业Id
     * @param cell        目标层
     * @param tier        目标高
     */
    public void GroundConfirm(String yardSiteId, String containerId, String yardBayId, String trkWorkId, String cell, String tier) {
        Map<String, String> map = new HashMap<>();
        map.put("yardSiteId", yardSiteId);
        map.put("containerId", containerId);
        map.put("yardBayId", yardBayId);
        map.put("trkWorkId", trkWorkId);
        map.put("cell", cell);
        map.put("tier", tier);
        RetrofitManager.createToken(WorkInterface.class)
                .GroundConfirm(map)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        LogUtils.sysout("===GroundConfirm===", JsonUtils.serialize(data));
                    }
                });
    }


    /**
     * 提箱确认
     *
     * @param yardSiteId  yardSiteId	 场站Id
     * @param containerId 集装箱Id
     * @param yardBayId   贝位Id
     * @param trkWorkId   拖车作业Id
     */
    public void PickupConfirm(String yardSiteId, String containerId, String yardBayId, String trkWorkId) {
        Map<String, String> map = new HashMap<>();
        map.put("yardSiteId", yardSiteId);
        map.put("containerId", containerId);
        map.put("yardBayId", yardBayId);
        map.put("trkWorkId", trkWorkId);
        RetrofitManager.createToken(WorkInterface.class)
                .PickupConfirm(map)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        LogUtils.sysout("===PickupConfirm===", JsonUtils.serialize(data));
                    }
                });
    }


    /**
     * 倒箱确认
     *
     * @param yardSiteId  yardSiteId	 场站Id
     * @param containerId 集装箱Id
     * @param yardBayId   贝位Id
     * @param trkWorkId   拖车作业Id
     * @param cell        目标层
     * @param tier        目标高
     */
    public void PutOtherConfirm(String yardSiteId, String containerId, String yardBayId, String trkWorkId, String cell, String tier) {
        Map<String, String> map = new HashMap<>();
        map.put("yardSiteId", yardSiteId);
        map.put("containerId", containerId);
        map.put("yardBayId", yardBayId);
        map.put("trkWorkId", trkWorkId);
        map.put("cell", cell);
        map.put("tier", tier);
        RetrofitManager.createToken(WorkInterface.class)
                .PutOtherConfirm(map)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        LogUtils.sysout("===GroundConfirm===", JsonUtils.serialize(data));
                    }
                });
    }

    /**
     * 判断能否交换箱(获取提箱交换箱的信息)
     *
     * @param trkWorkId 拖车作业Id
     */
    public void ExchangeCntr(String trkWorkId) {
        RetrofitManager.createToken(WorkInterface.class)
                .ExchangeCntr(trkWorkId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        LogUtils.sysout("===PickupConfirm===", JsonUtils.serialize(data));
                    }
                });
    }


    /**
     * 10、获取作业线拖车列表
     *
     * @param truckId 拖车Id
     */
    public void GetSameWorkLineTrk(String truckId) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetSameWorkLineTrk(truckId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data) {
                        LogUtils.sysout("===GetSameWorkLineTrk===", JsonUtils.serialize(data));
                    }
                });
    }
}
