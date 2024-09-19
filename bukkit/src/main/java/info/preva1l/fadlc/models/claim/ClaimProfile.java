package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IProfileFlag;
import info.preva1l.fadlc.models.claim.settings.ProfileFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Particle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClaimProfile implements IClaimProfile {
    private final UUID uniqueId;
    private String name;
    private final int id;
    private final List<IProfileGroup> groups;
    private final Map<IProfileFlag, Boolean> flags;
    private Particle border;

    public static ClaimProfile baseProfile(String player, int id) {
        Map<IProfileFlag, Boolean> flags = new HashMap<>();
        for (ProfileFlag flag : ProfileFlag.values()) {
            flags.put(flag, flag.isEnabledByDefault());
        }
        List<IProfileGroup> groups = List.of(
                ProfileGroup.baseVisitor(),
                ProfileGroup.baseMember(),
                ProfileGroup.baseTrusted(),
                ProfileGroup.baseAdmin()
        );
        return new ClaimProfile(UUID.randomUUID(), "&7%s's Claim".formatted(player), id, groups, flags, Particle.VILLAGER_HAPPY);
    }
}
