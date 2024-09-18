package info.preva1l.fadlc.models;

import info.preva1l.fadlc.persistence.DatabaseObject;
import org.bukkit.World;

import java.util.UUID;

public interface IClaimChunk extends DatabaseObject {
    UUID getUniqueId();

    int getChunkX();

    int getChunkZ();

    String getWorldName();

    String getServer();

    World getWorld();

    ChunkStatus getStatus();

    int getProfileId();

    void setProfileId(int profileId);

    long getClaimedSince();

    void setClaimedSince(long claimedSince);
}
