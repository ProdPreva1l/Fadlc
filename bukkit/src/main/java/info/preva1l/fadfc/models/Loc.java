package info.preva1l.fadfc.models;

import info.preva1l.fadfc.models.user.User;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter
public class Loc extends LocRef implements ILoc {
    private final String server;
    private final String world;

    public Loc(String server, String world, int x, int y, int z) {
        super(x, y, z);
        this.server = server;
        this.world = world;
    }

    public static ILoc fromBukkit(Location location) {
        return new Loc("", location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public IClaimChunk getChunk() {
        return new ClaimChunk(getX() >> 4, getZ() >> 4, getWorld());
    }

    @Override
    public void teleport(User user) {
        Player player = Bukkit.getPlayer(user.getUniqueId());
        if (player == null) {
            return;
        }
        World world = Bukkit.getWorld(getWorld());
        player.teleport(new Location(world, getX(), getY(), getZ()));
    }
}