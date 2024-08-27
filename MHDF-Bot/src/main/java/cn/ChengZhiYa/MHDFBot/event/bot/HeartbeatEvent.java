package cn.ChengZhiYa.MHDFBot.event.bot;

import cn.ChengZhiYa.MHDFBot.api.event.BotEvent;
import com.alibaba.fastjson2.JSONObject;

public final class HeartbeatEvent extends BotEvent {
    public HeartbeatEvent(JSONObject eventData) {
        super(eventData);
    }
}
