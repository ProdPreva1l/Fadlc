package info.preva1l.fadlc.managers;

import info.preva1l.fadlc.config.ServerSettings;
import info.preva1l.fadlc.models.ClaimChunk;
import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.ILoc;
import info.preva1l.fadlc.models.claim.Claim;
import info.preva1l.fadlc.models.claim.ClaimProfile;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IClaimProfile;
import info.preva1l.fadlc.models.user.OnlineUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClaimManager implements IClaimManager {
    private static ClaimManager instance;
    private final Map<UUID, IClaim> claimCache = new ConcurrentHashMap<>();
    private final Map<UUID, IClaimChunk> chunkCache = new ConcurrentHashMap<>();

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
        return claimCache.values().stream().filter(claim -> claim.getClaimedChunks().containsKey(claimChunk.getUniqueId())).findFirst();
    }

    @Override
    public Optional<IClaim> getClaimAt(ILoc loc) {
        return claimCache.values().stream().filter(claim -> claim.getClaimedChunks().containsKey(loc.getChunk().getUniqueId())).findFirst();
    }

    @Override
    public IClaim getClaimByOwner(OnlineUser user) {
        IClaim claim = claimCache.get(user.getUniqueId());
        if (claim == null) {
            String name = user.getName();
            Map<Integer, IClaimProfile> baseProfiles = new HashMap<>();
            baseProfiles.put(1, ClaimProfile.baseProfile(user, 1));
            claim = new Claim(user, new HashMap<>(), baseProfiles);
            updateClaim(claim);
        }
        return claim;
    }

    @Override
    public List<IClaim> getAllClaims() {
        return new ArrayList<>(claimCache.values());
    }

    public List<IClaimChunk> getClaimedChunks() {
        return new ArrayList<>(chunkCache.values());
    }

    public void cacheChunk(IClaimChunk claimChunk) {
        if (!claimChunk.getServer().equals(ServerSettings.getInstance().getName())) return;
        chunkCache.put(claimChunk.getUniqueId(), claimChunk);
    }

    @Override
    public IClaimChunk getChunk(UUID uniqueId) {
        return chunkCache.get(uniqueId);
    }

    @Override
    public IClaimChunk getChunkAt(ILoc loc) {
        Optional<IClaimChunk> optChunk = chunkCache.values().stream().filter(chunk -> chunk.getChunkX() == loc.getX() >> 4
                && chunk.getChunkZ() == loc.getZ() >> 4 && loc.getWorld().equals(chunk.getWorldName())).findFirst();

        if (optChunk.isPresent()) {
            return optChunk.get();
        }

        UUID uuid = UUID.randomUUID();
        IClaimChunk newChunk = new ClaimChunk(loc.getX() >> 4, loc.getZ() >> 4, loc.getWorld(), "", uuid, -1, -1);
        cacheChunk(newChunk);
        return newChunk;
    }


    public IClaimChunk getChunkAtChunk(int x, int z, String world) {
        Optional<IClaimChunk> optChunk = chunkCache.values().stream().filter(chunk -> chunk.getChunkX() == x
                && chunk.getChunkZ() == z && world.equals(chunk.getWorldName())).findFirst();

        if (optChunk.isPresent()) {
            return optChunk.get();
        }

        UUID uuid = UUID.randomUUID();
        IClaimChunk newChunk = new ClaimChunk(x, z, world, ServerSettings.getInstance().getName(), uuid, -1, -1);
        cacheChunk(newChunk);
        return newChunk;
    }
}
