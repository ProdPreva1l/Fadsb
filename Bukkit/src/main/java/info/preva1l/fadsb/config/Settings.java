package info.preva1l.fadsb.config;

import com.google.common.collect.Lists;
import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import info.preva1l.fadsb.database.DatabaseType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@SuppressWarnings("FieldMayBeFinal")
@Getter
@Configuration
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Settings {

    @Comment("Database connection configuration.")
    private DatabaseSettings database = new DatabaseSettings();

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabaseSettings {
        private DatabaseType type = DatabaseType.SQLITE;
    }

    @Comment("Multi-Server Configuration")
    private MultiServer multiServer = new MultiServer();

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MultiServer {
        @Comment("You must NOT be using SQLITE for multiple servers.")
        private boolean enabled = false;

        @Comment("Settings for if you're using REDIS as your message broker")
        private RedisSettings redis = new RedisSettings();

        @Getter
        @Configuration
        @NoArgsConstructor
        public static class RedisSettings {
            private String host = "127.0.0.1";
            private int port = 6379;
            @Comment("Password for your Redis server. Leave blank if you're not using a password.")
            private String password = "";
            private boolean useSsl = false;

            @Comment({"Settings for if you're using Redis Sentinels.",
                    "If you're not sure what this is, please ignore this section."})
            private SentinelSettings sentinel = new SentinelSettings();

            @Getter
            @Configuration
            @NoArgsConstructor
            public static class SentinelSettings {
                private String masterName = "";
                @Comment("List of host:port pairs")
                private List<String> nodes = Lists.newArrayList();
                private String password = "";
            }
        }
    }
}
