package com.smt.wxdj.swxdj.network.observer;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.smt.wxdj.swxdj.network.account.AccountManager;
import com.smt.wxdj.swxdj.network.entity.BaseResponse;
import com.smt.wxdj.swxdj.network.exception.ExceptionCode;
import com.smt.wxdj.swxdj.network.exception.NoDataException;
import com.smt.wxdj.swxdj.network.exception.NoNetworkException;
import com.smt.wxdj.swxdj.network.execption.ApiException;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author
 * @param <T>
 */
public abstract class ResponseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException exception = ((HttpException) e);

            int code = exception.code();
            if (code <= 500) {
                String msg = "服务器异常";
                String apiCode = code + "";

                try {
                    msg = ((HttpException) e).response().errorBody().string();
                    Map response = new Gson().fromJson(msg, Map.class);
                    if (response != null) {
                        response = (Map) response.get("error");
                        if (response != null)
                            msg = (String) response.get("message");
//                        if (response.getStatus() != null)
//                            apiCode = response.getStatus();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (JsonParseException e2) {
                    e2.printStackTrace();
                    msg = "数据解析异常";
                }

                AccountManager.handleInvalidToken(apiCode);
                onError(apiCode, msg);

            } else {
                onError(code + "", "服务器异常");
            }

        } else if (e instanceof ApiException) {
            ApiException exception = ((ApiException) e);
            AccountManager.handleInvalidToken(String.valueOf(exception.getCode()));
            onError(String.valueOf(exception.getCode()), exception.getDisplayMessage());
        } else if (e instanceof NoDataException) {
            onSuccess(null);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            onError(ExceptionCode.JSON_PARSE_ERROR, "数据解析异常");
        } else if (e instanceof NoNetworkException) {
            onError(ExceptionCode.NO_NERWORK_ERROR, "当前网络不可用，请检查您的网络设置");
        } else if (e instanceof SocketTimeoutException || e instanceof SSLHandshakeException) {
            onError(ExceptionCode.TIMEOUT_ERROR, "网络超时，请重试");
        } else {
            onError(ExceptionCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public void onNext(T data) {
        onSuccess(data);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T data);

    public void onError(String code, String msg) {
//        ToastUtil.Long(msg);
    }
}