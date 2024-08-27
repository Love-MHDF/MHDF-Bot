package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupRecallEvent extends NoticeEvent {
    Long groupId;
    Long operatorId;
    Long messageId;

    public GroupRecallEvent(JSONObject eventData) {
        super(eventData);
        this.groupId = eventData.getLong("group_id");
        this.operatorId = eventData.getLong("operator_id");
        this.messageId = eventData.getLong("message_id");
    }
}
