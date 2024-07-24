package info.preva1l.fadsb.island;

import info.preva1l.fadsb.Fadsb;
import info.preva1l.fadsb.user.User;

import java.util.UUID;

public final class BukkitUnloadedIsland extends UnloadedIsland {
    private BukkitUnloadedIsland(UUID uniqueId, User user, IslandType type) {
        super(uniqueId, user, type);
    }

    @Override
    public LoadedIsland load() {
        return new BukkitLoadedIsland(Fadsb.getInstance().getServerManager().serverName(),
                UUID.randomUUID(), User.of("test", UUID.randomUUID()), IslandType.DEFAULT);
    }
}
