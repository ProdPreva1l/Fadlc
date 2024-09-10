package info.preva1l.fadfc.models;

import info.preva1l.fadfc.managers.ClaimManager;
import info.preva1l.fadfc.models.claim.IClaim;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Optional;

@Getter
public class ClaimChunk implements IClaimChunk {
    private final int chunkX;
    private final int chunkZ;
    private final String worldName;

    public ClaimChunk(int chunkX, int chunkZ, String world) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.worldName = world;
    }

    public static IClaimChunk fromBukkit(Chunk chunk) {
        return new ClaimChunk(chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
    }

    @Override
    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    @Override
    public ChunkStatus getStatus() {
        Optional<IClaim> claim = ClaimManager.getInstance().getClaimAt(this);
        if (claim.isPresent()) {
            return ChunkStatus.ALREADY_CLAIMED;
        }

        // todo: check if world is restricted
        if (false) {
            return ChunkStatus.WORLD_DISABLED;
        }

        // todo: worldguard hook
        if (false) {
            return ChunkStatus.BLOCKED_WORLD_GUARD;
        }

        // todo: advanced server zones hook
        if (false) {
            return ChunkStatus.BLOCKED_ZONE_BORDER;
        }

        return ChunkStatus.CLAIMABLE;
    }

    @Override
    public long getClaimedSince() {
        return 10000000000L;
    }
}
