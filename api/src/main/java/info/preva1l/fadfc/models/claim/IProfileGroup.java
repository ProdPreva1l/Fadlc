package info.preva1l.fadfc.models.claim;

import info.preva1l.fadfc.models.claim.settings.IProfileSetting;
import info.preva1l.fadfc.models.user.User;

import java.util.List;
import java.util.Map;

public interface IProfileGroup {
    String getName();

    List<User> getUsers();

    Map<IProfileSetting, Boolean> getSettings();
}
