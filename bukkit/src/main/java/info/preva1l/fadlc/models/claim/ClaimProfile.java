package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IProfileFlag;
import info.preva1l.fadlc.models.claim.settings.ProfileFlag;
import info.preva1l.fadlc.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Particle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
public class ClaimProfile implements IClaimProfile {
    private final UUID uniqueId;
    private String name;
    private final int id;
    private final Map<Integer, IProfileGroup> groups;
    private final Map<IProfileFlag, Boolean> flags;
    private Particle border;

    private final Map<User, IProfileGroup> groupCache = new ConcurrentHashMap<>();

    public static ClaimProfile baseProfile(String player, int id) {
        Map<IProfileFlag, Boolean> flags = new HashMap<>();
        for (ProfileFlag flag : ProfileFlag.values()) {
            flags.put(flag, flag.isEnabledByDefault());
        }
        Map<Integer, IProfileGroup> groups = Map.of(
                1, ProfileGroup.rankOne(),
                2, ProfileGroup.rankTwo(),
                3, ProfileGroup.rankThree(),
                4, ProfileGroup.rankFour(),
                5, ProfileGroup.rankFive()
        );
        return new ClaimProfile(UUID.randomUUID(), "&7%s's Claim".formatted(player), id, groups, flags, Particle.VILLAGER_HAPPY);
    }

    /**
     * Uses a dirty cache technique, its kinda goofy but it works ong.
     * <p>
     *     If the user somehow ends up in more than 1 group it takes them out of the lowest priority.
     * </p>
     *
     * @param user the user to get
     * @return the group they are in
     */
    @Override
    public IProfileGroup getPlayerGroup(User user) {
        IProfileGroup cachedGroup = groupCache.get(user);
        if (cachedGroup != null) {
            return cachedGroup;
        }
        List<IProfileGroup> usersGroups = new ArrayList<>(groups.values().stream()
                .filter(g -> g.getUsers().contains(user))
                .sorted(Comparator.comparing(IProfileGroup::getId)).toList());

        IProfileGroup group = usersGroups.get(usersGroups.size() - 1);

        if (usersGroups.size() > 1) {
            while (usersGroups.size() > 1) {
                IProfileGroup g = usersGroups.remove(0);
                groups.get(g.getId()).getUsers().remove(user);
            }
        }

        if (group == null) {
            return groups.get(1);
        }
        groupCache.put(user, group);
        return group;
    }
}
