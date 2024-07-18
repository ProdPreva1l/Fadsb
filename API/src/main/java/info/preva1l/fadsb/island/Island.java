package info.preva1l.fadsb.island;

import info.preva1l.fadsb.user.User;

import java.util.UUID;

public abstract class Island {
    protected final UUID uniqueId;
    protected final User owner;
    protected final IslandType type;

    protected Island(UUID uniqueId, User owner, IslandType type) {
        this.uniqueId = uniqueId;
        this.owner = owner;
        this.type = type;
    }
}
