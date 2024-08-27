package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class PokeEvent extends NoticeEvent {
    Long groupId;
    Long targetId;

    public PokeEvent(JSONObject eventData) {
        super(eventData);
        this.groupId = eventData.getLong("group_id");
        this.targetId = eventData.getLong("target_id");
    }
}
