package com.smt.wxdj.swxdj.api;

import com.smt.wxdj.swxdj.dao.Tenants;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginInterface {

    @GET("PlatformOperations/tenants")
    Observable<Tenants> getTeantsInfo();

    @POST("SystemSetting/JobTicketEqp/search")
    Observable<Object> getTeantsInfo(@Body Map map);
}
