package com.smt.wxdj.swxdj.bean;

import java.util.List;

/**
 * Created by xlj on 2018/3/9.
 */

public class WorkStatisticsBean {

    /**
     * d : {"__type":"ServicesObjects.ResultObject","ResultCode":"0","ResultMessage":null,"ReturnObject":["CFSRS,,2,22222,门到门提箱,1,0","CFSRS,,2,22222,倒箱/箱区整理,5,0","RTG01,,2,22222,门到门放箱,0,1","CFSRS,,2,22222,门到门放箱,2,0","CFSRS,,2,22222,装卸船,10,0"],"Pager":null,"ObjectType":null}
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
         * ResultCode : 0
         * ResultMessage : null
         * ReturnObject : ["CFSRS,,2,22222,门到门提箱,1,0","CFSRS,,2,22222,倒箱/箱区整理,5,0","RTG01,,2,22222,门到门放箱,0,1","CFSRS,,2,22222,门到门放箱,2,0","CFSRS,,2,22222,装卸船,10,0"]
         * Pager : null
         * ObjectType : null
         */

        private String __type;
        private String ResultCode;
        private Object ResultMessage;
        private Object Pager;
        private Object ObjectType;
        private List<String> ReturnObject;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getResultCode() {
            return ResultCode;
        }

        public void setResultCode(String ResultCode) {
            this.ResultCode = ResultCode;
        }

        public Object getResultMessage() {
            return ResultMessage;
        }

        public void setResultMessage(Object ResultMessage) {
            this.ResultMessage = ResultMessage;
        }

        public Object getPager() {
            return Pager;
        }

        public void setPager(Object Pager) {
            this.Pager = Pager;
        }

        public Object getObjectType() {
            return ObjectType;
        }

        public void setObjectType(Object ObjectType) {
            this.ObjectType = ObjectType;
        }

        public List<String> getReturnObject() {
            return ReturnObject;
        }

        public void setReturnObject(List<String> ReturnObject) {
            this.ReturnObject = ReturnObject;
        }
    }
}
