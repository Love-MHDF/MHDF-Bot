package cn.ChengZhiYa.MHDFBot.event.notice;

import cn.ChengZhiYa.MHDFBot.api.event.NoticeEvent;
import cn.ChengZhiYa.MHDFBot.entity.group.File;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class GroupUploadEvent extends NoticeEvent {
    Long groupId;
    File file;

    public GroupUploadEvent(JSONObject eventData) {
        super(eventData);
        this.groupId = eventData.getLong("group_id");
        this.file = new File(eventData.getJSONObject("file"));
    }
}
