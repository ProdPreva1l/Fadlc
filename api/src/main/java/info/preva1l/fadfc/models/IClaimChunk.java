package info.preva1l.fadfc.models;

import org.bukkit.World;

public interface IClaimChunk {
    int getChunkX();

    int getChunkZ();

    String getWorldName();

    World getWorld();

    ChunkStatus getStatus();

    long getClaimedSince();
}
