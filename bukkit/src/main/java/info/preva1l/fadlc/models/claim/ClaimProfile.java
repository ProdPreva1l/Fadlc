package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IProfileFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClaimProfile implements IClaimProfile {
    private final UUID uniqueId;
    private final String name;
    private final int id;

    @Override
    public List<IProfileGroup> getGroups() {
        return List.of();
    }

    @Override
    public Map<IProfileFlag, Boolean> getFlags() {
        return Map.of();
    }
}
