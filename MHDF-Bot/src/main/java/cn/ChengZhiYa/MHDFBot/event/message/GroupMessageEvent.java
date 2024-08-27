package cn.ChengZhiYa.MHDFBot.event.message;

import cn.ChengZhiYa.MHDFBot.api.enums.user.RoleType;
import cn.ChengZhiYa.MHDFBot.api.event.MessageEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupMessageEvent extends MessageEvent {
    Long groupId;
    RoleType senderRole;

    public GroupMessageEvent(JSONObject eventData) {
        super(eventData);
        this.groupId = eventData.getLong("group_id");
        this.senderRole = RoleType.getType(eventData.getJSONObject("sender").getString("role"));
    }
}
