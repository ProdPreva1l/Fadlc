package info.preva1l.fadfc.commands.lib;

import info.preva1l.fadfc.models.user.CommandUser;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicSubCommand {
    private final Command info;

    public BasicSubCommand() {
        this.info = this.getClass().getAnnotation(Command.class);

        if (info == null) {
            throw new RuntimeException("BasicSubCommand must be annotated with @Command");
        }
    }

    public abstract void execute(CommandUser sender, String[] args);

    public List<String> tabComplete(CommandUser sender, String[] args) {
        return new ArrayList<>();
    }

    public String getName() {
        return info.name();
    }

    public String getPermission() {
        return info.permission();
    }

    public String[] getAliases() {
        return info.aliases();
    }

    public boolean isInGameOnly() {
        return info.inGameOnly();
    }

    public boolean isAsync() {
        return info.async();
    }
}