package com.smt.wxdj.swxdj.webservice;

import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

/**
 * Created by gbh on 16/6/30.
 */
public class ParamObject {
    private String ModuleName = "";//模块名
    private String FunctionName;//函数名
    private String ObjectType = "JSON";//类型
    private Object BizObject;//数据体
    private String ClassName;//
    private String UserName;//用户名（不用）
    private String UserPassword;


    public ParamObject(String functionName) {
        FunctionName = functionName;
        ModuleName = "RFCWSWebSrv";
    }

    public ParamObject(String functionName, Object bizObject) {
        ModuleName = "RFCWSWebSrv";
        FunctionName = functionName;
        BizObject = bizObject;
        ObjectType = "JSON";
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    public String getFunctionName() {
        return FunctionName;
    }

    public void setFunctionName(String functionName) {
        FunctionName = functionName;
    }

    public String getObjectType() {
        return ObjectType;
    }

    public void setObjectType(String objectType) {
        ObjectType = objectType;
    }

    public Object getBizObject() {
        return BizObject;
    }

    public void setBizObject(Object bizObject) {
        BizObject = bizObject;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    @Override
    public String toString() {
        String param = "{\"paramObject\":" + JsonUtils.serialize(this) + "}";
        LogUtils.sysout("==当前请求数据=" + FunctionName + "===params:", param);
        return param;
    }
}
