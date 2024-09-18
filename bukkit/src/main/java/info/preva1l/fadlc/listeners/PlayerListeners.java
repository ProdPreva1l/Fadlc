package info.preva1l.fadlc.listeners;

import info.preva1l.fadlc.managers.PersistenceManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.user.BukkitUser;
import info.preva1l.fadlc.models.user.OnlineUser;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@AllArgsConstructor
public class PlayerListeners implements Listener {
    private final UserManager userManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PersistenceManager.getInstance().get(OnlineUser.class, e.getPlayer().getUniqueId()).thenAccept(user -> {
            OnlineUser onlineUser;

            if (user.isEmpty()) {
                onlineUser = new BukkitUser(e.getPlayer().getName(), e.getPlayer().getUniqueId(), e.getPlayer(), 0); // todo: config first chunks
                PersistenceManager.getInstance().save(OnlineUser.class, onlineUser);
            } else {
                onlineUser = user.get();
            }

            userManager.cacheUser(onlineUser);
        });
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        userManager.invalidate(e.getPlayer().getUniqueId());
        userManager.invalidate(e.getPlayer().getName());
    }
}
