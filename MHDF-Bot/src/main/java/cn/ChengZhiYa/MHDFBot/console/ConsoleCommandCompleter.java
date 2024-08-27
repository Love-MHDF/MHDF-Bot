package cn.ChengZhiYa.MHDFBot.console;

import cn.ChengZhiYa.MHDFBot.entity.plugin.Command;
import cn.ChengZhiYa.MHDFBot.util.CommandUtil;
import jline.console.completer.Completer;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public final class ConsoleCommandCompleter implements Completer {
    @Override
    public int complete(String input, int i, List<CharSequence> list) {
        int lastSpace = input.lastIndexOf(" ");
        if (lastSpace == -1) {
            list.addAll(CommandUtil.getCommandHashMap().keySet().stream()
                    .filter((s) -> s.toLowerCase(Locale.ROOT).startsWith(input.toLowerCase(Locale.ROOT)))
                    .toList()
            );
        } else {
            String[] parts = input.split(" ");
            String[] args = new String[Math.toIntExact(input.chars().filter(c -> c == ' ').count())];
            System.arraycopy(parts, 1, args, 0, parts.length - 1);
            Command command = CommandUtil.getCommandHashMap().get(parts[0]);
            if (command != null && command.getTabComplete() != null) {
                list.addAll(command.getTabComplete().onTabComplete(parts[0], args).stream()
                        .filter((s) -> s.toLowerCase(Locale.ROOT).startsWith(input.substring(lastSpace + 1).toLowerCase(Locale.ROOT)))
                        .toList()
                );
            }
        }
        return (lastSpace == -1) ? i - input.length() : i - (input.length() - lastSpace - 1);
    }
}
