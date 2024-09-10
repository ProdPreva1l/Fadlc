package info.preva1l.fadfc.managers;

import info.preva1l.fadfc.models.IClaimChunk;
import info.preva1l.fadfc.models.ILoc;
import info.preva1l.fadfc.models.claim.IClaim;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClaimManager {
    Optional<IClaim> getClaimAt(IClaimChunk claimChunk);
    Optional<IClaim> getClaimAt(ILoc loc);
    IClaim getClaimByOwner(UUID uniqueId);

    List<IClaim> getAllClaims();
}
