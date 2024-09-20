package info.preva1l.fadlc.utils.sounds;

import info.preva1l.fadlc.Fadlc;
import info.preva1l.fadlc.utils.config.BasicConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class Sounds {
    private Map<String, SoundType> sounds = new ConcurrentHashMap<>();
    private final BasicConfig categoriesFile = new BasicConfig(Fadlc.i(), "sounds.yml");

    public void update() {
        sounds = fillListWithCategories();
    }

    public SoundType getSound(String name) {
        return sounds.get(name);
    }

    public Map<String, SoundType> fillListWithCategories() {
        Map<String, SoundType> list = new HashMap<>();
        for (String key : categoriesFile.getConfiguration().getKeys(false)) {
            Sound bukkit = Sound.valueOf(categoriesFile.getString(key + ".value"));
            float volume = categoriesFile.getFloat(key + ".volume");
            float pitch = categoriesFile.getFloat(key + ".pitch");

            list.put(key, new SoundType(key, bukkit, volume, pitch));
        }
        return list;
    }

    public void playSound(Player player, SoundType soundType) {
        player.playSound(player.getLocation(), soundType.getBukkit(), SoundCategory.MASTER, soundType.getVolume(), soundType.getPitch());
    }
}
