package info.preva1l.fadlc.commands.subcommands;

import info.preva1l.fadlc.commands.lib.BasicSubCommand;
import info.preva1l.fadlc.commands.lib.Command;
import info.preva1l.fadlc.models.user.CommandUser;

@Command(
        name = "profile",
        permission = "fadlc.profile"
)
public class ProfileSubCommand extends BasicSubCommand {
    @Override
    public void execute(CommandUser sender, String[] args) {

    }
}
