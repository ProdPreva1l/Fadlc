package info.preva1l.fadlc.models.claim;

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
        claimedChunks.put(claimChunk.getUniqueId(), 1);
    }

    @Override
    public void setProfile(IClaimChunk chunk, int profile) {
        claimedChunks.put(chunk.getUniqueId(), profile);
    }
}
