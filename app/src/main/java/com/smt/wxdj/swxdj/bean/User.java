package com.smt.wxdj.swxdj.bean;

import com.smt.wxdj.swxdj.MyApplication;
import com.smtlibrary.utils.JsonUtils;

import java.io.Serializable;

/**
 * Created by gbh on 16/6/27.
 * 用户表
 */
public class User implements Serializable {
    private String SignonUSERID;
    private String SignonPWD;
    private String USERTYPE;////用户类型
    private String Crn;//吊机编号
    private String CRANE;
    private String SignonMAJLOC;
    private String SignonSUBLOC;
    private String Language;
    private String Result;//结果

    public User() {
        SignonMAJLOC = MyApplication.MAJLOC;
        SignonSUBLOC = MyApplication.SUBLOC;
    }

    public String getSignonUSERID() {
        return SignonUSERID;
    }

    public void setSignonUSERID(String signonUSERID) {
        SignonUSERID = signonUSERID;
    }

    public String getSignonPWD() {
        return SignonPWD;
    }

    public void setSignonPWD(String signonPWD) {
        SignonPWD = signonPWD;
    }

    public String getUSERTYPE() {
        return USERTYPE;
    }

    public void setUSERTYPE(String USERTYPE) {
        this.USERTYPE = USERTYPE;
    }

    public String getCrn() {
        return Crn;
    }

    public void setCrn(String crn) {
        Crn = crn;
    }

    public String getSignonMAJLOC() {
        return SignonMAJLOC;
    }

    public void setSignonMAJLOC(String signonMAJLOC) {
        SignonMAJLOC = signonMAJLOC;
    }

    public String getSignonSUBLOC() {
        return SignonSUBLOC;
    }

    public void setSignonSUBLOC(String signonSUBLOC) {
        SignonSUBLOC = signonSUBLOC;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCRANE() {
        return CRANE;
    }

    public void setCRANE(String CRANE) {
        this.CRANE = CRANE;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }

}
