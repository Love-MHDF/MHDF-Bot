package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.enums.notice.GroupIncreaseSubType;
import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupIncreaseEvent extends NoticeEvent {
    GroupIncreaseSubType subType;
    Long groupId;
    Long operatorId;

    public GroupIncreaseEvent(JSONObject eventData) {
        super(eventData);
        this.subType = GroupIncreaseSubType.getSubType(eventData.getString("sub_type"));
        this.groupId = eventData.getLong("group_id");
        this.operatorId = eventData.getLong("operator_id");
    }
}
