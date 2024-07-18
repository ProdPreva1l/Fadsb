package info.preva1l.fadsb.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {
    private final String username;
    private final UUID uniqueId;

    @NotNull
    public static User of(@NotNull String username, @NotNull UUID uuid) {
        return new User(username, uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        return ((User) obj).getUniqueId().equals(this.uniqueId);
    }
}