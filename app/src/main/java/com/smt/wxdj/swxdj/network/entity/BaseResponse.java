package com.smt.wxdj.swxdj.network.entity;


/**
 * 杨桃技术部，API基础封装
 * @Author anhui
 * @Created 3/29/19
 * @Editor zrh
 * @Edited 3/29/19
 * @Type
 * @Layout
 * @Api
 */
public class BaseResponse<T> {

    private String status;
    private String message;
    private T data;
    private boolean succeed;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }
}
