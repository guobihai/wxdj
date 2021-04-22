package com.smt.wxdj.swxdj.utils;

import android.content.Context;
import android.util.Log;

import com.smt.wxdj.swxdj.R;
import com.smtlibrary.utils.SystemUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gbh on 16/9/21.
 */

public class ReadRawFileUtils {

    /**
     * 检查设备是否合法
     * @param context
     * @return
     * @throws Exception
     */
    public static String readCheckSoapFile(Context context)  {
        try {
            String deviceId = SystemUtils.getDeviceId(context);
            InputStream is = context.getResources().openRawResource(R.raw.checkdevice);
            String soapxml = readTextFromSDcard(is);
            Map<String, String> params = new HashMap<>();
            params.put("Id", deviceId);
            return replace(soapxml, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 加载码头信息
     * @param context
     * @return
     * @throws Exception
     */
    public static String readCheckSoapDock(Context context)  {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.checkdock);
            String soapxml = readTextFromSDcard(is);
            return  soapxml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检查更新升级
     * @param context
     * @return
     */
    public static String readCheckSoapUpdate(Context context, String fileName)  {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.checkupdate);
            String soapxml = readTextFromSDcard(is);
            Map<String, String> params = new HashMap<>();
            params.put("fileName", fileName);
            return replace(soapxml, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String replace(String xml, Map<String, String> params) throws Exception {
        String result = xml;
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String name = "\\$" + entry.getKey();
                Pattern pattern = Pattern.compile(name);
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    result = matcher.replaceAll(entry.getValue());
                }
            }
        }
        return result;
    }

    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
