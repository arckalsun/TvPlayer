package com.gxf.liveplay.site;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class LiveBilibiliTest {

    @Test
    public void parseLiveInfo() {
        LiveBilibili.LiveInfo info = LiveBilibili.parseLiveInfo("9196015");
        System.out.println(info);
    }

    @Test
    public void parsePattern() {
        // "__NEPTUNE_IS_MY_WAIFU__=\\{\\(\\.\\+\\?\\)\\}</script>"
        Pattern pattern =  Pattern.compile("__NEPTUNE_IS_MY_WAIFU__=\\{\\(\\.\\+\\?\\)\\}</script>");
        String content = "<script>window.__NEPTUNE_IS_MY_WAIFU__={\"roomInitRes\":{\"code\":0,\"message\":\"0\",\"ttl\":1}}</script>";
        System.out.println(content);
        Matcher m = pattern.matcher(content);
        String jsonStr = m.group();
        System.out.println(jsonStr);
    }

    @Test
    public void testUrlDecode() throws UnsupportedEncodingException {
        String url = "https://xy121x205x162x189xy.mcdn.bilivideo.cn:486/live-bvc/466962/live_290515513_3522421_2500.flv?expires=1626701236%26len=0%26oi=1944306180%26pt=web%26qn=0%26trid=1000a5483a8a3edd4d7092b501ca1d30314f%26sigparams=cdn,expires,len,oi,pt,qn,trid%26cdn=cn-live-mcdn%26sign=fbeca9218d270461037c2b63e67d8ffb%26p2p_type=0%26src=9%26sl=1%26free_type=0%26sk=417e709c171a500%26sid=11000064";

        String str = URLDecoder.decode(url,"UTF-8");
        System.out.println(str);
    }

}