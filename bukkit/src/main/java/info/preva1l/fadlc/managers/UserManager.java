package info.preva1l.fadlc.managers;

import info.preva1l.fadlc.models.user.BukkitUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserManager {
    private static UserManager instance;

    private final Map<String, BukkitUser> usersCacheName = new HashMap<>();
    private final Map<UUID, BukkitUser> usersCacheUuid = new HashMap<>();

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void cacheUser(BukkitUser user) {
        usersCacheUuid.put(user.getUniqueId(), user);
        usersCacheName.put(user.getName(), user);
    }

    public void invalidate(UUID uuid) {
        usersCacheUuid.remove(uuid);
    }

    public void invalidate(String username) {
        usersCacheName.remove(username);
    }

    public List<BukkitUser> getAllUsers() {
        return new ArrayList<>(usersCacheName.values());
    }

    public Optional<BukkitUser> getUser(String name) {
        return Optional.ofNullable(usersCacheName.get(name));
    }

    public Optional<BukkitUser> getUser(UUID uniqueId) {
        return Optional.ofNullable(usersCacheUuid.get(uniqueId));
    }
}
