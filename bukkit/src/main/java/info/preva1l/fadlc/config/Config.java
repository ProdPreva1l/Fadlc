package info.preva1l.fadlc.config;

import de.exlll.configlib.*;
import info.preva1l.fadlc.Fadlc;
import info.preva1l.fadlc.persistence.DatabaseType;
import info.preva1l.fadlc.utils.Logger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Getter
@Configuration
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("FieldMayBeFinal")
public class Config {
    private static Config instance;

    private static final String CONFIG_HEADER = """
            ##########################################
            #                  Fadlc                 #
            #      Finally a Decent Land Claim       #
            ##########################################
            """;

    private static final YamlConfigurationProperties PROPERTIES = YamlConfigurationProperties.newBuilder()
            .charset(StandardCharsets.UTF_8)
            .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
            .header(CONFIG_HEADER).build();

    private Jobs jobs = new Jobs();

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Jobs {
        @Comment("How big the thread pool is for jobs. Increase if jobs are taking a long time to complete.")
        private int poolSize = 3;

        @Comment("How often claim data should save, in minutes.")
        private int claimSaveInterval = 30;

        @Comment("How often user data should save, in minutes.")
        private int usersSaveInterval = 10;
    }

    private Storage storage = new Storage();

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Storage {
        @Comment("Allowed: SQLITE, MYSQL, MARIADB, MONGO")
        private DatabaseType type = DatabaseType.SQLITE;

        private String host = "localhost";
        private int port = 3306;
        private String database = "fadlc";
        private String username = "root";
        private String password = "myFancyPassword";
        private boolean useSsl = false;
    }

    public static void reload() {
        instance = YamlConfigurations.load(new File(Fadlc.i().getDataFolder(), "config.yml").toPath(), Config.class, PROPERTIES);
        Logger.info("Configuration automatically reloaded from disk.");
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = YamlConfigurations.update(new File(Fadlc.i().getDataFolder(), "config.yml").toPath(), Config.class, PROPERTIES);
            AutoReload.watch(Fadlc.i().getDataFolder().toPath(), "config.yml", Config::reload);
        }

        return instance;
    }
}