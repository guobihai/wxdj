package com.smt.wxdj.swxdj.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.utils.DateUtils;
import com.smtlibrary.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xlj on 2018/3/29.
 */

public class DownloadUtil {
    public static final int DOWNLOAD_FAIL = 100;
    public static final int DOWNLOAD_PROGRESS = 101;
    public static final int DOWNLOAD_SUCCESS = 102;
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;
//    private Handler mHandler;

    public static DownloadUtil getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient();
    }

//    public void setHandler(Handler handler) {
//        this.mHandler = handler;
//    }

    /**
     *
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        this.listener = listener;
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Message message = Message.obtain();
//                message.what = DOWNLOAD_FAIL;
//                mHandler.sendMessage(message);
                listener.onDownloadFailed(MyApplication.getContext().getString(R.string.failure_please_try_again));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    String time = DateUtils.getCurrentYMDHms();
                    String apkName = time + ".apk";
                    File file = new File(savePath, apkName);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中
//                        Message message = Message.obtain();
//                        message.what = DOWNLOAD_PROGRESS;
//                        message.obj = progress;
//                        mHandler.sendMessage(message);
                        listener.onDownloading(progress);

                    }
                    fos.flush();
                    //下载完成
//                    Message message = Message.obtain();
//                    message.what = DOWNLOAD_SUCCESS;
//                    message.obj = file.getAbsolutePath();
//                    mHandler.sendMessage(message);
                    long fileLength = file.length() / 1024;
                    LogUtils.e("fileLength", fileLength + "");
                    if (fileLength > 1) {
                        listener.onDownloadSuccess(file.getAbsolutePath());
                    } else {
                        listener.onDownloadFailed(MyApplication.getContext().getString(R.string.failure_please_try_again));
                    }
                } catch (Exception e) {
//                    Message message = Message.obtain();
//                    message.what = DOWNLOAD_FAIL;
//                    mHandler.sendMessage(message);
                    listener.onDownloadFailed(MyApplication.getContext().getString(R.string.failure_please_try_again));
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {

                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }
                }
            }
        });
    }

    public void installAPk(Context context, File apkFile) {
        Log.e("apkFile", apkFile.getName());
        Intent installAPKIntent = getApkInStallIntent(context, apkFile);
        context.startActivity(installAPKIntent);
    }

    private Intent getApkInStallIntent(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Log.e("Build.VERSION", "M....");
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".update.provider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            Log.e("Build.VERSION", "M....else");
            Uri uri = getApkUri(apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        return intent;
    }


    private Uri getApkUri(File apkFile) {
        LogUtils.e("getApkUri", apkFile.toString());
        //如果没有设置 SDCard 写权限，或者没有 SDCard,apk 文件保存在内存中，需要授予权限才能安装
        try {
            String[] command = {"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ignored) {
        }
        Uri uri = Uri.fromFile(apkFile);

        return uri;
    }


    private String isExistDir(String saveDir) throws IOException {
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    OnDownloadListener listener;

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * 下载进度
         *
         * @param progress
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed(String msg);
    }
}
