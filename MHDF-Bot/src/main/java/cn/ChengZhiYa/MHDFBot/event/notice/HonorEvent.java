package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.enums.notice.HonorType;
import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class HonorEvent extends NoticeEvent {
    HonorType honorType;
    Long groupId;
    Long targetId;

    public HonorEvent(JSONObject eventData) {
        super(eventData);
        this.honorType = HonorType.getType(eventData.getString("honorType"));
        this.groupId = eventData.getLong("group_id");
        this.targetId = eventData.getLong("target_id");
    }
}
