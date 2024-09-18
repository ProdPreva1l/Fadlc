package info.preva1l.fadlc.models;

import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.models.claim.IClaim;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class ClaimChunk implements IClaimChunk {
    private final UUID uniqueId;
    private final int chunkX;
    private final int chunkZ;
    private final String worldName;
    private final String server;
    private long claimedSince; // -1 if not claimed
    private int profileId; // -1 if not claimed

    public ClaimChunk(int chunkX, int chunkZ, String world, String server, UUID uniqueId, long claimedSince, int profileId) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.worldName = world;
        this.server = server;
        this.uniqueId = uniqueId;
        this.claimedSince = claimedSince;
        this.profileId = profileId;
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
}
