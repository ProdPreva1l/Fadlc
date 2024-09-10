package info.preva1l.fadfc.managers;

import info.preva1l.fadfc.models.IClaimChunk;
import info.preva1l.fadfc.models.ILoc;
import info.preva1l.fadfc.models.claim.IClaim;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ClaimManager implements IClaimManager {
    private static ClaimManager instance;
    private final Map<UUID, IClaim> claimCache = new ConcurrentHashMap<>();

    public static ClaimManager getInstance() {
        if (instance == null) {
            instance = new ClaimManager();
        }
        return instance;
    }

    public void updateClaim(IClaim claim) {
        claimCache.put(claim.getOwner().getUniqueId(), claim);
    }

    @Override
    public Optional<IClaim> getClaimAt(IClaimChunk claimChunk) {
        return claimCache.values().stream().filter(claim -> claim.getClaimedChunks().containsKey(claimChunk)).findFirst();
    }

    @Override
    public Optional<IClaim> getClaimAt(ILoc loc) {
        return claimCache.values().stream().filter(claim -> claim.getClaimedChunks().containsKey(loc.getChunk())).findFirst();
    }

    @Override
    public IClaim getClaimByOwner(UUID uniqueId) {
        return claimCache.get(uniqueId);
    }

    @Override
    public List<IClaim> getAllClaims() {
        return new ArrayList<>(claimCache.values());
    }
}
