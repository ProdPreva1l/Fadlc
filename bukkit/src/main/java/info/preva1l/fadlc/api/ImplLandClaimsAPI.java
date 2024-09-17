package info.preva1l.fadlc.api;

import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.IClaimManager;

public class ImplLandClaimsAPI extends LandClaimsAPI {
    @Override
    public IClaimManager getClaimManager() {
        return ClaimManager.getInstance();
    }
}
