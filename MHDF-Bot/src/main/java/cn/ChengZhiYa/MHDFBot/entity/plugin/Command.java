package cn.ChengZhiYa.MHDFBot.entity.plugin;

import cn.ChengZhiYa.MHDFBot.api.manager.CommandExecutor;
import cn.ChengZhiYa.MHDFBot.api.manager.TabComplete;
import cn.ChengZhiYa.MHDFBot.util.CommandUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Command {
    PluginInfo pluginInfo;
    CommandExecutor commandExecutor;
    TabComplete tabComplete;
    String description;
    String usage;
    List<String> aliases = new ArrayList<>();

    public Command setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
        return this;
    }

    public Command setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        return this;
    }

    public Command setTabComplete(TabComplete tabComplete) {
        this.tabComplete = tabComplete;
        return this;
    }

    public Command setDescription(String description) {
        this.description = description;
        return this;
    }

    public Command setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public Command addAlias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    public Command setAliases(String... aliases) {
        this.aliases.addAll(List.of(aliases));
        return this;
    }

    public void register() {
        for (String command : aliases) {
            CommandUtil.getCommandHashMap().put(command, this);
        }
    }
}
