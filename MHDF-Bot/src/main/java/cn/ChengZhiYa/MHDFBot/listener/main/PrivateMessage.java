package cn.ChengZhiYa.MHDFBot.listener.main;

import cn.ChengZhiYa.MHDFBot.api.interfaces.EventHandler;
import cn.ChengZhiYa.MHDFBot.api.manager.Listener;
import cn.ChengZhiYa.MHDFBot.event.message.PrivateMessageEvent;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

public final class PrivateMessage implements Listener {
    @EventHandler
    public void onPrivateMessage(PrivateMessageEvent event) {
        colorLog("在私聊收到了一个消息 {}({}) -> {}", event.getSender().getNickName(), event.getSender().getUserId(), event.getMessage());
    }
}
