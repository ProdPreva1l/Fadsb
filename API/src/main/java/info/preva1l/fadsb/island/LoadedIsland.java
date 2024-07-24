package info.preva1l.fadsb.island;

import info.preva1l.fadsb.user.User;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
public abstract class LoadedIsland extends Island {
    private final String loadedServer;

    protected LoadedIsland(@Nullable String loadedServer, @NotNull UUID uniqueId, @NotNull User owner, @NotNull IslandType type) {
        super(uniqueId, owner, type);
        this.loadedServer = loadedServer == null ? "Single Server" : loadedServer;
    }
}
