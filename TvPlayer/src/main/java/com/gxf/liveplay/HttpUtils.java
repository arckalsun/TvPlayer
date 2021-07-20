package com.gxf.liveplay;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gxf.liveplay.site.LiveBilibili;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gongxufan on 2017/9/5.
 */

public class HttpUtils {
    public static String apiPath = "http://220.248.174.237:3390/api/";
    public static String apiPathLocal = "http://172.17.30.14:8080/api/";
    public static String apiPathOuter = "http://220.248.174.237:3390/api/";
    public static String apkPath = apiPath + "downloadApk";

    public static String getPlayList(String userName, String password) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiPath + "getPlayList?userName=" + userName + "&password=" + password)
                .build();
        Response response = client.newCall(request).execute();
        String playList = response.body().string();
        return playList;
    }

    public static String getOfflinePlayList() {
        String[] rooms = new String[]{"9196015", "21809030", "21611957", "7644406", "22756079"};
        JSONArray list = new JSONArray();
        for(String s : rooms){
            JSONObject live = new JSONObject();
            LiveBilibili.LiveInfo info = new LiveBilibili().parseLiveInfo(s);
            live.put(info.getName(), info.getLiveUrl());
            list.add(live);
        }

        JSONArray array = new JSONArray();
        JSONObject group = new JSONObject();
        group.put("group", "B站直播");
        group.put("list", list);
        array.add(group);
        return array.toJSONString();
    }


    public static Boolean login(String mEmail, String mPassword, Context context) throws Exception {
        try {
            //获取uuid
            SharedPreferences userSettings = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String uuid = userSettings.getString("uuid", "none");
            if ("none".equals(uuid)) {
                SharedPreferences.Editor editor = userSettings.edit();
                uuid = UUID.randomUUID().toString();
                editor.putString("uuid", uuid);
                editor.commit();
            }
            String mac = DeviceUtils.getAdresseMAC(context);
            String eth0 = DeviceUtils.getMac();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiPath + "auth?userName=" + mEmail + "&password=" + mPassword + "&mac=" + mac + "&eth0=" + eth0 + "&uuid=" + uuid)
                    .build();
            Response response = client.newCall(request).execute();
            //网络连接错误
            if (response.code() != 200) {
                LoginActivity.state = 0;
                return false;
            }
            String result = response.body().string();
            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            LoginActivity.state = (Integer) jsonObject.get("result");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LoginActivity.state = 0;
            return false;
        }
    }

    public static Map<String, String> getVersionInfo() {
        Map<String, String> versionInfo = new HashMap<>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiPath + "getAppVersion").build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            versionInfo.put("version", jsonObject.getString("version"));
            versionInfo.put("updateInfo", jsonObject.getString("updateInfo"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return versionInfo;
    }
}
