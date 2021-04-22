package com.smt.wxdj.swxdj.logins.model;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.dao.Tenants;
import com.smt.wxdj.swxdj.dao.TokenInfo;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.param.ParamUtils;
import com.smt.wxdj.swxdj.utils.JsonUtils;
import com.smt.wxdj.swxdj.utils.PraseJsonUtils;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.LogUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

/**
 * Created by gbh on 16/6/27.
 */
public class LoginModelImpl implements LoginModel {
    private final String SOCKET_TIMEOUT = MyApplication.getContext().getString(R.string.network_error);

    @Override
    public void getTenants(final IPublicResultInterface<Tenants> onLoginListener) {
        OkHttpUtils.get(URLTool.getUrl().concat("PlatformOperations/tenants"), new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("getTenants=====", response);
                onLoginListener.onSucess(JsonUtils.deserialize(response, Tenants.class));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                onLoginListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void loadToken(List<OkHttpUtils.Param> params, final IPublicResultInterface onLoginListener) {
        OkHttpUtils.post(URLTool.authHost, params, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("login=====", response);
                onLoginListener.onSucess(PraseJsonUtils.praseUserData(response));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                LogUtils.sysout("login=====", e.toString());
                onLoginListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void login(List<OkHttpUtils.Param> params, final IPublicResultInterface onLoginListener) {


        OkHttpUtils.post(URLTool.authHost, params, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("login=====", response);
                onLoginListener.onSucess(JsonUtils.deserialize(response, TokenInfo.class));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                LogUtils.sysout("login=====", e.toString());
                onLoginListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void getUserInfo(final IPublicResultInterface onLoginListener) {
        OkHttpUtils.get(URLTool.getUrl().concat("abp/application-configuration?culture=zh-Hans"), new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {

                Map<String, Object> stringObjectMap = JsonUtils.deserialize(response, Map.class);
                Map<String, Object> userMap = (Map<String, Object>) stringObjectMap.get("currentUser");
                LogUtils.sysout("get user info=====", userMap.get("id"));
                if (null != onLoginListener)
                    onLoginListener.onSucess(JsonUtils.deserialize(response, TokenInfo.class));
                String data= JsonUtils.serialize(ParamUtils.getCurChaneParam(String.valueOf(userMap.get("id"))));
                LogUtils.sysout("吊机data=====", data);
                saveRTG(data,null);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                if (null != onLoginListener)
                    onLoginListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 登录吊机
     *
     * @param data
     * @param onLoginListener
     */
    @Override
    public void saveRTG(String data, final IPublicResultInterface onLoginListener) {
        OkHttpUtils.post(URLTool.getUrl().concat("SystemSetting/JobTicketEqp/search"), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("saveRTG", response);

//                try {
//                    boolean b = PraseJsonUtils.praseMoveBoxResult(response);
//                    if (b)
//                        onLoginListener.onSucess(b);
//                    else
//                        onLoginListener.onFailure(MyApplication.getContext().getString(R.string.failure_please_try_again), null);
//                } catch (Exception e) {
//                    onLoginListener.onFailure(MyApplication.getContext().getString(R.string.failure_please_try_again), e);
//                }
            }

            @Override
            public void onFailure(Exception e) {
                onLoginListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void loadMachineList(String data, final IPublicResultInterface onLoginListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
//                Logger.json(response);
                LogUtils.sysout("loadMachineList", response);
                onLoginListener.onSucess(PraseJsonUtils.praseMachineNoList(response));
            }

            @Override
            public void onFailure(Exception e) {
                onLoginListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取系统参数
     *
     * @param data
     * @param iPublicResultInterface
     */
    public void GetSysParam(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.sysout("=======GetSysParam", response);
                try {
                    iPublicResultInterface.onSucess(PraseJsonUtils.praseCheckBoxResult(response).equals("YES") ? true : false);
                } catch (Exception e) {
                    iPublicResultInterface.onSucess(false);
                }
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.sysout("=======GetCntrCellForRFCWS", e.toString());
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取所有系统参数
     *
     * @param iPublicResultInterface
     */
    @Override
    public void GetAllSysParam(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(Object o) {
//                Logger.json(o.toString());
//                LogUtils.e("GetAllSysParam", o.toString());
//                CustomLog.json(o.toString());
                iPublicResultInterface.onSucess(o);
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void getAppUpdate(String data, final IPublicResultInterface iPublicResultInterface) {
//        LogUtils.e("getAppUpdate request", data);
        String url = URLTool.getUrl().substring(0, URLTool.getUrl().lastIndexOf("/"));
//        LogUtils.e("url", url);
        OkHttpUtils.postResultStream(url, data, new OkHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(final Object response) {
                LogUtils.e("response", response.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = (InputStream) response;
                        try {
                            iPublicResultInterface.onSucess(parseCheckResponseXML(is));
                        } catch (Exception e) {
                            e.printStackTrace();
                            iPublicResultInterface.onFailure(e.getMessage(), e);
                        } finally {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(Exception e) {
                if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
                    iPublicResultInterface.onFailure(e.getMessage(), e);
                } else {
                    iPublicResultInterface.onFailure(e.toString(), e);
                }
            }
        });
    }

    @Override
    public void GetBasicData(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.e("GetBasicData response", o.toString());
                iPublicResultInterface.onSucess(o);
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 解析XML
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    private String parseCheckResponseXML(InputStream inStream) throws Exception {

        XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
        //获取XmlPullParser的实例
        XmlPullParser parser = pullParserFactory.newPullParser();
//        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();//产生第一个事件
        while (eventType != XmlPullParser.END_DOCUMENT) {//只要不是文档结束事件
            String nodeName = parser.getName();//获取解析器当前指向的元素的名称
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("GetAppUpdateResult".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.e("GetAppUpdateResult", v);
                        return v;
                    }
                    break;
            }
            eventType = parser.next();
        }

        return "";
    }
}
