package cn.ChengZhiYa.MHDFBot.task;

import cn.ChengZhiYa.MHDFBot.api.MHDFBotRunnable;
import cn.ChengZhiYa.MHDFBot.server.WebSocket;
import com.alibaba.fastjson2.JSONObject;

public final class HeartBeat extends MHDFBotRunnable {
    @Override
    public void run() {
        WebSocket.send("heartBeat",new JSONObject());
    }
}
