package info.preva1l.fadlc.models.claim.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum EProfileFlag {
    EXPLOSION_DAMAGE(
            "Allow Explosion Damage",
            List.of("Whether or not to allow", "TNT, End Crystals & TNT Minecarts", "to break blocks."),
            false
    ),
    ENTITY_GRIEFING(
            "Allow Entity Griefing",
            List.of("Whether or not to allow", "creepers, endermen &", "to break blocks."),
            false
    ),
    PVP(
            "PvP",
            List.of("Whether or not to allow", "players to attack eachother."),
            false
    ),
    PASSIVE_MOB_SPAWNING(
            "",
            List.of(),
            true
    ),
    HOSTILE_MOB_SPAWNING(
            "",
            List.of(),
            false
    ),

    ;

    private final String name;
    private final List<String> description;
    private final boolean enabledByDefault;
}
