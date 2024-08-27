package cn.ChengZhiYa.MHDFBot.event.message;

import cn.ChengZhiYa.MHDFBot.api.event.MessageEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class PrivateMessageEvent extends MessageEvent {
    public PrivateMessageEvent(JSONObject eventData) {
        super(eventData);
    }
}
