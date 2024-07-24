package info.preva1l.fadsb.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Configuration
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("FieldMayBeFinal")
public class ServerSettings {
    static final String CONFIG_HEADER = """
            #########################################
            #          Server configuration         #
            # (This file must be unique per-server) #
            #########################################
            """;


    @Comment({"Make sure this is unique and matches the server name in bungeecord/velocity",
            "(Only modify if using in cross-server mode)"})
    private String serverName = "skyblock-main-01";
}
