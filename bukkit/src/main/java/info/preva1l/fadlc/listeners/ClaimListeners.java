package info.preva1l.fadlc.listeners;

import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.ILoc;
import info.preva1l.fadlc.models.Loc;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IProfileGroup;
import info.preva1l.fadlc.models.claim.settings.IProfileSetting;
import info.preva1l.fadlc.models.claim.settings.ProfileSetting;
import info.preva1l.fadlc.models.user.BukkitUser;
import info.preva1l.fadlc.models.user.User;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Optional;

@AllArgsConstructor
public class ClaimListeners implements Listener {
    private final ClaimManager claimManager;

    /**
     * Check if the player can perform an action at the provided location.
     *
     * @param user the player to check.
     * @param location the location of the action.
     * @return true if the action is allowed false if not
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isActionAllowed(User user, ILoc location, IProfileSetting setting) {
        Optional<IClaim> claimAtLocation = claimManager.getClaimAt(location);
        if (claimAtLocation.isEmpty()) {
            return true;
        }

        IProfileGroup group = user.getTrustedClaims().get(claimAtLocation.get());
        if (group == null) {
            return true;
        }

        return group.getSettings().get(setting);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        BukkitUser user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId()).orElseThrow();
        if (!isActionAllowed(user, Loc.fromBukkit(event.getBlock().getLocation()), ProfileSetting.PLACE_BLOCKS)) {
            return;
        }
        event.setCancelled(true);
        // todo: send message
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        BukkitUser user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId()).orElseThrow();
        if (!isActionAllowed(user, Loc.fromBukkit(event.getBlock().getLocation()), ProfileSetting.BREAK_BLOCKS)) {
            return;
        }
        event.setCancelled(true);
        // todo: send message
    }
}
