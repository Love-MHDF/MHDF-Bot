package cn.ChengZhiYa.MHDFBot.command.main;

import cn.ChengZhiYa.MHDFBot.api.manager.CommandExecutor;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import cn.ChengZhiYa.MHDFBot.util.PluginUtil;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

public final class Plugins implements CommandExecutor {
    @Override
    public void onCommand(String command, String[] args) {
        StringBuilder pluginsMessage = new StringBuilder("插件(" + PluginUtil.getPluginHashMap().size() + "): ");
        for (PluginInfo pluginInfo : PluginUtil.getPluginHashMap().keySet()) {
            switch (PluginUtil.getPluginHashMap().get(pluginInfo)) {
                case Load_Error -> pluginsMessage.append("&c");
                case Load_Done -> pluginsMessage.append("&a");
            }
            pluginsMessage.append(pluginInfo.getName()).append("&f, ");
        }
        colorLog(pluginsMessage.toString());
    }
}
