package info.preva1l.fadsb.island;

import info.preva1l.fadsb.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class BukkitLoadedIsland extends LoadedIsland {
    public BukkitLoadedIsland(@Nullable String loadedServer, @NotNull UUID uniqueId, @NotNull User owner, @NotNull IslandType type) {
        super(loadedServer, uniqueId, owner, type);
    }
}
