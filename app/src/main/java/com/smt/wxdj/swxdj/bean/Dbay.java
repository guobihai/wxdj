package com.smt.wxdj.swxdj.bean;

import com.smtlibrary.utils.JsonUtils;

import java.util.List;

/**
 * Created by gbh on 17/1/4.
 */

public class Dbay {
    private String __type;
    private String ResultCode;
    private String ResultMessage;
    private String Pager;
    private String ObjectType;
    private List<String> ReturnObject;
    private transient boolean isSelect;

    public List<String> getReturnObject() {
        return ReturnObject;
    }

    public void setReturnObject(List<String> returnObject) {
        ReturnObject = returnObject;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultMessage() {
        return ResultMessage;
    }

    public void setResultMessage(String resultMessage) {
        ResultMessage = resultMessage;
    }

    public String getPager() {
        return Pager;
    }

    public void setPager(String pager) {
        Pager = pager;
    }

    public String getObjectType() {
        return ObjectType;
    }

    public void setObjectType(String objectType) {
        ObjectType = objectType;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
