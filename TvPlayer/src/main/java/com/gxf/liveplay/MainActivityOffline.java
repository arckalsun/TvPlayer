package com.gxf.liveplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

/**
 * splash
 * 启动登录检测
 */
public class MainActivityOffline extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 1000; // 两秒后进入系统
    private boolean isUpdateChecked = false;
    public void checkLogin(){
        SharedPreferences userSettings = getSharedPreferences("auth", Context.MODE_PRIVATE);
        final String lastVedioUrl = userSettings.getString("lastVedioUrl", "none");
        final Toast toast = Toast.makeText(MainActivityOffline.this, "系统初始化失败...", Toast.LENGTH_SHORT);
        // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PlayListCache.initPlayInfo(null,null);
                    String url = lastVedioUrl;
                    if (url.equals("none"))
                        url = PlayListCache.playListMap.get(PlayListCache.playListMap.keySet().iterator().next());
//                    url = "https://xy121x205x162x189xy.mcdn.bilivideo.cn:486/live-bvc/466962/live_290515513_3522421_2500.flv?expires=1626701236&len=0&oi=1944306180&pt=web&qn=0&trid=1000a5483a8a3edd4d7092b501ca1d30314f&sigparams=cdn,expires,len,oi,pt,qn,trid&cdn=cn-live-mcdn&sign=fbeca9218d270461037c2b63e67d8ffb&p2p_type=0&src=9&sl=1&free_type=0&sk=417e709c171a500&sid=11000064";
                    LiveActivityRel.activityStart(MainActivityOffline.this, url);
                    MainActivityOffline.this.finish();
                } catch (Exception e) {
                    toast.show();
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLogin();
    }
}
