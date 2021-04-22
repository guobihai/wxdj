package com.smt.wxdj.swxdj.bean;

/**
 * Created by xlj on 2017/10/30.
 */

public class CheckBean {

    /**
     * d : {"__type":"ServicesObjects.ResultObject","ResultCode":null,"ResultMessage":null,"ReturnObject":"0","Pager":null,"ObjectType":null}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        /**
         * __type : ServicesObjects.ResultObject
         * ResultCode : null
         * ResultMessage : null
         * ReturnObject : 0
         * Pager : null
         * ObjectType : null
         */

        private String __type;
        private String ResultCode;
        private Object ResultMessage;
        private String ReturnObject;
        private Object Pager;
        private Object ObjectType;

        public String get__type() {
            return __type;
        }

        public String getResultCode() {
            return ResultCode;
        }

        public Object getResultMessage() {
            return ResultMessage;
        }

        public String getReturnObject() {
            return ReturnObject;
        }


        public Object getPager() {
            return Pager;
        }

        public Object getObjectType() {
            return ObjectType;
        }

    }
}
