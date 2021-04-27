package com.smt.wxdj.swxdj.viewmodel.api;

import com.smt.wxdj.swxdj.dao.Tenants;
import com.smt.wxdj.swxdj.viewmodel.nbean.ChaneStackInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.NMachineInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.OprTaskInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WorkInterface {

    @GET("PlatformOperations/tenants")
    Observable<Tenants> getTeantsInfo();

    @POST("SystemSetting/JobTicketEqp/search")
    Observable<List<NMachineInfo>> getJobTicketEqpSearch(@Body Map map);

    //根据设备ID获取有作业任务的作业街区
    @GET("CntrManagement/CraneWorkScope/GetWorkScope")
    Observable<List<ChaneStackInfo>> getJobTicketTaskStack(@Query("CraneId") String CraneId);

    //根据街区ID获取贝位
    @GET("SystemSetting/YardBay/GetListByBlockId")
    Observable<List<YardBayInfo>> GetYardBayListByBlockId(@Query("blockId") String blockId);

    //    SystemSetting/YardBay/GetListBySiteId(根据场站ID获取贝位)、
    @GET("SystemSetting/YardBay/GetListBySiteId")
    Observable<List<Object>> GetYardBayListBySiteId(@Query("siteId") String blockId, @Query("includeCell") boolean includeCell,
                                                    @Query("includeCntr") boolean includeCntr);

    //    SystemSetting/YardBlock/GetListBySiteId(根据场站ID获取街区信息)、
    @GET("SystemSetting/YardBlock/GetListBySiteId")
    Observable<List<Object>> GetYardBlockListBySiteId(@Query("siteId") String siteId,
                                                      @Query("includeCell") boolean includeCell,
                                                      @Query("includeCntr") boolean includeCntr);

    //    SystemSetting/YardBlock/GetListByAreaId(根据场站区域ID获取街区信息)、
    @GET("SystemSetting/YardBlock/GetListByAreaId")
    Observable<List<Object>> GetYardBlockGetListByAreaId(@Query("areaId") String blockId, @Query("includeCell") boolean includeCell,
                                                         @Query("includeCntr") boolean includeCntr);

    //    SystemSetting/YardBlock/GetByBlockId(根据街区信息ID获取街区信息)
    @GET("SystemSetting/YardBlock/GetByBlockId")
    Observable<List<Object>> GetYardBlockGetByBlockId(@Query("blockId") String blockId, @Query("includeCell") boolean includeCell,
                                                      @Query("includeCntr") boolean includeCntr);

    //4、获取指定街区的作业任务列表
    @GET("SystemSetting/TrkWork/GetTrkWorkByBlockId")
    Observable<List<YardCntrInfo>> GetTrkWorkByBlockId(@Query("blockId") String blockId, @Query("activity") String activity);

    //5、获取指定街区的取消提箱任务列表
    @GET("SystemSetting/TrkWork/GetTrkWorkIsUPByBlockId")
    Observable<List<YardCntrInfo>> GetTrkWorkIsUPByBlockId(@Query("blockId") String blockId);

    //6、获取指定街区的倒箱任务列表
    @GET("SystemSetting/TrkWork/GetTrkWorkIsCTCByBlockId")
    Observable<List<YardCntrInfo>> GetTrkWorkIsCTCByBlockId(@Query("blockId") String blockId);


    //6、根据贝位获取id 获取列信息
    @GET("SystemSetting/YardCell/GetListByBayId")
    Observable<List<Object>> GetListByBayId(@Query("bayId") String blockId);

    //7、放箱确认
    @POST("CntrManagement/CraneWork/GroundConfirm")
    Observable<Object> GroundConfirm(@Body Map map);


    //8、提箱确认
    @POST("CntrManagement/CraneWork/PickupConfirm")
    Observable<Object> PickupConfirm(@Body Map map);

    //10、倒箱确认
    @POST("CntrManagement/CraneWork/PutOtherConfirm")
    Observable<Object> PutOtherConfirm(@Body Map map);

    // 9、判断能否交换箱(获取提箱交换箱的信息)
    @GET("SystemSetting/TrkWork/ExchangeCntr")
    Observable<List<Object>> ExchangeCntr(@Query("trkWorkId") String trkWorkId);

    //10、获取作业线拖车列表
    @GET("TruckManagement/WorkLineTrk/GetSameWorkLineTrk")
    Observable<List<Object>> GetSameWorkLineTrk(@Query("truckId") String truckId);
}
