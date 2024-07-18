package info.preva1l.fadsb.multiserver;

import lombok.Getter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Getter
public class Messenger implements RedisConnection {
    private final JedisPool jedisPool;

    public Messenger() {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(0);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        this.jedisPool = new JedisPool(config, "127.0.0.1", 6379, 0, "redis password");
    }

    @Override
    public void connect() {

    }

    @Override
    public void destroy() {
        getJedisPool().destroy();
    }
}
