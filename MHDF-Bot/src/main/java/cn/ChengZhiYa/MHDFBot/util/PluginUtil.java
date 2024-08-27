package cn.ChengZhiYa.MHDFBot.util;

import cn.ChengZhiYa.MHDFBot.MHDFBot;
import cn.ChengZhiYa.MHDFBot.api.MHDFBotPlugin;
import cn.ChengZhiYa.MHDFBot.api.enums.PluginStatus;
import cn.ChengZhiYa.MHDFBot.api.manager.Listener;
import cn.ChengZhiYa.MHDFBot.entity.YamlConfiguration;
import cn.ChengZhiYa.MHDFBot.entity.plugin.Command;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

public final class PluginUtil {
    @Getter
    private static final Path pluginFolder = Paths.get("./plugins");
    @Getter
    static HashMap<PluginInfo, PluginStatus> pluginHashMap = new HashMap<>();
    @Getter
    private static List<MHDFBotPlugin> botPluginList = new ArrayList<>();

    public static void loadPlugins() throws IOException {
        try (Stream<Path> paths = Files.list(pluginFolder)) {
            paths.filter(path -> path.toString().endsWith(".jar")).forEach(PluginUtil::loadPlugin);
        }
    }

    public static List<Path> getNotLoadPlugins() throws IOException {
        try (Stream<Path> paths = Files.list(pluginFolder)) {
            return paths.filter(path -> path.toString().endsWith(".jar"))
                    .filter(path -> !getPluginHashMap().containsKey(getPluginInfo(path)))
                    .toList();
        }
    }

    public static MHDFBotPlugin getPlugin(String pluginName) {
        for (MHDFBotPlugin plugin : getBotPluginList()) {
            if (plugin.getPluginInfo().getName().equals(pluginName)) {
                return plugin;
            }
        }
        return null;
    }

    public static void reloadPlugin(String pluginName) {
        MHDFBotPlugin plugin = getPlugin(pluginName);
        if (plugin != null) {
            File pluginFile = new File(plugin.getPluginJar().getName());
            unloadPlugin(pluginName);
            loadPlugin(pluginFile.toPath());
        }
    }

    public static void unloadPlugin(String pluginName) {
        MHDFBotPlugin plugin = getPlugin(pluginName);
        if (plugin != null) {
            getPluginHashMap().remove(plugin.getPluginInfo());
            getBotPluginList().remove(plugin);
            {
                HashMap<Listener, PluginInfo> listenerTempHashMap = (HashMap<Listener, PluginInfo>) ListenerUtil.getListenerHashMap().clone();
                for (Listener listener : listenerTempHashMap.keySet()) {
                    if (ListenerUtil.getListenerHashMap().get(listener).getName().equals(pluginName)) {
                        ListenerUtil.getListenerHashMap().remove(listener);
                    }
                }
            }
            {
                HashMap<String, Command> commandTempHashMap = (HashMap<String, Command>) CommandUtil.getCommandHashMap().clone();
                for (String commandString : commandTempHashMap.keySet()) {
                    if (CommandUtil.getCommandHashMap().get(commandString).getPluginInfo() != null) {
                        if (CommandUtil.getCommandHashMap().get(commandString).getPluginInfo().getName().equals(pluginName)) {
                            CommandUtil.getCommandHashMap().remove(commandString);
                        }
                    }
                }

            }
            plugin.onDisable();
            try {
                plugin.getPluginJar().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static PluginInfo getPluginInfo(Path pluginPath) {
        try (JarFile jarFile = new JarFile(pluginPath.toFile())) {
            JarEntry jarEntry = jarFile.getJarEntry("plugin.yml");
            if (jarEntry != null) {
                YamlConfiguration pluginInfoData = YamlConfiguration.loadConfiguration(jarFile.getInputStream(jarEntry));
                return new PluginInfo(
                        pluginInfoData.getString("name"),
                        pluginInfoData.getString("version"),
                        pluginInfoData.getStringList("author"),
                        pluginInfoData.getString("main")
                );
            } else {
                colorLog("&c{}不是一个有效的插件!", pluginPath.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadPlugin(Path pluginPath) {
        try (JarFile jarFile = new JarFile(pluginPath.toFile())) {
            JarEntry jarEntry = jarFile.getJarEntry("plugin.yml");
            if (jarEntry != null) {
                YamlConfiguration pluginInfoData = YamlConfiguration.loadConfiguration(jarFile.getInputStream(jarEntry));
                PluginInfo pluginInfo = new PluginInfo(
                        pluginInfoData.getString("name"),
                        pluginInfoData.getString("version"),
                        pluginInfoData.getStringList("author"),
                        pluginInfoData.getString("main")
                );
                colorLog("加载插件{}({})中", pluginInfo.getName(), pluginInfo.getVersion());
                try {
                    URL[] urls = {pluginPath.toUri().toURL()};
                    URLClassLoader classLoader = new URLClassLoader(urls, MHDFBot.class.getClassLoader());
                    Thread.currentThread().setContextClassLoader(classLoader);
                    Class<?> clazz = classLoader.loadClass(pluginInfo.getMain());
                    MHDFBotPlugin mhdfBotPlugin = (MHDFBotPlugin) clazz.getDeclaredConstructor().newInstance();
                    mhdfBotPlugin.setPluginInfo(pluginInfo);
                    mhdfBotPlugin.setPluginJar(jarFile);
                    mhdfBotPlugin.onEnable();
                    botPluginList.add(mhdfBotPlugin);
                    getPluginHashMap().put(pluginInfo, PluginStatus.Load_Done);
                } catch (Exception e) {
                    getPluginHashMap().put(pluginInfo, PluginStatus.Load_Error);
                    e.printStackTrace();
                }
            } else {
                colorLog("&c{}不是一个有效的插件!", pluginPath.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
