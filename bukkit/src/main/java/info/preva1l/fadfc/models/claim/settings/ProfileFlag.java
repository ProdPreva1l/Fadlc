package info.preva1l.fadfc.models.claim.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ProfileFlag implements IProfileFlag {
    EXPLOSION_DAMAGE(
            "Allow Explosion Damage",
            List.of("Whether or not to allow", "TNT, End Crystals & TNT Minecarts", "to break blocks."),
            false
    ),
    ;

    private final String name;
    private final List<String> description;
    private final boolean enabledByDefault;
}
