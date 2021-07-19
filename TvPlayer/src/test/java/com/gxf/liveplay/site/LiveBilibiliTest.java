package com.gxf.liveplay.site;

import org.junit.Test;

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

}