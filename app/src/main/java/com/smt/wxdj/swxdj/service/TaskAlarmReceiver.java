package com.smt.wxdj.swxdj.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *
 * @author xlj
 * @date 2017/10/30
 */

public class TaskAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, CheckTaskService.class);
        context.startService(i);
    }
}
