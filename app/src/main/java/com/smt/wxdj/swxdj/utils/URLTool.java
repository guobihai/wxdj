package com.smt.wxdj.swxdj.utils;

/**
 * Created by gbh on 16/8/15.
 */
public class URLTool {
    private static String url;

    //授权的URL
    public static String authHost = "https://auth.wit-union.com/connect/token";
//    public static String authHost = "http://124.70.27.37:64999/connect/token";

    public static String getUrl() {
//        if (LogUtils.LOG_DEBUG)
//        return "http://106.75.138.45/RFHYWebSvr/WebService.asmx/WebServiceBizExecute";
//        return "http://192.9.200.104/DHCTWebService/WebService.asmx/WebServiceBizExecute";
//        return "http://192.9.200.193/RFCWSWebSrv/WebService.asmx/WebServiceBizExecute";
//        return "http://192.9.200.235:8081/WebService.asmx/WebServiceBizExecute";
//        return "http://192.9.200.203:8080/WebService.asmx/WebServiceBizExecute";
//        return "http://192.168.80.201/RFWebSvr/WebService.asmx/WebServiceBizExecute";//宏业内部
//        return "http://192.9.200.239:2311/WebService.asmx/WebServiceBizExecute";
//        return "http://192.9.200.77:2311/WebService.asmx/WebServiceBizExecute";
//        return URLTool.url.trim() + "/WebServiceBizExecute";
        return "https://lgsapi.wit-union.com/api/";
    }

    public static void setUrl(String url) {
//        URLTool.url = url;
    }

    /**
     * 共有路径
     *
     * @return
     */
    public static String getPubUrl() {
        return publicUrl();
    }

    private static String publicUrl() {
//        private String appUrl = "http://106.75.138.45/EvPlatformSrv/WebService.asmx";
        return "http://106.75.138.45/EvPlatformSrv/WebService.asmx";
    }
}
