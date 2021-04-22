package com.smt.wxdj.swxdj.enums;

/**
 * Created by gbh on 17/3/28.
 */

public enum DataType {
    ALL("ALL"),//全部
    EQPTYPE("EQPTYPE"),//箱型
    OPRUSER("OPRUSER"),//登录员
    DRIVER("DRIVER"),//司机
    CRANE("CRANE"),//岸桥号
    TRK("TRK");//拖车

    DataType(String type) {
        this.type = type;
    }

    private String type;
    private String crane;

    public String getCrane() {
        return crane;
    }

    public DataType setCrane(String crane) {
        this.crane = crane;
        return this;
    }

    public String getType() {
        return type;
    }

}
