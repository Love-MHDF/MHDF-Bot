package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.enums.notice.GroupBanSubType;
import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupBanEvent extends NoticeEvent {
    GroupBanSubType subType;
    Long groupId;
    Long operatorId;
    Long duration;

    public GroupBanEvent(JSONObject eventData) {
        super(eventData);
        this.subType = GroupBanSubType.getSubType(eventData.getString("subType"));
        this.groupId = eventData.getLong("group_id");
        this.operatorId = eventData.getLong("operator_id");
        this.duration = eventData.getLong("duration");
    }
}
