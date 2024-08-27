package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;

public final class LuckyKingEvent extends NoticeEvent {
    Long groupId;
    Long targetId;

    public LuckyKingEvent(JSONObject eventData) {
        super(eventData);
        this.groupId = eventData.getLong("group_id");
        this.targetId = eventData.getLong("target_id");
    }
}
