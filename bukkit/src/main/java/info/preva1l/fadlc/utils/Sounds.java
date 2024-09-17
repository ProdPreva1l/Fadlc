package info.preva1l.fadlc.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

@UtilityClass
public class Sounds {

    public void success(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1, 1.2F);
    }

    public void fail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.MASTER, 1, 0.5F);
    }
}
