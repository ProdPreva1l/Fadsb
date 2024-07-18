package info.preva1l.fadsb.multiserver;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import info.preva1l.fadsb.Fadsb;
import info.preva1l.fadsb.utils.TaskManager;
import info.preva1l.fadsb.utils.TimeUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

@Getter
public final class HeartBeat implements RedisConnection {
    private final Fadsb plugin;
    private final JedisPool jedisPool;
    private static Map<String, String> servers = new ConcurrentHashMap<>();

    public HeartBeat() {
        this.plugin = Fadsb.getInstance();

        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(1);
        config.setMaxTotal(3);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        this.jedisPool = new JedisPool(config, "127.0.0.1", 6379, 0, "redis password");
    }

    @Override
    public void connect() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.ping();
        } catch (Exception e) {
            Fadsb.getConsole().log(Level.SEVERE, "Failed to ping Redis server! (%s)".formatted(e.getMessage()), e);
        }

        startGet();
        startPost();
    }

    @Override
    public void destroy() {
        getJedisPool().destroy();
    }

    private void startGet() {
        TaskManager.Async.runTask(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                servers = jedis.hgetAll("skyblock-heartbeat");
                servers.forEach((serverName, serverData) -> {
                    if (isOnline(serverName)) {
                        jedis.hdel("skyblock-heartbeat", serverName);
                        servers.remove(serverName);
                    }
                });
            } catch (Exception e) {
                Fadsb.getConsole().log(Level.SEVERE, "Could not get server data from Redis! (%s)".formatted(e.getMessage()), e);
            }
        }, 20L);
    }

    private void startPost() {
        long startTime = System.currentTimeMillis();

        TaskManager.Async.runTask(() -> {
            String currentServer = "config thing";
            double tps = Math.min(plugin.getServer().getTPS()[0], 20.0);
            double mspt = Math.round(plugin.getServer().getAverageTickTime() * 100.0) / 100.0;
            long lastHeartBeat = System.currentTimeMillis();
            int onlinePlayers = Bukkit.getOnlinePlayers().size();

            JsonObject serverData = new JsonObject();
            serverData.addProperty("server", currentServer);
            serverData.addProperty("onlinePlayers", onlinePlayers);
            serverData.addProperty("tps", tps);
            serverData.addProperty("mspt", mspt);
            serverData.addProperty("startTime", startTime);
            serverData.addProperty("lastHeartBeat", lastHeartBeat);

            String serverDataJson = serverData.toString();

            try (Jedis jedis = jedisPool.getResource()) {
                jedis.hset("skyblock-heartbeat", currentServer, serverDataJson);
            } catch (Exception e) {
                Fadsb.getConsole().log(Level.SEVERE, "Could not post server data to Redis! (%s)".formatted(e.getMessage()), e);
            }
        }, 20L);
    }

    public static JsonObject getServerObject(String server) {
        try {
            String jsonString = servers.get(server);
            if (jsonString != null) {
                return (JsonObject) JsonParser.parseString(jsonString);
            }
            return null;
        } catch (JsonSyntaxException e) {
            Fadsb.getConsole().log(Level.SEVERE, "Error while parsing JSON! (%s)".formatted(e.getMessage()), e);
        }
        return null;
    }

    public static int getPlayerCount(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            return Integer.parseInt(serverObj.get("onlinePlayers").toString());
        }
        return 0;
    }

    public static double getTPS(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            return Double.parseDouble(serverObj.get("tps").toString());
        }
        return 00.0;
    }

    public static double getMSPT(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            return Double.parseDouble(serverObj.get("mspt").toString());
        }
        return 00.0;
    }

    public static boolean isOnline(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            return getLastHeartbeat(server) >= System.currentTimeMillis() - 4000;
        }
        return false;
    }

    public static long[] getOnlineTime(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            long milliOnline = Long.parseLong(serverObj.get("lastHeartBeat").toString()) - Long.parseLong(serverObj.get("startTime").toString());
            return TimeUtil.splitTime(milliOnline);
        }
        return new long[]{0, 0, 0, 0};
    }

    public static String getFormattedOnlineTime(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            long milliOnline = Long.parseLong(serverObj.get("lastHeartBeat").toString()) - Long.parseLong(serverObj.get("startTime").toString());
            return TimeUtil.formatTimeSince(milliOnline);
        }
        return "0s";
    }

    public static long getLastHeartbeat(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            return Long.parseLong(serverObj.get("lastHeartBeat").toString());
        }
        return 0;
    }

    public static long getStartTime(String server) {
        JsonObject serverObj = getServerObject(server);
        if (serverObj != null) {
            return Long.parseLong(serverObj.get("startTime").toString());
        }
        return 0;
    }
}
