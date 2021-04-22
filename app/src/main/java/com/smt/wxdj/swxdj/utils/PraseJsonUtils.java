package com.smt.wxdj.swxdj.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.bean.Dbay;
import com.smt.wxdj.swxdj.bean.MachineNo;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smt.wxdj.swxdj.bean.User;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smtlibrary.utils.JsonUtils.deserialize;

/**
 * Created by gbh on 16/7/1.
 * Json数据解析类
 */
public class PraseJsonUtils {


    /**
     * 解析吊机列表信息
     *
     * @param data
     * @return string[]
     */
    public static List<MachineNo> praseMachineNoList(String data) {
        List<MachineNo> list = new ArrayList<MachineNo>();
        try {
            JsonArray array = (JsonArray) getHead(data);
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = (JsonObject) array.get(i);
                MachineNo machineNo = deserialize(obj, MachineNo.class);
                list.add(machineNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 解析数据头
     *
     * @param data
     * @return
     */
    private static Object getHead(String data) {
        try {
            JsonParser prase = new JsonParser();
            JsonObject obj = (JsonObject) prase.parse(data);
            obj = (JsonObject) obj.get("d");
            return obj.get("ReturnObject");
        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
            LogUtils.e("tag", e.toString());
            return null;
        }
    }


    /**
     * 解析用户登录信息
     *
     * @param data
     * @return
     */
    public static User praseUserData(String data) {
        try {
            JsonObject obj = (JsonObject) getHead(data);
            return deserialize(obj, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    /**
     * 解析司机列表
     *
     * @param data
     * @return
     */
    public static <T> List<T> praseUserList(String data, String key, Class<T> tClass) {
        List<T> lists = new ArrayList<>();
        try {
            JsonObject obj = (JsonObject) getHead(data);
            JsonArray array = (JsonArray) obj.get(key);
            for (int i = 0; i < array.size(); i++) {
                obj = (JsonObject) array.get(i);
                lists.add(JsonUtils.deserialize(obj, tClass));
            }
        } catch (Exception e) {
            return lists;
        }
        return lists;
    }

    /**
     * 解析任务列表数据
     *
     * @param data
     * @return
     */
    public static List<BoxDetalBean> praseBoxListData(String data) {
        List<BoxDetalBean> list = new ArrayList<>();
        try {
            Object object = getHead(data);
            if (object instanceof JsonArray) {
                JsonArray array = (JsonArray) getHead(data);
                for (int i = 0; i < array.size(); i++) {
                    JsonObject obj = (JsonObject) array.get(i);
                    BoxDetalBean cntr = deserialize(obj, BoxDetalBean.class);
                    list.add(cntr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    /**
     * 解析单个箱子信息
     *
     * @param data
     * @return
     */
    public static BoxDetalBean praseBoxInfoData(String data) {
        try {
            JsonObject object = (JsonObject) getHead(data);
            BoxDetalBean info = JsonUtils.deserialize(object, BoxDetalBean.class);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解析取消提箱状态结果
     *
     * @param data
     * @return
     */
    public static boolean praseCancleBoxResult(String data) throws Exception {
        JsonParser prase = new JsonParser();
        JsonObject obj = (JsonObject) prase.parse(data);
        obj = (JsonObject) obj.get("d");
        return obj.get("ReturnObject").getAsBoolean();
    }


    /**
     * 解析提箱状态结果
     *
     * @param data
     * @return
     */
    public static Object praseGetBoxResult(String data) throws Exception {
        JsonParser prase = new JsonParser();
        JsonObject obj = (JsonObject) prase.parse(data);
        obj = (JsonObject) obj.get("d");
        return obj.get("ReturnObject").getAsString();

    }

    /**
     * 解析场地字典信息
     *
     * @param data
     * @return
     */
    public static List<StackBean> praseCdListData(String data) {
        List<StackBean> list = new ArrayList<>();
        try {
            JsonArray array = (JsonArray) getHead(data);
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = (JsonObject) array.get(i);
                StackBean cdbean = deserialize(obj, StackBean.class);
                list.add(cdbean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 解析田位数据
     *
     * @param data
     * @return
     */
    public static Bay praseBayData(String data) {
        try {
            JsonObject obj = (JsonObject) getHead(data);
            if (null == obj) return null;
            return deserialize(obj, Bay.class);
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析田位数据
     *
     * @param data
     * @return
     */
    public static List<String> praseListBayData(String data) {
        try {
            JsonParser prase = new JsonParser();
            JsonObject obj = (JsonObject) prase.parse(data);
            obj = (JsonObject) obj.get("d");
            Dbay dbay = JsonUtils.deserialize(obj, Dbay.class);
            return dbay.getReturnObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析箱子移动合法性状态结果
     *
     * @param data
     * @return
     */
    public static String praseCheckBoxResult(String data) throws Exception {
        JsonParser prase = new JsonParser();
        JsonObject obj = (JsonObject) prase.parse(data);
        obj = (JsonObject) obj.get("d");
        return obj.get("ReturnObject").getAsString();
    }

    /**
     * 解析箱子移动状态结果
     *
     * @param data
     * @return
     */
    public static boolean praseMoveBoxResult(String data) throws Exception {
        JsonParser prase = new JsonParser();
        JsonObject obj = (JsonObject) prase.parse(data);
        obj = (JsonObject) obj.get("d");
        return obj.get("ReturnObject").getAsBoolean();
    }

    /**
     * 解析所有系统参数
     * @param data
     */
    public static Map<String, String> parseAllSysParams(String data){
        try {
            JsonObject obj = (JsonObject) getHead(data);
            Map<String,String> maps  =  JsonUtils.deserialize(obj,Map.class);
            return maps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析resultCode
     * @param data
     * @return
     */
    public static String parseResultCode(String data){
        JsonParser prase = new JsonParser();
        JsonObject obj = (JsonObject) prase.parse(data);
        obj = (JsonObject) obj.get("ResultCode");
        return obj.toString();
    }
}
