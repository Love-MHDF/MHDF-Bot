package cn.ChengZhiYa.MHDFBot.listener.main;

import cn.ChengZhiYa.MHDFBot.api.interfaces.EventHandler;
import cn.ChengZhiYa.MHDFBot.api.manager.Listener;
import cn.ChengZhiYa.MHDFBot.event.bot.LifecycleEvent;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

public final class Lifecycle implements Listener {
    @EventHandler
    public void onBotOnline(LifecycleEvent event) {
        switch (event.getSubType()) {
            case ENABLE, CONNECT -> colorLog("{}已上线", event.getSelfId());
            case DISABLE -> colorLog("{}已离线", event.getSelfId());
        }
    }
}
