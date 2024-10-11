package info.preva1l.fadlc.config.particles;

import info.preva1l.fadlc.Fadlc;
import info.preva1l.fadlc.utils.config.BasicConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class Particles {
    private Map<String, ParticleType> particles = new ConcurrentHashMap<>();
    private final BasicConfig particlesFile = new BasicConfig(Fadlc.i(), "particles.yml");

    public void update() {
        particles = getParticlesFromFile();
    }

    public ParticleType getSound(String name) {
        ParticleType type = particles.get(name);
        if (type == null) {
            type = particles.values().stream().findFirst().orElse(new ParticleType("default",
                    "Fix Your Config", List.of("issue"), List.of("errm"),
                    Particle.VILLAGER_HAPPY, Color.RED, 1, 1));
        }
        return type;
    }

    public Map<String, ParticleType> getParticlesFromFile() {
        Map<String, ParticleType> list = new HashMap<>();
        for (String key : particlesFile.getConfiguration().getKeys(false)) {
            Particle bukkit = Particle.valueOf(particlesFile.getString(key + ".value"));
            List<String> unlockedDesc = particlesFile.getStringList(key + ".description.unlocked");
            List<String> lockedDesc = particlesFile.getStringList(key + ".description.locked");
            String display = particlesFile.getString(key + ".display");
            Color color = Color.fromRGB(
                    particlesFile.getInt(key + ".color.red"),
                    particlesFile.getInt(key + ".color.green"),
                    particlesFile.getInt(key + ".color.blue")
            );
            int size = particlesFile.getInt(key + ".size");
            int amount = particlesFile.getInt(key + ".amount");

            list.put(key, new ParticleType(key, display, lockedDesc, unlockedDesc, bukkit, color, size, amount));
        }
        return list;
    }

    public void showParticle(Player player, String border, Location location) {
        ParticleType particleType = getSound(border);
        player.spawnParticle(particleType.getBukkit(), location, particleType.getAmount(), 0, 0, 0,
                new Particle.DustOptions(particleType.getColor(), particleType.getSize()));
    }
}
