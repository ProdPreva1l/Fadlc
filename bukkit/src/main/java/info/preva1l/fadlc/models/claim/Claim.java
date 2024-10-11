package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Claim implements IClaim {
    private final User owner;
    private final Map<UUID, Integer> claimedChunks;
    private final Map<Integer, IClaimProfile> profiles;

    @Override
    public Optional<IClaimProfile> getProfile(IClaimChunk chunk) {
        return Optional.ofNullable(profiles.get(claimedChunks.get(chunk.getUniqueId())));
    }

    @Override
    public void claimChunk(@NotNull IClaimChunk claimChunk) {
        claimChunk.setClaimedSince(System.currentTimeMillis());
        claimChunk.setProfileId(1);
        claimedChunks.put(claimChunk.getUniqueId(), 1);
        ClaimManager.getInstance().cacheChunk(claimChunk);
        ClaimManager.getInstance().updateClaim(this);
    }

    @Override
    public void updateProfile(IClaimProfile profile) {
        profiles.put(profile.getId(), profile);
        ClaimManager.getInstance().updateClaim(this);
    }
}
