package info.preva1l.fadfc.api;

import info.preva1l.fadfc.managers.ClaimManager;
import info.preva1l.fadfc.managers.IClaimManager;

public class ImplLandClaimsAPI extends LandClaimsAPI {
    @Override
    public IClaimManager getClaimManager() {
        return ClaimManager.getInstance();
    }
}
