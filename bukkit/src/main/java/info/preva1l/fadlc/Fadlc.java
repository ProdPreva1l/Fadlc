package info.preva1l.fadlc;

import com.google.gson.Gson;
import info.preva1l.fadlc.api.ImplLandClaimsAPI;
import info.preva1l.fadlc.api.LandClaimsAPI;
import info.preva1l.fadlc.jobs.SaveJobs;
import info.preva1l.fadlc.managers.ClaimManager;
import info.preva1l.fadlc.managers.CommandManager;
import info.preva1l.fadlc.managers.PersistenceManager;
import info.preva1l.fadlc.managers.UserManager;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Fadlc extends JavaPlugin {
    private static Fadlc instance;
    private BukkitAudiences audiences;
    private Gson gson;

    @Override
    public void onEnable() {
        audiences = BukkitAudiences.create(this);
        gson = new Gson();
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
