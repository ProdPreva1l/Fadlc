package info.preva1l.fadlc.models;

import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.models.claim.IClaim;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Optional;
import java.util.UUID;

@Getter
public class ClaimChunk implements IClaimChunk {
    private final UUID uniqueId;
    private final int chunkX;
    private final int chunkZ;
    private final String worldName;

    public ClaimChunk(int chunkX, int chunkZ, String world, UUID uniqueId) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.worldName = world;
        this.uniqueId = uniqueId;
    }

    public static IClaimChunk fromBukkit(Chunk chunk) {
        return new ClaimChunk(chunk.getX(), chunk.getZ(), chunk.getWorld().getName(), UUID.randomUUID());
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
