package info.preva1l.fadfc.models.claim;

import info.preva1l.fadfc.models.claim.settings.IProfileFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ClaimProfile implements IClaimProfile {
    private final String name;

    @Override
    public List<IProfileGroup> getGroups() {
        return List.of();
    }

    @Override
    public Map<IProfileFlag, Boolean> getFlags() {
        return Map.of();
    }
}
