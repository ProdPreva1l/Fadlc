package info.preva1l.fadlc.managers;

import info.preva1l.fadlc.models.user.OnlineUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserManager {
    List<OnlineUser> getAllUsers();

    Optional<OnlineUser> getUser(String name);

    Optional<OnlineUser> getUser(UUID uniqueId);
}
