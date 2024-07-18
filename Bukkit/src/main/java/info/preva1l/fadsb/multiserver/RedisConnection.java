package info.preva1l.fadsb.multiserver;

import info.preva1l.fadsb.database.DatabaseConnection;
import redis.clients.jedis.JedisPool;

public interface RedisConnection extends DatabaseConnection {
    JedisPool getJedisPool();
}
