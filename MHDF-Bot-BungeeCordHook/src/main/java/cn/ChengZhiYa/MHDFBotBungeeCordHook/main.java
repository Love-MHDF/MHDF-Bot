package cn.ChengZhiYa.MHDFBotBungeeCordHook;

import cn.ChengZhiYa.MHDFBotBungeeCordHook.util.ConfigUtil;
import net.md_5.bungee.api.plugin.Plugin;

import static cn.ChengZhiYa.MHDFBotBungeeCordHook.client.WebSocket.connectWebSocketServer;
import static cn.ChengZhiYa.MHDFBotBungeeCordHook.util.ConfigUtil.reloadConfig;
import static cn.ChengZhiYa.MHDFBotBungeeCordHook.util.FileUtil.createDir;

public final class main extends Plugin {
    public static main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        createDir(getDataFolder());
        ConfigUtil.saveResource("./", "config.yml", "config.yml", false);

        reloadConfig();

        connectWebSocketServer();

        getLogger().info("&f============&6梦之机器人-子服连接器&f============");
        getLogger().info("&e插件启动完成!");
        getLogger().info("&f============&6梦之机器人-子服连接器&f============");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;

        getLogger().info("&f============&6梦之机器人-子服连接器&f============");
        getLogger().info("&e插件已卸载!");
        getLogger().info("&f============&6梦之机器人-子服连接器&f============");
    }
}
