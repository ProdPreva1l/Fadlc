package info.preva1l.fadlc.commands;

import info.preva1l.fadlc.commands.lib.BasicCommand;
import info.preva1l.fadlc.commands.lib.Command;
import info.preva1l.fadlc.menus.ClaimMenu;
import info.preva1l.fadlc.models.user.CommandUser;

@Command(
        name = "claim",
        aliases = {"c", "claims"},
        permission = "fadlc.claim"
)
public class ClaimCommand extends BasicCommand {
    @Override
    public void execute(CommandUser sender, String[] args) {
        // sub command stuff

        new ClaimMenu(sender.asPlayer()).open(sender.asPlayer());
    }
}
