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
import info.preva1l.fadlc.utils.Metrics;
import info.preva1l.fadlc.utils.Text;
import info.preva1l.fadlc.utils.config.BasicConfig;
import info.preva1l.fadlc.utils.sounds.Sounds;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.william278.desertwell.util.UpdateChecker;
import net.william278.desertwell.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Stream;

public final class Fadlc extends JavaPlugin {
    private static final String skibidiToilet = "%%__USERNAME__%% (%%__USER__%%)";
    private static final int POLYMART_ID = 6616;
    private static final int METRICS_ID = 23412;
    private final Version pluginVersion = Version.fromString(getDescription().getVersion());

    @Getter private static Fadlc instance;
    @Getter private BukkitAudiences audiences;
    @Getter private Gson gson;

    private ClaimBorderJob borderJob;

    private Metrics metrics;

    @Override
    public void onEnable() {
        audiences = BukkitAudiences.create(this);
        gson = new Gson();
        instance = this;

        // Init the managers
        Logger.info("Initializing Managers...");
        FastInvManager.register(this);
        PersistenceManager.getInstance();
        ClaimManager.getInstance();
        UserManager.getInstance();
        CommandManager.getInstance();
        Logger.info("Managers initialized!");

        Sounds.update();

        loadMenus();

        populateCaches();

        Logger.info("Registering Commands...");
        Stream.of(
                new ClaimCommand()
        ).forEach(CommandManager.getInstance()::registerCommand);
        Logger.info("Commands Registered!");

        Logger.info("Registering Listeners...");
        Stream.of(
                new ClaimListeners(ClaimManager.getInstance()),
                new PlayerListeners(UserManager.getInstance())
        ).forEach(e -> getServer().getPluginManager().registerEvents(e, this));
        Logger.info("Listeners Registered!");

        // Init Jobs
        Logger.info("Starting Jobs...");
        SaveJobs.startAll();
        borderJob = new ClaimBorderJob();
        borderJob.start();
        Logger.info("Jobs Started!");

        Logger.info("Loading API...");
        LandClaimsAPI.setInstance(new ImplLandClaimsAPI());
        Logger.info("API Loaded!");

        setupMetrics();

        Bukkit.getConsoleSender().sendMessage(Text.legacyMessage("&2&l------------------------------"));
        Bukkit.getConsoleSender().sendMessage(Text.legacyMessage("&a  Finally a Decent Land Claim"));
        Bukkit.getConsoleSender().sendMessage(Text.legacyMessage("&a   has successfully started!"));
        Bukkit.getConsoleSender().sendMessage(Text.legacyMessage("&a  Licenced to: " + skibidiToilet));
        Bukkit.getConsoleSender().sendMessage(Text.legacyMessage("&2&l------------------------------"));

        Bukkit.getScheduler().runTaskLater(this, this::checkForUpdates, 60L);
    }

    @Override
    public void onDisable() {
        SaveJobs.forceRunAll();
        SaveJobs.shutdownAll();
        if (borderJob != null) {
            borderJob.shutdown();
        }

        if (metrics != null) {
            metrics.shutdown();
        }
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

    private void loadMenus() {
        Logger.info("Loading Menus...");
        LayoutManager.getInstance().loadLayout(new BasicConfig(this, "menus/claim.yml"));
        Logger.info("Menus Loaded!");
    }

    private void setupMetrics() {
        Logger.info("Starting Metrics...");

        metrics = new Metrics(this, METRICS_ID);
        metrics.addCustomChart(new Metrics.SingleLineChart("claims_created", () -> ClaimManager.getInstance().getAllClaims().size()));
        metrics.addCustomChart(new Metrics.SingleLineChart("chunks_claimed", () -> ClaimManager.getInstance().getClaimedChunks().size()));

        Logger.info("Metrics Logging Started!");
    }

    private void checkForUpdates() {
        final UpdateChecker checker = UpdateChecker.builder()
                .currentVersion(pluginVersion)
                .endpoint(UpdateChecker.Endpoint.POLYMART)
                .resource(Integer.toString(POLYMART_ID))
                .build();
        checker.check().thenAccept(checked -> {
            if (checked.isUpToDate()) {
                return;
            }
            Bukkit.getConsoleSender().sendMessage(Text.legacyMessage("&7[Fadlc]&f Fadlc is &#D63C3COUTDATED&f! " +
                    "&7Current: &#D63C3C%s &7Latest: &#18D53A%s".formatted(checked.getCurrentVersion(), checked.getLatestVersion())));
        });
    }

    public static Fadlc i() {
        return instance;
    }
}
