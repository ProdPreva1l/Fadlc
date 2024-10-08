package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IGroupSetting;
import info.preva1l.fadlc.models.user.User;
import info.preva1l.fadlc.persistence.DatabaseObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IProfileGroup extends DatabaseObject {
    UUID getUniqueId();

    int getId();

    String getName();

    List<User> getUsers();

    Map<? extends IGroupSetting, Boolean> getSettings();
}
