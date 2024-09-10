package info.preva1l.fadfc;

import info.preva1l.fadfc.api.ImplLandClaimsAPI;
import info.preva1l.fadfc.api.LandClaimsAPI;
import info.preva1l.fadfc.jobs.SaveJobs;
import info.preva1l.fadfc.managers.ClaimManager;
import info.preva1l.fadfc.managers.CommandManager;
import info.preva1l.fadfc.managers.PersistenceManager;
import info.preva1l.fadfc.managers.UserManager;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Fadlc extends JavaPlugin {
    private static Fadlc instance;
    private BukkitAudiences audiences;

    @Override
    public void onEnable() {
        audiences = BukkitAudiences.create(this);
        instance = this;

        // Init the managers
        PersistenceManager.getInstance();
        ClaimManager.getInstance();
        UserManager.getInstance();
        CommandManager.getInstance();

        // Init Jobs
        SaveJobs.startAll();

        LandClaimsAPI.setInstance(new ImplLandClaimsAPI());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Fadlc i() {
        return instance;
    }
}
