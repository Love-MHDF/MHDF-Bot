package cn.ChengZhiYa.MHDFBot.command;

import cn.ChengZhiYa.MHDFBot.api.MHDFBot;
import cn.ChengZhiYa.MHDFBot.command.main.Help;
import cn.ChengZhiYa.MHDFBot.command.main.Plugins;
import cn.ChengZhiYa.MHDFBot.command.main.Plugman;

public final class CommandRegister {
    public static void registerCommands() {
        MHDFBot.getCommand("stop").setCommandExecutor((command, args) -> MHDFBot.exit()).setDescription("关闭框架").register();
        MHDFBot.getCommand("plugins").setCommandExecutor(new Plugins()).setDescription("查询插件列表").setAliases("pl").register();
        MHDFBot.getCommand("help").setCommandExecutor(new Help()).setDescription("查询命令帮助").register();
        MHDFBot.getCommand("plugman").setCommandExecutor(new Plugman()).setTabComplete(new Plugman()).setDescription("插件管理").register();
    }
}
