package info.preva1l.fadlc.models.user;

import info.preva1l.fadlc.persistence.DatabaseObject;

import java.util.UUID;

public interface User extends DatabaseObject {
    String getName();
    UUID getUniqueId();

    OnlineUser getOnlineUser();
}
