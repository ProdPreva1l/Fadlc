package info.preva1l.fadlc;

import com.google.gson.Gson;
import info.preva1l.fadlc.api.ImplLandClaimsAPI;
import info.preva1l.fadlc.api.LandClaimsAPI;
import info.preva1l.fadlc.commands.ClaimCommand;
import info.preva1l.fadlc.jobs.ClaimBorderJob;
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

import java.util.List;
import java.util.stream.Stream;

public final class Fadlc extends JavaPlugin {
    @Getter private static Fadlc instance;
    @Getter private BukkitAudiences audiences;
    @Getter private Gson gson;

    private ClaimBorderJob borderJob;

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
        borderJob = new ClaimBorderJob();
        borderJob.start();

        LandClaimsAPI.setInstance(new ImplLandClaimsAPI());
    }

    @Override
    public void onDisable() {
        SaveJobs.forceRunAll();
        SaveJobs.shutdownAll();
        borderJob.shutdown();
    }

    private void populateCaches() {
        Logger.info("Populating Caches...");
        List<IClaimChunk> chunks = PersistenceManager.getInstance().getAll(IClaimChunk.class).join();
        chunks.forEach(chunk -> ClaimManager.getInstance().cacheChunk(chunk));
        Logger.info("Chunk Cache Populated!");

        List<IClaim> claims = PersistenceManager.getInstance().getAll(IClaim.class).join();
        claims.forEach(claim -> ClaimManager.getInstance().updateClaim(claim));
        Logger.info("Claim Cache Populated!");
    }

    public static Fadlc i() {
        return instance;
    }
}
