package com.smt.wxdj.swxdj.interfaces;

/**
 * Created by gbh on 16/7/4.
 */
public interface IPublicResultInterface<T> {
    void onSucess(T object);
    void onFailure(String msg, Exception e);
}
