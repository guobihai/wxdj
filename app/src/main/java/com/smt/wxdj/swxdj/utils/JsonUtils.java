package com.smt.wxdj.swxdj.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * json工具类
 * Created by gbh on 16/6/25.
 */
public class JsonUtils {
    private static Gson mGson = new Gson();

    /**
     * 将对象转为JSON字符串
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将json 数据转为实体对象类
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Class<T> clz) {
        return mGson.fromJson(json, clz);
    }


    /**
     * 将json 数据转为实体对象类
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(JsonObject json, Class<T> clz) {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }

    public static String toJson(Object src) {
        return mGson.toJson(src);
    }

    public static <T> T toObject(String json, Class<T> claxx) {
        return mGson.fromJson(json, claxx);
    }

    public static <T> T toObject(byte[] bytes, Class<T> claxx) {
        return mGson.fromJson(new String(bytes), claxx);
    }

    public static <T> List<T> toList(String json, Class<T> claxx) {
        Type type = new TypeToken<ArrayList<T>>() {
        }.getType();
        List<T> list = mGson.fromJson(json, type);
        return list;
    }

    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) throws Exception {
        List<T> lst = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            lst.add(new Gson().fromJson(elem, clazz));
        }
        return lst;
    }

}
