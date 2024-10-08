package info.preva1l.fadlc.models.claim.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum GroupSetting implements IGroupSetting {
    PLACE_BLOCKS(
            "Allow Block Placing",
            List.of("Whether or not to allow", "player to place blocks.")
    ),
    BREAK_BLOCKS(
            "Allow Block Breaking",
            List.of("Whether or not to allow", "player to break blocks.")
    ),
    ENTER(
            "Allow Entry to the Claim",
            List.of("Whether or not to allow", "player to enter the claim.")
    );

    private final String name;
    private final List<String> description;
}
