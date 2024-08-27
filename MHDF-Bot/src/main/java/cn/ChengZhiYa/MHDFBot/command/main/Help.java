package cn.ChengZhiYa.MHDFBot.command.main;

import cn.ChengZhiYa.MHDFBot.MHDFBot;
import cn.ChengZhiYa.MHDFBot.api.manager.CommandExecutor;
import cn.ChengZhiYa.MHDFBot.entity.plugin.Command;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import cn.ChengZhiYa.MHDFBot.util.CommandUtil;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

public final class Help implements CommandExecutor {
    @Override
    public void onCommand(String label, String[] args) {
        StringBuilder helpMessage = new StringBuilder("&f==============&e命令帮助&f==============&r").append("\n");
        for (String commandString : CommandUtil.getCommandHashMap().keySet()) {
            Command command = CommandUtil.getCommandHashMap().get(commandString);
            PluginInfo pluginInfo = command.getPluginInfo() != null ? command.getPluginInfo() : MHDFBot.getBotInfo();
            helpMessage.append("#88CDFF").append(commandString);
            if (command.getUsage() != null) {
                helpMessage.append(" 用法:").append(command.getUsage());
            }
            if (command.getDescription() != null) {
                helpMessage.append("#FF888C 简介:").append(command.getDescription());
            }
            helpMessage.append("#FFBD88 来自:").append(pluginInfo.getName()).append("(").append(pluginInfo.getVersion()).append(")").append("\n");
        }
        helpMessage.append("&f==============&e命令帮助&f==============");
        colorLog(helpMessage.toString());
    }
}
