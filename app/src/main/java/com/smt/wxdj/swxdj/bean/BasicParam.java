package com.smt.wxdj.swxdj.bean;

import com.smt.wxdj.swxdj.MyApplication;
import com.smtlibrary.utils.JsonUtils;

/**
 * Created by gbh on 17/3/28.
 * 获取基础类型数据
 */

public class BasicParam {
    public String MajLoc;
    public String SubLoc;
    public String DataType;//类型
    private String Crane;//岸桥号
    private String Trk;//岸桥号

    public BasicParam(String dataType) {
        DataType = dataType;
//        if (LogUtils.LOG_DEBUG) {
//            this.MajLoc = "DGA";
//            this.SubLoc = "DHCT";
//        }else{
        MajLoc = MyApplication.MAJLOC;
        SubLoc = MyApplication.SUBLOC;
//        }

    }

    public BasicParam(String dataType, String trk) {
        DataType = dataType;
        Trk = trk;
//        if (LogUtils.LOG_DEBUG) {
//            this.MajLoc = "DGA";
//            this.SubLoc = "DHCT";
//        }else{
        MajLoc = MyApplication.MAJLOC;
        SubLoc = MyApplication.SUBLOC;
//        }
    }

    public String getMajLoc() {
        return MajLoc;
    }

    public void setMajLoc(String majLoc) {
        MajLoc = majLoc;
    }

    public String getSubLoc() {
        return SubLoc;
    }

    public void setSubLoc(String subLoc) {
        SubLoc = subLoc;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getCrane() {
        return Crane;
    }

    public void setCrane(String crane) {
        Crane = crane;
    }

    public String getTrk() {
        return Trk;
    }

    public void setTrk(String trk) {
        Trk = trk;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }
}
