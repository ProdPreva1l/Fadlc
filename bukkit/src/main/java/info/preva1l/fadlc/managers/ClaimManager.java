package info.preva1l.fadlc.managers;

import info.preva1l.fadlc.models.ClaimChunk;
import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.ILoc;
import info.preva1l.fadlc.models.claim.Claim;
import info.preva1l.fadlc.models.claim.ClaimProfile;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IClaimProfile;
import info.preva1l.fadlc.models.user.OfflineUser;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    public IClaim getClaimByOwner(UUID uniqueId) {
        IClaim claim = claimCache.get(uniqueId);
        if (claim == null) {
            String name = Bukkit.getOfflinePlayer(uniqueId).getName();
            Map<Integer, IClaimProfile> baseProfiles = new HashMap<>();
            baseProfiles.put(1, ClaimProfile.baseProfile(name, 1));
            baseProfiles.put(2, ClaimProfile.baseProfile(name, 2));
            baseProfiles.put(3, ClaimProfile.baseProfile(name, 3));
            baseProfiles.put(4, ClaimProfile.baseProfile(name, 4));
            baseProfiles.put(5, ClaimProfile.baseProfile(name, 5));
            claim = new Claim(new OfflineUser(uniqueId, name), new HashMap<>(), baseProfiles);
            updateClaim(claim);
        }
        return claim;
    }

    @Override
    public List<IClaim> getAllClaims() {
        return new ArrayList<>(claimCache.values());
    }

    public void cacheChunk(IClaimChunk claimChunk) {
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
        IClaimChunk newChunk = new ClaimChunk(x, z, world, "", uuid, -1, -1);
        cacheChunk(newChunk);
        return newChunk;
    }
}
