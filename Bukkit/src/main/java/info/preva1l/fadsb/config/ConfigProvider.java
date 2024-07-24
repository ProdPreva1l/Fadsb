package info.preva1l.fadsb.config;

import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import info.preva1l.fadsb.Fadsb;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public interface ConfigProvider {
    @NotNull
    YamlConfigurationProperties.Builder<?> YAML_CONFIGURATION_PROPERTIES = YamlConfigurationProperties.newBuilder()
            .charset(StandardCharsets.UTF_8)
            .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE);

    @NotNull
    Settings getSettings();

    void setSettings(@NotNull Settings settings);

    default void loadSettings() {
        setSettings(YamlConfigurations.update(
                getConfigDirectory().resolve("config.yml"),
                Settings.class,
                YAML_CONFIGURATION_PROPERTIES.header(ServerSettings.CONFIG_HEADER).build()
        ));
    }


    @NotNull
    String getServerName();

    void setServerName(@NotNull ServerSettings serverSettings);

    default void loadServer() {
        setServerName(YamlConfigurations.update(
                getConfigDirectory().resolve("server.yml"),
                ServerSettings.class,
                YAML_CONFIGURATION_PROPERTIES.header(ServerSettings.CONFIG_HEADER).build()
        ));
    }

    default Path getConfigDirectory() {
        return Fadsb.getInstance().getDataFolder().toPath();
    }
}
