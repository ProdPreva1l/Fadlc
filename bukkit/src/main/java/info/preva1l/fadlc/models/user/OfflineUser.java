package info.preva1l.fadlc.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OfflineUser implements User {
    private final UUID uniqueId;
    private final String name;
}
