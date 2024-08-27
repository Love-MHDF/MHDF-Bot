package cn.ChengZhiYa.MHDFBot.util;

import cn.ChengZhiYa.MHDFBot.api.manager.CommandExecutor;
import cn.ChengZhiYa.MHDFBot.entity.plugin.Command;
import lombok.Getter;

import java.util.HashMap;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

public final class CommandUtil {
    @Getter
    static HashMap<String, Command> commandHashMap = new HashMap<>();

    public static Command getCommand(String command) {
        if (getCommandHashMap().get(command) != null) {
            return getCommandHashMap().get(command);
        }
        return new Command();
    }

    public static void executeCommand(String commandString) {
        String[] parts = commandString.split(" ");
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);
        if (commandHashMap.get(parts[0]) != null) {
            CommandExecutor commandExecutor = commandHashMap.get(parts[0]).getCommandExecutor();
            commandExecutor.onCommand(parts[0], args);
        } else {
            colorLog("&c{}命令不存在!", parts[0]);
        }
    }
}
