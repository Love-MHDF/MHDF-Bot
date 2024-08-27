package cn.ChengZhiYa.MHDFBot.event.bot;

import cn.ChengZhiYa.MHDFBot.api.enums.bot.LifecycleSubType;
import cn.ChengZhiYa.MHDFBot.api.event.BotEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class LifecycleEvent extends BotEvent {
    LifecycleSubType subType;

    public LifecycleEvent(JSONObject eventData) {
        super(eventData);
        subType = LifecycleSubType.getSubType(eventData.getString("sub_type"));
    }
}
