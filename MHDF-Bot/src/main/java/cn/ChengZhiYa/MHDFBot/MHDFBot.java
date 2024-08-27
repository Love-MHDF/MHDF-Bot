package cn.ChengZhiYa.MHDFBot;

import cn.ChengZhiYa.MHDFBot.console.ConsoleCommandCompleter;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import cn.ChengZhiYa.MHDFBot.server.WebSocket;
import cn.ChengZhiYa.MHDFBot.task.HeartBeat;
import cn.ChengZhiYa.MHDFBot.util.CommandUtil;
import jline.console.ConsoleReader;
import lombok.Getter;

import java.util.List;

import static cn.ChengZhiYa.MHDFBot.client.WebSocket.connectOneBotServer;
import static cn.ChengZhiYa.MHDFBot.command.CommandRegister.registerCommands;
import static cn.ChengZhiYa.MHDFBot.listener.ListenerRegister.registerListeners;
import static cn.ChengZhiYa.MHDFBot.util.ConfigUtil.saveDefaultConfig;
import static cn.ChengZhiYa.MHDFBot.util.PluginUtil.loadPlugins;

public class MHDFBot {
    @Getter
    private static final PluginInfo botInfo = new PluginInfo("MHDF-Bot", "1.0.0", List.of("ChengZhiYa"), "cn.ChengZhiYa.MHDFBot");
    @Getter
    private static ConsoleReader consoleReader;

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        saveDefaultConfig();

        cn.ChengZhiYa.MHDFBot.api.MHDFBot.getScheduler().runTaskAsynchronously(WebSocket::startWebSocketServer);
        connectOneBotServer();

        new HeartBeat().runTaskAsynchronouslyTimer(0L,1L);

        registerCommands();
        registerListeners();
        loadPlugins();

        consoleReader = new ConsoleReader();
        consoleReader.setExpandEvents(false);
        consoleReader.addCompleter(new ConsoleCommandCompleter());

        String line;
        while ((line = consoleReader.readLine()) != null) {
            CommandUtil.executeCommand(line);
        }
    }
}
