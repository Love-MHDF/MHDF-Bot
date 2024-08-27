package cn.ChengZhiYa.MHDFBot.api.event;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public abstract class NoticeEvent extends BotEvent {
    Long userId;

    public NoticeEvent(JSONObject eventData) {
        super(eventData);
        this.userId = eventData.getLong("user_id");
    }
}
