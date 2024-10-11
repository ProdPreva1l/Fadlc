package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IProfileFlag;
import info.preva1l.fadlc.models.user.User;
import info.preva1l.fadlc.persistence.DatabaseObject;

import java.util.Map;
import java.util.UUID;

public interface IClaimProfile extends DatabaseObject {
    UUID getUniqueId();

    int getId();

    String getName();

    Map<Integer, IProfileGroup> getGroups();

    Map<IProfileFlag, Boolean> getFlags();

    String getBorder();

    IProfileGroup getPlayerGroup(User user);
}
