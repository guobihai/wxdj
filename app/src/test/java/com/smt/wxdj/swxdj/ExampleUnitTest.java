package com.smt.wxdj.swxdj;

import com.smt.wxdj.swxdj.bean.Dbay;
import com.smtlibrary.utils.JsonUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//        String url = "http://192.9.200.203:8080/WebService.asmx/WebServiceBizExecute";
//        String apkName = url.substring(0, url.lastIndexOf("/"));
//        System.out.println(apkName);
        int a = 6;
        int[] ints = new int[]{1,2,3,4};
        int pos = Arrays.binarySearch(ints, a);
        System.out.println(pos);
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 等待时间转换
     */
    private String changeWaitTime(String str) {
        Long time = Long.parseLong(str);
        long day = time / (24 * 60);
        long hour = (time % (24 * 60)) / 60;
        long minute = (time % (24 * 60)) % 60;
        if (day == 0) {
            if (hour == 0) {
                return "" + minute;
            } else {
                return hour + ":" + minute;
            }
        }
        return day + " " + hour + ":" + minute;
    }

    @Test
    public void textFor() {
        int col = 10;
        int row = 4;
        for (int i = 0; i < col; i++) {
            System.out.println("=============i:" + i);
            for (int j = 0; j < row; j++) {
//                System.out.println("=============j:"+j);
                if (j == 3)
                    break;
            }

        }
    }

    @Test
    public void searchByCode() {
        String str = "7.2";
        String str1 = "7.3";
        System.out.println("=============i:");
    }

    @Test
    public void listToJson() {

        Dbay dbay = new Dbay();
        List<String> list = new ArrayList<>();
        list.add("AB1");
        list.add("AB3");
        list.add("AB2");
        list.add("AB4");
        dbay.setReturnObject(list);
        String str = JsonUtils.serialize(list);
        System.out.println("=============str:" + str);
        List<String> strList = JsonUtils.deserialize(str, List.class);
        System.out.println("=======s======str:" + strList);
    }

    @Test
    public void ec() {
        String pol = null;
        String pod = null;
        if (pol.equals(pod)) {
            System.out.println("=============str:");
        } else
            System.out.println("====s=========str:");
    }

    @Test
    public void test() {
        String str = DesUtils.strEnc("440811198805302374", "1", "2", "3");
        System.out.println(str);
        //774CB6A7198A3A74
        //774CB6A7198A3A74
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }
}