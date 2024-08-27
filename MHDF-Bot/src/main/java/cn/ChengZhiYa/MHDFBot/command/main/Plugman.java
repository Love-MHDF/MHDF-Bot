package cn.ChengZhiYa.MHDFBot.command.main;

import cn.ChengZhiYa.MHDFBot.api.manager.TabExecutor;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import cn.ChengZhiYa.MHDFBot.util.PluginUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;
import static cn.ChengZhiYa.MHDFBot.util.PluginUtil.reloadPlugin;
import static cn.ChengZhiYa.MHDFBot.util.PluginUtil.unloadPlugin;

public final class Plugman implements TabExecutor {
    @Override
    public void onCommand(String command, String[] args) {
        if (args.length == 2) {
            switch (args[0]) {
                case "reload" -> {
                    if (PluginUtil.getPlugin(args[1]) != null) {
                        reloadPlugin(args[1]);
                    } else {
                        colorLog("&c不存在{}这个插件!", args[1]);
                    }
                    return;
                }
                case "unload" -> {
                    if (PluginUtil.getPlugin(args[1]) != null) {
                        unloadPlugin(args[1]);
                    } else {
                        colorLog("&c不存在{}这个插件!", args[1]);
                    }
                    return;
                }
                case "load" -> {
                    File pluginFile = new File(PluginUtil.getPluginFolder().toFile(), args[1]);
                    if (pluginFile.exists() && pluginFile.isFile()) {
                        PluginInfo info = PluginUtil.getPluginInfo(pluginFile.toPath());
                        if (PluginUtil.getPlugin(Objects.requireNonNull(info).getName()) == null) {
                            PluginUtil.loadPlugin(pluginFile.toPath());
                        } else {
                            colorLog("&c{}这个插件已经加载了!", args[1]);
                        }
                    } else {
                        colorLog("&c不存在{}这个插件!", args[1]);
                    }
                    return;
                }
            }
        }
        colorLog("""
                &f=========&e插件管理&f=========
                #88CDFFplugman reload <插件名字> &f| #FF888C重载指定插件
                #88CDFFplugman unload <插件名字> &f| #FF888C卸载指定插件
                #88CDFFplugman load <插件文件名字> &f| #FF888C加载指定插件
                &f=========&e插件管理&f=========""");
    }

    @Override
    public List<String> onTabComplete(String command, String[] args) {
        if (args.length == 1) {
            return List.of("reload", "unload", "load");
        }
        if (args.length == 2) {
            switch (args[0]) {
                case "reload", "unload" -> {
                    return PluginUtil.getPluginHashMap().keySet().stream().map(PluginInfo::getName).toList();
                }
                case "load" -> {
                    try {
                        return PluginUtil.getNotLoadPlugins().stream().map(Path::toString).map(s -> s.replaceAll("\\\\", "/").replaceAll("\\./plugins/", "")).toList();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return new ArrayList<>();
    }
}
