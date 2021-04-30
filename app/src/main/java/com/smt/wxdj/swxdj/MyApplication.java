package com.smt.wxdj.swxdj;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.User;
import com.smt.wxdj.swxdj.network.RetrofitManager;
import com.smt.wxdj.swxdj.network.account.AccountManager;
import com.smt.wxdj.swxdj.switchlang.Utils;
//import com.smtlibrary.crashlog.AppCrashHandler;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smt.wxdj.swxdj.viewmodel.nbean.ChaneStackInfo;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardBayInfo;
import com.smtlibrary.utils.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gbh on 16/7/6.
 */
public class MyApplication extends MultiDexApplication {
    public static User user;
    public static String MAJLOC;//主区
    public static String SUBLOC;
    public Map<String, List<YardBayInfo>> mapBay;//田位缓存
    public Map<String, List<ChaneStackInfo>> mkeyStack;//场地缓存
    private static Context context;

    private static MyApplication myApplication = null;

    public static MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    public static String CRANE;//吊机号/岸桥号

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mapBay = new HashMap<>();//田位缓存
        mkeyStack = new HashMap<>();//田位缓存
        LogUtils.LOG_DEBUG = BuildConfig.DEBUG;
        context = getApplicationContext();
        user = new User();
        AccountManager.init(this);
        RetrofitManager.getInstance().init(URLTool.getUrl());

//        PropertyConfigurator.getConfigurator(this).configure();
//        AppCrashHandler.getInstance().init(this);

        Utils.init(this);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 根据场地获取田位
     *
     * @param key
     * @return
     */
    public List<YardBayInfo> getMapBay(String key) {
        return mapBay.get(key);
    }

    /**
     * 缓存田位
     *
     * @param key
     * @param list
     */
    public void putBayMap(String key, List<YardBayInfo> list) {
        mapBay.put(key, list);
    }


    /**
     * 根据场地获取场地
     *
     * @param key
     * @return
     */
    public List<ChaneStackInfo> getKeyListStack(String key) {
        return mkeyStack.get(key);
    }

    /**
     * 缓存场地
     *
     * @param key
     * @param list
     */
    public void putStackMap(String key, List<ChaneStackInfo> list) {
        mkeyStack.put(key, list);
    }


}
