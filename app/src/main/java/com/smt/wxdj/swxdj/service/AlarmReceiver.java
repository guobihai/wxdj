package com.smt.wxdj.swxdj.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.smt.wxdj.swxdj.utils.FileKeyName;

import java.io.File;

/**
 *
 * @author xlj
 * @date 2017/10/30
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(null == intent || TextUtils.isEmpty(intent.getAction())) return;
        if(FileKeyName.OPEN.equals(intent.getAction())){
            Intent i = new Intent(context, CheckUserService.class);
            context.startService(i);
        } else if (FileKeyName.CHECKTASK.equals(intent.getAction())) {
            Intent i = new Intent(context, CheckTaskService.class);
            context.startService(i);
        }
    }
}
