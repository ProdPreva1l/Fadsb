package info.preva1l.fadsb.multiserver;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.preva1l.fadsb.Fadsb;
import info.preva1l.fadsb.config.Settings;
import lombok.Getter;
import redis.clients.jedis.*;
import redis.clients.jedis.util.Pool;

import java.util.Set;

@Getter
public final class Messenger extends JedisPubSub implements RedisConnection {
    private final Fadsb plugin;
    private final Pool<Jedis> jedisPool;

    public Messenger(Fadsb plugin) {
        this.plugin = plugin;
        final Settings.MultiServer.RedisSettings settings = plugin.getSettings().getMultiServer().getRedis();
        final Set<String> sentinelNodes = Sets.newHashSet(settings.getSentinel().getNodes());

        if (sentinelNodes.isEmpty()) {
            this.jedisPool = new JedisPool(
                    new JedisPoolConfig(),
                    settings.getHost(),
                    settings.getPort(),
                    0,
                    settings.getPassword().isEmpty() ? null : settings.getPassword(),
                    settings.isUseSsl()
            );
            plugin.getLogger().info("Using Redis pool");
        } else {
            this.jedisPool = new JedisSentinelPool(
                    settings.getSentinel().getMasterName(),
                    sentinelNodes,
                    settings.getPassword().isEmpty() ? null : settings.getPassword(),
                    settings.getSentinel().getPassword().isEmpty() ? null : settings.getSentinel().getPassword()
            );
            plugin.getLogger().info("Using Redis Sentinel pool");
        }
    }

    @Override
    public void connect() {

    }

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals("fadsb.skyblock")) {
            return;
        }

        JsonObject data = JsonParser.parseString(message).getAsJsonObject();


    }
}
