package cn.ChengZhiYa.MHDFBot.util;

import cn.ChengZhiYa.MHDFBot.api.interfaces.EventHandler;
import cn.ChengZhiYa.MHDFBot.api.manager.Event;
import cn.ChengZhiYa.MHDFBot.api.manager.Listener;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.HashMap;

public final class ListenerUtil {
    @Getter
    private static final HashMap<Listener, PluginInfo> listenerHashMap = new HashMap<>();

    public static void registerListener(PluginInfo pluginInfo, Listener listener) {
        getListenerHashMap().put(listener, pluginInfo);
    }

    public static void callEvent(Event event) {
        for (Listener listener : getListenerHashMap().keySet()) {
            for (Method method : listener.getClass().getMethods()) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    try {
                        method.invoke(listener, event);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }
}
