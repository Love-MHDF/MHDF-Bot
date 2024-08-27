package cn.ChengZhiYa.MHDFBot.listener.main;

import cn.ChengZhiYa.MHDFBot.api.interfaces.EventHandler;
import cn.ChengZhiYa.MHDFBot.api.manager.Listener;
import cn.ChengZhiYa.MHDFBot.event.message.GroupMessageEvent;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

public final class GroupMessage implements Listener {
    @EventHandler
    public void onGroupMessage(GroupMessageEvent event) {
        colorLog("在QQ群({})收到了一个消息 {}({}) -> {}", event.getGroupId(), event.getSender().getNickName(), event.getSender().getUserId(), event.getMessage());
    }
}
