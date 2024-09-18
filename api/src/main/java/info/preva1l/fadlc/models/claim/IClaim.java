package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.user.User;
import info.preva1l.fadlc.persistence.DatabaseObject;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface IClaim extends DatabaseObject {
    User getOwner();

    Map<Integer, IClaimProfile> getProfiles();

    Optional<IClaimProfile> getProfile(IClaimChunk chunk);

    Map<UUID, Integer> getClaimedChunks();

    void claimChunk(IClaimChunk chunk);

    void setProfile(IClaimChunk chunk, int profile);
}
