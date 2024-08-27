package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.enums.notice.GroupAdminSubType;
import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupAdminEvent extends NoticeEvent {
    GroupAdminSubType subType;
    Long groupId;

    public GroupAdminEvent(JSONObject eventData) {
        super(eventData);
        this.subType = GroupAdminSubType.getSubType(eventData.getString("subType"));
        this.groupId = eventData.getLong("group_id");
    }
}
