package com.smt.wxdj.swxdj.logins.presenter;

import android.text.TextUtils;

import com.smt.wxdj.swxdj.BasePresenter;
import com.smt.wxdj.swxdj.bean.AppUpdateBean;
import com.smt.wxdj.swxdj.bean.BasicParam;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.CheckBean;
import com.smt.wxdj.swxdj.bean.Dock;
import com.smt.wxdj.swxdj.bean.DriverList;
import com.smt.wxdj.swxdj.bean.MachineNo;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.dao.HostSettingInfo;
import com.smt.wxdj.swxdj.dao.Tenants;
import com.smt.wxdj.swxdj.dao.TokenInfo;
import com.smt.wxdj.swxdj.enums.DataType;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.logins.model.LoginModel;
import com.smt.wxdj.swxdj.logins.model.LoginModelImpl;
import com.smt.wxdj.swxdj.logins.view.LoginView;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.PraseJsonUtils;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smt.wxdj.swxdj.webservice.BizObject;
import com.smt.wxdj.swxdj.webservice.ParamObject;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smt.wxdj.swxdj.setting.model.SettingModelImpl.InputStreamTOString;
import static com.smt.wxdj.swxdj.setting.model.SettingModelImpl.StringTOInputStream;

/**
 * Created by gbh on 16/6/27.
 */
