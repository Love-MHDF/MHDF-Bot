package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.enums.notice.GroupDecreaseSubType;
import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupDecreaseEvent extends NoticeEvent {
    GroupDecreaseSubType subType;
    Long groupId;
    Long operatorId;

    public GroupDecreaseEvent(JSONObject eventData) {
        super(eventData);
        this.subType = GroupDecreaseSubType.getSubType(eventData.getString("sub_type"));
        this.groupId = eventData.getLong("group_id");
        this.operatorId = eventData.getLong("operator_id");
    }
}

