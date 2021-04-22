package com.smt.wxdj.swxdj.bean;

import java.util.List;

/**
 * app
 * Created by xlj on 2018/3/12.
 */

public class AppUpdateBean {


    private List<AppBean> app;

    public List<AppBean> getApp() {
        return app;
    }

    public void setApp(List<AppBean> app) {
        this.app = app;
    }

    public static class AppBean {
        /**
         * name : 无线吊机
         * packageName : com.smt.wxdj
         * versionCode : 201803131731
         * updateMessage : 测试更新
         * url : http://192.9.200.134/GCTBkgManageUI/download/123.apk
         */

        private String name;
        private String packageName;
        private String versionCode;
        private String updateMessage;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getUpdateMessage() {
            return updateMessage;
        }

        public void setUpdateMessage(String updateMessage) {
            this.updateMessage = updateMessage;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
