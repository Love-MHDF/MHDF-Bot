package cn.ChengZhiYa.MHDFBot_PluginExample;

import cn.ChengZhiYa.MHDFBot.api.MHDFBotPlugin;
import cn.ChengZhiYa.MHDFBot_PluginExample.command.HelloWorld;

public final class main extends MHDFBotPlugin {
    public static main instance;

    @Override
    public void onEnable() {
        instance = this;

        colorLog("&a第一个插件!");
        registerCommand("输出参数","helloworld <参数>",new HelloWorld(),"helloworld");
    }

    @Override
    public void onDisable() {
        instance = null;

    }
}