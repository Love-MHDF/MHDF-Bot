package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupCardEvent extends NoticeEvent {
    Long groupId;
    String cardNew;
    String cardOld;

    public GroupCardEvent(JSONObject eventData) {
        super(eventData);
        this.groupId = eventData.getLong("group_id");
        this.cardNew = eventData.getString("card_new");
        this.cardOld = eventData.getString("card_old");
    }
}
