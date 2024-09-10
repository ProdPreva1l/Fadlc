package info.preva1l.fadfc.commands;

import info.preva1l.fadfc.commands.lib.BasicCommand;
import info.preva1l.fadfc.commands.lib.Command;
import info.preva1l.fadfc.models.user.CommandUser;

@Command(
        name = "claim",
        aliases = {"c", "claims"},
        permission = "fadlc.claim"
)
public class ClaimCommand extends BasicCommand {
    @Override
    public void execute(CommandUser sender, String[] args) {

    }
}
