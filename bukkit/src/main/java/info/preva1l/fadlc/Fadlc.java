package info.preva1l.fadlc;

import com.google.gson.Gson;
import info.preva1l.fadlc.api.ImplLandClaimsAPI;
import info.preva1l.fadlc.api.LandClaimsAPI;
import info.preva1l.fadlc.commands.ClaimCommand;
import info.preva1l.fadlc.jobs.SaveJobs;
import info.preva1l.fadlc.listeners.ClaimListeners;
import info.preva1l.fadlc.listeners.PlayerListeners;
import info.preva1l.fadlc.managers.*;
import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.utils.Logger;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

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
        FastInvManager.register(this);
        PersistenceManager.getInstance();
        ClaimManager.getInstance();
        UserManager.getInstance();
        CommandManager.getInstance();

        populateCaches();

        Stream.of(
                new ClaimCommand()
        ).forEach(CommandManager.getInstance()::registerCommand);

        Stream.of(
                new ClaimListeners(ClaimManager.getInstance()),
                new PlayerListeners(UserManager.getInstance())
        ).forEach(e -> getServer().getPluginManager().registerEvents(e, this));

        // Init Jobs
        SaveJobs.startAll();

        LandClaimsAPI.setInstance(new ImplLandClaimsAPI());
    }

    @Override
    public void onDisable() {
        SaveJobs.forceRunAll();
        SaveJobs.shutdownAll();
    }

    private void populateCaches() {
        Logger.info("Populating Caches...");
        PersistenceManager.getInstance().getAll(IClaimChunk.class).thenAccept(chunks -> {
            chunks.forEach(chunk -> ClaimManager.getInstance().cacheChunk(chunk));
            Logger.info("Chunk Cache Populated!");

            PersistenceManager.getInstance().getAll(IClaim.class).thenAccept(claims -> {
                claims.forEach(claim -> ClaimManager.getInstance().updateClaim(claim));
                Logger.info("Claim Cache Populated!");
            });
        });

    }

    public static Fadlc i() {
        return instance;
    }
}
