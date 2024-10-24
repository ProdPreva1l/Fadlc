package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.config.Config;
import info.preva1l.fadlc.models.claim.settings.GroupSetting;
import info.preva1l.fadlc.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProfileGroup implements IProfileGroup {
    private final UUID uniqueId;
    private final int id;
    private String name;
    private final List<User> users;
    private final Map<GroupSetting, Boolean> settings;

    public static ProfileGroup rankOne() {
        Config.Groups.First conf = Config.getInstance().getGroupDefaults().getFirst();
        Map<GroupSetting, Boolean> settings = conf.getRealSettings();
        return new ProfileGroup(UUID.randomUUID(), 1, "Default", List.of(), settings);
    }

    public static ProfileGroup rankTwo() {
        Config.Groups.Second conf = Config.getInstance().getGroupDefaults().getSecond();
        Map<GroupSetting, Boolean> settings = conf.getRealSettings();
        return new ProfileGroup(UUID.randomUUID(), 2, "Rank 2", List.of(), settings);
    }

    public static ProfileGroup rankThree() {
        Config.Groups.Third conf = Config.getInstance().getGroupDefaults().getThird();
        Map<GroupSetting, Boolean> settings = conf.getRealSettings();
        return new ProfileGroup(UUID.randomUUID(), 3, "Rank 3", List.of(), settings);
    }

    public static ProfileGroup rankFour() {
        Config.Groups.Fourth conf = Config.getInstance().getGroupDefaults().getFourth();
        Map<GroupSetting, Boolean> settings = conf.getRealSettings();
        return new ProfileGroup(UUID.randomUUID(), 4, "Rank 4", List.of(), settings);
    }

    public static ProfileGroup rankFive() {
        Config.Groups.Fifth conf = Config.getInstance().getGroupDefaults().getFifth();
        Map<GroupSetting, Boolean> settings = conf.getRealSettings();
        return new ProfileGroup(UUID.randomUUID(), 5, "Rank 5", List.of(), settings);
    }
}
