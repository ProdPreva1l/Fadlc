package info.preva1l.fadlc.utils.sounds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Sound;

@Getter
@AllArgsConstructor
public class SoundType {
    private final String name;
    private final Sound bukkit;
    private final float volume;
    private final float pitch;
}
