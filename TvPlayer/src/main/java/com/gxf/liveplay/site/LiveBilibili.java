package com.gxf.liveplay.site;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LiveBilibili {

    private static String baseUrl = "https://live.bilibili.com/";
    private static Pattern pattern =  Pattern.compile("__NEPTUNE_IS_MY_WAIFU__=\\{\\(\\.\\+\\?\\)}</script>");
//    private static Pattern pattern =  Pattern.compile("__NEPTUNE_IS_MY_WAIFU__={(.+?)}</script>");
//    private static Pattern pattern =  Pattern.compile("__NEPTUNE_IS_MY_WAIFU__=\\{\\(.+?\\)}</script>");
    public static class LiveInfo{
        String roomId;
        String liveUrl;
        String name;

        public String toString(){
            return "roomid: " + roomId + "\nname: "+name + "\nliveUrl: " + liveUrl + "\n";
        }
    }
    /**
     * 示例 https://live.bilibili.com/9196015
     * 根据房间号，获取直播地址和标题
     * @param roomId
     * @return
     */
    public static LiveInfo parseLiveInfo(String roomId) {
        LiveInfo liveInfo = new LiveInfo();
        try {
            System.out.println(pattern.pattern());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("accept","*/*")
                    .addHeader("cache-control","no-cache")
                    .addHeader("pragma","no-cache")
                    .addHeader("user-agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; .NET CLR 1.1.4322; systeccloud 3.5.1)")
                    .url(baseUrl + roomId).build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            System.out.println(result);
            // 正则取出json字符串
            Matcher m = pattern.matcher(result);
            String jsonStr = m.group();
            System.out.println(jsonStr);
            JSONObject jsonObject = (JSONObject) JSON.parse(jsonStr);
            JSONObject codec = jsonObject.getJSONObject("roomInitRes").getJSONObject("data")
                    .getJSONObject("playurl_info").getJSONObject("playurl").getJSONArray("stream").getJSONObject(0)
                    .getJSONArray("format").getJSONObject(0).getJSONArray("codec").getJSONObject(0);
            JSONObject url_info = codec.getJSONArray("url_info").getJSONObject(0);

            liveInfo.roomId = roomId;
            liveInfo.name = jsonObject.getJSONObject("roomInfoRes").getJSONObject("data").getJSONObject("room_info").getString("title");
            liveInfo.liveUrl = url_info.getString("host") + codec.getString("base_url") + url_info.getString("extra");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return liveInfo;
    }

}
