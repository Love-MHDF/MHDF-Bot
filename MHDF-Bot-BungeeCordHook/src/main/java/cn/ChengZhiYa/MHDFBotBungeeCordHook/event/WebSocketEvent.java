package cn.ChengZhiYa.MHDFBotBungeeCordHook.event;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@Getter
public final class WebSocketEvent extends Event {
    JSONObject data;

    public WebSocketEvent(JSONObject data) {
        this.data = data;
    }
}
