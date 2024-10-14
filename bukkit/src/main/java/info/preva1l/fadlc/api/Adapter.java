package info.preva1l.fadlc.api;

import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.*;
import info.preva1l.fadlc.models.user.OfflineUser;
import info.preva1l.fadlc.models.user.OnlineUser;
import info.preva1l.fadlc.models.user.User;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Adapter implements IAdapter {
    private static Adapter instance;

    public static Adapter getInstance() {
        if (instance == null) {
            instance = new Adapter();
        }
        return instance;
    }

    @Override
    public OnlineUser player(Player player) {
        return UserManager.getInstance().getUser(player.getUniqueId()).orElseThrow();
    }

    @Override
    public User offlinePlayer(OfflinePlayer offlinePlayer) {
        return new OfflineUser(offlinePlayer.getUniqueId(), offlinePlayer.getName());
    }

    @Override
    public IClaimChunk chunk(Chunk chunk) {
        return ClaimManager.getInstance().getChunkAt(chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
    }

    @Override
    public ILoc location(Location location, String server) {
        return Loc.fromBukkit(location, server);
    }

    @Override
    public ILoc location(Location location) {
        return Loc.fromBukkit(location);
    }

    @Override
    public ILocRef vector(Location location) {
        return new LocRef(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public ILocRef vector(Vector location) {
        return new LocRef(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
