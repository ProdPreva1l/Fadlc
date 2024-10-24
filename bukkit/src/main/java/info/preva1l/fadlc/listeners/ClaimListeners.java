package info.preva1l.fadlc.listeners;

import info.preva1l.fadlc.api.FadlcAPI;
import info.preva1l.fadlc.api.events.ClaimEnterEvent;
import info.preva1l.fadlc.api.events.ClaimLeaveEvent;
import info.preva1l.fadlc.config.Lang;
import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.ILoc;
import info.preva1l.fadlc.models.Loc;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.settings.GroupSetting;
import info.preva1l.fadlc.models.user.OnlineUser;
import info.preva1l.fadlc.registry.GroupSettingsRegistry;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

@AllArgsConstructor
public class ClaimListeners implements Listener {
    private final ClaimManager claimManager;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isActionAllowed(OnlineUser user, ILoc location, GroupSetting setting) {
        return FadlcAPI.getInstance().isActionAllowed(user, location, setting);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        OnlineUser user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId()).orElseThrow();
        if (isActionAllowed(user, Loc.fromBukkit(event.getBlock().getLocation()), GroupSettingsRegistry.PLACE_BLOCKS.get())) {
            return;
        }
        event.setCancelled(true);

        Lang.sendMessage(event.getPlayer(), Lang.getInstance().getGroupSettings().getPlaceBlocks().getMessage()
                .replace("%player%", user.getName()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        OnlineUser user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId()).orElseThrow();
        if (isActionAllowed(user, Loc.fromBukkit(event.getBlock().getLocation()), GroupSettingsRegistry.BREAK_BLOCKS.get())) {
            return;
        }
        event.setCancelled(true);

        Lang.sendMessage(event.getPlayer(), Lang.getInstance().getGroupSettings().getBreakBlocks().getMessage()
                .replace("%player%", user.getName()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to == null) return;
        if (to.getChunk().equals(from.getChunk())) return;
        IClaimChunk fromChunk = claimManager.getChunkAt(from.getChunk().getX(), from.getChunk().getZ(), from.getWorld().getName());
        IClaimChunk toChunk = claimManager.getChunkAt(to.getChunk().getX(), to.getChunk().getZ(), to.getWorld().getName());
        IClaim fromClaim = claimManager.getClaimAt(fromChunk).orElse(null);
        IClaim toClaim = claimManager.getClaimAt(toChunk).orElse(null);
        OnlineUser user = UserManager.getInstance().getUser(e.getPlayer().getUniqueId()).orElseThrow();

        if (fromClaim != null) {
            if (toClaim != null && fromClaim.getOwner().equals(toClaim.getOwner())) {
                return;
            }

            ClaimLeaveEvent leaveEvent = new ClaimLeaveEvent(e.getPlayer(), fromClaim, fromChunk);
            Bukkit.getPluginManager().callEvent(leaveEvent);

            Lang.sendMessage(e.getPlayer(), Lang.getInstance().getClaimMessages().getLeave()
                    .replace("%player%", fromClaim.getOwner().getName()));
        }

        if (toClaim != null) {
            if (!isActionAllowed(user, Loc.fromBukkit(e.getTo()), GroupSettingsRegistry.ENTER.get())) {
                e.setCancelled(true);
                return;
            }

            if (fromClaim != null && toClaim.getOwner().equals(fromClaim.getOwner())) {
                return;
            }

            ClaimEnterEvent enterEvent = new ClaimEnterEvent(e.getPlayer(), toClaim, toChunk);
            Bukkit.getPluginManager().callEvent(enterEvent);
            if (enterEvent.isCancelled()) {
                e.setCancelled(true);
                return;
            }

            Lang.sendMessage(e.getPlayer(), Lang.getInstance().getClaimMessages().getEnter()
                    .replace("%player%", toClaim.getOwner().getName()));
        }
    }
}
