package info.preva1l.fadlc.models;

import info.preva1l.fadlc.models.user.User;

public interface ILoc extends ILocRef {
    String getServer();

    String getWorld();

    IClaimChunk getChunk();

    void teleport(User user);
}
