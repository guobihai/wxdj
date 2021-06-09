package com.smt.wxdj.swxdj.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.viewmodel.api.WorkInterface;
import com.smt.wxdj.swxdj.viewmodel.nbean.ChaneStackInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.NMachineInfo;
import com.smt.wxdj.swxdj.network.RetrofitManager;
import com.smt.wxdj.swxdj.network.account.AccountManager;
import com.smt.wxdj.swxdj.network.observer.ResponseObserver;
import com.smt.wxdj.swxdj.network.utils.RxUtils;
import com.smt.wxdj.swxdj.param.ParamUtils;
import com.smt.wxdj.swxdj.utils.JsonUtils;
import com.smt.wxdj.swxdj.viewmodel.nbean.RmdLocationInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayCntrInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardTaskInfo;
import com.smtlibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class WorkViewModel extends ViewModel {

    private boolean isLog = true;

    public static final int UP_BOX = 1;//提箱子
    public static final int PUT_BOX = 2;//放箱子
    public static final int MOVE_BOX = 3;//移动箱子
    public static final int DX_BOX = 4;//倒箱子

    public MutableLiveData<String> ErrorMsg = new MutableLiveData<>();
    //任务类型
    public MutableLiveData<Integer> DoTaskStatus = new MutableLiveData<>();

    //设备列表
    private MutableLiveData<List<NMachineInfo>> machineList;

    //设备场地区域
    private MutableLiveData<List<ChaneStackInfo>> ChaneStackInfoList;
    public MutableLiveData<List<ChaneStackInfo>> ALlChaneStackInfoList;
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

    public MutableLiveData<List<YardCntrInfo>> mSearchCntrInfo;//搜索的箱子信息

    public MutableLiveData<RmdLocationInfo> mRmdLocationInfo;//最优推荐位
    public MutableLiveData<List<String>> mRmdCellList;//最优推荐位


    public WorkViewModel() {
        machineList = new MutableLiveData<>();
        ChaneStackInfoList = new MutableLiveData<>();
        YardCntrInfoList = new MutableLiveData<>();
        cntrInfoMutableLiveData = new MutableLiveData<>();
        mCntrYardCntrInfo = new MutableLiveData<>();
        mCurBayCntrInfo = new MutableLiveData<>();
        mRmdLocationInfo = new MutableLiveData<>();
        mRmdCellList = new MutableLiveData<>();
        mSearchCntrInfo = new MutableLiveData<>();
        ALlChaneStackInfoList = new MutableLiveData<>();
    }

    public MutableLiveData<List<YardBayInfo>> getYardBayInfoList() {
        if (null == YardBayInfoList) YardBayInfoList = new MutableLiveData<>();
        return YardBayInfoList;
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
        if (null == mDwListCntrInfo) mDwListCntrInfo = new MutableLiveData<>();
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
                        Collections.sort(data, new ChaneStackInfo());
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

                    @Override
                    public void onError(String code, String msg) {
                        super.onError(code, msg);
                        ErrorMsg.setValue("网络异常"+code);
                    }
                });

    }


    /**
     * 根据设备ID获取有作业任务的作业街区
     *
     * @param chanId
     */
    public void getJobTicketListStackByCraneId(String chanId) {
        RetrofitManager.createToken(WorkInterface.class)
                .getJobTicketListStackByCraneId(ParamUtils.getCurListStack(chanId))
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<ChaneStackInfo>>() {
                    @Override
                    public void onSuccess(List<ChaneStackInfo> data) {
                        Collections.sort(data, new ChaneStackInfo());
                        ALlChaneStackInfoList.setValue(data);
                        LogUtils.sysout("===getJobTicketListStackByCraneId===", JsonUtils.serialize(data));
                    }

                    @Override
                    public void onError(String code, String msg) {
                        super.onError(code, msg);
                        ErrorMsg.setValue("网络异常"+code);
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
                        Collections.sort(data, new YardBayInfo());
                        getYardBayInfoList().setValue(data);
                        String strData = JsonUtils.serialize(data);
                        LogUtils.sysout("===根据场站ID获取贝位===", strData);
                    }

                    @Override
                    public void onError(String code, String msg) {
                        super.onError(code, msg);
                        ErrorMsg.setValue("网络异常"+code);
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

                    @Override
                    public void onError(String code, String msg) {
                        super.onError(code, msg);
                        ErrorMsg.setValue("网络异常"+code);
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
                .subscribe(new ResponseObserver<Object>() {
                    @Override
                    public void onSuccess(Object data) {
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
                        String str = JsonUtils.serialize(data);
                        LogUtils.sysout("===获取指定街区的作业任务列表===", str);
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
                        String st = JsonUtils.serialize(data);
                        LogUtils.sysout("===获取指定街区的倒箱任务列表===", JsonUtils.serialize(data));
                        YardCntrInfoList.setValue(data);
                    }

                    @Override
                    public void onError(String code, String msg) {
                        super.onError(code, msg);
                        ErrorMsg.setValue("网络异常"+code);
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
    @SuppressLint("CheckResult")
    public void GroundConfirm(String yardSiteId, String containerId, String yardBayId, String trkWorkId, String craneId, int cell, int tier) {
        Map<String, Object> map = new HashMap<>();
        map.put("yardSiteId", yardSiteId);
        map.put("containerId", containerId);
        map.put("yardBayId", yardBayId);
        map.put("trkWorkId", trkWorkId);
        map.put("craneId", craneId);
        map.put("cell", cell);
        map.put("tier", tier);
        if (isLog) {
            System.out.println(JsonUtils.serialize(map));
        }
//        DoTaskStatus.setValue(PUT_BOX);
        RetrofitManager.createToken(WorkInterface.class)
                .GroundConfirm(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Consumer<Response>) response -> {
                            int code = response.code();
                            if (code == 200 || code == 204) {
                                DoTaskStatus.setValue(PUT_BOX);
                            } else {
                                praseErrorMsg(response);
                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            ErrorMsg.setValue(throwable.toString());
                        }

                );
    }


    /**
     * 提箱确认
     *
     * @param yardSiteId  yardSiteId	 场站Id
     * @param containerId 集装箱Id
     * @param yardBayId   贝位Id
     * @param trkWorkId   拖车作业Id
     */
    @SuppressLint("CheckResult")
    public void PickupConfirm(String yardSiteId, String containerId, String yardBayId, String trkWorkId,
                              String craneId) {
        Map<String, String> map = new HashMap<>();
        map.put("yardSiteId", yardSiteId);
        map.put("containerId", containerId);
        map.put("yardBayId", yardBayId);
        map.put("trkWorkId", trkWorkId);
        map.put("craneId", craneId);
        if (isLog) {
            System.out.println(JsonUtils.serialize(map));
        }
        RetrofitManager.createToken(WorkInterface.class)
                .PickupConfirm(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Consumer<Response>) response -> {
                            int code = response.code();
                            if (code == 200 || code == 204) {
                                DoTaskStatus.setValue(UP_BOX);
                            } else {
                                praseErrorMsg(response);
                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            ErrorMsg.setValue(throwable.toString());
                        }

                );
    }

    /**
     * 倒箱任务提箱
     *
     * @param yardSiteId    yardSiteId	 场站Id
     * @param containerId   集装箱Id
     * @param fromYardBayId 提箱子的贝位
     * @param toYardBayId   放箱子的贝位
     * @param trkWorkId     拖车作业Id
     */
    @SuppressLint("CheckResult")
    public void PickupConfirmByDx(String yardSiteId,
                                  String containerId,
                                  String fromYardBayId,
                                  String toYardBayId,
                                  String trkWorkId,
                                  String craneId, int cell, int tier) {
        Map<String, String> map = new HashMap<>();
        map.put("yardSiteId", yardSiteId);
        map.put("containerId", containerId);
        map.put("yardBayId", fromYardBayId);
        map.put("trkWorkId", trkWorkId);
        map.put("craneId", craneId);
        if (isLog) {
            System.out.println(JsonUtils.serialize(map));
        }
        RetrofitManager.createToken(WorkInterface.class)
                .PickupConfirm(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Consumer<Response>) response -> {
                            int code = response.code();
                            if (code == 200 || code == 204) {
//                                DoTaskStatus.setValue(UP_BOX);
                                GroundConfirm(yardSiteId, containerId, toYardBayId, trkWorkId, craneId, cell, tier);
                            } else {
                                praseErrorMsg(response);
                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            ErrorMsg.setValue(throwable.toString());
                        }

                );
    }

    /**
     * 倒箱确认
     *
     * @param yardSiteId  yardSiteId	 场站Id
     * @param containerId 集装箱Id
     * @param yardBayId   贝位Id
     * @param craneId     吊机id
     * @param cell        目标层
     * @param tier        目标高
     */
    @SuppressLint("CheckResult")
    public void PutOtherConfirm(String yardSiteId,
                                String containerId,
                                String yardBayId,
                                String trkWorkId,
                                String craneId,
                                int cell, int tier) {
        Map<String, Object> map = new HashMap<>();
        map.put("yardSiteId", yardSiteId);
        map.put("containerId", containerId);
        map.put("yardBayId", yardBayId);
        map.put("trkWorkId", trkWorkId);
        map.put("craneId", craneId);
        map.put("cell", cell);
        map.put("tier", tier);

        if (isLog) {
            System.out.println(JsonUtils.serialize(map));
        }
//        DoTaskStatus.setValue(MOVE_BOX);
        RetrofitManager.createToken(WorkInterface.class)
                .GroundConfirm(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                               @Override
                               public void accept(Response response) throws Exception {
                                   int code = response.code();
                                   if (code == 200 || code == 204) {
                                       DoTaskStatus.setValue(-1);
                                       DoTaskStatus.setValue(MOVE_BOX);
                                   } else {
                                       praseErrorMsg(response);
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                ErrorMsg.setValue(throwable.toString());
                            }
                        }

                );
    }

    private void praseErrorMsg(Response response) {
        try {
            ResponseBody responseBody = response.errorBody();
            if (null == responseBody) return;
            String res = responseBody.string();
            JsonObject object = new JsonObject();
            JsonParser parser = new JsonParser();
            object = parser.parse(res).getAsJsonObject();
            object = object.getAsJsonObject("error");
            String message = object.get("message").getAsString();
            ErrorMsg.setValue(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断能否交换箱(获取提箱交换箱的信息)
     *
     * @param cntr       要交换的箱子
     * @param curCraneId 设备
     * @param trkWorkId  拖车作业Id
     */
    public void ExchangeCntr(String cntr, String curCraneId, String trkWorkId) {
        RetrofitManager.createToken(WorkInterface.class)
                .ExchangeCntr(trkWorkId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<YardCntrInfo>>() {
                    @Override
                    public void onSuccess(List<YardCntrInfo> data) {
                        LogUtils.sysout("===ExchangeCntr===", JsonUtils.serialize(data));
                        boolean isChange = false;
                        YardCntrInfo bean = null;
                        for (YardCntrInfo cntrInfo : data) {
                            if (TextUtils.equals(cntr, cntrInfo.getCntr())) {
                                bean = cntrInfo;
                                isChange = true;
                            }
                            if (!isChange) {
                                ErrorMsg.setValue(MyApplication.getContext().getString(R.string.failure_to_meet_exchange_box_conditions));
                            } else {
                                PickupConfirm(bean.getYardSiteId(), bean.getId(), bean.getYardBayId(),trkWorkId, curCraneId);
                            }
                        }
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

    /**
     * 最优推荐位
     */
    public void GetYardLocation(String cntrId) {
        RetrofitManager.createToken(WorkInterface.class)
                .GetYardLocation(cntrId)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<RmdLocationInfo>() {
                               @Override
                               public void onSuccess(RmdLocationInfo data) {
                                   LogUtils.sysout("===GetYardLocation===", JsonUtils.serialize(data));
                                   mRmdLocationInfo.setValue(data);
                               }

                               @Override
                               public void onError(String code, String msg) {
                                   super.onError(code, msg);
                                   ErrorMsg.setValue(msg);
                               }
                           }
                );
    }

    //获取推荐位---获取放箱推荐位
    public void GetBestCell(String cntrId, String yardBayId, String activity) {
        Map<String, String> param = new HashMap<>();
        param.put("cntrId", cntrId);
        param.put("yardBayId", yardBayId);
        param.put("activity", activity);
        RetrofitManager.createToken(WorkInterface.class)
                .GetBestCell(param)
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> data) {
                        LogUtils.sysout("===GetBestCell===", JsonUtils.serialize(data));
                        mRmdCellList.setValue(data);
                    }
                });
    }


    /**
     * 模糊搜索箱子
     *
     * @param cntr
     */
    public void searchCntrInfoBytag(String cntr, String yardSiteId) {
        RetrofitManager.createToken(WorkInterface.class)
                .searchCntrInfo(ParamUtils.getCurSearchCntrParam(cntr, yardSiteId))
                .compose(RxUtils.getWrapper())
                .subscribe(new ResponseObserver<List<YardCntrInfo>>() {
                    @Override
                    public void onSuccess(List<YardCntrInfo> data) {
                        String str = JsonUtils.serialize(data);
                        LogUtils.sysout("===searchCntrInfoBytag===", str);
                        mSearchCntrInfo.setValue(data);
                    }
                });
    }
}
