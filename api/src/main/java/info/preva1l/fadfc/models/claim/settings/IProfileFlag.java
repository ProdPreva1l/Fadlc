package info.preva1l.fadfc.models.claim.settings;

import java.util.List;

public interface IProfileFlag {
    String getName();

    List<String> getDescription();

    boolean isEnabledByDefault();
}
