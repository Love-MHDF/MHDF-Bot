package cn.ChengZhiYa.MHDFBot.api.event;

import cn.ChengZhiYa.MHDFBot.api.manager.Event;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public abstract class BotEvent implements Event {
    Long selfId;

    public BotEvent(JSONObject eventData) {
        this.selfId = eventData.getLong("self_id");
    }
}
