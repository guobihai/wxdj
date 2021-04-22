package com.smt.wxdj.swxdj.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.smt.wxdj.swxdj.R;

/**
 * Created by gbh on 16/11/9.
 */

public class PlayRing {
    /**
     * 播放音乐
     */
    public static synchronized void ring(Context context) {
        // TODO Auto-generated method stub
        try {
            MediaPlayer player = MediaPlayer.create(context, R.raw.toast);
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
