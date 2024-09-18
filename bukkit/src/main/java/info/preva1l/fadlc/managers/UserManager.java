package info.preva1l.fadlc.managers;

import info.preva1l.fadlc.models.user.OnlineUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserManager {
    private static UserManager instance;

    private final Map<String, OnlineUser> usersCacheName = new ConcurrentHashMap<>();
    private final Map<UUID, OnlineUser> usersCacheUuid = new ConcurrentHashMap<>();

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void cacheUser(OnlineUser user) {
        usersCacheUuid.put(user.getUniqueId(), user);
        usersCacheName.put(user.getName(), user);
    }

    public void invalidate(UUID uuid) {
        usersCacheUuid.remove(uuid);
    }

    public void invalidate(String username) {
        usersCacheName.remove(username);
    }

    public List<OnlineUser> getAllUsers() {
        return new ArrayList<>(usersCacheName.values());
    }

    public Optional<OnlineUser> getUser(String name) {
        return Optional.ofNullable(usersCacheName.get(name));
    }

    public Optional<OnlineUser> getUser(UUID uniqueId) {
        return Optional.ofNullable(usersCacheUuid.get(uniqueId));
    }
}
