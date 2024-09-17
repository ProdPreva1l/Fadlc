package info.preva1l.fadlc.listeners;

import info.preva1l.fadlc.managers.PersistenceManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.user.BukkitUser;
import info.preva1l.fadlc.models.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PersistenceManager.getInstance().get(User.class, e.getPlayer().getUniqueId()).thenAccept(user -> {
            User offlineUser = user.orElseThrow();
            BukkitUser onlineUser = new BukkitUser(offlineUser.getName(), offlineUser.getUniqueId(), e.getPlayer());
            UserManager.getInstance().cacheUser(onlineUser);
        });
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        UserManager.getInstance().invalidate(e.getPlayer().getUniqueId());
        UserManager.getInstance().invalidate(e.getPlayer().getName());
    }
}
