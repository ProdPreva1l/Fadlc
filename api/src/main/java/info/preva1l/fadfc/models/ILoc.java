package info.preva1l.fadfc.models;

import info.preva1l.fadfc.models.user.User;

public interface ILoc extends ILocRef {
    String getServer();

    String getWorld();

    IClaimChunk getChunk();

    void teleport(User user);
}
