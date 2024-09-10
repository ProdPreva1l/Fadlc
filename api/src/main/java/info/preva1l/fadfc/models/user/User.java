package info.preva1l.fadfc.models.user;

import info.preva1l.fadfc.models.claim.IClaim;
import info.preva1l.fadfc.models.claim.IProfileGroup;
import info.preva1l.fadfc.persistence.DatabaseObject;

import java.util.Map;
import java.util.UUID;

public interface User extends DatabaseObject {
    String getName();
    UUID getUniqueId();

    int getAvailableChunks();

    IClaim getClaim();
    Map<IClaim, IProfileGroup> getTrustedClaims();
}
