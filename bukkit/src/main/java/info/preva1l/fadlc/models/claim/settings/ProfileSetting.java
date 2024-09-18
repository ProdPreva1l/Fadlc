package info.preva1l.fadlc.models.claim.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ProfileSetting implements IProfileSetting {
    PLACE_BLOCKS(
            "Allow Block Placing",
            List.of("Whether or not to allow", "player to place blocks.")
    ),
    BREAK_BLOCKS(
            "Allow Block Breaking",
            List.of("Whether or not to allow", "player to break blocks.")
    ),
    ;

    private final String name;
    private final List<String> description;
}
