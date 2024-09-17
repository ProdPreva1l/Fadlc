package info.preva1l.fadlc.models;

import org.bukkit.World;

import java.util.UUID;

public interface IClaimChunk {
    UUID getUniqueId();

    int getChunkX();

    int getChunkZ();

    String getWorldName();

    World getWorld();

    ChunkStatus getStatus();

    long getClaimedSince();
}
