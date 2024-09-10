package info.preva1l.fadfc.managers;

import info.preva1l.fadfc.Fadlc;
import info.preva1l.fadfc.commands.lib.BasicCommand;
import info.preva1l.fadfc.commands.lib.BasicSubCommand;
import info.preva1l.fadfc.config.Lang;
import info.preva1l.fadfc.models.user.CommandUser;
import info.preva1l.fadfc.models.user.ConsoleUser;
import info.preva1l.fadfc.utils.CommandMapUtil;
import info.preva1l.fadfc.utils.Logger;
import info.preva1l.fadfc.utils.TaskManager;
import info.preva1l.fadfc.utils.Text;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public final class CommandManager {
    private static CommandManager instance;
    private final List<BasicCommand> loadedCommands = new ArrayList<>();

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    /**
     * Register a new command.
     *
     * @param basicCommand the command.
     */
    public void registerCommand(BasicCommand basicCommand) {
        CommandMapUtil.getCommandMap().register("", new CommandExecutor(basicCommand));
        loadedCommands.add(basicCommand);
        Logger.info(String.format("Registered Command /%s", basicCommand.getName()));
    }

    /**
     * Remove the first element of the args array.
     *
     * @param array args
     * @return args - 1st element
     */
    protected String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[]{};
        }

        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);

        return newArray;
    }

    private class CommandExecutor extends BukkitCommand {
        private final BasicCommand basicCommand;

        public CommandExecutor(BasicCommand basicCommand) {
            super(basicCommand.getName());
            this.setAliases(Arrays.asList(basicCommand.getAliases()));
            if (!basicCommand.getPermission().isEmpty()) {
                this.setPermission(basicCommand.getPermission());
            }
            this.basicCommand = basicCommand;
        }

        public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
            if (this.basicCommand.isInGameOnly() && sender instanceof ConsoleCommandSender) {
                sender.sendMessage(Text.legacyMessage(Lang.getInstance().getCommand().getNoPermission()));
                return false;
            }
            if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
                sender.sendMessage(Text.legacyMessage(Lang.getInstance().getCommand().getNoPermission()));
                return false;
            }


            CommandUser commandUser = sender instanceof Player ? UserManager.getInstance().getUser(sender.getName()).get() : new ConsoleUser(Fadlc.i().getAudiences().console());

            if (this.basicCommand.isAsync()) {
                TaskManager.runAsync(Fadlc.i(), () -> basicCommand.execute(commandUser, args));
            } else {
                TaskManager.runSync(Fadlc.i(), () -> basicCommand.execute(commandUser, args));
            }
            return false;
        }

        @NotNull
        public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
            CommandUser commandUser = sender instanceof Player ? UserManager.getInstance().getUser(sender.getName()).get() : new ConsoleUser(Fadlc.i().getAudiences().console());

            // Primary argument
            if (args.length <= 1) {
                List<String> completors = basicCommand.tabComplete(commandUser, args);

                if (completors.isEmpty() && !basicCommand.getSubCommands().isEmpty()) {
                    List<String> ret = new ArrayList<>();
                    for (BasicSubCommand subCommand : basicCommand.getSubCommands()) {
                        if (!subCommand.getPermission().isEmpty() && !commandUser.hasPermission(subCommand.getPermission())) {
                            continue;
                        }
                        ret.add(subCommand.getName());
                        Collections.addAll(ret, subCommand.getAliases());
                    }
                    if (args.length == 0) {
                        completors.addAll(ret);
                    } else {
                        StringUtil.copyPartialMatches(args[0], ret, completors);
                    }
                    return completors;
                }

                return completors;
            }

            // Sub command tab completer
            List<String> completors = new ArrayList<>();

            List<String> ret = new ArrayList<>();
            for (BasicSubCommand subCommand : basicCommand.getSubCommands()) {
                if (!subCommand.getName().equals(args[0]) && !Arrays.stream(subCommand.getAliases()).toList().contains(args[0])) {
                    continue;
                }
                if (!subCommand.getPermission().isEmpty() && !commandUser.hasPermission(subCommand.getPermission())) {
                    continue;
                }
                ret.addAll(subCommand.tabComplete(commandUser, removeFirstElement(args)));
            }
            StringUtil.copyPartialMatches(args[args.length - 1], ret, completors);
            return completors;
        }
    }
}
