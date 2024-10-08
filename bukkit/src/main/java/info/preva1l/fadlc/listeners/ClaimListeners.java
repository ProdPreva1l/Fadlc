package info.preva1l.fadlc.listeners;

import info.preva1l.fadlc.api.events.ClaimEnterEvent;
import info.preva1l.fadlc.api.events.ClaimLeaveEvent;
import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.ILoc;
import info.preva1l.fadlc.models.Loc;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IProfileGroup;
import info.preva1l.fadlc.models.claim.settings.GroupSetting;
import info.preva1l.fadlc.models.claim.settings.IGroupSetting;
import info.preva1l.fadlc.models.user.OnlineUser;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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
    private boolean isActionAllowed(OnlineUser user, ILoc location, IGroupSetting setting) {
        Optional<IClaim> claimAtLocation = claimManager.getClaimAt(location);
        if (claimAtLocation.isEmpty()) {
            return true;
        }

        IProfileGroup group = claimAtLocation.get().getProfile(location.getChunk()).orElseThrow().getPlayerGroup(user);

        if (group == null) {
            return false;
        }

        return group.getSettings().get(setting);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        OnlineUser user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId()).orElseThrow();
        if (isActionAllowed(user, Loc.fromBukkit(event.getBlock().getLocation()), GroupSetting.PLACE_BLOCKS)) {
            return;
        }
        event.setCancelled(true);
        // todo: send message
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        OnlineUser user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId()).orElseThrow();
        if (isActionAllowed(user, Loc.fromBukkit(event.getBlock().getLocation()), GroupSetting.BREAK_BLOCKS)) {
            return;
        }
        event.setCancelled(true);
        // todo: send message
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to == null) return;
        if (to.getChunk().equals(from.getChunk())) return;
        IClaimChunk fromChunk = claimManager.getChunkAtChunk(from.getChunk().getX(), from.getChunk().getZ(), from.getWorld().getName());
        IClaimChunk toChunk = claimManager.getChunkAtChunk(to.getChunk().getX(), to.getChunk().getZ(), to.getWorld().getName());
        IClaim fromClaim = claimManager.getClaimAt(fromChunk).orElse(null);
        IClaim toClaim = claimManager.getClaimAt(toChunk).orElse(null);

        if (fromClaim != null) {
            ClaimLeaveEvent leaveEvent = new ClaimLeaveEvent(e.getPlayer(), fromClaim, fromChunk);
            Bukkit.getPluginManager().callEvent(leaveEvent);

            // todo: send message
        }

        if (toClaim != null) {
            ClaimEnterEvent enterEvent = new ClaimEnterEvent(e.getPlayer(), toClaim, toChunk);
            Bukkit.getPluginManager().callEvent(enterEvent);
            if (enterEvent.isCancelled()) {
                e.setCancelled(true);
                return;
            }

            // todo: send message
        }
    }
}
