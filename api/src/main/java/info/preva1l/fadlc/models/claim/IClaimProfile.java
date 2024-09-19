package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IProfileFlag;
import info.preva1l.fadlc.persistence.DatabaseObject;
import org.bukkit.Particle;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IClaimProfile extends DatabaseObject {
    UUID getUniqueId();

    int getId();

    String getName();

    List<IProfileGroup> getGroups();

    Map<IProfileFlag, Boolean> getFlags();

    Particle getBorder();
}
