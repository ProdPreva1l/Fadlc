package info.preva1l.fadlc.api;

import info.preva1l.fadlc.managers.IClaimManager;
import info.preva1l.fadlc.managers.IUserManager;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public abstract class LandClaimsAPI {
    /**
     * -- GETTER --
     *  Get the instance of the LandClaims API.
     */
    @Getter private static LandClaimsAPI instance;

    /**
     * Get the claim manager to interact with claims.
     * @return the claim manager instance.
     */
    public abstract IClaimManager getClaimManager();

    /**
     * Get the user manager to interact with users.
     * @return the user manager instance.
     */
    public abstract IUserManager getUserManager();

    /**
     * The adapter to adapt bukkit types/values to Fadlc objects.
     * <p>
     *     Example: {@link org.bukkit.Location} -> {@link info.preva1l.fadlc.models.ILoc}
     * </p>
     * @return the adapter instance.
     */
    public abstract IAdapter getAdapter();

    @ApiStatus.Internal
    public static void setInstance(@NotNull LandClaimsAPI newInstance) {
        if (instance != null) {
            throw new IllegalStateException("LandClaimsAPI instance already set!");
        }
        instance = newInstance;
    }
}
