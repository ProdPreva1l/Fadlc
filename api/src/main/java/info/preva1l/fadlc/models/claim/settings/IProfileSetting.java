package info.preva1l.fadlc.models.claim.settings;

import java.util.List;

public interface IProfileSetting {
    String getName();

    List<String> getDescription();

    boolean isEnabledByDefault();
}
