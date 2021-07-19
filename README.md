# TvPlayer

1，android智能电视播放器，可以播放各电视台节目，播放基于ijkplayer的实现

2，测试的源地址可能失效，如需测试可以自己更换播放源。HttpUtils.getOfflinePlayList()返回的是测试源地址

3，项目有MainActivityOffline和MainActivity，MainActivityOffline是离线使用的，MainActivity涉及到登录和节目列表获取

4，编译环境：Android Studio3.0,jdk8

本项目中的播放源地址可能会失效，如需测试需要更改可用的播放源，具体代码在com.gxf.liveplay.HttpUtils#getOfflinePlayList，数据格式如下：

```

```
# screenshot
![screenshot](https://github.com/gongxufan/TvPlayer/blob/master/screenshot/3.jpeg?raw=true)

已实现https协议
