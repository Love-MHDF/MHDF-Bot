package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class FriendRecallEvent extends NoticeEvent {
    Long messageId;

    public FriendRecallEvent(JSONObject eventData) {
        super(eventData);
        this.messageId = eventData.getLong("message_id");
    }
}
