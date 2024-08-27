package cn.ChengZhiYa.MHDFBotBukkitHook.event;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public final class WebSocketEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    JSONObject data;

    public WebSocketEvent(JSONObject data) {
        super(true);
        this.data = data;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }
}
