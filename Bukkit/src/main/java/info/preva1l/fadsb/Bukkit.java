package info.preva1l.fadsb;

import info.preva1l.fadsb.utils.Metrics;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Bukkit extends JavaPlugin {

    @Getter private static Logger console;

    private static final int METRICS_ID = 22688;
    private Metrics metrics;
    @Override
    public void onEnable() {
        console = getLogger();

    }

    @Override
    public void onDisable() {
        if (metrics != null) metrics.shutdown();
    }


    private void setupMetrics() {
        getConsole().info("Starting Metrics...");

        metrics = new Metrics(this, METRICS_ID);

        getConsole().info("Metrics Logging Started!");
    }
}
