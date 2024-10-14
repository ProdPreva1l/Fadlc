package info.preva1l.fadlc.registry;

import info.preva1l.fadlc.models.claim.settings.GroupSetting;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupSettingsRegistry {
    public static final GroupSetting PLACE_BLOCKS = get("place_blocks");
    public static final GroupSetting BREAK_BLOCKS = get("break_blocks");
    public static final GroupSetting ENTER = get("enter");

    private static Map<String, GroupSetting> settings = new ConcurrentHashMap<>();

    public static void register(String name, GroupSetting value) {
        if (settings == null) {
            settings = new ConcurrentHashMap<>();
        }
        settings.put(name, value);
    }

    public static GroupSetting get(String name) {
        if (settings == null) {
            settings = new ConcurrentHashMap<>();
        }
        return settings.get(name);
    }

    public static Collection<GroupSetting> getAll() {
        if (settings == null) {
            settings = new ConcurrentHashMap<>();
        }
        return settings.values();
    }

    @ApiStatus.Obsolete
    public static GroupSetting[] values() {
        return getAll().toArray(new GroupSetting[0]);
    }
}
