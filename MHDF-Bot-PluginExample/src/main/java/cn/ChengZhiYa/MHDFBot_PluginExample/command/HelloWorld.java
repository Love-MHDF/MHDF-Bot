package cn.ChengZhiYa.MHDFBot_PluginExample.command;

import cn.ChengZhiYa.MHDFBot.api.manager.CommandExecutor;
import cn.ChengZhiYa.MHDFBot_PluginExample.main;

import java.util.Arrays;

public final class HelloWorld implements CommandExecutor {
    @Override
    public void onCommand(String command, String[] args) {
        main.instance.colorLog(Arrays.toString(args));
    }
}
