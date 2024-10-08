package info.preva1l.fadlc.api;

import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.ILoc;
import info.preva1l.fadlc.models.ILocRef;
import info.preva1l.fadlc.models.user.OnlineUser;
import info.preva1l.fadlc.models.user.User;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public interface IAdapter {
    OnlineUser player(Player player);

    User offlinePlayer(OfflinePlayer offlinePlayer);

    IClaimChunk chunk(Chunk chunk);


    ILoc location(Location location, String server);

    ILoc location(Location location);

    ILocRef vector(Location location);

    ILocRef vector(Vector location);
}
