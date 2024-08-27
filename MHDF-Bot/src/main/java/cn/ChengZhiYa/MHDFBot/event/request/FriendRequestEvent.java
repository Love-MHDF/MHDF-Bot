package cn.ChengZhiYa.MHDFBot.event.request;

import cn.ChengZhiYa.MHDFBot.api.MHDFBot;
import cn.ChengZhiYa.MHDFBot.api.event.RequestEvent;
import com.alibaba.fastjson2.JSONObject;

public final class FriendRequestEvent extends RequestEvent {
    public FriendRequestEvent(JSONObject eventData) {
        super(eventData);
    }

    public void handlingFriendRequest(boolean accept) {
        MHDFBot.handlingFriendRequest(getFlag(), accept);
    }
}
