package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IProfileSetting;
import info.preva1l.fadlc.models.user.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IProfileGroup {
    UUID getUniqueId();

    String getName();

    List<User> getUsers();

    Map<IProfileSetting, Boolean> getSettings();
}
