package info.preva1l.fadsb;

import info.preva1l.fadsb.commands.AboutCommand;
import info.preva1l.fadsb.config.ConfigProvider;
import info.preva1l.fadsb.config.ServerSettings;
import info.preva1l.fadsb.config.Settings;
import info.preva1l.fadsb.database.StorageDatabase;
import info.preva1l.fadsb.database.SQLiteDatabase;
import info.preva1l.fadsb.island.IslandManager;
import info.preva1l.fadsb.multiserver.ServerManager;
import info.preva1l.fadsb.utils.Metrics;
import info.preva1l.fadsb.utils.Text;
import info.preva1l.fadsb.utils.commands.CommandManager;
import lombok.Getter;
import lombok.Setter;
import net.william278.desertwell.util.UpdateChecker;
import net.william278.desertwell.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;
import java.util.stream.Stream;

@Getter
public final class Fadsb extends JavaPlugin implements ConfigProvider {
    @Getter private static Fadsb instance;
    private static final int SPIGOT_ID = 116157;
    private Version pluginVersion;

    @Getter private static Logger console;

    private StorageDatabase database;

    private CommandManager commandManager;
    private IslandManager islandManager;
    private ServerManager serverManager;

    @Setter private Settings settings;
    @Setter private ServerSettings serverName;

    private static final int METRICS_ID = 22688;
    private Metrics metrics;


    @Override
    public void onEnable() {
        instance = this;
        pluginVersion = Version.fromString(getDescription().getVersion());
        console = getLogger();

        database = switch (getSettings().getDatabase().getType()) {
            case SQLITE -> new SQLiteDatabase();
            case MYSQL, MARIADB -> null;
            case POSTGRESQL -> null;
            case MONGODB -> null;
        };

        commandManager = new CommandManager(this);
        islandManager = new IslandManager();
        serverManager = new ServerManager();

        registerCommands();

        setupMetrics();
        checkForUpdates();
    }

    @Override
    public void onDisable() {
        if (metrics != null) metrics.shutdown();
    }

    private void registerCommands() {
        getConsole().info("Loading commands...");
        Stream.of(
                new AboutCommand(this)
        ).forEach(commandManager::registerCommand);
        getConsole().info("Commands loaded!");
    }

    private void checkForUpdates() {
        final UpdateChecker checker = UpdateChecker.builder()
                .currentVersion(pluginVersion)
                .endpoint(UpdateChecker.Endpoint.SPIGOT)
                .resource(Integer.toString(SPIGOT_ID))
                .build();
        checker.check().thenAccept(checked -> {
            if (checked.isUpToDate()) {
                return;
            }
            Bukkit.getConsoleSender().sendMessage(Text.modernMessage("&#9667e6&lFadsb &fis &#D63C3COUTDATED&f! " +
                    "&7Current: &#D63C3C%s &7Latest: &#18D53A%s"));
        });
    }

    private void setupMetrics() {
        getConsole().info("Starting Metrics...");

        metrics = new Metrics(this, METRICS_ID);

        metrics.addCustomChart(new Metrics.SimplePie("database_type",
                () -> getSettings().getDatabase().getType().getFriendlyName()));

        getConsole().info("Metrics Logging Started!");
    }

    @NotNull
    @Override
    public String getServerName() {
        return serverName != null ? serverName.getServerName() : "server";
    }
}
