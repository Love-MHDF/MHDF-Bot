package cn.ChengZhiYa.MHDFBot.event.request;

import cn.ChengZhiYa.MHDFBot.api.MHDFBot;
import cn.ChengZhiYa.MHDFBot.api.enums.request.RequestSubType;
import cn.ChengZhiYa.MHDFBot.api.event.RequestEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupRequestEvent extends RequestEvent {
    RequestSubType subType;
    Long groupId;

    public GroupRequestEvent(JSONObject eventData) {
        super(eventData);
        subType = RequestSubType.getSubType(eventData.getString("sub_type"));
        groupId = eventData.getLong("group_id");
    }

    public void handlingGroupRequest(boolean accept) {
        MHDFBot.handlingGroupRequest(getFlag(), getSubType(), accept);
    }
}
