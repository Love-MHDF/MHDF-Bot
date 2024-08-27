package cn.ChengZhiYa.MHDFBot.api.event;

import cn.ChengZhiYa.MHDFBot.api.enums.message.MessageSubType;
import cn.ChengZhiYa.MHDFBot.api.enums.message.MessageType;
import cn.ChengZhiYa.MHDFBot.entity.user.Sender;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public abstract class MessageEvent extends BotEvent {
    MessageType messageType;
    MessageSubType subType;
    String message;
    Integer messageId;
    Sender sender;

    public MessageEvent(JSONObject eventData) {
        super(eventData);
        this.messageType = MessageType.getType(eventData.getString("message_type"));
        this.subType = MessageSubType.getSubType(eventData.getString("sub_type"));
        this.message = eventData.getString("raw_message");
        this.messageId = eventData.getIntValue("message_id");
        this.sender = new Sender(eventData.getJSONObject("sender"));
    }
}
