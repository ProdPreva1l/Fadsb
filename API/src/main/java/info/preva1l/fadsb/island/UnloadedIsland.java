package info.preva1l.fadsb.island;

import info.preva1l.fadsb.user.User;

import java.util.UUID;

public abstract class UnloadedIsland extends Island {
    protected UnloadedIsland(UUID uniqueId, User user, IslandType type) {
        super(uniqueId, user, type);
    }

    public abstract LoadedIsland load();
}
