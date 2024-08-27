package cn.ChengZhiYa.MHDFBot.api.event;

import cn.ChengZhiYa.MHDFBot.api.enums.request.RequestType;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public abstract class RequestEvent extends BotEvent {
    RequestType requestType;
    Long userId;
    String comment;
    String flag;

    public RequestEvent(JSONObject eventData) {
        super(eventData);
        this.requestType = RequestType.getType(eventData.getString("request_type"));
        this.userId = eventData.getLong("user_id");
        this.comment = eventData.getString("comment");
        this.flag = eventData.getString("flag");
    }
}
