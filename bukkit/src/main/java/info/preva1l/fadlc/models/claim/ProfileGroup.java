package info.preva1l.fadlc.models.claim;

import info.preva1l.fadlc.models.claim.settings.IProfileSetting;
import info.preva1l.fadlc.models.claim.settings.ProfileSetting;
import info.preva1l.fadlc.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProfileGroup implements IProfileGroup {
    private final UUID uniqueId;
    private String name;
    private final List<User> users;
    private final Map<IProfileSetting, Boolean> settings;

    public static ProfileGroup baseVisitor() {
        Map<IProfileSetting, Boolean> settings = new HashMap<>();
        settings.put(ProfileSetting.PLACE_BLOCKS, false);
        settings.put(ProfileSetting.BREAK_BLOCKS, false);
        return new ProfileGroup(UUID.randomUUID(), "Visitor", List.of(), settings);
    }

    public static ProfileGroup baseMember() {
        Map<IProfileSetting, Boolean> settings = new HashMap<>();
        settings.put(ProfileSetting.PLACE_BLOCKS, false);
        settings.put(ProfileSetting.BREAK_BLOCKS, false);
        return new ProfileGroup(UUID.randomUUID(), "Member", List.of(), settings);
    }

    public static ProfileGroup baseTrusted() {
        Map<IProfileSetting, Boolean> settings = new HashMap<>();
        settings.put(ProfileSetting.PLACE_BLOCKS, true);
        settings.put(ProfileSetting.BREAK_BLOCKS, true);
        return new ProfileGroup(UUID.randomUUID(), "Trusted", List.of(), settings);
    }

    public static ProfileGroup baseAdmin() {
        Map<IProfileSetting, Boolean> settings = new HashMap<>();
        settings.put(ProfileSetting.PLACE_BLOCKS, true);
        settings.put(ProfileSetting.BREAK_BLOCKS, true);
        return new ProfileGroup(UUID.randomUUID(), "Admin", List.of(), settings);
    }
}
