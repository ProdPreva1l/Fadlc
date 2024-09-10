package info.preva1l.fadfc.models.claim;

import info.preva1l.fadfc.models.claim.settings.IProfileFlag;

import java.util.List;
import java.util.Map;

public interface IClaimProfile {
    String getName();

    List<IProfileGroup> getGroups();

    Map<IProfileFlag, Boolean> getFlags();
}
