package info.preva1l.fadlc.api;

import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.IClaimManager;
import info.preva1l.fadlc.managers.IUserManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.models.ILoc;
import org.bukkit.Location;

public class ImplLandClaimsAPI extends LandClaimsAPI {
    @Override
    public IClaimManager getClaimManager() {
        return ClaimManager.getInstance();
    }

    /**
     * Get the user manager to interact with users.
     *
     * @return the user manager instance.
     */
    @Override
    public IUserManager getUserManager() {
        return UserManager.getInstance();
    }

    /**
     * The adapter to adapt bukkit types/values to Fadlc objects.
     * <p>
     * Example: {@link Location} -> {@link ILoc}
     * </p>
     *
     * @return the adapter instance.
     */
    @Override
    public IAdapter getAdapter() {
        return null;
    }
}
