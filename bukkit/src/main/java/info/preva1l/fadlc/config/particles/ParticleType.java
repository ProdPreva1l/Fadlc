package info.preva1l.fadlc.config.particles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Particle;

import java.util.List;

@Getter
@AllArgsConstructor
public class ParticleType {
    private final String id;
    private final String displayName;
    private final List<String> lockedDesc;
    private final List<String> unlockedDesc;
    private final Particle bukkit;
    private final Color color;
    private final float size;
    private final int amount;
}
