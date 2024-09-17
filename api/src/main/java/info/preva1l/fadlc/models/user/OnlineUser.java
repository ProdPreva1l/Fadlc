package info.preva1l.fadlc.models.user;

import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IProfileGroup;

import java.util.Map;

public interface OnlineUser extends User {
    int getAvailableChunks();

    IClaim getClaim();
    Map<IClaim, IProfileGroup> getTrustedClaims();
}