public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter {
    private LoginModel mLoginModel;

    public LoginPresenterImpl() {
        this.mLoginModel = new LoginModelImpl();
    }

    @Override
    public void getTenants() {
       mLoginModel.getTenants(new IPublicResultInterface<Tenants>() {
            @Override
            public void onSucess(Tenants object) {
                getView().showTenants(object);
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }

    @Override
    public void getSettingInfo() {
        mLoginModel.getSettingInfo(new IPublicResultInterface<List<HostSettingInfo>>() {
            @Override
            public void onSucess(List<HostSettingInfo> object) {
                System.out.println("===loclhost:"+JsonUtils.serialize(object));
                getView().onSuccess(object);
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });

    }

    @Override
    public void loadToken() {
        getView().showProgress();
        mLoginModel.loadToken(getLoginRequestData(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                getView().hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().showLoginFaileMsg(msg);
                getView().hideProgress();
            }
        });

    }

    @Override
    public void login() {
        if (!isViewAttached()) return;
        getView().showProgress();
        mLoginModel.login(getLoginRequestData(), new IPublicResultInterface<TokenInfo>() {
            @Override
            public void onSucess(TokenInfo object) {
                if (!isViewAttached()) return;
                getView().onSuccess(object);
                getView().hideProgress();

            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().showLoginFaileMsg(msg);
                getView().hideProgress();
            }
        });

    }

    /**
     * 登录吊机
     *
     * @param user 吊机号
     */
    @Override
    public void saveRTG(User user) {
        mLoginModel.saveRTG(getsaveRTGData(user.getSignonUSERID(), user.getCrn()), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().loginCrnSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().showLoginFaileMsg(msg);
                getView().hideProgress();
            }
        });
    }


    @Override
    public void loadMachineList() {
//        Logger.d("获取吊机列表");
        mLoginModel.loadMachineList(getMachineNoRequestData(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                getView().addMachListData((List<MachineNo>) object);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().showLoginFaileMsg(msg);
            }
        });
    }

    /**
     * 获取所有推荐位系统参数
     */
    @Override
    public void getAllSystem() {
//        if(LruchUtils.isExist()) return;
        mLoginModel.GetAllSysParam(getAllSysData(), new IPublicResultInterface() {
            @Override
            public void onSucess(Object obj) {
//                Logger.json(obj.toString());
                if (!isViewAttached()) return;
                LogUtils.e("GetAllSysParam", obj.toString());
                Map<String, String> maps = PraseJsonUtils.parseAllSysParams(obj.toString());
                if (null == maps) {
                    maps = new HashMap<String, String>();
                    maps.put(FileKeyName.OPENSTACKLISTSWITCH, "YES");
                }
                LruchUtils.clear();
                LruchUtils.setSystemMaps(maps);
//                Iterator<Map.Entry<String, String>> it = maps.entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry<String, String> entry = it.next();
//                    System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
//                }
                getView().setAllSystemResult(true);
//                MyApplication.getInstance().setAllSysParams(maps);
//                String str = maps.get("*," + context.getResources().getString(R.string.open_stack_list_switch));
//                if(null != str && "YES".equals(str)){
//                    PreferenceUtils.putBoolean(context, FileKeyName.SHOWSTACKLIST, true);
//                }else {
//                    PreferenceUtils.putBoolean(context, FileKeyName.SHOWSTACKLIST, false);
//                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().setAllSystemResult(false);
            }
        });
    }

    @Override
    public void getAppUpdate(String data) {
//        getView().showLoadMsg();
        mLoginModel.getAppUpdate(getView().getAppUpdateRequestBody(data), new IPublicResultInterface() {
            @Override
            public void onSucess(Object obj) {
                if (!isViewAttached()) return;
//                LogUtils.e("getAppUpdate", obj.toString());
                getView().hideProgress();
                AppUpdateBean bean = com.smt.wxdj.swxdj.utils.JsonUtils.toObject(obj.toString(), AppUpdateBean.class);

                if (null == bean.getApp()) {
                    LogUtils.e("getAppUpdate error", "服务返回参数错误,");
                    getView().hideProgress();
                    return;
                }
                if (bean.getApp().size() <= 0) {
                    getView().hideProgress();
                    return;
                }
                getView().getAppUpdateData(bean);

            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().hideProgress();
            }
        });
    }

    @Override
    public void getDataList(final DataType dataType) {
        mLoginModel.GetBasicData(getGetBasicDataParam(dataType), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                List<DriverList> list = PraseJsonUtils.praseUserList(object.toString(), dataType.getType(), DriverList.class);
                getView().getDataList(list);
//                getView().addMachListData((List<MachineNo>) object);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().showLoginFaileMsg(msg);
            }
        });
    }

    @Override
    public void CheckSignOnQC(User user) {
        getView().showProgress();
        mLoginModel.GetBasicData(CheckSignOnQCParam(user), new IPublicResultInterface() {
            @Override
            public void onSucess(Object object) {
                if (!isViewAttached()) return;
                LogUtils.e("CheckSignOnQC result", object.toString());
                getView().hideProgress();
                CheckBean bean = JsonUtils.deserialize(object.toString(), CheckBean.class);
                if (null == bean) {
                    return;
                }
                String res = bean.getD().getReturnObject();
                if (TextUtils.isEmpty(res)) {
                    return;
                }
                if (res.equals("0")) {
                    getView().checkSignOnQCResult(true);
                } else if (res.equals("1")) {
                    getView().checkSignOnQCResult(false);
                } else {
                    getView().showLoginFaileMsg(res);
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
                getView().showLoginFaileMsg(msg);
            }
        });
    }


    /**
     * 初始化登录请求数据体
     * 画
     *
     * @return
     */
    private List<OkHttpUtils.Param> getLoginRequestData() {
//        BizObject user = new BizObject();
//        user.setSignonUSERID(getView().getUserName());
//        user.setSignonPWD(getView().getPassWord());
//        user.setCRANE(getView().getMachineNo());
//        user.setUSERTYPE("2");
//        ParamObject m = new ParamObject("SignonRFCWS");
//        m.setBizObject(JsonUtils.serialize(user));


//        Map requestParams = {
//                "grant_type":'password',
//                "username":userName,
//                "password":password,
//                "scope":"openid address offline_access  email phone role IdentityService InternalGateway BackendGateway TenantService BillingService CntrService CFSService ContainerCRService SystemSettingService TruckService EDIService HRService VesselService SystemService PlatformService ReportService MessageService",
//                "client_id":"basic-web",
//                "client_secret":"1q2w3e*"
//    };
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        paramList.add(new OkHttpUtils.Param("grant_type", "password"));
        paramList.add(new OkHttpUtils.Param("username", getView().getUserName()));
        paramList.add(new OkHttpUtils.Param("password", getView().getPassWord()));
        paramList.add(new OkHttpUtils.Param("scope", "openid address offline_access  email phone role IdentityService InternalGateway BackendGateway TenantService BillingService CntrService CFSService ContainerCRService SystemSettingService TruckService EDIService HRService VesselService SystemService PlatformService ReportService MessageService"));
        paramList.add(new OkHttpUtils.Param("client_id", "basic-web"));
        paramList.add(new OkHttpUtils.Param("client_secret", "1q2w3e*"));

        return paramList;
    }

    /**
     * 初始化吊机列表请求数据体
     *
     * @return
     */
    private String getMachineNoRequestData() {
        BizObject bizObject = new BizObject();
        ParamObject m = new ParamObject("ShowCraneInfor");
        m.setBizObject(JsonUtils.serialize(bizObject));
        return m.toString();
    }


    /**
     * 纪录保存登录的吊机
     *
     * @return
     */
    private String getsaveRTGData(String userId, String crn) {
        BizObject bizObject = new BizObject();
        bizObject.setCrn(crn);
        bizObject.setSignonUSERID(userId);
        ParamObject m = new ParamObject("SaveRTG");
        m.setBizObject(JsonUtils.serialize(bizObject));
        return m.toString();
    }

    /**
     * 获取推荐位系统参数
     *
     * @param param 参数名字
     */
    @Override
    public void GetSysParam(String param) {
        mLoginModel.GetSysParam(getSysParam(param), new IPublicResultInterface<Boolean>() {
            @Override
            public void onSucess(Boolean bol) {
                if (!isViewAttached()) return;
            }

            @Override
            public void onFailure(String msg, Exception e) {
                if (!isViewAttached()) return;
            }
        });
    }

    /**
     * 获取放箱推荐位数据题
     *
     * @return
     */
    private String getSysParam(String param) {
        ParamObject m = new ParamObject("GetSysParam");
        BoxDetalBean bean = new BoxDetalBean();
        bean.setActivity(param);
        m.setBizObject(bean.toString());
        return m.toString();
    }

    /**
     * 获取放箱推荐位数据题
     *
     * @return
     */
    private String getAllSysData() {
        ParamObject m = new ParamObject("GetAllSysParam");
        BoxDetalBean bean = new BoxDetalBean();
        m.setBizObject(bean.toString());
        return m.toString();
    }


    /**
     * 获取任务列表
     *
     * @return
     */
    private String getGetBasicDataParam(DataType dataType) {
        return new ParamObject("GetBasicData_app", new BasicParam(dataType.getType()).toString()).toString();
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    private String CheckSignOnQCParam(User user) {
        return new ParamObject("CheckSignOnQC", user.toString()).toString();
    }

    /**
     * 获取码头信息
     */
    public void loadDockData() {
        getView().showProgress();
        OkHttpUtils.postResultStream(URLTool.getPubUrl(), getView().getRequestBody(), new OkHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(final Object response) {
                if (!isViewAttached()) return;
                getView().hideProgress();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = (InputStream) response;
                        try {
                            getView().onSuccess(parseAppResponseXML(is));
                        } catch (Exception e) {
                            e.printStackTrace();
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
                if (!isViewAttached()) return;
                getView().hideProgress();
                getView().showLoginFaileMsg(e.getMessage());
            }
        });
    }

    /**
     * 解析码头信息
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    private List<Dock> parseAppResponseXML(InputStream inStream) throws Exception {
        List<Dock> lists = new ArrayList<>();
        Dock dock = null;
        String str = InputStreamTOString(inStream);
        LogUtils.sysout("字符串", str);
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");

        XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
        //获取XmlPullParser的实例
        XmlPullParser parser = pullParserFactory.newPullParser();
        parser.setInput(StringTOInputStream(str), "UTF-8");
        int eventType = parser.getEventType();//产生第一个事件
        while (eventType != XmlPullParser.END_DOCUMENT) {//只要不是文档结束事件
            String nodeName = parser.getName();//获取解析器当前指向的元素的名称
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
//                    LogUtils.sysout("==value=", "START_DOCUMENT");
                    break;
                case XmlPullParser.START_TAG:
                    if ("rows".equals(nodeName)) {
//                        LogUtils.sysout("=================value=", "new class");
                        dock = new Dock();
                    }
                    if ("TERMINAL".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==TERMINAL=", v);
                        dock.setDock(v);
                    }
                    if ("MAJ_LOC".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==MAJ_LOC=", v);
                        dock.setMajloc(v);
                    }
                    if ("SUB_LOC".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==SUB_LOC=", v);
                        dock.setSubloc(v);
                    }
                    if ("WS_URL".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==WS_URL=", v);
                        dock.setWebUrl(v);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("rows".equals(nodeName)) {
                        LogUtils.sysout("==value=", "END_TAG");
                        lists.add(dock);
                    }
                    break;
            }
            eventType = parser.next();
        }

        return lists;
    }
}
