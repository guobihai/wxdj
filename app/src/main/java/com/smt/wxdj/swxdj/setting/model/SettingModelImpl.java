package com.smt.wxdj.swxdj.setting.model;

import com.smt.wxdj.swxdj.bean.Dock;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.LogUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbh on 16/9/21.
 */

public class SettingModelImpl implements SettingModel {
    @Override
    public void loadDockData(final String param, final IPublicResultInterface iLoadResultinterfaces) {
        OkHttpUtils.postResultStream(URLTool.getPubUrl(), param, new OkHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(final Object response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = (InputStream) response;
                        try {
                            iLoadResultinterfaces.onSucess(parseAppResponseXML(is));
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
                if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
                    iLoadResultinterfaces.onFailure(e.toString(), e);
                } else {
                    iLoadResultinterfaces.onFailure(e.toString(), e);
                }
            }
        });


    }

    @Override
    public void checkDevice(String param, final IPublicResultInterface iLoadResultinterfaces) {
        OkHttpUtils.postResultStream(URLTool.getPubUrl(), param, new OkHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(final Object response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = (InputStream) response;
                        try {
                            boolean b = parseCheckResponseXML(is);
                            LogUtils.sysout("b=========", b);
                            iLoadResultinterfaces.onSucess(b);
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
                if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
                    iLoadResultinterfaces.onFailure(e.toString(), e);
                } else {
                    iLoadResultinterfaces.onFailure(e.toString(), e);
                }
            }
        });
    }


    private List<Dock> parseAppResponseXML(InputStream inStream) throws Exception {

        List<Dock> lists = new ArrayList<>();
        Dock dock = null;
        String str = InputStreamTOString(inStream);
//        LogUtils.sysout("?????????", str);
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");


        XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
        //??????XmlPullParser?????????
        XmlPullParser parser = pullParserFactory.newPullParser();
        parser.setInput(StringTOInputStream(str), "UTF-8");
        int eventType = parser.getEventType();//?????????????????????
        while (eventType != XmlPullParser.END_DOCUMENT) {//??????????????????????????????
            String nodeName = parser.getName();//?????????????????????????????????????????????
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
                        LogUtils.sysout("==value=", v);
                        dock.setDock(v);
                    }
                    if ("MAJ_LOC".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==value=", v);
                        dock.setMajloc(v);
                    }
                    if ("SUB_LOC".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==value=", v);
                        dock.setSubloc(v);
                    }
                    if ("WS_URL".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==value=", v);
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

    /**
     * ??????XML
     * @param inStream
     * @return
     * @throws Exception
     */
    private boolean parseCheckResponseXML(InputStream inStream) throws Exception {

        XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
        //??????XmlPullParser?????????
        XmlPullParser parser = pullParserFactory.newPullParser();
//        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();//?????????????????????
        while (eventType != XmlPullParser.END_DOCUMENT) {//??????????????????????????????
            String nodeName = parser.getName();//?????????????????????????????????????????????
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("CheckDeviceAuthResult".equals(nodeName)) {
                        String v = parser.nextText();
                        LogUtils.sysout("==========is true:", v);
                        return v.equalsIgnoreCase("true");
                    }
                    break;
            }
            eventType = parser.next();
        }

        return false;
    }

    /**
     * ???String?????????InputStream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream StringTOInputStream(String in) throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("UTF-8"));
        return is;
    }

    public static String InputStreamTOString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while ((count = in.read(data, 0, 1024)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }


}
